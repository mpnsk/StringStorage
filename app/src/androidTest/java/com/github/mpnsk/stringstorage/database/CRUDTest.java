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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;


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
        assertEquals(readItem.getDescription(), writeItem.getDescription());
        assertEquals(readItem.getLocation(), writeItem.getLocation());
        assertEquals(readItem.getTimestamp(), writeItem.getTimestamp());
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
        Cursor cursor = crud.read(null, StorageitemContract.DESCRIPTION + " = '" + itemValue[0] + "'", null);
        Storageitem storageitem = null;
        if(cursor.moveToFirst()) {
            storageitem = new Storageitem(cursor);
        }else{
            fail("No matching row found!");
        }

        assertEquals("Should be equal, isnt! \"" + storageitem.getLocation() + "\" and \"" + itemValue[0] + "\"",
                storageitem.getDescription(), itemValue[0]);
        assertEquals(storageitem.getLocation(), itemValue[1]);
    }

    @Test
    public void testUpdate() throws Exception {
        Storageitem notUpdated = new Storageitem("notUpdatedDesc", "notUpdatedLoc");
        long rowId = crud.create(notUpdated);
        int rowsAffected = db.update(
                StorageitemContract.TABLE_NAME,
                new Storageitem("updatedDesc", "updatedLoc").toContentvalues(),
                StorageitemContract._ID + " = " + rowId, null);
        if (rowsAffected == 0) {
            fail("update failed");
        }
        Cursor cursor = crud.read(null, StorageitemContract._ID + " LIKE " + rowId, null);

        Storageitem updated = null;
        if(cursor.moveToFirst()){
            updated = new Storageitem(cursor);
        }else{
            fail("No matching row found!");
        }
        assertThat(updated.getDescription(), is(equalTo("updatedDesc")));
        assertThat(updated.getLocation(), is(equalTo("updatedLoc")));
        assertThat(updated.getTimestamp(), not(equalTo(notUpdated.getTimestamp())));
    }
    @Test
    public void testDelete() throws Exception {
        Storageitem item = new Storageitem("desc", "loc");
        long rowId = crud.create(item);
        Cursor cursorUndeleted = crud.read(null, StorageitemContract._ID + " LIKE " + rowId, null);
        assertThat(cursorUndeleted.moveToFirst(), is(true));
        int deltedRows = crud.delete(StorageitemContract._ID + " LIKE " + rowId, null);
        assertThat(deltedRows, is(greaterThan(0)));
        Cursor cursorDeleted = crud.read(null, StorageitemContract._ID + " LIKE " + rowId, null);
        assertThat(cursorDeleted.moveToFirst(), is(not(true)));
            }
    @After
    public void tearDown() throws Exception {
        targetContext.deleteDatabase(SqlHelper.NAME);
    }
}