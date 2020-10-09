package com.example.cityguide.Common.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.cityguide.Databases.SessionManager;
import com.example.cityguide.LocationOwner.RetailerDashboardd;
import com.example.cityguide.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.net.NetworkInterface;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    CountryCodePicker countryCodePicker;
    TextInputLayout phoneNumber,password;
    CheckBox rememberMe;
    TextInputEditText phone,pswd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_login);

        countryCodePicker=findViewById(R.id.countryCodePickerLogin);
        phoneNumber=findViewById(R.id.login_phone_number);
        password=findViewById(R.id.login_password);
        rememberMe = findViewById(R.id.remember_me);
        phone = findViewById(R.id.login_phone_editText);
        pswd = findViewById(R.id.login_password_editText);
        SessionManager sessionManager = new SessionManager(LoginActivity.this,SessionManager.SESSION_REMEMBERMESESSSION);
        if (sessionManager.checkRemembebrMe()){
            HashMap<String ,String > rememberMeDetails = sessionManager.getUserDetailfromSession();
            phone.setText(rememberMeDetails.get(SessionManager.KEY_SESSIONPHONENO));
            pswd.setText(rememberMeDetails.get(SessionManager.KEY_SESSIONPASSWORD));
        }

    }
    public void LetTheUserLogIn(View view){


        if(!isConnected(this)){
            showCustomDialog();
        }
        if(!validatePhoneNo() | !validatePassword()){
            return;
        }
        String _phoneNo = phoneNumber.getEditText().getText().toString().trim();
        final String _password = password.getEditText().getText().toString().trim();
        if (_phoneNo.charAt(0)=='0'){
            _phoneNo=_phoneNo.substring(1);
        }

        final String completePhoneNo = "+"+countryCodePicker.getFullNumber()+_phoneNo;

        if (rememberMe.isChecked()){
            SessionManager sessionManager = new SessionManager(LoginActivity.this,SessionManager.SESSION_REMEMBERMESESSSION);
            sessionManager.createRememberMeSession(_phoneNo,_password);
        }

        Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(completePhoneNo);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    phoneNumber.setError(null);
                    phoneNumber.setErrorEnabled(false);

                    String systemPassword = dataSnapshot.child(completePhoneNo).child("password").getValue(String.class);
                    if (systemPassword.equals(_password)){
                        password.setError(null);
                        password.setErrorEnabled(false);

                        String _fullname = dataSnapshot.child(completePhoneNo).child("fullname").getValue(String.class);
                        String _username = dataSnapshot.child(completePhoneNo).child("username").getValue(String.class);
                        String _email = dataSnapshot.child(completePhoneNo).child("email").getValue(String.class);
                        String _password = dataSnapshot.child(completePhoneNo).child("password").getValue(String.class);
                        String _gender = dataSnapshot.child(completePhoneNo).child("gender").getValue(String.class);
                        String _date = dataSnapshot.child(completePhoneNo).child("date").getValue(String.class);
                        String _phoneNo = dataSnapshot.child(completePhoneNo).child("phoneNo").getValue(String.class);

                        SessionManager sessionManager = new SessionManager(LoginActivity.this,SessionManager.SESSION_USERSESSSION);
                        sessionManager.createLoginSession(_fullname,_username,_email,_password,_gender,_date,_phoneNo);
                        startActivity(new Intent(getApplicationContext(), RetailerDashboardd.class));


                        Toast.makeText(LoginActivity.this, "Currently this app is under maintainence \n We will reach u back Shortly :)", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(LoginActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(LoginActivity.this, "No such User exists", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(LoginActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage("Please connect to the internet to proceed further")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(),RetailerDashboard.class));
                        finish();
                    }
                });
    }

    private boolean isConnected(LoginActivity loginActivity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) loginActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifiConn!=null && wifiConn.isConnected())||(mobileConn!=null&& mobileConn.isConnected())){
            return true;
        }else {
            return false;
        }
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
    private boolean validatePassword() {
        String val = password.getEditText().getText().toString().trim();
        String checkPassword = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=S+$)" +           //no white spaces
                //".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            password.setError("Field can not be empty");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    public void callforgetPassword(View view){
        startActivity(new Intent(getApplicationContext(),ForgetPassword.class));
    }
    public void createAccount(View view){
        startActivity(new Intent(getApplicationContext(),RetailerSignupActivity.class));
        finish();
    }
}