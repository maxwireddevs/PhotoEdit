package com.matchadevs.styleworks.RecyclerView;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.matchadevs.styleworks.R;
import com.matchadevs.styleworks.utils.FilterItem;

import java.sql.SQLException;

import androidx.recyclerview.widget.RecyclerView;

public class CropRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final ImageView itemimageview;
    private final TextView filtertextview;
    private String filtername;
    private int filterid;
    private Context context;
    public CropRecyclerViewHolder(Context context, View itemView){
        super(itemView);
        this.context = context;
        this.itemimageview=(ImageView) itemView.findViewById(R.id.adjustimage);
        this.filtertextview=(TextView)itemView.findViewById(R.id.adjusttext);
        itemView.setOnClickListener(this);
    }
    public void bindItem(FilterItem filter) throws SQLException {
        this.filtername=filter.getFiltername();
        this.filterid=filter.getFilterimage();
        this.itemimageview.setImageDrawable(context.getResources().getDrawable(filterid));
        this.filtertextview.setText(filtername);
    }
    @Override
    public void onClick(View v) {
        if (this.filtername != null) {
            //TODO onClick attendance list item
        }
    }
}

