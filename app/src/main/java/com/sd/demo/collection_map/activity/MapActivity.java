package com.sd.demo.collection_map.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.sd.demo.collection_map.databinding.ActivityMapBinding;
import com.sd.lib.collection.map.IMap;
import com.sd.lib.collection.map.weak.FWeakKeyMap;
import com.sd.lib.collection.map.weak.FWeakMap;
import com.sd.lib.collection.map.weak.FWeakValueMap;

public class MapActivity extends AppCompatActivity {
    private static final String TAG = MapActivity.class.getSimpleName();

    private ActivityMapBinding mBinding;

    private final IMap<Object, Object> mWeakKeyMap = new FWeakKeyMap<>();
    private final IMap<Object, Object> mWeakValueMap = new FWeakValueMap<>();
    private final IMap<Object, Object> mWeakMap = new FWeakMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMapBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mWeakKeyMap.put(new Object(), this);
        mWeakValueMap.put(this, new Object());
        mWeakMap.put(new Object(), new Object());

        mBinding.btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printTime(new FWeakValueMap<>());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        printSize();
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.gc();
    }

    private void printSize() {
        Log.i(TAG, "FWeakKeyMap size:" + mWeakKeyMap.size());
        Log.i(TAG, "FWeakValueMap size:" + mWeakValueMap.size());
        Log.i(TAG, "FWeakMap size:" + mWeakMap.size());
    }

    private void printTime(IMap<Object, Object> map) {
        final long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            map.put(i, i + 1);
        }
        Log.i(TAG, "time:" + (System.currentTimeMillis() - startTime));
    }
}