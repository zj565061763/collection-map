package com.sd.collection_map;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.sd.collection_map.databinding.ActivityMainBinding;
import com.sd.lib.collection.map.IMap;
import com.sd.lib.collection.map.impl.FHashMap;
import com.sd.lib.collection.map.impl.FUniqueMap;
import com.sd.lib.collection.map.impl.FWeakKeyMap;
import com.sd.lib.collection.map.impl.FWeakKeyUniqueMap;
import com.sd.lib.collection.map.impl.FWeakValueMap;
import com.sd.lib.collection.map.impl.FWeakValueUniqueMap;

public class MainActivity extends AppCompatActivity
{
    public static final String TAG = MainActivity.class.getSimpleName();

    private ActivityMainBinding mBinding;

    private final FWeakKeyMap<Object, Object> mWeakKeyMap = new FWeakKeyMap<>();
    private final FWeakValueMap<Object, Object> mWeakValueMap = new FWeakValueMap<>();

    private final FUniqueMap<Object, Object> mUniqueMap = new FUniqueMap<>();
    private final FWeakKeyUniqueMap<Object, Object> mWeakKeyUniqueMap = new FWeakKeyUniqueMap<>();
    private final FWeakValueUniqueMap<Object, Object> mWeakValueUniqueMap = new FWeakValueUniqueMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
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

        printSize();

        mBinding.btnTime.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                printTime(new FWeakValueMap<Object, Object>());
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        printSize();
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