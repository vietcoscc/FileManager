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
                    itemViewFile = new ItemViewFile(MainActivity.FOLDER, fileName,files[i].getPath(),true);
                }else {
                    itemViewFile = new ItemViewFile(MainActivity.FILE, fileName,files[i].getPath(),false);
                }
                arrItemViewFile.add(itemViewFile);
            }
        }
        return arrItemViewFile;
    }
}
