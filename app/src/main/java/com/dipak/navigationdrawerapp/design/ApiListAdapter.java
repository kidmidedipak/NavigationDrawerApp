package com.dipak.navigationdrawerapp.design;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dipak.navigationdrawerapp.R;
import com.dipak.navigationdrawerapp.model.MyApi;

import java.util.List;

public class ApiListAdapter extends BaseAdapter {
    private Context context;
    private List<MyApi> apiList;

    public ApiListAdapter(Context context, List<MyApi> apiList) {
        this.context = context;
        this.apiList = apiList;
    }

    @Override
    public int getCount() {
        return apiList.size();
    }

    @Override
    public Object getItem(int position) {
        return apiList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_contact, parent, false);
        }

        // Get the current contact
        MyApi api = apiList.get(position);

        // Set contact information to the views in the item layout
        ImageView profileImageView = convertView.findViewById(R.id.profileImageView);
//        profileImageView.setImageResource(api.getProfileImageResId());

        profileImageView.setVisibility(View.GONE);


        TextView nameTextView = convertView.findViewById(R.id.nameTextView);
        nameTextView.setText(api.getApiType());

        TextView phoneTextView = convertView.findViewById(R.id.phoneTextView);
        phoneTextView.setText(api.getApi());

        return convertView;
    }
}
