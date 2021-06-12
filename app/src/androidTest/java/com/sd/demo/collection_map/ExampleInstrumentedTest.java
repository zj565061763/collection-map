package com.sd.demo.collection_map;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.sd.lib.collection.map.IMap;
import com.sd.lib.collection.map.IUniqueMap;
import com.sd.lib.collection.map.impl.FHashMap;
import com.sd.lib.collection.map.impl.FUniqueMap;
import com.sd.lib.collection.map.impl.FWeakKeyMap;
import com.sd.lib.collection.map.impl.FWeakValueMap;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private static final String ONE = "1";

    @Test
    public void test_Map() {
        testNormalMap(new FHashMap<>());
        testNormalMap(new FWeakKeyMap<>());
        testNormalMap(new FWeakValueMap<>());
    }

    @Test
    public void test_WeakKeyMap() {
        testWeakKeyMap(new FWeakKeyMap<>());
    }

    @Test
    public void test_WeakValueMap() {
    }

    @Test
    public void test_uniqueMap() {
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

    private void testNormalMap(IMap<Object, Object> map) {
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

    private void testWeakKeyMap(IMap<Object, Object> map) {
        assertEquals(null, map.put(ONE, "1"));
        assertEquals(null, map.put("2", "2"));
        assertEquals(2, map.size());
        assertEquals("1", map.get(ONE));
        assertEquals("2", map.get("2"));
        assertEquals(true, map.containsKey(ONE));
        assertEquals(true, map.containsKey("2"));
    }
}