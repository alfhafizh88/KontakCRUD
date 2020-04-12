package com.example.kontakcrud;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomList extends ArrayAdapter<String> {
    private String[] androidosnames;
    /*private String[] urls;*/
    private Bitmap[] bitmaps;
    private Activity context;

    public CustomList(Activity context, String[] androidosnames, Bitmap[] bitmaps) {
        super(context, R.layout.image_list_view, androidosnames);
        this.context = context;
        /*this.urls= urls;*/
        this.androidosnames = androidosnames;
        this.bitmaps= bitmaps;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.image_list_view, null, true);
        TextView textViewURL = (TextView) listViewItem.findViewById(R.id.textViewURL);
        ImageView image = (ImageView) listViewItem.findViewById(R.id.imageDownloaded);

        /*textViewURL.setText(urls[position]);*/
        textViewURL.setText(androidosnames[position]);
        image.setImageBitmap(Bitmap.createScaledBitmap(bitmaps[position],100,50,false));
        return  listViewItem;
    }
}