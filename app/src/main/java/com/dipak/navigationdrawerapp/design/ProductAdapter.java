package com.dipak.navigationdrawerapp.design;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dipak.navigationdrawerapp.R;
import com.dipak.navigationdrawerapp.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends BaseAdapter {

    private Context context;
    private List<Product> productList;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }
    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        HolderView holderView;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.custom_layout_with_cardview, viewGroup, false);
            holderView=new HolderView(convertView);
            convertView.setTag(holderView);
        }else {
            holderView=(HolderView) convertView.getTag();
        }

//// Using Picasso to load the image from the URL into the ImageView
//

        int desireWidth=170;
        int desireHeight=170;

        // Get the current contact
        Product product = productList.get(position);

        Picasso.get().
                load(product.getImg_url())
                .resize(desireWidth,desireHeight)
                .centerCrop()
                .into(holderView.pImage);
        holderView.pTitle.setText(product.getImg_title());
        String desc=product.getImg_info().substring(0,58)+"...";
        holderView.pDescription.setText(desc);

        //list animation
        convertView.setScaleX(0.8f);
        convertView.setScaleY(0.8f);
        convertView.animate().scaleX(1).scaleY(1).setDuration(500).start();


        return convertView;
    }

    private static class HolderView{
        private final ImageView pImage;
        private final TextView pTitle;
        private final TextView pDescription;

        public HolderView(View view)
        {
            pImage=view.findViewById(R.id.pImage);
            pTitle=view.findViewById(R.id.pTitle);
            pDescription=view.findViewById(R.id.pDescription);
        }
    }
}
