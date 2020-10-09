package com.example.cityguide.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import com.example.cityguide.R;

public class RetailerDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_dashboard);
    }
    public void callLogin(View view){
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View,String>(findViewById(R.id.login_btn),"transition_login");
        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(RetailerDashboard.this,pairs);
        startActivity(intent,activityOptions.toBundle());
    }
    public void callSignup(View view){
        Intent intent = new Intent(getApplicationContext(),RetailerSignupActivity.class);
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View,String>(findViewById(R.id.signup_btn),"transition_signup");
        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(RetailerDashboard.this,pairs);
        startActivity(intent,activityOptions.toBundle());
    }
}