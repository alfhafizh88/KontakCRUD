package com.example.kontakcrud.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kontakcrud.R;
import com.example.kontakcrud.data.DataModelKontak;

import java.util.List;

import androidx.annotation.Nullable;

public class AdapterKontak extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<DataModelKontak> item;

    public AdapterKontak(Activity activity, List<DataModelKontak> item) {
        this.activity = activity;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_item, null);

        TextView name= (TextView) convertView.findViewById(R.id.name);

        name.setText(item.get(position).getNama_depan());

        return convertView;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}