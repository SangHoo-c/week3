package com.example.myapplication;


import android.content.Intent;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.fragment.FragExample;
import com.google.android.material.tabs.TabLayout;



public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    public FragExample frag1;
    public FragExample frag2;
    public FragExample frag3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //frag1 = new FragExample(accountName);
        frag1 = new FragExample();
        frag2 = new FragExample();
        frag3 = new FragExample();

        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());


        // Add Fragment and set tab name
        adapter.AddFragment(frag1, "");
        adapter.AddFragment(frag2, "");
        adapter.AddFragment(frag3, "");
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        // Set tab image
        tabLayout.getTabAt(0).setIcon(R.drawable.icon_default_profile);
        tabLayout.getTabAt(1).setIcon(R.drawable.icon_default_profile);
        tabLayout.getTabAt(2).setIcon(R.drawable.icon_default_profile);
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
        }    }


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

