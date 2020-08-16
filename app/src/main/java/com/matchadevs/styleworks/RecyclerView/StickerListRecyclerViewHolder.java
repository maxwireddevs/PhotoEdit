package com.matchadevs.styleworks.RecyclerView;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.matchadevs.styleworks.R;

import java.sql.SQLException;

import androidx.recyclerview.widget.RecyclerView;

public class StickerListRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final TextView stickernametextview;
    private String filtername;
    private int filterid;
    private Context context;
    public StickerListRecyclerViewHolder(Context context, View itemView){
        super(itemView);
        this.context = context;
        this.stickernametextview=(TextView)itemView.findViewById(R.id.stickerlisttextview);
        itemView.setOnClickListener(this);
    }
    public void bindItem(String stickername) throws SQLException {
        this.stickernametextview.setText(stickername);
    }
    @Override
    public void onClick(View v) {
        if (this.filtername != null) {
            //TODO onClick attendance list item
        }
    }
}

