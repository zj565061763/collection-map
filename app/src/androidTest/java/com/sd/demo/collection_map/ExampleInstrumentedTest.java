package com.sd.demo.collection_map;

import static org.junit.Assert.assertEquals;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.sd.lib.collection.map.IMap;
import com.sd.lib.collection.map.IUniqueMap;
import com.sd.lib.collection.map.impl.FMap;
import com.sd.lib.collection.map.impl.FUniqueMap;
import com.sd.lib.collection.map.impl.FWeakKeyMap;
import com.sd.lib.collection.map.impl.FWeakValueMap;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private static final String ONE = "1";

    @Test
    public void testNormal() {
        testNormal(new FMap<>());
        testNormal(new FWeakKeyMap<>());
        testNormal(new FWeakValueMap<>());
    }

    @Test
    public void testWeakKeyMap() {
        final IMap<Object, Object> map = new FWeakKeyMap<>();
        for (int i = 0; i < 10; i++) {
            map.put(new Object(), new Object());
        }

        while (map.size() > 0) {
            System.gc();
            LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1));
        }
        assertEquals(0, map.size());
    }

    @Test
    public void testWeakValueMap() {
        final IMap<Object, Object> map = new FWeakValueMap<>();
        for (int i = 0; i < 10; i++) {
            map.put(new Object(), new Object());
        }

        while (map.size() > 0) {
            System.gc();
            LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1));
        }
        assertEquals(0, map.size());
    }

    @Test
    public void testUniqueMap() {
        IUniqueMap<Object, Object> map = null;

        map = new FUniqueMap<>();
        assertEquals(null, map.put("2", "1"));
        assertEquals("1", map.put("3", "1"));
        assertEquals(1, map.size());
        assertEquals(null, map.get("2"));
        assertEquals("1", map.get("3"));
        assertEquals("3", map.getKeyByValue("1"));

        map = new FUniqueMap<>();
        assertEquals(null, map.put("2", "1"));
        assertEquals("1", map.put("2", "1"));
        assertEquals(1, map.size());
        assertEquals("1", map.get("2"));
        assertEquals("2", map.getKeyByValue("1"));

        map = new FUniqueMap<>();
        assertEquals(null, map.put("2", "1"));
        assertEquals("1", map.put("2", "3"));
        assertEquals(1, map.size());
        assertEquals("3", map.get("2"));
        assertEquals(null, map.getKeyByValue("1"));
        assertEquals("2", map.getKeyByValue("3"));

        map = new FUniqueMap<>();
        assertEquals(null, map.put("2", "1"));
        assertEquals(null, map.put("3", "2"));
        assertEquals("2", map.put("3", "1"));
        assertEquals(1, map.size());
        assertEquals("1", map.get("3"));
        assertEquals("3", map.getKeyByValue("1"));
        assertEquals(null, map.getKeyByValue("2"));
    }

    private void testNormal(IMap<Object, Object> map) {
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
}