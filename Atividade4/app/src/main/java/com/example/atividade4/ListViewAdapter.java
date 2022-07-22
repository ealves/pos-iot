package com.example.atividade4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends BaseAdapter {

    private Context context;

    private List<String> items = new ArrayList<>();

    public ListViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public String getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_view_layout, viewGroup, false);

        TextView textItem = v.findViewById(R.id.item);

        String item = items.get(i);
        textItem.setText(item.toString());

        return v;
    }

    public void addItem(String item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void removeItem(int i) {
        items.remove(i);
        notifyDataSetChanged();
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
        notifyDataSetChanged();
    }
}
