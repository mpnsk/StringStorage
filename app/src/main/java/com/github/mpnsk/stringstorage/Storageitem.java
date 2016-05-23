package com.github.mpnsk.stringstorage;

import android.content.ContentValues;

import com.github.mpnsk.stringstorage.database.StorageitemContract;

import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by topsykrett on 23.05.16.
 */
public class Storageitem {

    public String description;
    public String location;
    public String timestamp;

    public Storageitem(String description, String location) {
        this.description = description;
        this.location = location;

        long now = Calendar.getInstance().getTime().getTime();
        String timestamp = new Timestamp(now).toString();
        this.timestamp = timestamp;
    }

    public Storageitem(String description, String location, String timestamp) {
        this.description = description;
        this.location = location;
        this.timestamp = timestamp;
    }

    public ContentValues toContentvalues() {
        ContentValues values = new ContentValues();
        values.put(StorageitemContract.DESCRIPTION, description);
        values.put(StorageitemContract.LOCATION, location);
        values.put(StorageitemContract.TIMESTAMP, timestamp);
        return values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Storageitem that = (Storageitem) o;

        if (!description.equals(that.description)) return false;
        if (!location.equals(that.location)) return false;
        return timestamp != null ? timestamp.equals(that.timestamp) : that.timestamp == null;

    }

    @Override
    public int hashCode() {
        int result = description.hashCode();
        result = 31 * result + location.hashCode();
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        return result;
    }
}
