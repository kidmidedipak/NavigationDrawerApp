package com.dipak.navigationdrawerapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.dipak.navigationdrawerapp.R;
import com.google.android.gms.common.api.GoogleApiClient;

public class UpiPayment extends Fragment {
    View view;
    private static final String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
    private static final int GOOGLE_PAY_REQUEST_CODE = 123;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view=inflater.inflate(R.layout.fragment_upi_payment, container, false);


// Call this method when you want to initiate the Google Pay payment
        Button send=view.findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
             initiateGooglePayPayment();

        return view;
    }

    private void initiateGooglePayPayment() {
        Uri uri = new Uri.Builder()
                .scheme("upi")
                .authority("pay")
//                .appendQueryParameter("pa", "sadf")
                .appendQueryParameter("pn", "Dipak kidmide")
//                .appendQueryParameter("mc", "your-merchant-code")
                .appendQueryParameter("tr", "your-transaction-ref-id")
                .appendQueryParameter("tn", "My first transaction")
                .appendQueryParameter("am", "1000")
                .appendQueryParameter("cu", "INR")
                .appendQueryParameter("url", "kidmidedipak0666@okaxis")
                .build();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        intent.setPackage(GOOGLE_PAY_PACKAGE_NAME);

        startActivityForResult(intent, GOOGLE_PAY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_PAY_REQUEST_CODE) {
            // Handle the result of the Google Pay payment here
            // You can check resultCode and data to determine the success or failure of the payment
            Toast.makeText(requireContext(), ""+requestCode, Toast.LENGTH_SHORT).show();
        }
    }


}