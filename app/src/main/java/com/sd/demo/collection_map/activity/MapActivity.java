package com.sd.demo.collection_map.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.sd.demo.collection_map.databinding.ActivityMapBinding;
import com.sd.lib.collection.map.IMap;
import com.sd.lib.collection.map.impl.FWeakKeyMap;
import com.sd.lib.collection.map.impl.FWeakValueMap;

public class MapActivity extends AppCompatActivity {
    public static final String TAG = MapActivity.class.getSimpleName();

    private ActivityMapBinding mBinding;

    private final FWeakKeyMap<Object, Object> mWeakKeyMap = new FWeakKeyMap<>();
    private final FWeakValueMap<Object, Object> mWeakValueMap = new FWeakValueMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMapBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mWeakKeyMap.put(new Object(), this);
        mWeakValueMap.put(this, new Object());

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

    private void printSize() {
        Log.i(TAG, "FWeakKeyMap size:" + mWeakKeyMap.size());
        Log.i(TAG, "FWeakValueMap size:" + mWeakValueMap.size());
    }

    private void printTime(IMap<Object, Object> map) {
        final long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            map.put(i, i + 1);
        }
        Log.i(TAG, "time:" + (System.currentTimeMillis() - startTime));
    }
}