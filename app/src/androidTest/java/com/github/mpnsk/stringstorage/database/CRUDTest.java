package com.github.mpnsk.stringstorage.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;

import com.github.mpnsk.stringstorage.Storageitem;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by topsykrett on 23.05.16.
 */
public class CRUDTest {
    private SqlHelper sqlHelper;
    private CRUD crud;
    private Context targetContext;
    @Before
    public void setUp() throws Exception {
        targetContext = InstrumentationRegistry.getTargetContext();
        crud = new CRUD(targetContext);
        sqlHelper = new SqlHelper(targetContext);
    }
    @Test
    public void testRead() throws Exception {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();

        String[] itemValue = new String[]{"Buch", "Fach3"};
        ContentValues values = new ContentValues();
        values.put(StorageitemContract.DESCRIPTION, itemValue[0]);
        values.put(StorageitemContract.LOCATION, itemValue[1]);

        db.insert(StorageitemContract.TABLE_NAME, null, values);
        Storageitem storageitem = crud.read(itemValue[0]);
        assertEquals("Should be equal, isnt! \"" + storageitem.description + "\" and \"" + itemValue[0] + "\"",
                storageitem.description, itemValue[0]);
        assertEquals(storageitem.location, itemValue[1]);
    }
    @After
    public void tearDown() throws Exception {
        targetContext.deleteDatabase(SqlHelper.NAME);
    }
}