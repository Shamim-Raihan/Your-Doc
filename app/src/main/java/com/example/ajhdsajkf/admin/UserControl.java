package com.example.ajhdsajkf.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.ajhdsajkf.ChatActivity;
import com.example.ajhdsajkf.Fragment.ConnectionFragment;
import com.example.ajhdsajkf.Fragment.DoctorsFragment;
import com.example.ajhdsajkf.Fragment.PatientsFragment;
import com.example.ajhdsajkf.Fragment.UserFragment;
import com.example.ajhdsajkf.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class UserControl extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_control);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);

        UserControl.ViewPagertAdapter viewPagertAdapter = new UserControl.ViewPagertAdapter(getSupportFragmentManager());
        viewPagertAdapter.addFragment(new DoctorsFragment(), "Doctors");
        viewPagertAdapter.addFragment(new PatientsFragment(), "Patients");

        viewPager.setAdapter(viewPagertAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    class ViewPagertAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        public ViewPagertAdapter(@NonNull FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }


        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment fragment, String title){
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }




}