package com.sd.collection_map;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.sd.lib.collection.map.impl.FUniqueMap;
import com.sd.lib.collection.map.impl.FWeakKeyMap;
import com.sd.lib.collection.map.impl.FWeakKeyUniqueMap;
import com.sd.lib.collection.map.impl.FWeakValueMap;

public class MainActivity extends AppCompatActivity
{
    public static final String TAG = MainActivity.class.getSimpleName();

    private final FWeakKeyMap<Object, Object> mWeakKeyMap = new FWeakKeyMap<>();
    private final FWeakValueMap<Object, Object> mWeakValueMap = new FWeakValueMap<>();

    private final FUniqueMap<Object, Object> mUniqueMap = new FUniqueMap<>();
    private final FWeakKeyUniqueMap<Object, Object> mWeakKeyUniqueMap = new FWeakKeyUniqueMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWeakKeyMap.put(new Object(), this);
        mWeakValueMap.put(this, new Object());

        mUniqueMap.put(new Object(), "1");
        mUniqueMap.put(new Object(), "1");
        mUniqueMap.put(new Object(), "1");

        mWeakKeyUniqueMap.put(new Object(), "1");
        mWeakKeyUniqueMap.put(new Object(), "1");
        mWeakKeyUniqueMap.put(new Object(), "1");

        printSize();
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
        Log.i(TAG, "FWeakValueMap size:" + mWeakKeyMap.size());
        Log.i(TAG, "FUniqueMap size:" + mUniqueMap.size());
        Log.i(TAG, "FWeakKeyUniqueMap size:" + mWeakKeyUniqueMap.size());
    }
}