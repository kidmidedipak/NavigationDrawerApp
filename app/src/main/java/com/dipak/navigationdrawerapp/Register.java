
package com.dipak.navigationdrawerapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
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
import android.widget.Toast;

import com.dipak.navigationdrawerapp.database.MyDB;
import com.dipak.navigationdrawerapp.model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    AppCompatButton btn;
    TextView loginBtn;
    EditText nameTxt,emailTxt,phoneTxt,passTxt;
    private CheckBox showPasswordCheckBox;

    MyDB myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameTxt=findViewById(R.id.editTextName);
        emailTxt=findViewById(R.id.editTextEmail);
        phoneTxt=findViewById(R.id.editTextPhone);
        passTxt=findViewById(R.id.editTextPassword);
        showPasswordCheckBox = findViewById(R.id.showPasswordCheckBox);
        loginBtn=findViewById(R.id.login);
        btn=findViewById(R.id.buttonSignUp);
        passTxt.setTransformationMethod(new PasswordTransformationMethod());
        showPasswordCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Toggle password visibility based on the checkbox state
            togglePasswordVisibility(isChecked);
        });

        myDB=new MyDB(getApplicationContext());


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=nameTxt.getText().toString();
                String email=emailTxt.getText().toString();
                String phone=phoneTxt.getText().toString();
                String pass=passTxt.getText().toString();

                if( name.trim().isEmpty() || email.trim().isEmpty() ||
                        phone.trim().isEmpty() || pass.trim().isEmpty())
                {
                    showErrorDialog("All fields are required");

                }else if(fieldsValidate(email,phone)){

                    if(myDB.loginValid(email, pass)!=null)
                    {
                        Toast.makeText(Register.this, "Account already exists", Toast.LENGTH_SHORT).show();
                    }else {
                        //send register value to db

                        boolean b = myDB.register(new User(0,name,email,phone,pass));
                        if (b) {
                            showSuccessDialog("User registered successfully...!!");
                            clearFields();
                        } else {
                            Toast.makeText(Register.this, "Something wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(Register.this   ,LoginActivity.class);
                startActivity(it);
            }
        });
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

    private boolean fieldsValidate(String email, String phone) {
        final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        if(matcher.matches())
        {
            if(phone.trim().length()==10)
            {
                return true;
            }else{
                showErrorDialog("Enter valid 10 digit mobile number") ;
            return false;
            }
        }else{
            showErrorDialog("Enter valid email") ;

         return false;
        }
    }

    public void clearFields(){
        nameTxt.setText("");
        emailTxt.setText("");
        phoneTxt.setText("");
        passTxt.setText("");
    }

    private  void showSuccessDialog(String msg ){
        ConstraintLayout successConstraintLayout=findViewById(R.id.successConsLayout);
        View view= LayoutInflater.from(Register.this).inflate(R.layout.success_dialog,successConstraintLayout );
        Button successDone=view.findViewById(R.id.successDone);
        TextView textView=view.findViewById(R.id.successdesc);
        textView.setText(msg);
        AlertDialog.Builder builder=new AlertDialog.Builder(Register.this);
        builder.setView(view);
        final AlertDialog alertDialog=builder.create();

        successDone.findViewById(R.id.successDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(Register.this   ,LoginActivity.class);
                startActivity(it);
                alertDialog.dismiss();
            }
        });

        if(alertDialog.getWindow()!=null)
        {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }


    private  void showErrorDialog(String msg ){
            ConstraintLayout successConstraintLayout=findViewById(R.id.successConsLayout);
            View view= LayoutInflater.from(Register.this).inflate(R.layout.success_dialog,successConstraintLayout );
            Button successDone=view.findViewById(R.id.successDone);
            ImageView errorIcon=view.findViewById(R.id.successImage);
            TextView textView=view.findViewById(R.id.successdesc);
            TextView dialogTitle=view.findViewById(R.id.successTitle);
            dialogTitle.setText("Invalid Input");
            errorIcon.setImageResource(R.drawable.error_icon);
            textView.setText(msg);
            AlertDialog.Builder builder=new AlertDialog.Builder(Register.this);
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