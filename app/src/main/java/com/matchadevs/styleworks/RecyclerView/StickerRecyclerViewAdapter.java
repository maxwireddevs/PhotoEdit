package com.matchadevs.styleworks.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.matchadevs.styleworks.R;
import com.matchadevs.styleworks.utils.Stickers;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;
import java.sql.SQLException;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StickerRecyclerViewAdapter extends RecyclerView.Adapter<StickerRecyclerViewHolder> implements FastScrollRecyclerView.SectionedAdapter {

    // 1. Initialize our adapter
    private final List<Stickers> stickerList;
    private Context context;
    private int itemResource;

    public StickerRecyclerViewAdapter(Context context, int itemResource, List<Stickers> stickerList){
        this.stickerList=stickerList;
        this.context=context;
        this.itemResource=itemResource;
    }

    // 2. Override the onCreateViewHolder method
    @Override
    public StickerRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 3. Inflate the view and return the new ViewHolder
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stickeritem, parent, false);
        return new StickerRecyclerViewHolder(this.context,view);
    }

    // 4. Override the onBindViewHolder method
    @Override
    public void onBindViewHolder(StickerRecyclerViewHolder holder, int position) {
        // 5. Use position to access the correct Student object
        try {
            holder.bindItem(stickerList.get(position));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return this.stickerList.size();
    }

    @NonNull
    @Override
    public String getSectionName(int position) {
        return null;
    }
}
