package com.dipak.navigationdrawerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.dipak.navigationdrawerapp.config.SSLUtils;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SSLUtils.trustAllCertificates();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent it=new Intent(SplashActivity.this   ,LoginActivity.class);
                startActivity(it);
            }
        },2000);
    }
}