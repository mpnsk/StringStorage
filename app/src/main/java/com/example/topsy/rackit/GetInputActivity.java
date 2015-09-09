package com.example.topsy.rackit;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class GetInputActivity extends AppCompatActivity {
    public static final String ITEM_NAME = "com.example.topsy.rackit.ITEM_NAME";
    ListView resultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getInput();
        setContentView(R.layout.activity_get_input);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_get_input, menu);
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

    public void getInput() {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        //i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
        try {
            startActivityForResult(i, 1);
        } catch (Exception e) {
            Toast.makeText(this, "Error initializing speech to text engine.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            final ArrayList<String> thingsYouSaid = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            resultList = (ListView) findViewById(R.id.resultList);
            resultList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, thingsYouSaid));
            resultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                  @Override
                                                  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                      Intent intent = new Intent(GetInputActivity.this, EditQueryActivity.class);
                                                      intent.putExtra(GetInputActivity.ITEM_NAME, thingsYouSaid.get(position).toLowerCase());
                                                      Toast.makeText(GetInputActivity.this.getApplicationContext(),
                                                              "Click ListItem Number " + position + "\nsaid:\"" + thingsYouSaid.get(position) + "\"", Toast.LENGTH_LONG)
                                                              .show();
                                                      startActivity(intent);
                                                  }
                                              }
            );

            Log.d("test", "onActivityResult ");
        }
    }

}
