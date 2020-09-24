package com.sd.collection_map;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.sd.lib.collection.map.impl.FUniqueMap;
import com.sd.lib.collection.map.impl.FWeakKeyUniqueMap;
import com.sd.lib.collection.map.impl.FWeakValueUniqueMap;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest
{
    @Test
    public void useAppContext()
    {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.sd.collection_map", appContext.getPackageName());
    }

    @Test
    public void test_map()
    {
        final DataModel model1 = new DataModel(new String("1"));
        final DataModel model2 = new DataModel(new String("1"));

        assert(model1 == model2);

        final Map<Object, Object> map = new HashMap<>();
        final Object putResult1 = map.put("a", model1);
        final Object putResult2 = map.put("a", model2);

        assertEquals(null, putResult1);

        assertEquals(model1, putResult2);
        assertEquals(model2, putResult2);

        assert(model1 == putResult2);
        assert(model2 == putResult2);
    }

    @Test
    public void test_FUniqueMap()
    {
        final FUniqueMap<Object, Object> map = new FUniqueMap<>();
        assertEquals(null, map.put("a", "1"));
        assertEquals(null, map.put("b", "1"));
        assertEquals(null, map.put("c", "1"));
        assertEquals("1", map.put("c", "1"));

        assertEquals(1, map.size());
        assertEquals("1", map.get("c"));
        assertEquals("c", map.getKeyByValue("1"));
        assertEquals("c", map.removeByValue("1"));
        assertEquals(0, map.size());
    }

    @Test
    public void test_FWeakKeyUniqueMap()
    {
        final FWeakKeyUniqueMap<Object, Object> map = new FWeakKeyUniqueMap<>();
        assertEquals(null, map.put("a", "1"));
        assertEquals(null, map.put("b", "1"));
        assertEquals(null, map.put("c", "1"));
        assertEquals("1", map.put("c", "1"));

        assertEquals(1, map.size());
        assertEquals("1", map.get("c"));
        assertEquals("c", map.getKeyByValue("1"));
        assertEquals("c", map.removeByValue("1"));
        assertEquals(0, map.size());
    }

    @Test
    public void test_FWeakValueUniqueMap()
    {
        final FWeakValueUniqueMap<Object, Object> map = new FWeakValueUniqueMap<>();
        assertEquals(null, map.put("a", "1"));
        assertEquals(null, map.put("b", "1"));
        assertEquals(null, map.put("c", "1"));
        assertEquals("1", map.put("c", "1"));

        assertEquals(1, map.size());
        assertEquals("1", map.get("c"));
        assertEquals("c", map.getKeyByValue("1"));
        assertEquals("c", map.removeByValue("1"));
        assertEquals(0, map.size());
    }
}