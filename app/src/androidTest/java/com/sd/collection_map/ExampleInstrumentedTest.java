package com.sd.collection_map;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.sd.lib.collection.map.impl.FUniqueMap;

import org.junit.Test;
import org.junit.runner.RunWith;

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
    public void test_FUniqueMap()
    {
        final FUniqueMap<String, String> map = new FUniqueMap<>();
        map.put("a", "1");
        map.put("b", "1");
        map.put("c", "1");
        map.put("c", "1");

        assertEquals(1, map.size());
        assertEquals("1", map.get("c"));
        assertEquals("c", map.getKeyByValue("1"));
        assertEquals("c", map.removeKeyByValue("1"));
    }
}