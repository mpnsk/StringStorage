package com.github.mpnsk.stringstorage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class EditQueryActivity extends AppCompatActivity {
    AutoCompleteTextView itemNameTextbox;
    AutoCompleteTextView itemLocationTextbox;
    private List<String> itemNames;
    private List<String> itemLocations;
    private RealmResults<Storageitem> allItems;
    private Realm realm;


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

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());

        realm = Realm.getInstance(new RealmConfiguration.Builder(this).build());


        itemNameTextbox = (AutoCompleteTextView) findViewById(R.id.item_name);
        ArrayAdapter<String> itenNameadapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, itemNames);
        itemNameTextbox.setAdapter(itenNameadapter);

        itemLocationTextbox = (AutoCompleteTextView) findViewById(R.id.item_location);
        ArrayAdapter<String> itemLocationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, itemLocations);
        itemLocationTextbox.setAdapter(itemLocationAdapter);

        allItems = realm.where(Storageitem.class).findAll();

        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(new StorageitemAdapter(this, allItems));

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
                        itemNameTextbox.setText(thingsYouSaid.get(which));
                    } else if (requestCode == R.id.micItemLocation % 10) {
                        itemLocationTextbox.setText(thingsYouSaid.get(which));
                    }

                    Log.d("REQ+RES", requestCode + "+" + resultCode);
                }
            });
            builder.show();

            Log.d("test", "onActivityResult ");
        }
    }

    private void loadStorageitems() {
        RealmConfiguration config = new RealmConfiguration.Builder(this).build();
        Realm realm = Realm.getInstance(config);
        allItems = realm.where(Storageitem.class).findAll();
        for (Storageitem item :
                allItems) {
            itemNames.add(item.getDescription());
            itemLocations.add(item.getLocation());
        }
        allItems.addChangeListener(new RealmChangeListener<RealmResults<Storageitem>>() {
            @Override
            public void onChange(RealmResults<Storageitem> element) {
                Log.d("RealmResult", element.toString() + " changed!");
            }
        });
        realm.close();
    }

    public void persistThisStorageitem(View view) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Storageitem storageitem = realm.createObject(Storageitem.class);
                storageitem.setDescription(itemNameTextbox.getText().toString());
                storageitem.setLocation(itemLocationTextbox.getText().toString());
            }
        });


    }
}
