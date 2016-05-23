package com.github.mpnsk.stringstorage;

/**
 * Created by topsykrett on 23.05.16.
 */
public class Storageitem {

    public String description;
    public String location;
    public String timestamp;

    public Storageitem(String description, String location, String timestamp) {
        this.description = description;
        this.location = location;
        this.timestamp = timestamp;
    }
}
