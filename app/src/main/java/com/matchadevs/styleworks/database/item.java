package com.matchadevs.styleworks.database;

import java.sql.Blob;

public class item {

    public static final String TABLE_NAME = "itemlist";

    public static final String COLUMN_ITEM_NAME = "name";
    public static final String COLUMN_ITEM_IMAGEBLOB = "imageblob";

    private String name;
    private Blob imageblob;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ITEM_NAME + " TEXT,"
                    + COLUMN_ITEM_IMAGEBLOB+" BLOB"
                    + ")";

    public item() {
    }

    public item(String name, Blob imageblob) {
        this.name = name;
        this.imageblob=imageblob;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Blob getImageblob(){return imageblob;}

    public void setImageblob(Blob imageblob){this.imageblob=imageblob;}

}
