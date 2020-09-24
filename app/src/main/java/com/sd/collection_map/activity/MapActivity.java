package com.sd.collection_map.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.sd.collection_map.databinding.ActivityMapBinding;
import com.sd.collection_map.model.DataModel;
import com.sd.lib.collection.map.IMap;
import com.sd.lib.collection.map.impl.FUniqueMap;
import com.sd.lib.collection.map.impl.FWeakKeyMap;
import com.sd.lib.collection.map.impl.FWeakKeyUniqueMap;
import com.sd.lib.collection.map.impl.FWeakValueMap;
import com.sd.lib.collection.map.impl.FWeakValueUniqueMap;

import java.util.HashMap;
import java.util.Map;

public class MapActivity extends AppCompatActivity
{
    public static final String TAG = MapActivity.class.getSimpleName();

    private ActivityMapBinding mBinding;

    private final FWeakKeyMap<Object, Object> mWeakKeyMap = new FWeakKeyMap<>();
    private final FWeakValueMap<Object, Object> mWeakValueMap = new FWeakValueMap<>();

    private final FUniqueMap<Object, Object> mUniqueMap = new FUniqueMap<>();
    private final FWeakKeyUniqueMap<Object, Object> mWeakKeyUniqueMap = new FWeakKeyUniqueMap<>();
    private final FWeakValueUniqueMap<Object, Object> mWeakValueUniqueMap = new FWeakValueUniqueMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMapBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mWeakKeyMap.put(new Object(), this);
        mWeakValueMap.put(this, new Object());

        mUniqueMap.put(new Object(), this);
        mUniqueMap.put(new Object(), this);
        mUniqueMap.put(new Object(), this);

        mWeakKeyUniqueMap.put(new Object(), this);
        mWeakKeyUniqueMap.put(new Object(), this);
        mWeakKeyUniqueMap.put(this, new Object());

        mWeakValueUniqueMap.put(this, new Object());
        mWeakValueUniqueMap.put(this, new Object());
        mWeakValueUniqueMap.put(new Object(), this);

        mBinding.btnTime.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                printTime(new FWeakValueMap<Object, Object>());
            }
        });

        testMap();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        printSize();
    }

    private void testMap()
    {
        final DataModel model1 = new DataModel(new String("1"));
        final DataModel model2 = new DataModel(new String("1"));

        final Map<Object, Object> map = new HashMap<>();
        final Object putResult1 = map.put("a", model1);
        final Object putResult2 = map.put("a", model2);

        Log.i(TAG, "putResult1:" + putResult1);
        Log.i(TAG, "putResult2:" + putResult2);

        Log.i(TAG, "model1 == putResult2:" + (model1 == putResult2));
        Log.i(TAG, "model2 == putResult2:" + (model2 == putResult2));

        Log.i(TAG, "model1 equals putResult2:" + (model1.equals(putResult2)));
        Log.i(TAG, "model2 equals putResult2:" + (model2.equals(putResult2)));
    }

    private void printSize()
    {
        Log.i(TAG, "FWeakKeyMap size:" + mWeakKeyMap.size());
        Log.i(TAG, "FWeakValueMap size:" + mWeakValueMap.size());
        Log.i(TAG, "FUniqueMap size:" + mUniqueMap.size());
        Log.i(TAG, "FWeakKeyUniqueMap size:" + mWeakKeyUniqueMap.size());
        Log.i(TAG, "FWeakValueUniqueMap size:" + mWeakValueUniqueMap.size());
    }

    private void printTime(IMap<Object, Object> map)
    {
        final long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++)
        {
            map.put(i, i + 1);
        }
        Log.i(TAG, "time:" + (System.currentTimeMillis() - startTime));
    }
}