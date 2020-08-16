package com.matchadevs.styleworks.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.matchadevs.styleworks.R;
import com.matchadevs.styleworks.utils.FilterItem;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import java.sql.SQLException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdjustRecyclerViewAdapter extends RecyclerView.Adapter<AdjustRecyclerViewHolder> implements FastScrollRecyclerView.SectionedAdapter {

    // 1. Initialize our adapter
    private final List<FilterItem> filterList;
    private Context context;
    private int itemResource;

    public AdjustRecyclerViewAdapter(Context context, int itemResource, List<FilterItem> imagelist){
        this.filterList=imagelist;
        this.context=context;
        this.itemResource=itemResource;
    }

    // 2. Override the onCreateViewHolder method
    @Override
    public AdjustRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 3. Inflate the view and return the new ViewHolder
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adjustitem, parent, false);
        return new AdjustRecyclerViewHolder(this.context,view);
    }

    // 4. Override the onBindViewHolder method
    @Override
    public void onBindViewHolder(AdjustRecyclerViewHolder holder, int position) {
        // 5. Use position to access the correct Student object
        FilterItem filter=this.filterList.get(position);
        try {
            holder.bindItem(filter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return this.filterList.size();
    }

    @NonNull
    @Override
    public String getSectionName(int position) {
        return null;
    }
}
