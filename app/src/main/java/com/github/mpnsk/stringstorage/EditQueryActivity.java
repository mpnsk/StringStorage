package com.github.mpnsk.stringstorage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
    private List<String> itemNames;
    private List<String> itemLocations;
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

        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");

        itemNames = new ArrayList<>(list);
        itemLocations = new ArrayList<>(list);
        itemNameAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,
                itemNames);
        itemLocationAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,
                itemLocations);
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
                String currentName = EditQueryActivity.this.itemName.getText().toString();
                if (itemLocations.contains(currentName)) {
                    itemLocation.setText(currentName);
                } else {
                    itemLocation.setText("");
                }
            }
        });

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
