package com.sd.collection_map.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.sd.collection_map.adapter.RecyclerViewAdapter;
import com.sd.collection_map.databinding.ActivityRecyclerViewBinding;
import com.sd.collection_map.model.DataModel;

public class RecyclerViewActivity extends AppCompatActivity
{
    private ActivityRecyclerViewBinding mBinding;
    private final RecyclerViewAdapter mAdapter = new RecyclerViewAdapter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mBinding = ActivityRecyclerViewBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.lvContent.setLayoutManager(new LinearLayoutManager(this));
        mBinding.lvContent.setAdapter(mAdapter);
        mAdapter.getDataHolder().setData(DataModel.get(200));
    }
}
