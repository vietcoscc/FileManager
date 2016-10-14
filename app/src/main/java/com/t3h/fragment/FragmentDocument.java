package com.t3h.fragment;

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

public class FragmentDocument extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_document, container, false);
        initData();
        setOnItemClick(listView);
        return v;
    }

    @Override
    public void initData() {

        listView = (ListView) v.findViewById(R.id.lv_document);
        arrItemViewFile.clear();
        arrItemViewFile = fileManager.getArrayFile(Environment.getExternalStorageDirectory().getPath() + "/" + Environment.DIRECTORY_DOCUMENTS);
        listViewMainAdapter = new ListViewMainAdapter(getActivity().getBaseContext(), arrItemViewFile);
        listView.setAdapter(listViewMainAdapter);
    }
}
