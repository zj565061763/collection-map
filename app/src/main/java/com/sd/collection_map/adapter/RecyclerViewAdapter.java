package com.sd.collection_map.adapter;

import android.view.ViewGroup;

import com.sd.collection_map.R;
import com.sd.collection_map.databinding.ItemRecyclerViewBinding;
import com.sd.collection_map.model.DataModel;
import com.sd.lib.adapter.FSimpleRecyclerAdapter;
import com.sd.lib.adapter.viewholder.FRecyclerViewHolder;

public class RecyclerViewAdapter extends FSimpleRecyclerAdapter<DataModel>
{
    @Override
    public int getLayoutId(ViewGroup parent, int viewType)
    {
        return R.layout.item_recycler_view;
    }

    @Override
    public void onBindData(FRecyclerViewHolder<DataModel> holder, int position, DataModel model)
    {
        final ItemRecyclerViewBinding binding = ItemRecyclerViewBinding.bind(holder.itemView);
        binding.btn.setText(model.mObject.toString());
    }
}
