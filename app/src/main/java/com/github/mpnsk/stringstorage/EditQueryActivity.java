package com.github.mpnsk.stringstorage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EditQueryActivity extends AppCompatActivity {
    AutoCompleteTextView itemName;
    AutoCompleteTextView itemLocation;
    private List<String> storedNames;
    private List<String> storedLocations;
    private TheBackupAgent theBackupAgent;
    private ArrayAdapter<String> itemNameAdapter;
    private ArrayAdapter<String> itemLocationAdapter;


    public void getSpeechInput(View view) {
        int requestCode = view.getId() % 10;
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        //i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
        try {
            startActivityForResult(i, requestCode);
        } catch (Exception e) {
            Toast.makeText(this, "Error initializing speech to text engine.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_query);

        theBackupAgent = new TheBackupAgent();
        int success = theBackupAgent.requestRestore(this);
        Log.d(Util.logKey, "requestRestore() = " + Integer.toString(success));

        SharedPreferences save = getSharedPreferences(TheBackupAgent.PREFS_STRINGS, MODE_PRIVATE);
//        save.getAll();
        //String[] allKeys = new String[save.getAll().keySet().size()];
        //String[] allValues = new String[save.getAll().values().size()];
        //allValues = save.getAll().values().toArray(allValues);
        //allKeys = save.getAll().keySet().toArray(allKeys);
        storedNames = new ArrayList<>(save.getAll().keySet());
        storedLocations = new ArrayList(save.getAll().values());
        itemNameAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,
                storedNames);
        itemLocationAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,
                storedLocations);
        itemName = (AutoCompleteTextView) findViewById(R.id.item_name);
        itemLocation = (AutoCompleteTextView) findViewById(R.id.item_location);
        itemName.setAdapter(itemNameAdapter);
        itemLocation.setAdapter(itemLocationAdapter);

        itemName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                SharedPreferences save =
//                        getSharedPreferences(TheBackupAgent.PREFS_STRINGS, MODE_PRIVATE);
                String currentName = EditQueryActivity.this.itemName.getText().toString();
                if (storedLocations.contains(currentName)) {
                    itemLocation.setText(currentName);
                } else {
                    itemLocation.setText("");
                }
            }
        });

    }

    public void saveItem(View view) {
        SharedPreferences save = getSharedPreferences(TheBackupAgent.PREFS_STRINGS, MODE_PRIVATE);
        SharedPreferences.Editor editor = save.edit();
        editor.putString(itemName.getText().toString(), itemLocation.getText().toString());

        // the map needs the set updated for the autocomplete field
        storedNames.add(itemName.getText().toString());
        storedLocations.add(itemLocation.getText().toString());
        editor.apply();
        theBackupAgent.requestBackup(this);

        Runnable run = new Runnable() {
            @Override
            public void run() {
                update();
            }
        };
        new Thread(run).start();
    }

    public void update() {
        itemNameAdapter.notifyDataSetChanged();
        itemLocationAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_query, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            final ArrayList<String> thingsYouSaid = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Pick a color");
            builder.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, thingsYouSaid), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (requestCode == R.id.micItemName % 10) {
                        itemName.setText(thingsYouSaid.get(which));
                    } else if (requestCode == R.id.micItemLocation % 10) {
                        itemLocation.setText(thingsYouSaid.get(which));
                    }

                    Log.d("REQ+RES", requestCode + "+" + resultCode);
                }
            });
            builder.show();

            Log.d("test", "onActivityResult ");
        }
    }

}
