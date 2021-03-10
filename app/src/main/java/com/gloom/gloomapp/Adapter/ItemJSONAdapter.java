package com.gloom.gloomapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.gloom.gloomapp.Model.Item;
import com.gloom.gloomapp.R;

import java.util.ArrayList;

public class ItemJSONAdapter extends BaseAdapter {

    Context context;
    ArrayList<Item> arrayList;

    public ItemJSONAdapter(Context context, ArrayList<Item> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_row, parent, false);
        }
        TextView name;
        name = convertView.findViewById(R.id.nameItem);
        name.setText(arrayList.get(position).getName());

        return convertView;
    }
}