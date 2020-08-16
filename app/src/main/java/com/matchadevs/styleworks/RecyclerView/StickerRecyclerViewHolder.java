package com.matchadevs.styleworks.RecyclerView;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.matchadevs.styleworks.R;
import com.matchadevs.styleworks.utils.Stickers;

import java.sql.SQLException;

import androidx.recyclerview.widget.RecyclerView;

public class StickerRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final ImageView itemimageview;
    private Stickers stickers;
    private Integer stickerthumbnail;
    private Integer stickerreal;
    private Context context;
    public StickerRecyclerViewHolder(Context context, View itemView){
        super(itemView);
        this.context = context;
        this.itemimageview=(ImageView) itemView.findViewById(R.id.stickerimage);
        itemView.setOnClickListener(this);
    }
    public void bindItem(Stickers sticker) throws SQLException {
        this.stickers=sticker;
        this.stickerthumbnail=sticker.getStickerthumbnail();
        this.stickerreal=sticker.getSticker();
        this.itemimageview.setImageDrawable(context.getResources().getDrawable(stickerthumbnail));
    }
    @Override
    public void onClick(View v) {
        if (this.stickerreal != null) {
            //TODO onClick attendance list item
        }
    }
}

