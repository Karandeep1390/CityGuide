package com.example.cityguide.Common.LoginSignup;

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

import com.example.cityguide.Databases.CheckInternet;
import com.example.cityguide.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SetNewPassword extends AppCompatActivity {
    ImageView icon;
    TextView title,desc;
    TextInputLayout password,reEnterPassword;
    Button setNewPasswordbtn;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_set_new_password);
        icon=findViewById(R.id.set_new_password_icon);
        title=findViewById(R.id.set_new_password_title);
        desc=findViewById(R.id.set_new_password_desc);
        password=findViewById(R.id.set_new_password);
        reEnterPassword=findViewById(R.id.set_new_password_reenter);
        setNewPasswordbtn=findViewById(R.id.set_new_password_btn);

        animation = AnimationUtils.loadAnimation(this,R.anim.side_anim);

        icon.setAnimation(animation);
        title.setAnimation(animation);
        desc.setAnimation(animation);
        password.setAnimation(animation);
        reEnterPassword.setAnimation(animation);
        setNewPasswordbtn.setAnimation(animation);
    }
    public void SetNewPasswordBtn(View view){
        CheckInternet checkInternet = new CheckInternet();
        if (!checkInternet.isConnected(this)){
            showCustomDialog();
            return;
        }
        if (!validatePassword() | !validateConfirmPassword()){
            return;
        }
        String _password = password.getEditText().getText().toString().trim();
        String _phoneNo = getIntent().getStringExtra("phoneNo");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(_phoneNo).child("password").setValue(_password);
        startActivity(new Intent(getApplicationContext(),ForgetpasswordSuccessful.class));
        finish();
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
    private boolean validateConfirmPassword() {
        String val = reEnterPassword.getEditText().getText().toString().trim();
        String valu = password.getEditText().getText().toString().trim();
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
            reEnterPassword.setError("Field can not be empty");
            return false;
        } else if (!val.equals(valu)){
            reEnterPassword.setError("Password do not Match");
            return false;
        }else {
            reEnterPassword.setErrorEnabled(false);
            reEnterPassword.setError(null);
            return true;
        }
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SetNewPassword.this);
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