package com.example.cityguide.LocationOwner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.example.cityguide.Databases.SessionManager;
import com.example.cityguide.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.HashMap;

public class RetailerDashboardd extends AppCompatActivity {
    ChipNavigationBar chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_dashboardd);
        chipNavigationBar=findViewById(R.id.btm_nav_menu);
        chipNavigationBar.setItemSelected(R.id.btm_nav_dashboard,true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new RetailerDashboardFragment()).commit();
        bottomMenu();
    }

    private void bottomMenu() {
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i){
                    case R.id.btm_nav_dashboard:
                        fragment = new RetailerDashboardFragment();
                        break;
                    case R.id.btm_nav_manage:
                        fragment = new RetailerManageFragment();
                        break;
                    case R.id.btm_nav_profile:
                        fragment = new RetailerProfileFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
        });
    }
}