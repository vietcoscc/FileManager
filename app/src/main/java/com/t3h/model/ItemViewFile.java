package com.t3h.model;

/**
 * Created by vaio on 10/14/2016.
 */

public class ItemViewFile  {
    private int iv;
    private String tv;
    private String path;
    private boolean isDirectory;
    public ItemViewFile(int iv, String tv ,String path, boolean isDirectory) {
        this.iv = iv;
        this.tv = tv;
        this.path = path;
        this.isDirectory = isDirectory;
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

    public boolean isDirectory() {
        return isDirectory;
    }
}
