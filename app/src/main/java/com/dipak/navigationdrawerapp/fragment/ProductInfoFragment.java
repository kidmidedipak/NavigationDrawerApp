package com.dipak.navigationdrawerapp.fragment;

import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dipak.navigationdrawerapp.R;
import com.dipak.navigationdrawerapp.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ProductInfoFragment extends Fragment {


    ArrayList<Product> productArrayList;
    int pid;
    public ProductInfoFragment() {

    }

    public void setProductData(int id, ArrayList<Product> productArrayList) {
       this.productArrayList=productArrayList;
       this.pid=id;
        Bundle args = new Bundle();
        args.putString("key", id+"");
        setArguments(args);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_product_info, container, false);
        ImageView imageView=view.findViewById(R.id.product_image);
        TextView title=view.findViewById(R.id.product_title);
        TextView subtitle=view.findViewById(R.id.product_subtitle);
        TextView description=view.findViewById(R.id.product_description);
        Product product=productArrayList.get(pid);

        int desireWidth=600;
        int desireHeight=500;

        Picasso.get()
                .load(product.getImg_url())
                .resize(desireWidth,desireHeight)
                .into(imageView);
        title.setText("Title : "+product.getImg_title());
        subtitle.setText("Subtitle : "+product.getImg_sub_title());
        description.setText("Information : "+product.getImg_info());
        return view;
    }
}