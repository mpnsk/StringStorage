package com.example.topsy.rackit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by topsy on 9/4/15.
 */
public class RackTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void constructTest() {
        Rack rack = new Rack(3, 4);
        rack.put("test", new RackObject(""));
    }
}