package com.sd.demo.collection_map;

import static org.junit.Assert.assertEquals;

import com.sd.lib.collection.map.IMap;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class TestUtils {
    public static void testNormal(IMap<Object, Object> map) {
        assertEquals(null, map.put("2", "1"));
        assertEquals(1, map.size());
        assertEquals("1", map.get("2"));
        assertEquals(true, map.containsKey("2"));

        assertEquals(null, map.put("3", "1"));
        assertEquals(2, map.size());
        assertEquals("1", map.get("2"));
        assertEquals("1", map.get("3"));
        assertEquals(true, map.containsKey("2"));
        assertEquals(true, map.containsKey("3"));

        assertEquals("1", map.put("3", "2"));
        assertEquals(2, map.size());
        assertEquals("1", map.get("2"));
        assertEquals("2", map.get("3"));
        assertEquals(true, map.containsKey("2"));
        assertEquals(true, map.containsKey("3"));

        assertEquals("2", map.remove("3"));
        assertEquals(1, map.size());
        assertEquals("1", map.get("2"));
        assertEquals(null, map.get("3"));
        assertEquals(true, map.containsKey("2"));
        assertEquals(false, map.containsKey("3"));

        map.clear();
        assertEquals(0, map.size());
        assertEquals(null, map.get("2"));
        assertEquals(null, map.get("3"));
        assertEquals(false, map.containsKey("2"));
        assertEquals(false, map.containsKey("3"));
    }

    public static void testWeak(IMap<Object, Object> map) {
        for (int i = 0; i < 10; i++) {
            map.put(new Object(), new Object());
        }
        while (map.size() > 0) {
            System.gc();
            LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1));
        }
        assertEquals(0, map.size());
    }
}
