package com.matchadevs.styleworks.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.matchadevs.styleworks.R;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import java.sql.SQLException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StickerListRecyclerViewAdapter extends RecyclerView.Adapter<StickerListRecyclerViewHolder> implements FastScrollRecyclerView.SectionedAdapter {

    // 1. Initialize our adapter
    private final List<String> stickerPackList;
    private Context context;
    private int itemResource;

    public StickerListRecyclerViewAdapter(Context context, int itemResource, List<String> stickerpacklist){
        this.stickerPackList=stickerpacklist;
        this.context=context;
        this.itemResource=itemResource;
    }

    // 2. Override the onCreateViewHolder method
    @Override
    public StickerListRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 3. Inflate the view and return the new ViewHolder
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stickerlistitem, parent, false);
        return new StickerListRecyclerViewHolder(this.context,view);
    }

    // 4. Override the onBindViewHolder method
    @Override
    public void onBindViewHolder(StickerListRecyclerViewHolder holder, int position) {
        // 5. Use position to access the correct Student object
        String stickername=this.stickerPackList.get(position);
        try {
            holder.bindItem(stickername);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return this.stickerPackList.size();
    }

    @NonNull
    @Override
    public String getSectionName(int position) {
        return null;
    }
}
