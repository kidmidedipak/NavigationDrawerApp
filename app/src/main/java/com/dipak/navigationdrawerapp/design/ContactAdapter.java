package com.dipak.navigationdrawerapp.design;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dipak.navigationdrawerapp.model.Contact;
import com.dipak.navigationdrawerapp.R;

import java.util.List;

public class ContactAdapter extends BaseAdapter {
    private Context context;
    private List<Contact> contactList;

    public ContactAdapter(Context context, List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @Override
    public int getCount() {
        return contactList.size();
    }

    @Override
    public Object getItem(int position) {
        return contactList.get(position);
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
        Contact contact = contactList.get(position);

        // Set contact information to the views in the item layout
        ImageView profileImageView = convertView.findViewById(R.id.profileImageView);
        profileImageView.setImageResource(contact.getProfileImageResId());

        TextView nameTextView = convertView.findViewById(R.id.nameTextView);
        nameTextView.setText(contact.getName());

        TextView phoneTextView = convertView.findViewById(R.id.phoneTextView);
        phoneTextView.setText(contact.getPhone());

        return convertView;
    }
}
