package com.example.topsy.rackit;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditQueryActivity extends AppCompatActivity {
    EditText itemName, itemLocation;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_query);


        itemName = (EditText) findViewById(R.id.item_name);
        itemName.setText(getIntent().getStringExtra(GetInputActivity.ITEM_NAME));
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
                if (save.contains(EditQueryActivity.this.itemName.getText().toString())) {
                    EditQueryActivity.this.itemLocation.setText(save.getString(EditQueryActivity.this.itemName.getText().toString(), "null?"));
                }
            }
        });

    }

    public void saveItem(View view) {
        SharedPreferences save = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = save.edit();
        itemName = (EditText) findViewById(R.id.item_name);
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
