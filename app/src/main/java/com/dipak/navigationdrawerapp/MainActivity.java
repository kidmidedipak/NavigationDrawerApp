package com.dipak.navigationdrawerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.multidex.MultiDex;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dipak.navigationdrawerapp.fragment.APIFragment;
import com.dipak.navigationdrawerapp.fragment.AkFragment;
import com.dipak.navigationdrawerapp.fragment.BlankFragment;
import com.dipak.navigationdrawerapp.fragment.BroadcastReceiver;
import com.dipak.navigationdrawerapp.fragment.ContactList;
import com.dipak.navigationdrawerapp.fragment.MyProfile;
import com.dipak.navigationdrawerapp.fragment.ProductListFragment;
import com.dipak.navigationdrawerapp.fragment.SendSMS;
import com.dipak.navigationdrawerapp.fragment.UpiPayment;
import com.dipak.navigationdrawerapp.fragment.spinnerAutocompleteView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity  {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MultiDex.install(this);
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.navigationView);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        //set data to header
        setNameAndEmail();
        loadFragment(new SendSMS());

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(
                this,drawerLayout,toolbar,R.string.open,R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id= item.getItemId();
                if(id==R.id.home)
                {
                    loadFragment(new SendSMS());
                }else if(id==R.id.contact){
                    loadFragment(new ContactList());
                }else if(id==R.id.broadcast){
                    loadFragment(new BroadcastReceiver());
                }else if(id==R.id.about){
                   loadFragment(new MyProfile());
                } else if(id==R.id.share){
                    Toast.makeText(MainActivity.this, "Share Page", Toast.LENGTH_SHORT).show();
                }else if(id==R.id.rate_us){
                    Toast.makeText(MainActivity.this, "Rate Us Page", Toast.LENGTH_SHORT).show();
                }else if(id==R.id.logout){
                    logout();
                }else if(id==R.id.apitest){
                    loadFragment(new APIFragment());
                }else if(id==R.id.spinnerOrACtxt){
                    loadFragment(new spinnerAutocompleteView());
                }else if(id==R.id.productlist){
                    loadFragment(new ProductListFragment());
                }else if(id==R.id.paymentGetway){
                    loadFragment(new UpiPayment());
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }

        });
    }


    private void setNameAndEmail() {
        Intent receivedIntent = getIntent();
        View headerView = navigationView.getHeaderView(0);

        // Access the TextView inside the header layout
        TextView logInUserNameTxt = headerView.findViewById(R.id.logInUserName);
        TextView logInuserEmailTxt = headerView.findViewById(R.id.logInUserEmail);

// Retrieve the extra data using the key
        if (receivedIntent != null && receivedIntent.hasExtra("email")) {
            String email = receivedIntent.getStringExtra("email");
            String name = receivedIntent.getStringExtra("name");
            logInUserNameTxt.setText(name);
            logInuserEmailTxt.setText(email);
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            // If there are fragments in the back stack, pop the top one
            fragmentManager.popBackStack();
        } else if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
            finishAffinity();
        }
    }

    private void loadFragment(Fragment fragment) {

        FragmentManager fm=getSupportFragmentManager() ;
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.container, fragment );
        ft.commit();
    }

    public void logout(){
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
        Intent intent=new Intent(MainActivity.this, LoginActivity.class);

        startActivity(intent);
        finish();
    }

}