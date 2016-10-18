package com.t3h.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.example.vaio.filemanager.FileManager;
import com.example.vaio.filemanager.MyResource;
import com.example.vaio.filemanager.R;
import com.t3h.adapter.ListViewMainAdapter;
import com.t3h.model.ItemViewFile;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by vaio on 10/14/2016.
 */

public class FragmentMain extends Fragment {


    protected View v;
    protected ListView listView ;
    protected ListViewMainAdapter listViewMainAdapter;
    protected ArrayList<ItemViewFile> arrItemViewFile = new ArrayList<>();
    protected FileManager fileManager = new FileManager();
    protected TranslateAnimation animation;
    public ListViewMainAdapter getAdapter(){
        return listViewMainAdapter;
    }
    public MyResource myResource = new MyResource();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_main, container, false);
        initData(Environment.getExternalStorageDirectory().getPath());
        setOnItemClick(listView);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        myResource.getApplicationContext();
    }

    public void setOnItemClick(final ListView listView){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String path = arrItemViewFile.get(position).getPath();
                String name = arrItemViewFile.get(position).getTv();
                Boolean isDirectory = arrItemViewFile.get(position).isDirectory();

                if (isDirectory) {
                    arrItemViewFile.clear();
                    arrItemViewFile= fileManager.getArrayFile(path);
                    animation = (TranslateAnimation) AnimationUtils.loadAnimation(getActivity().getBaseContext(),R.anim.list_view_animation);
                    listView.startAnimation(animation);
                }else {
                    File file = new File(path);
                    Intent intent = new Intent();
                    if(file.getName().contains(".jpg")) {
                        intent.setDataAndType(Uri.fromFile(file), "image/*");
                        startActivity(intent);
                    }else if(file.getName().contains(".mp4")) {
                        intent.setDataAndType(Uri.fromFile(file), "video/*");
                        startActivity(intent);
                    }
                }
                listViewMainAdapter.notifyDataSetChanged();
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                PopupMenu popupMenu = new PopupMenu(getActivity(),view);
                popupMenu.getMenuInflater().inflate(R.menu.pop_up_menu,popupMenu.getMenu());
                popupMenu.show();
                return true;
            }
        });
    }




    public void initData(String path) {
        listView = (ListView) v.findViewById(R.id.lv_main);
        arrItemViewFile.clear();
        arrItemViewFile = fileManager.getArrayFile(path);
        listViewMainAdapter = new ListViewMainAdapter(getActivity().getBaseContext(), arrItemViewFile);
        listView.setAdapter(listViewMainAdapter);
    }

}
