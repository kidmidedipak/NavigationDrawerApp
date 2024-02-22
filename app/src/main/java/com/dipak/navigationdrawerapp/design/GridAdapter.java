package com.dipak.navigationdrawerapp.design;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dipak.navigationdrawerapp.R;

public class GridAdapter extends BaseAdapter {

    private Context context;

    public GridAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 10; // Display numbers from 1 to 10
    }

    @Override
    public Object getItem(int position) {
        return null; // Not used in this example
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridViewItem;

        if (convertView == null) {
            // Inflate the layout for each grid item
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridViewItem = inflater.inflate(R.layout.grid_item, null);
        } else {
            gridViewItem = convertView;
        }

        // Set the text for each grid item as the position + 1
        TextView textView = gridViewItem.findViewById(R.id.gridItemTextView);
        textView.setText(String.valueOf(position + 1));

        return gridViewItem;
    }
}
