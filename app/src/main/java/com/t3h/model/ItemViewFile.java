package com.t3h.model;

/**
 * Created by vaio on 10/14/2016.
 */

public class ItemViewFile  {
    private int iv;
    private String tv;
    private String path;
    public ItemViewFile(int iv, String tv ,String path) {
        this.iv = iv;
        this.tv = tv;
        this.path = path;
    }

    public int getIv() {
        return iv;
    }

    public String getTv() {
        return tv;
    }

    public String getPath() {
        return path;
    }
}
