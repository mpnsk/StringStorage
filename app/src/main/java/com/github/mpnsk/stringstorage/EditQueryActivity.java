package com.github.mpnsk.stringstorage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.github.mpnsk.stringstorage.persistence.FilteringStorageitemAdapter;
import com.github.mpnsk.stringstorage.persistence.Storageitem;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;

public class EditQueryActivity extends AppCompatActivity {
    @Inject
    public Realm realm;
    AutoCompleteTextView itemNameTextbox;
    AutoCompleteTextView itemLocationTextbox;
    private List<String> itemNames;
    private List<String> itemLocations;
    private RealmResults<Storageitem> allItems;
    private RealmResults<Storageitem> allItemsDistinctName;
    private FilteringStorageitemAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication application = (MyApplication) getApplication();
        application.getComponent().inject(this);

        setContentView(R.layout.activity_edit_query);

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());

        loadStorageitems();

        allItems = realm.where(Storageitem.class).findAll();
        adapter = new FilteringStorageitemAdapter(this, allItems);

        itemNameTextbox = (AutoCompleteTextView) findViewById(R.id.edit_item_name);
        ArrayAdapter<String> itemNameadapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, itemNames);
        itemNameTextbox.setAdapter(itemNameadapter);

        itemNameTextbox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(itemNameTextbox.getText().toString());
            }
        });


        itemLocationTextbox = (AutoCompleteTextView) findViewById(R.id.edit_item_location);
        ArrayAdapter<String> itemLocationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, itemLocations);
        itemLocationTextbox.setAdapter(itemLocationAdapter);

        ListView listView = (ListView) findViewById(R.id.listview);
        assert listView != null;
        listView.setAdapter(adapter);
        listView.setDivider(null);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showPopup(view, position);
            }
        });

        adapter.getFilter().filter("");
    }

    public void showPopup(View v, final int position) {
        PopupMenu popup = new PopupMenu(this, v);
        Menu menu = popup.getMenu();

        menu.add("edit " + adapter.getItem(position).getDescription());
        menu.getItem(0).setOnMenuItemClickListener(getMenuItemClickListener(position));

        menu.add("edit " + adapter.getItem(position).getLocation());
        menu.getItem(1).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditQueryActivity.this);
                final EditText input = new EditText(EditQueryActivity.this);
                input.setText(adapter.getItem(position).getLocation());
                builder.setView(input);
                builder.setTitle("Location");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                Storageitem item = adapter.getItem(position);
                                item.setLocation(input.getText().toString());
                            }
                        });
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
                return false;
            }
        });
        menu.add("delete");
        menu.getItem(2).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Storageitem deleteItem = adapter.getItem(position);
                        Toast.makeText(getBaseContext(), "deleting " + deleteItem.toString() + " ...", Toast.LENGTH_SHORT).show();
                        deleteItem.deleteFromRealm();
                        Toast.makeText(getBaseContext(), "Success!", Toast.LENGTH_SHORT).show();
                    }
                });
                return false;
            }
        });
        popup.show();
//        realm.close();
    }

    @NonNull
    private MenuItem.OnMenuItemClickListener getMenuItemClickListener(final int position) {
        return new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditQueryActivity.this);
                final EditText input = new EditText(EditQueryActivity.this);


                input.setText(adapter.getItem(position).getDescription());
                builder.setView(input);
                builder.setTitle("Description");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                Storageitem item = adapter.getItem(position);
                                item.setDescription(input.getText().toString());
                            }
                        });
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
                return false;
            }
        };
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
        itemNames = new ArrayList<>();
        itemLocations = new ArrayList<>();
        RealmResults<Storageitem> allItems = realm.where(Storageitem.class).findAll();
        allItemsDistinctName = allItems.distinct("description");
        for (Storageitem item :
                allItemsDistinctName) {
            itemNames.add(item.getDescription());
            itemLocations.add(item.getLocation());
        }

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
}
