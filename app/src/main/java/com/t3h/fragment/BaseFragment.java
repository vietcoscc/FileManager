package com.t3h.fragment;

import android.app.Fragment;
import android.content.ClipData;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.vaio.filemanager.FileManager;
import com.example.vaio.filemanager.R;
import com.t3h.adapter.ListViewMainAdapter;
import com.t3h.model.ItemViewFile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vaio on 10/14/2016.
 */

public abstract class BaseFragment extends Fragment{

    protected View v;
    protected ListView listView ;
    protected ListViewMainAdapter listViewMainAdapter;
    protected ArrayList<ItemViewFile> arrItemViewFile = new ArrayList<>();
    protected FileManager fileManager = new FileManager();
    public ListViewMainAdapter getAdapter(){
        return listViewMainAdapter;
    }
    public void setListViewMainAdapter(ListViewMainAdapter listViewMainAdapter){
        this.listViewMainAdapter = listViewMainAdapter;
    }
    public ArrayList<ItemViewFile> getArrItemViewFile(){
        return arrItemViewFile;
    }
    public void setOnItemClick(ListView listView){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String path = arrItemViewFile.get(position).getPath();
                String name = arrItemViewFile.get(position).getTv();
                Boolean isDirectory = arrItemViewFile.get(position).isDirectory();

                if (isDirectory) {
                    arrItemViewFile.clear();
                    arrItemViewFile= fileManager.getArrayFile(path);
                }
                listViewMainAdapter.notifyDataSetChanged();
            }
        });
    }
    public abstract void initData();
}
