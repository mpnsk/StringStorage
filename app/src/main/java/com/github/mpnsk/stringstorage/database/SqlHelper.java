package com.github.mpnsk.stringstorage.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlHelper extends SQLiteOpenHelper {

    public static final String NAME = "stringstorage";
    public static final int VERSION = 1;

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE `" + StorageitemContract.TABLE_NAME + "` (\n" +
                    "  `" + StorageitemContract._ID + "` INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "  `" + StorageitemContract.DESCRIPTION + "` TEXT NOT NULL,\n" +
                    "  `" + StorageitemContract.LOCATION + "` TEXT NOT NULL,\n" +
                    "  `" + StorageitemContract.TIMESTAMP + "` DATETIME DEFAULT CURRENT_TIMESTAMP\n" +
                    ");\n";
    private static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + StorageitemContract.TABLE_NAME;


    public SqlHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
