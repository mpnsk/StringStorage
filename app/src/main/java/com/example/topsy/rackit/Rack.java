package com.example.topsy.rackit;

import java.util.HashMap;

/**
 * Created by topsy on 9/4/15.
 */
public class Rack extends HashMap<String, RackObject> {
    private final int width;
    private final int height;

    public Rack(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public RackObject put(String key, RackObject value) {
        System.out.println("OVERRIDE PUT!");
        return super.put(key, value);
    }

}
