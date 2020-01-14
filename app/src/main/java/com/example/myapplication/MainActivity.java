package com.example.myapplication;


import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.fragment.FragExample;
import com.example.myapplication.fragment.FragExample2;
import com.example.myapplication.fragment.FragExample3;
import com.example.myapplication.fragment.FragExample4;
import com.example.myapplication.fragment.Fragment_example;
import com.example.myapplication.login.LoginActivity;
import com.google.android.material.tabs.TabLayout;



public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    public FragExample3 frag1;
    public FragExample2 frag2;
//    public FragExample frag3;
    public Fragment_example frag3;
    public FragExample4 frag4;

//    TextView main_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
        if (pref.getString("key1", "").isEmpty()) {
            finish();
        }

        Button logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = pref.edit();
                editor.remove("key1");
                editor.commit();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });

//        main_text = findViewById(R.id.main_Text);
//        main_text.setText("검사결과");

        //frag1 = new FragExample(accountName);
        frag1 = new FragExample3();
        frag2 = new FragExample2();
//        frag3 = new FragExample();
        frag3 = new Fragment_example();
        frag4 = new FragExample4();

        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());


        // Add Fragment and set tab name
        adapter.AddFragment(frag1, "");
        adapter.AddFragment(frag2, "");
        adapter.AddFragment(frag3, "");
        adapter.AddFragment(frag4, "");
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        // Set tab image
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_icon_1_zoom);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_world);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_icon_2_send);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_icon_4single);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                changeView(pos);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // do nothing
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // do nothing
            }
        });

        // Remove Shadow From the action bar
        ActionBar actionBar = getSupportActionBar();
//        actionBar.setElevation(0);
    }

    private void changeView(int index) {
        switch (index) {
            case 0:
                viewPager.setCurrentItem(0);
                break;
            case 1:
                viewPager.setCurrentItem(1);
                break;
            case 2:
                viewPager.setCurrentItem(2);
                break;
            case 3:
                viewPager.setCurrentItem(3);
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

