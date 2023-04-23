package com.example.silverstore_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.silverstore_app.adapter.ViewPagerAdapter;
import com.example.silverstore_app.model.Account;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class StoreActivity extends AppCompatActivity {

    private Account objAccount;
    private ViewPager2 viewPager2;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        reference();

        //get account login
        Intent intent = getIntent();
        objAccount = (Account) intent.getSerializableExtra("objAccount");

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(adapter);

        //set checked icon when change tab
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.tabHome).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.tabCart).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.tabNoti).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.tabUser).setChecked(true);
                        break;
                }
            }
        });

        //set event click item on bottom navigation
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.tabHome:
                        viewPager2.setCurrentItem(0);
                        break;
                    case R.id.tabCart:
                        viewPager2.setCurrentItem(1);
                        break;
                    case R.id.tabNoti:
                        viewPager2.setCurrentItem(2);
                        break;
                    case R.id.tabUser:
                        viewPager2.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });

    }

    private void reference(){
        viewPager2 = findViewById(R.id.viewPager2);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
    }

}