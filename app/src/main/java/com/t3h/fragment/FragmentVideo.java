package com.t3h.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.vaio.filemanager.R;
import com.t3h.adapter.ListViewMainAdapter;

/**
 * Created by vaio on 10/14/2016.
 */

public class FragmentVideo extends BaseFragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_video, container, false);
        initData();
        setOnItemClick(listView);
        return v;
    }

    public void initData() {
        listView = (ListView) v.findViewById(R.id.lv_video);
        arrItemViewFile.clear();
        arrItemViewFile = fileManager.getArrayFile(Environment.getExternalStorageDirectory().toString()+"/"+Environment.DIRECTORY_MOVIES);
        listViewMainAdapter = new ListViewMainAdapter(getActivity().getBaseContext(), arrItemViewFile);
        listView.setAdapter(listViewMainAdapter);
    }
}
