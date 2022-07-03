package com.sd.demo.collection_map;

import static org.junit.Assert.assertEquals;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.sd.lib.collection.map.FMap;
import com.sd.lib.collection.map.FUniqueMap;
import com.sd.lib.collection.map.IUniqueMap;
import com.sd.lib.collection.map.weak.FWeakKeyMap;
import com.sd.lib.collection.map.weak.FWeakMap;
import com.sd.lib.collection.map.weak.FWeakValueMap;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void testNormal() {
        TestUtils.testNormal(new FMap<>());
        TestUtils.testNormal(new FWeakKeyMap<>());
        TestUtils.testNormal(new FWeakValueMap<>());
        TestUtils.testNormal(new FWeakMap<>());
    }

    @Test
    public void testWeakKeyMap() {
        TestUtils.testWeak(new FWeakKeyMap<>());
    }

    @Test
    public void testWeakValueMap() {
        TestUtils.testWeak(new FWeakValueMap<>());
    }

    @Test
    public void testWeakMap() {
        TestUtils.testWeak(new FWeakMap<>());
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