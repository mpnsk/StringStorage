package com.github.mpnsk.stringstorage;

import java.util.Date;

import io.realm.RealmObject;

public class Storageitem extends RealmObject {


    private String description;
    private String location;
    private long timestamp;

    public Storageitem() {
        this.timestamp = new Date().getTime();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
