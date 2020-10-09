package com.example.cityguide.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cityguide.R;

import java.util.Calendar;

public class Signup2ndclass extends AppCompatActivity {
    ImageView backbtn;
    Button login,next;
    TextView titletext;
    RadioGroup radioGroup;
    RadioButton slectedGender;
    DatePicker datePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup2ndclass);

        backbtn = findViewById(R.id.signup_back_button);
        login = findViewById(R.id.signup_login_button);
        next = findViewById(R.id.signup_next_button);
        titletext = findViewById(R.id.signup_title_text);

        radioGroup = findViewById(R.id.radiogroup);
        datePicker = findViewById(R.id.agePicker);
    }

        public void signupcallnextscreen (View view){
            if (!validateRadioGroup() | !validateAge()){
                return;
            }else {

                String _fullname= getIntent().getStringExtra("fullname");
                String _username= getIntent().getStringExtra("username");
                String _email= getIntent().getStringExtra("email");
                String _password= getIntent().getStringExtra("password");

                slectedGender=findViewById(radioGroup.getCheckedRadioButtonId());
                String _selectGender= slectedGender.getText().toString();

                int day= datePicker.getDayOfMonth();
                int month= datePicker.getMonth();
                int year = datePicker.getYear();

                String date = day + "/" + month + "/" +year;

                Intent intent = new Intent(getApplicationContext(), Signup3rdClass.class);
                intent.putExtra("fullname",_fullname);
                intent.putExtra("username",_username);
                intent.putExtra("email",_email);
                intent.putExtra("password",_password);
                intent.putExtra("selectGender",_selectGender);
                intent.putExtra("date",date);
                Pair[] pairs = new Pair[4];
                pairs[0] = new Pair<View, String>(backbtn, "transition_back_btn");
                pairs[1] = new Pair<View, String>(login, "transition_login_btn");
                pairs[2] = new Pair<View, String>(next, "transition_next_btn");
                pairs[3] = new Pair<View, String>(titletext, "transition_title_text");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Signup2ndclass.this, pairs);
                startActivity(intent, options.toBundle());
            }

        }
        private boolean validateRadioGroup(){
        if (radioGroup.getCheckedRadioButtonId()==-1){
            Toast.makeText(this, "Please select Gender!", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
        }
        private boolean validateAge(){
        int currentYear= Calendar.getInstance().get(Calendar.YEAR);
        int userAge = datePicker.getYear();
        int validationAge = currentYear-userAge;
        if (validationAge<14){
            Toast.makeText(this, "You are not eligible to apply!", Toast.LENGTH_SHORT).show();
            return false;
        }else
            return true;
        }
    public void login(View view){
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
    }
    }