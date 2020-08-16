package com.matchadevs.styleworks.utils;

import android.graphics.Bitmap;

public class FilterItem {

    String filtername;
    int filterimage;

    public FilterItem(String filterName, int filterImage)
    {
        this.filtername=filterName;
        this.filterimage=filterImage;
    }
    public String getFiltername()
    {
        return filtername;
    }
    public int getFilterimage()
    {
        return filterimage;
    }

}
