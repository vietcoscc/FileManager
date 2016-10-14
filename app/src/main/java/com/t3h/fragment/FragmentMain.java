package com.t3h.fragment;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.vaio.filemanager.FileManager;
import com.example.vaio.filemanager.R;
import com.t3h.adapter.ListViewMainAdapter;
import com.t3h.model.ItemViewFile;

import java.util.ArrayList;

/**
 * Created by vaio on 10/14/2016.
 */

public class FragmentMain extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_main, container, false);
        initData();
        setOnItemClick(listView);
        return v;
    }

    public void initData() {
        listView = (ListView) v.findViewById(R.id.lv_main);
        arrItemViewFile.clear();
        arrItemViewFile = fileManager.getArrayFile(Environment.getExternalStorageDirectory().toString());
        listViewMainAdapter = new ListViewMainAdapter(getActivity().getBaseContext(), arrItemViewFile);
        listView.setAdapter(listViewMainAdapter);
    }

}
