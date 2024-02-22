package com.dipak.navigationdrawerapp.fragment;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.telephony.SmsManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dipak.navigationdrawerapp.MainActivity;
import com.dipak.navigationdrawerapp.R;
import com.dipak.navigationdrawerapp.design.FeaturePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SendSMS extends Fragment {

    EditText mobileno,et_msg ;
    Button sendsms;
    private ViewPager viewPager;
    private FeaturePagerAdapter featurePagerAdapter;
    private LinearLayout dotsLayout;
    private List<String> imageUrls;
    View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view= inflater.inflate(R.layout.fragment_send_s_m_s, container, false);
        mobileno=view.findViewById(R.id.mobilenum);
        et_msg=view.findViewById(R.id.et_msg);
        sendsms=view.findViewById(R.id.sendbtn);

// Check if the permission is not granted
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            // Request the permission
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, 0);
        } else {
            // Permission is already granted, proceed with sending SMS
            sendsms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Intent intent=new Intent(getContext(), MainActivity.class);
                    PendingIntent pi=PendingIntent.getActivity(getContext(), 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);

                    String mo=mobileno.getText().toString();
                    String msg=et_msg.getText().toString();
//Get the SmsManager instance and call the sendTextMessage method to send message
                    SmsManager sms=SmsManager.getDefault();
                    sms.sendTextMessage(mo, null, msg, pi,null);
                    Toast.makeText(getContext(), "Message Sent successfully!",
                            Toast.LENGTH_LONG).show();
                }
            });
        }

        newFeaturesAdsImplementation();

        return view;
    }

    private void newFeaturesAdsImplementation() {
        viewPager = view.findViewById(R.id.viewPager);
        dotsLayout =view.findViewById(R.id.dotsLayout);

        // Sample image URLs (replace with your actual image URLs)
        imageUrls = new ArrayList<>();
        imageUrls.add("https://rukminim2.flixcart.com/fk-p-flap/1600/710/image/c0aed8819fdf0746.jpeg?q=20");
        imageUrls.add("https://rukminim2.flixcart.com/fk-p-flap/1600/710/image/06b3e7aff6bfbad2.jpg?q=20");
        imageUrls.add("https://rukminim2.flixcart.com/fk-p-flap/1600/710/image/58869310f542a104.jpeg?q=20");
        imageUrls.add("https://rukminim2.flixcart.com/fk-p-flap/1600/710/image/64740f70659f8b3d.jpg?q=20");
        imageUrls.add("https://rukminim2.flixcart.com/fk-p-flap/1600/710/image/46cc01b55f9b9d4b.jpg?q=20");
        imageUrls.add("https://rukminim2.flixcart.com/fk-p-flap/1600/710/image/06b3e7aff6bfbad2.jpg?q=20");
        imageUrls.add("https://rukminim2.flixcart.com/fk-p-flap/1600/710/image/e2ddbbf7ef284eb3.jpeg?q=20");
        imageUrls.add("https://rukminim2.flixcart.com/fk-p-flap/1600/710/image/06b3e7aff6bfbad2.jpg?q=20");

        int margin = getResources().getDimensionPixelOffset(R.dimen.viewpager_page_margin); // Adjust margin as needed
        viewPager.setPageMargin(-margin);
        viewPager.setClipToPadding(false);
        viewPager.setPadding(margin, 0, margin, 0);
        viewPager.setPageTransformer(true, new MarginPageTransformer(margin));
//        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
//        viewPager.setPageTransformer(true, new ClipToPaddingPageTransformer());


        featurePagerAdapter = new FeaturePagerAdapter(getContext(), imageUrls);
        viewPager.setAdapter(featurePagerAdapter);

        // Add indicators (dots)
        addDotsIndicator();

        // Optional: Set up automatic scrolling
        startAutoScrolling();
    }

    private void addDotsIndicator() {
        for (int i = 0; i < featurePagerAdapter.getCount(); i++) {
            ImageView dot = new ImageView(getContext());
            dot.setImageDrawable(getResources().getDrawable(R.drawable.dot_inactive));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.gravity = Gravity.CENTER;

            dotsLayout.addView(dot, params);
        }

    }

    private void setDotStatus(int position, boolean isActive) {

        ImageView dot = (ImageView) dotsLayout.getChildAt(position);
        if (dot != null && isAdded()) {
            // Check if the fragment is added to an activity before using context or resources
            dot.setImageDrawable(requireContext().getResources().getDrawable(
                    isActive ? R.drawable.dot_active : R.drawable.dot_inactive));
        }


    }

    private void startAutoScrolling() {
        final int delayMillis = 3000; // Adjust the delay time as needed
        final int pageCount = featurePagerAdapter.getCount();

        final android.os.Handler handler = new android.os.Handler();
        final Runnable runnable = new Runnable() {
            public void run() {
                int currentItem = viewPager.getCurrentItem();
                int nextItem = (currentItem + 1) % pageCount;
                viewPager.setCurrentItem(nextItem, true);

                // Update dots indicator
                setDotStatus(currentItem, false);
                setDotStatus(nextItem, true);

                handler.postDelayed(this, delayMillis);
            }
        };

        handler.postDelayed(runnable, delayMillis);
    }


}