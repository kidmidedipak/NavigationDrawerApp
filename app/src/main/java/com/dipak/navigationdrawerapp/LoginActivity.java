package com.dipak.navigationdrawerapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dipak.navigationdrawerapp.database.MyDB;
import com.dipak.navigationdrawerapp.model.User;

public class LoginActivity extends AppCompatActivity {

    EditText emailTxt,passTxt;
    AppCompatButton loginBtn;
    TextView createBtn;
    private CheckBox showPasswordCheckBox;
    MyDB myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailTxt=findViewById(R.id.editTextEmail);
        passTxt=findViewById(R.id.editTextPassword);
        loginBtn=findViewById(R.id.buttonLogin);
        createBtn=findViewById(R.id.createNewAc);
        showPasswordCheckBox = findViewById(R.id.showPasswordCheckBox);
        passTxt.setTransformationMethod(new PasswordTransformationMethod());
        showPasswordCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Toggle password visibility based on the checkbox state
            togglePasswordVisibility(isChecked);
        });

        if(checkAuthToken())
        {
            Intent intent=new Intent(LoginActivity.this, MainActivity.class);

            startActivity(intent);
            finish();
        }


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDB=new MyDB(getApplicationContext());
                String email=emailTxt.getText().toString();
                String pass=passTxt.getText().toString();
                if(email.trim()!=null || pass.trim()!=null) {
                    User obj = myDB.loginValid(email, pass);
                    if (obj != null) {
                        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("email", email);
                        editor.putString("password", pass);
                        editor.apply();

                        myDB.changeLoginStatus(email, pass);
                        Intent it = new Intent(LoginActivity.this, MainActivity.class);
                        it.putExtra("email", obj.getEmail());
                        it.putExtra("name", obj.getName());
                        startActivity(it);
                    } else {
                        showErrorDialog("Invalid user");
                    }
                }else{
                    showErrorDialog("All fields are required");
                }

            }
        });

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(LoginActivity.this,Register.class);
                startActivity(it);
            }
        });
    }


    boolean checkAuthToken() {
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String email = prefs.getString("email", null);
        String password = prefs.getString("password", null);

        if(email==null    || password==null  )
        {
            return false;
        }
        MyDB obj=new MyDB(getApplicationContext());
        int cnt=obj.isLogin(email,password);


        if(cnt==1)
        {
            return  true;
        }
        return true;
    }

    private void togglePasswordVisibility(boolean showPassword) {

        if (showPassword) {
            // Show password
            passTxt.setTransformationMethod(null);
        } else {
            // Hide password
            passTxt.setTransformationMethod(new PasswordTransformationMethod());
        }

        // Move the cursor to the end of the text to maintain the cursor position
        passTxt.setSelection(passTxt.getText().length());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    private  void showErrorDialog(String msg ){
        ConstraintLayout successConstraintLayout=findViewById(R.id.successConsLayout);
        View view= LayoutInflater.from(LoginActivity.this).inflate(R.layout.success_dialog,successConstraintLayout );
        Button successDone=view.findViewById(R.id.successDone);
        ImageView errorIcon=view.findViewById(R.id.successImage);
        TextView textView=view.findViewById(R.id.successdesc);
        TextView dialogTitle=view.findViewById(R.id.successTitle);
        dialogTitle.setText("Invalid Input");
        errorIcon.setImageResource(R.drawable.error_icon);
        textView.setText(msg);
        AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this);
        builder.setView(view);
        final AlertDialog alertDialog=builder.create();

        successDone.findViewById(R.id.successDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();
            }
        });

        if(alertDialog.getWindow()!=null)
        {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }
}