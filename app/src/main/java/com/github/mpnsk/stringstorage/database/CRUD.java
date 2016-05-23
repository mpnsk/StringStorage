package com.github.mpnsk.stringstorage.database;

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

    public CRUD(){}
    public CRUD(Context context) {
        this.context = context;
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
        Storageitem storageitem;
        if(cursor.moveToFirst()){
            storageitem = new Storageitem(cursor.getString(1),
                    cursor.getString(2), cursor.getString(3));
        }else{
            throw new Exception("no item for description found");
        }
        cursor.close();
        db.close();
        return storageitem;
    }
}
