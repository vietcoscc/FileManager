package com.t3h.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vaio.filemanager.R;
import com.t3h.model.ItemViewDrawer;

import java.util.ArrayList;

/**
 * Created by vaio on 10/14/2016.
 */
public class ListViewDrawerAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<ItemViewDrawer> arrItemViewDrawer;

    public ListViewDrawerAdapter(Context context, ArrayList<ItemViewDrawer> arrItemViewDrawer) {
        this.context = context;
        this.arrItemViewDrawer = arrItemViewDrawer;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return arrItemViewDrawer.size();
    }

    @Override
    public Object getItem(int position) {
        return arrItemViewDrawer.get(position);
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

        viewHolder.iv.setImageResource(arrItemViewDrawer.get(position).getIv());
        viewHolder.tv.setText(arrItemViewDrawer.get(position).getTv());
        return v;
    }

    class ViewHolder {
        ImageView iv;
        TextView tv;

    }
}
