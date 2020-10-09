package com.example.cityguide.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cityguide.R;
import com.example.cityguide.User.UserDashboard;

public class SplashScreen extends AppCompatActivity {
    ImageView splash_image;
    TextView powered;
    SharedPreferences onlineBoarding;
    Animation side_anim,bottom_anim;
    private static int splash_timer=5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);

        splash_image=findViewById(R.id.backgroun_image);
        powered= findViewById(R.id.powered_line);

        side_anim = AnimationUtils.loadAnimation(this,R.anim.side_anim);
        bottom_anim = AnimationUtils.loadAnimation(this,R.anim.bottom_anim);

        splash_image.setAnimation(side_anim);
        powered.setAnimation(bottom_anim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onlineBoarding= getSharedPreferences("onBoarding",MODE_PRIVATE);
                boolean isFirstTime = onlineBoarding.getBoolean("firstTime",true);
                if (isFirstTime){
                    SharedPreferences.Editor editor= onlineBoarding.edit();
                    editor.putBoolean("firstTime",false);
                    editor.commit();
                    Intent intent=new Intent(getApplicationContext(), OnBoarding.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent=new Intent(getApplicationContext(), UserDashboard.class);
                    startActivity(intent);
                    finish();
                }
            }
        },splash_timer);

    }
}