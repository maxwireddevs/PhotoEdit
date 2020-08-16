package com.matchadevs.styleworks.utils;

import android.content.Context;

import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import com.matchadevs.styleworks.database.itemHelper;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> itemListName=new ArrayList<>();
    private ArrayList<byte[]> itemList=new ArrayList<>();

    // Constructor
    public ImageAdapter(Context c) {
        mContext = c;
        itemHelper ih=new itemHelper(c);
        Cursor res=ih.getAllData();
        while(res.moveToNext()){
            itemListName.add(res.getString(0));
            itemList.add(res.getBlob(1));
        }
    }

    public int getCount() {
        return itemList.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(300, 250));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(2, 2, 2, 2);
        }
        else
        {
            imageView = (ImageView) convertView;
        }
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(itemList.get(position),0,itemList.get(position).length));
        return imageView;
    }
}