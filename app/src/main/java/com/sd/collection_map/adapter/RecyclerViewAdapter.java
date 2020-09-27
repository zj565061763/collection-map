package com.sd.collection_map.adapter;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.sd.collection_map.R;
import com.sd.collection_map.databinding.ItemRecyclerViewBinding;
import com.sd.collection_map.model.DataModel;
import com.sd.lib.adapter.FSimpleRecyclerAdapter;
import com.sd.lib.adapter.viewholder.FRecyclerViewHolder;
import com.sd.lib.collection.map.IUniqueMap;
import com.sd.lib.collection.map.impl.FUniqueMap;

import java.util.Map;
import java.util.WeakHashMap;

public class RecyclerViewAdapter extends FSimpleRecyclerAdapter<DataModel>
{
    private static final String TAG = RecyclerViewAdapter.class.getSimpleName();
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    private final Map<View, Integer> mMapWeak = new WeakHashMap<>();
    private final IUniqueMap<Object, Object> mMapUnique = new FUniqueMap<>();

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

        mMapWeak.put(holder.itemView, position);
        mMapUnique.put(holder.itemView, position);

        HANDLER.removeCallbacks(mRunnable);
        HANDLER.postDelayed(mRunnable, 1 * 1000);
    }

    private final Runnable mRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            printMapWeak();
            printMapUnique();
        }
    };

    private void printMapWeak()
    {
        Log.i(TAG, "printMapWeak mapSize:" + mMapWeak.size());
    }

    private void printMapUnique()
    {
        Log.i(TAG, "printMapUnique mapSize:" + mMapUnique.toMap().size());
    }
}
