
package com.example.cityguide.Common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cityguide.HelperClasses.SliderAdapter;
import com.example.cityguide.R;
import com.example.cityguide.User.UserDashboard;

public class OnBoarding extends AppCompatActivity {
    ViewPager slider;
    LinearLayout dots;
    SliderAdapter sliderAdapter;
    TextView[] dot;
    Button letsGetStarted;
    Animation animation;int currentPos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_on_boarding);
        slider = findViewById(R.id.slider);
        dots = findViewById(R.id.dots);
        letsGetStarted = findViewById(R.id.get_started);

        sliderAdapter = new SliderAdapter(this);
        slider.setAdapter(sliderAdapter);
        addDots(0);
        slider.addOnPageChangeListener(changeListener);
    }
    public void skip(View view){
        startActivity(new Intent(getApplicationContext(), UserDashboard.class));
    }
    public void next(View view){
        slider.setCurrentItem(currentPos +1);
    }

    private void addDots(int position){
        dot = new TextView[4];
        dots.removeAllViews();
        for (int i=0;i<dot.length;i++){
            dot[i]= new TextView(this);
            dot[i].setText(Html.fromHtml("&#8226"));

            dots.addView(dot[i]);
        }
        if (dot.length>0){
            dot[position].setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }
    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);
            currentPos=position;
            if (position==0){
                letsGetStarted.setVisibility(View.INVISIBLE);
            }else if (position==1){
                letsGetStarted.setVisibility(View.INVISIBLE);
            }else if (position==2){
                letsGetStarted.setVisibility(View.INVISIBLE);
            }else {
                animation = AnimationUtils.loadAnimation(OnBoarding.this,R.anim.bottom_anim);
                letsGetStarted.setAnimation(animation);
                letsGetStarted.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}