package com.github.mpnsk.stringstorage;

import com.github.mpnsk.stringstorage.persistence.StorageItem;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;


public class PersistenceTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    Realm realm;

    @Before
    public void setUp() throws Exception {
        File tempFolder = testFolder.newFolder("realmdata");
        RealmConfiguration config = new RealmConfiguration.Builder(tempFolder).build();
        realm = Realm.getInstance(config);
    }

    @Test
    public void testCrud() {
        RealmResults<StorageItem> noResult = realm.where(StorageItem.class).findAll();
        assertThat(noResult.size(), is(0));
        realm.beginTransaction();
        StorageItem item = realm.createObject(StorageItem.class);
        item.setDescription("Desc");
        item.setLocation("Loc");
        realm.commitTransaction();
        RealmResults<StorageItem> someResult = realm.where(StorageItem.class).findAll();
        assertThat(someResult.first(), equalTo(item));

    }

    @After
    public void tearDown() throws Exception {

    }
}