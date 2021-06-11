package com.sd.demo.collection_map.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.sd.demo.collection_map.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.btnMap.setOnClickListener(this);
        mBinding.btnRecyclerView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == mBinding.btnMap) {
            startActivity(new Intent(this, MapActivity.class));
        } else if (view == mBinding.btnRecyclerView) {
            startActivity(new Intent(this, RecyclerViewActivity.class));
        }
    }
}