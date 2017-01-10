package com.example.vaio.filemanager;

import android.os.Environment;

import com.t3h.model.ItemViewFile;

import java.io.File;
import java.util.ArrayList;
/**
 * Created by vaio on 10/14/2016.
 */

public class FileManager {
    private ArrayList<ItemViewFile> arrItemViewFile =new ArrayList<>();

    public ArrayList<ItemViewFile> getArrayFile(String path){
        File file = new File(path);
        File[] files = file.listFiles();
        if(files!=null){
            for (int i = 0; i < files.length; i++) {
                ItemViewFile itemViewFile;
                String fileName = files[i].getName();
                if(files[i].isDirectory()) {
                    itemViewFile = new ItemViewFile(R.drawable.folder, fileName,files[i].getPath());
                }else {
                    if (files[i].getPath().contains(".jpg")||files[i].getPath().contains(".jepg")||files[i].getPath().contains(".png")){
                        itemViewFile = new ItemViewFile(R.drawable.image, fileName,files[i].getPath());
                    }else if(files[i].getPath().contains(".mp4")){
                        itemViewFile = new ItemViewFile( R.drawable.video,fileName,files[i].getPath());
                    }else if(files[i].getPath().contains(".pdf")){
                        itemViewFile = new ItemViewFile(R.drawable.document,fileName,files[i].getPath());
                    }else if(files[i].getPath().contains(".apk")){
                        itemViewFile = new ItemViewFile(R.mipmap.ic_launcher,fileName,files[i].getPath());
                    }else{
                        itemViewFile = new ItemViewFile(R.drawable.document,fileName,files[i].getPath());
                    }
                }
                arrItemViewFile.add(itemViewFile);
            }
        }
        return arrItemViewFile;
    }
}
