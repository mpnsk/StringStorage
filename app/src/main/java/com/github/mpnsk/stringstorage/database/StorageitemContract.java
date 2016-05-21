package com.github.mpnsk.stringstorage.database;

import android.provider.BaseColumns;

import java.util.HashSet;

/**
 * Contract for the Storageitems that SQLite will swallow.
 */
public final class StorageitemContract implements BaseColumns {
    public StorageitemContract() {
    }

    public static final String TABLE_NAME = "storageitem";
    public static final String DESCRIPTION = "description";
    public static final String LOCATION = "location";
    public static final String TIMESTAMP = "timestamp";

    /**
     * Returns a HashSet of all the columns in the contract. For the lazy.
     * @return A hardcoded Set. Don't expect too much.
     */
    public static HashSet<String> getColumns(){
        HashSet<String> columns = new HashSet<>();
        columns.add(_ID);
        columns.add(DESCRIPTION);
        columns.add(LOCATION);
        columns.add(TIMESTAMP);
        return columns;
    }
}
