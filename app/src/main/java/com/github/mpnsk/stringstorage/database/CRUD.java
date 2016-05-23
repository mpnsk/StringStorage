package com.github.mpnsk.stringstorage.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.github.mpnsk.stringstorage.Storageitem;

/**
 * Created by topsykrett on 23.05.16.
 */
public class CRUD {
    private Context context;
    private SQLiteDatabase db;

    public CRUD() {
    }

    public CRUD(Context context) {
        this.context = context;
    }

    /**
     * Creates the argument Storageitem in SQLite.
     * <p>Instantiates it as ContentValues thorugh .getContentvalues first.</p>
     * @param item
     * @return The RowId in SQLite, -1 if it fails.
     */
    public long create(Storageitem item) {
        ContentValues values = item.toContentvalues();
        db = new SqlHelper(context).getWritableDatabase();
        return db.insert(StorageitemContract.TABLE_NAME, null, values);
    }

    public Storageitem read(String byDescription) throws Exception {
        db = new SqlHelper(context).getReadableDatabase();
        Cursor cursor = db.query(StorageitemContract.TABLE_NAME,
//                new String[]{"description"},
                null,
//                "description LIKE ?",
                null,
//                new String[]{byDescription},
                null,
                null,
                null,
                null);
        String location;
        Storageitem storageitem = null;
        if (cursor.moveToFirst()) {
            storageitem = new Storageitem(
                    cursor.getString(cursor.getColumnIndex(StorageitemContract.DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(StorageitemContract.LOCATION)),
                    cursor.getString(cursor.getColumnIndex(StorageitemContract.TIMESTAMP)));
        } else {
            throw new Exception("No item found.");
        }
        cursor.close();
        db.close();
        return storageitem;
    }
}
