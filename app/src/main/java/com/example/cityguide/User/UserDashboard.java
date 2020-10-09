package com.example.cityguide.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.cityguide.AllCategories;
import com.example.cityguide.Common.LoginSignup.LoginActivity;
import com.example.cityguide.Common.LoginSignup.RetailerDashboard;
import com.example.cityguide.HelperClasses.HomeAdapterClass.CatAdapter;
import com.example.cityguide.HelperClasses.HomeAdapterClass.CatHelperClass;
import com.example.cityguide.HelperClasses.HomeAdapterClass.FeaturedAdapter;
import com.example.cityguide.HelperClasses.HomeAdapterClass.FeaturedHelperClass;
import com.example.cityguide.HelperClasses.HomeAdapterClass.MvAdapter;
import com.example.cityguide.HelperClasses.HomeAdapterClass.MvHelperClass;
import com.example.cityguide.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class UserDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    static final float END_SCALE = 0.7f;
    RecyclerView featured_recycler,mv_recycler,categories_recycler;
    RecyclerView.Adapter adapter;
    ImageView menu_icon;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    LinearLayout contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_dashboard);

        featured_recycler = findViewById(R.id.featured_recycler);
        mv_recycler= findViewById(R.id.mv_recycler);
        categories_recycler= findViewById(R.id.categories_recycler);
        menu_icon = findViewById(R.id.menu_icon);
        contentView=findViewById(R.id.content);

        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.design_navigation_view);

        navigationDrawer();

        feature_recycler();
        mv_recycler();
        categories_recycler();
    }

    private void navigationDrawer() {



        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        menu_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else
                    drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        animateNavigateDrawer();
    }

    private void animateNavigateDrawer() {
        drawerLayout.setScrimColor(getResources().getColor(R.color.colorPrimaryDark));
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });
    }
    public void RetailerCall(View view){
        startActivity(new Intent(getApplicationContext(), RetailerDashboard.class));
    }


    private void categories_recycler() {
        categories_recycler.setHasFixedSize(true);
        categories_recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        ArrayList<CatHelperClass> catLocations = new ArrayList<>();
        catLocations.add(new CatHelperClass(R.drawable.ic_baseline_restaurant_24,"Restaurants"));
        catLocations.add(new CatHelperClass(R.drawable.bank,"Banks"));
        catLocations.add(new CatHelperClass(R.drawable.ic_baseline_shopping_cart_24,"Shops"));
        catLocations.add(new CatHelperClass(R.drawable.ic_baseline_local_airport_24,"Airports"));
        catLocations.add(new CatHelperClass(R.drawable.ic_baseline_hotel_24,"Hotels"));
        catLocations.add(new CatHelperClass(R.drawable.ic_baseline_school_24,"Institutes"));

        adapter= new CatAdapter(catLocations);
        categories_recycler.setAdapter(adapter);
    }

    private void mv_recycler() {
        mv_recycler.setHasFixedSize(true);
        mv_recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        ArrayList<MvHelperClass> mvLocations = new ArrayList<>();
        mvLocations.add(new MvHelperClass(R.drawable.mcd,"Mcdonald's"));
        mvLocations.add(new MvHelperClass(R.drawable.railway,"Railway Station"));
        mvLocations.add(new MvHelperClass(R.drawable.mosque,"Darul Uloom"));
        adapter = new MvAdapter(mvLocations);
        mv_recycler.setAdapter(adapter);

    }

    private void feature_recycler() {
        featured_recycler.setHasFixedSize(true);
        featured_recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        ArrayList<FeaturedHelperClass> featuredLocation= new ArrayList<>();
        featuredLocation.add(new FeaturedHelperClass(R.drawable.mcd,"Mcdonald's","dndanlkbjnknbqbbfwbkjfewb jb, dnNMn/k C NN  vnv"));
        featuredLocation.add(new FeaturedHelperClass(R.drawable.railway,"Railway Station","dndanlkbjnknbqbbfwbkjfewb jb, dnNMn/k C NN  vnv"));
        featuredLocation.add(new FeaturedHelperClass(R.drawable.mosque,"Darul Uloom","dndanlkbjnknbqbbfwbkjfewb jb, dnNMn/k C NN  vnv"));

        adapter = new FeaturedAdapter(featuredLocation);
        featured_recycler.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_allCategories:
                startActivity(new Intent(getApplicationContext(), AllCategories.class));
                break;
            case R.id.nav_login:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
        return true;
    }
}