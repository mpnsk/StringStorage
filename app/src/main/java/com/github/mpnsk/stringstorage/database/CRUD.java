package com.github.mpnsk.stringstorage.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.github.mpnsk.stringstorage.Storageitem;

public class CRUD {
    private Context context;
    private SQLiteDatabase db;

    private CRUD() {
    }

    public CRUD(Context context) {
        this.context = context;
    }

    /**
     * Creates the argument Storageitem in SQLite.
     * <p>Instantiates it as ContentValues thorugh .getContentvalues first.</p>
     *
     * @param item
     * @return The RowId in SQLite, -1 if it fails.
     */
    public long create(Storageitem item) {
        ContentValues values = item.toContentvalues();
        db = new SqlHelper(context).getWritableDatabase();
        return db.insert(StorageitemContract.TABLE_NAME, null, values);
    }

    /**
     *  Wraps SQLiteDatabase.query(), which wraps a SQL statement.
     * @param selectColumns Is interpreted as "*" when null!
     * @param where select X from Y "WHERE Z LIKE ?" without the "where"
     * @param whereArgs fits into the "WHERE Z LIKE ?" part substituting every ?
     * @return TODO still only returns the first row found..
     * @throws Exception
     */
    public Cursor read(String[] selectColumns, String where, String[] whereArgs) throws Exception {
        db = new SqlHelper(context).getReadableDatabase();
        Cursor cursor = db.query(StorageitemContract.TABLE_NAME, selectColumns,
                where, whereArgs,
                null, null, null);
//        Storageitem storageitem;
//        if (cursor.moveToFirst()) {
//            storageitem = new Storageitem(
//                    cursor.getString(cursor.getColumnIndex(StorageitemContract.DESCRIPTION)),
//                    cursor.getString(cursor.getColumnIndex(StorageitemContract.LOCATION)),
//                    cursor.getString(cursor.getColumnIndex(StorageitemContract.TIMESTAMP)));
//        } else {
//            throw new Exception("No item found.");
//        }
//        cursor.close();
                return cursor;
    }


    public int delete(String where, String[] whereArgs) {
        return db.delete(StorageitemContract.TABLE_NAME, where, whereArgs);
    }
}
