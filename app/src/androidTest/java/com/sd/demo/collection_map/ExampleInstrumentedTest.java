package com.sd.demo.collection_map;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.sd.lib.collection.map.FMap;
import com.sd.lib.collection.map.FUniqueMap;
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
        TestUtils.testUnique(new FUniqueMap<>());
    }
}