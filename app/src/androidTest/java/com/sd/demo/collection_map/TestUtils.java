package com.sd.demo.collection_map;

import static org.junit.Assert.assertEquals;

import com.sd.lib.collection.map.FUniqueMap;
import com.sd.lib.collection.map.IMap;
import com.sd.lib.collection.map.IUniqueMap;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class TestUtils {
    public static void testNormal(IMap<Object, Object> map) {
        map.clear();
        assertEquals(null, map.put("2", "1"));
        assertEquals(1, map.size());
        assertEquals("1", map.get("2"));
        assertEquals(true, map.containsKey("2"));
        assertEquals(true, map.containsValue("1"));

        assertEquals(null, map.put("3", "1"));
        assertEquals(2, map.size());
        assertEquals("1", map.get("2"));
        assertEquals("1", map.get("3"));
        assertEquals(true, map.containsKey("2"));
        assertEquals(true, map.containsKey("3"));
        assertEquals(true, map.containsValue("1"));

        assertEquals("1", map.put("3", "2"));
        assertEquals(2, map.size());
        assertEquals("1", map.get("2"));
        assertEquals("2", map.get("3"));
        assertEquals(true, map.containsKey("2"));
        assertEquals(true, map.containsKey("3"));
        assertEquals(true, map.containsValue("1"));
        assertEquals(true, map.containsValue("2"));

        assertEquals("2", map.remove("3"));
        assertEquals(1, map.size());
        assertEquals("1", map.get("2"));
        assertEquals(null, map.get("3"));
        assertEquals(true, map.containsKey("2"));
        assertEquals(false, map.containsKey("3"));
        assertEquals(true, map.containsValue("1"));
        assertEquals(false, map.containsValue("2"));

        map.clear();
        assertEquals(0, map.size());
        assertEquals(null, map.get("2"));
        assertEquals(null, map.get("3"));
        assertEquals(false, map.containsKey("2"));
        assertEquals(false, map.containsKey("3"));
        assertEquals(false, map.containsValue("1"));
        assertEquals(false, map.containsValue("2"));
    }

    public static void testWeak(IMap<Object, Object> map) {
        map.clear();
        for (int i = 0; i < 10; i++) {
            map.put(new Object(), new Object());
        }
        while (map.size() > 0) {
            System.gc();
            LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1));
        }
        assertEquals(0, map.size());
    }

    public static void testUnique(IUniqueMap<Object, Object> map){
        map.clear();
        assertEquals(null, map.put("2", "1"));
        assertEquals("1", map.put("3", "1"));
        assertEquals(1, map.size());
        assertEquals(null, map.get("2"));
        assertEquals("1", map.get("3"));
        assertEquals("3", map.getKeyByValue("1"));

        map.clear();
        assertEquals(null, map.put("2", "1"));
        assertEquals("1", map.put("2", "1"));
        assertEquals(1, map.size());
        assertEquals("1", map.get("2"));
        assertEquals("2", map.getKeyByValue("1"));

        map.clear();
        assertEquals(null, map.put("2", "1"));
        assertEquals("1", map.put("2", "3"));
        assertEquals(1, map.size());
        assertEquals("3", map.get("2"));
        assertEquals(null, map.getKeyByValue("1"));
        assertEquals("2", map.getKeyByValue("3"));

        map.clear();
        /**
         * 2 -> 1,  1 -> 2
         */
        assertEquals(null, map.put("2", "1"));
        /**
         * 2 -> 1,  1 -> 2
         * 3 -> 2,  2 -> 3
         */
        assertEquals(null, map.put("3", "2"));
        /**
         * 1. put reverse (1, 3) return an old key "2"
         *      2 -> 1,  1 -> 3
         *      3 -> 2,  2 -> 3
         * 2. remove old key "2" from normal map
         *      empty,  1 -> 3
         *      3 -> 2,  2 -> 3
         * 3. put normal (3, 1) return an old value "2"
         *      empty,  1 -> 3
         *      3 -> 1,  2 -> 3
         * 4. remove old value "2" from reverse map
         *      empty,  1 -> 3
         *      3 -> 1,  empty
         * 5. finally
         *      3 -> 1, 1 -> 3
         */
        assertEquals("2", map.put("3", "1"));
        assertEquals(1, map.size());
        assertEquals("1", map.get("3"));
        assertEquals("3", map.getKeyByValue("1"));
        assertEquals(null, map.getKeyByValue("2"));
    }
}
