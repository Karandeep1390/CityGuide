package com.example.cityguide.Common.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cityguide.Databases.CheckInternet;
import com.example.cityguide.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class ForgetPassword extends AppCompatActivity {
    private ImageView screenIcon;
    private TextInputLayout phoneNumberTextField;
    private TextView title,desc;
    private Button nextBtn;
    private Animation animation;
    private CountryCodePicker countryCodePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forget_password);

        screenIcon = findViewById(R.id.forget_password_icon);
        title = findViewById(R.id.forget_password_title);
        desc = findViewById(R.id.forget_password_desc);
        phoneNumberTextField = findViewById(R.id.foget_password_phone_number);
        nextBtn = findViewById(R.id.nextBtn);
        countryCodePicker = findViewById(R.id.forget_password_countryCodePicker);

        animation = AnimationUtils.loadAnimation(this,R.anim.side_anim);

        screenIcon.setAnimation(animation);
        title.setAnimation(animation);
        desc.setAnimation(animation);
        phoneNumberTextField.setAnimation(animation);
        nextBtn.setAnimation(animation);
        countryCodePicker.setAnimation(animation);
    }
    public void callOtp(View view){
        CheckInternet checkInternet = new CheckInternet();
        if (!checkInternet.isConnected(this)){
            showCustomDialog();
            return;
        }
        if (!validatePhoneNo()){
            return;
        }
        String _phoneNo = phoneNumberTextField.getEditText().getText().toString().trim();
        if (_phoneNo.charAt(0)=='0'){
            _phoneNo=_phoneNo.substring(1);
        }

        final String completePhoneNo = "+"+countryCodePicker.getFullNumber()+_phoneNo;

        Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(completePhoneNo);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    phoneNumberTextField.setError(null);
                    phoneNumberTextField.setErrorEnabled(false);
                    Intent intent = new Intent(getApplicationContext(),OtpActivity.class);
                    intent.putExtra("PhoneNo.",completePhoneNo);
                    startActivity(intent);
                    finish();
                }else {
                    phoneNumberTextField.setError("No such user exists!");
                    phoneNumberTextField.requestFocus();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ForgetPassword.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean validatePhoneNo(){
        String _phoneNumber=phoneNumberTextField.getEditText().getText().toString().trim();
        if (_phoneNumber.isEmpty()){
            phoneNumberTextField.setError("Field can't be empty");
            return false;
        }else if (_phoneNumber.length()>10){
            phoneNumberTextField.setError("Enter correct Phone no.");
            return false;
        }
        else
            phoneNumberTextField.setError(null);
            phoneNumberTextField.setErrorEnabled(false);
            return true;
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ForgetPassword.this);
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
}