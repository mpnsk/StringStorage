package com.example.topsy.rackit;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

public class EditQueryActivity extends AppCompatActivity {
    AutoCompleteTextView itemName;
    EditText itemLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_query);


        itemName = (AutoCompleteTextView) findViewById(R.id.item_name);
        itemName.setText(getIntent().getStringExtra(GetInputActivity.ITEM_NAME));

        SharedPreferences save = getPreferences(MODE_PRIVATE);
        String[] allKeys = new String[save.getAll().keySet().size()];
        allKeys = save.getAll().keySet().toArray(allKeys);
        for (String key : allKeys) {
            Log.e("STRINGMAP ENTHAELT:", key + "!!!!!");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, allKeys);
        itemName.setAdapter(adapter);

        itemName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                SharedPreferences save = getPreferences(MODE_PRIVATE);
                String currentName = EditQueryActivity.this.itemName.getText().toString();
                if (save.contains(currentName)) {
                    Log.e("STRING FOUND IN MAP", "ITS" + currentName + "!!!!!");
                    Log.e("VALUE FOUND IN MAP", "ITS" + save.getString(currentName, "lol") + "!!!!!");
                    itemLocation = (EditText) findViewById(R.id.item_location);
                    itemLocation.setText(save.getString(currentName, "defaulValue"));
                }
            }
        });

    }

    public void saveItem(View view) {
        SharedPreferences save = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = save.edit();
        itemName = (AutoCompleteTextView) findViewById(R.id.item_name);
        itemLocation = (EditText) findViewById(R.id.item_location);
        editor.putString(itemName.getText().toString(), itemLocation.getText().toString());
        editor.commit();

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
}
