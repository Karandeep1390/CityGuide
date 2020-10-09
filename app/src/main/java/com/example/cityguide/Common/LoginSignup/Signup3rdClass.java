package com.example.cityguide.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.ScrollView;

import com.example.cityguide.R;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

public class Signup3rdClass extends AppCompatActivity {
    TextInputLayout phoneNumber;
    ScrollView scrollView;
    CountryCodePicker countryCodePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup3rd_class);

        scrollView = findViewById(R.id.signup_3rdclass_scrollview);
        phoneNumber = findViewById(R.id.signup_phone_number);
        countryCodePicker = findViewById(R.id.countryCodePicker);
    }
    public void callVerifyOtp(View view){
        if (!validatePhoneNo()){
            return;
        }
        String _fullname= getIntent().getStringExtra("fullname");
        String _username= getIntent().getStringExtra("username");
        String _email= getIntent().getStringExtra("email");
        String _password= getIntent().getStringExtra("password");
        String _selectGender= getIntent().getStringExtra("selectGender");
        String date= getIntent().getStringExtra("date");

        String _getUserEnteredphoneNumber=phoneNumber.getEditText().getText().toString().trim();
        String _PhoneNo = "+"+countryCodePicker.getFullNumber()+_getUserEnteredphoneNumber;

        Intent intent = new Intent(getApplicationContext(),VerirfyOTP.class);
        intent.putExtra("fullname",_fullname);
        intent.putExtra("PhoneNo.",_PhoneNo);
        intent.putExtra("username",_username);
        intent.putExtra("email",_email);
        intent.putExtra("password",_password);
        intent.putExtra("selectGender",_selectGender);
        intent.putExtra("date",date);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View,String>(scrollView,"transitionOtpScreen");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Signup3rdClass.this, pairs);
        startActivity(intent, options.toBundle());

    }
    private boolean validatePhoneNo(){
        String _phoneNumber=phoneNumber.getEditText().getText().toString().trim();
        if (_phoneNumber.isEmpty()){
            phoneNumber.setError("Field can't be empty");
            return false;
        }else if (_phoneNumber.length()>10){
            phoneNumber.setError("Enter correct Phone no.");
            return false;
        }
        else
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;
    }
    public void login(View view){
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
    }
}