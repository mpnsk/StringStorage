package com.github.mpnsk.stringstorage.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * Created by topsykrett on 17.05.16.
 */
public class SqlHelperTest {

    private SqlHelper sqlHelper;

    @Before
    public void setUp() throws Exception {
        InstrumentationRegistry.getTargetContext().deleteDatabase(SqlHelper.NAME);
        sqlHelper = new SqlHelper(InstrumentationRegistry.getTargetContext());
    }

    @Test
    public void testCreateDb() {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        assertTrue(db.isOpen());
        Cursor c = db.rawQuery("PRAGMA table_info(" + StorageitemContract.TABLE_NAME + ")",
                null);
        assertTrue("Could't move Cursor to first.", c.moveToFirst());

        // Where SQLite saves the column names for the table we just queried
        // ie:  table_info(table) = cursor
        //                          cursor[x] = "ColumnX"
        int columnNameIndex = c.getColumnIndex("name");

        HashSet<String> columnNameChecklist = StorageitemContract.getColumns();

        do {
            String columnName = c.getString(columnNameIndex);
            Log.d("SQLite", columnName + " in sql");
            assertTrue(columnNameChecklist.remove(columnName));
        } while (c.moveToNext());

        for (String s : columnNameChecklist) {
            fail(s + " should have been a column in SQLite but wasn't!");
        }
        c.close();
        db.close();
    }

}