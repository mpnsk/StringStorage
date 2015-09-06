package com.example.topsy.rackit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class RackObjectTest {

    Random rand = new Random();

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void constructorTest() {
        int a = rand.nextInt();
        int b = rand.nextInt();
        RackObject so = new RackObject("some String");
        RackObject soPositioned = new RackObject("positioned Object", a, b);
        assertEquals(soPositioned.getxPosition(), a);
        assertEquals(soPositioned.getyPosition(), b);
    }

    @Test
    public void eqqualsTest() {
        RackObject so1 = new RackObject("box", 1, 2);
        RackObject so2 = new RackObject("piano", 1, 2);
        RackObject so3 = new RackObject("piano", 1, 3);
        assertFalse(so1.equals(so2));
        assertFalse(so1.equals(so3));
        assertFalse(so2.equals(so3));
    }
}