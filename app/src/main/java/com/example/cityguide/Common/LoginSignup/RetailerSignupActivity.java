package com.example.cityguide.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cityguide.R;
import com.google.android.material.textfield.TextInputLayout;

public class RetailerSignupActivity extends AppCompatActivity {
    ImageView backbtn;
    Button login,next;
    TextView titletext;

    TextInputLayout fullName,username,email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_retailer_signup);

        backbtn=findViewById(R.id.signup_back_button);
        login=findViewById(R.id.signup_login_button);
        next=findViewById(R.id.signup_next_button);
        titletext=findViewById(R.id.signup_title_text);

        fullName = findViewById(R.id.signup_fullname);
        username = findViewById(R.id.signup_username);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);
    }
    public void signupcallnextscreen(View view){
        if (!validateFullName() | !validateEmail() | !validatePassword() | !validateUsername()){
            return;
        }  else {
            String val= fullName.getEditText().getText().toString();
            String usernam= username.getEditText().getText().toString();
            String emai= email.getEditText().getText().toString();
            String passwor= password.getEditText().getText().toString();

            Intent intent = new Intent(getApplicationContext(), Signup2ndclass.class);
            intent.putExtra("fullname", val);
            intent.putExtra("username", usernam);
            intent.putExtra("email", emai);
            intent.putExtra("password", passwor);

            Pair[] pairs = new Pair[4];
            pairs[0] = new Pair<View, String>(backbtn, "transition_back_btn");
            pairs[1] = new Pair<View, String>(login, "transition_login_btn");
            pairs[2] = new Pair<View, String>(next, "transition_next_btn");
            pairs[3] = new Pair<View, String>(titletext, "transition_title_text");
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RetailerSignupActivity.this, pairs);
            startActivity(intent, options.toBundle());
        }

    }

    private boolean validateFullName() {
        String val = fullName.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            fullName.setError("Field can not be empty");
            return false;
        } else {
            fullName.setError(null);
            fullName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUsername() {
        String val = username.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            username.setError("Field can not be empty");
            return false;
        } else if (val.length() > 20) {
            username.setError("Username is too large!");
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        String val = email.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if (val.isEmpty()) {
            email.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            email.setError("Invalid Email!");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
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
    public void login(View view){
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
    }
}