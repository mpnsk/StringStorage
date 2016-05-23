package com.github.mpnsk.stringstorage.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;

import com.github.mpnsk.stringstorage.Storageitem;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

/**
 * Created by topsykrett on 23.05.16.
 */
public class CRUDTest {
    private SqlHelper sqlHelper;
    private CRUD crud;
    private Context targetContext;
    private SQLiteDatabase db;

    @Before
    public void setUp() throws Exception {
        targetContext = InstrumentationRegistry.getTargetContext();
        crud = new CRUD(targetContext);
        sqlHelper = new SqlHelper(targetContext);
        db = sqlHelper.getWritableDatabase();
    }

    @Test
    public void testCreate() {
        String[] itemValue = new String[]{"Dose", "Fach4"};
        Storageitem writeItem = new Storageitem(itemValue[0], itemValue[1]);
        ContentValues values = writeItem.toContentvalues();
        long rowId = crud.create(writeItem);

        Storageitem readItem = null;
        Cursor cursor = db.query(StorageitemContract.TABLE_NAME, null,
                StorageitemContract._ID + " = " + rowId, null,
                null, null, null);
        if (cursor.moveToFirst()) {
            readItem = new Storageitem(
                    cursor.getString(cursor.getColumnIndex(StorageitemContract.DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(StorageitemContract.LOCATION)),
                    cursor.getString(cursor.getColumnIndex(StorageitemContract.TIMESTAMP)));
        } else {
            fail("Could not find item. Was it created?");
        }
        assertEquals(readItem.description, writeItem.description);
        assertEquals(readItem.location, writeItem.location);
        assertEquals(readItem.timestamp, writeItem.timestamp);
        assertEquals(readItem, writeItem);
        cursor.close();
    }

    @Test
    public void testRead() throws Exception {
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