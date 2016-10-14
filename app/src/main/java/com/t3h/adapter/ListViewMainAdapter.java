package com.t3h.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vaio.filemanager.R;
import com.t3h.model.ItemViewFile;

import java.util.ArrayList;

/**
 * Created by vaio on 10/14/2016.
 */

public class ListViewMainAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private ArrayList<ItemViewFile> arrItemViewFile;

    public ListViewMainAdapter(Context context, ArrayList<ItemViewFile> arrItemViewFile) {
        this.context = context;
        this.arrItemViewFile = arrItemViewFile;
        inflater = LayoutInflater.from(context);
    }
    public ArrayList<ItemViewFile> getArrItemViewFile(){
        return arrItemViewFile;
    }
    @Override
    public int getCount() {
        return arrItemViewFile.size();
    }

    @Override
    public Object getItem(int position) {
        return arrItemViewFile.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (v == null) {
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.lv_item_drawer, parent,false);
            viewHolder.iv = (ImageView) v.findViewById(R.id.iv_list_view_item_drawer);
            viewHolder.tv = (TextView) v.findViewById(R.id.tv_list_view_item_drawer);
            v.setTag(viewHolder);
        } else {
           viewHolder = (ViewHolder) v.getTag();
        }

        viewHolder.iv.setImageResource(arrItemViewFile.get(position).getIv());
        viewHolder.tv.setText(arrItemViewFile.get(position).getTv());
        return v;
    }
    class ViewHolder {
        ImageView iv;
        TextView tv;

    }
}
