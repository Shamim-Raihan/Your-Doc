package com.example.ajhdsajkf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DayList extends AppCompatActivity {

    private Button saturday, sunday, monday, tuesday, wednesday, thursday, friday;
    private String uid;

    private String image, name, specialist, qualification,chamber, visitingHour, email, phone, fee, userCategory;

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_list);


        saturday = findViewById(R.id.saturday);
        sunday = findViewById(R.id.sunday);
        monday = findViewById(R.id.monday);
        tuesday = findViewById(R.id.tuesday);
        wednesday = findViewById(R.id.wednesday);
        thursday = findViewById(R.id.thursday);
        friday = findViewById(R.id.friday);


        saturday.setAnimation(AnimationUtils.loadAnimation(DayList.this, R.anim.fade_scale_animation_for_day_list));
        sunday.setAnimation(AnimationUtils.loadAnimation(DayList.this, R.anim.fade_scale_animation_for_day_list));
        monday.setAnimation(AnimationUtils.loadAnimation(DayList.this, R.anim.fade_scale_animation_for_day_list));
        tuesday.setAnimation(AnimationUtils.loadAnimation(DayList.this, R.anim.fade_scale_animation_for_day_list));
        wednesday.setAnimation(AnimationUtils.loadAnimation(DayList.this, R.anim.fade_scale_animation_for_day_list));
        thursday.setAnimation(AnimationUtils.loadAnimation(DayList.this, R.anim.fade_scale_animation_for_day_list));
        friday.setAnimation(AnimationUtils.loadAnimation(DayList.this, R.anim.fade_scale_animation_for_day_list));


        Bundle bundle = getIntent().getExtras();

        if (bundle != null)
        {
            uid = bundle.getString("uid");
        }


        saturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DayList.this, BookNow.class);
                intent.putExtra("uid", uid);
                intent.putExtra("day", "saturday");
                startActivity(intent);
            }
        });

        sunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DayList.this, BookNow.class);
                intent.putExtra("uid", uid);
                intent.putExtra("day", "sunday");

                startActivity(intent);
            }
        });

        monday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DayList.this, BookNow.class);
                intent.putExtra("uid", uid);
                intent.putExtra("day", "monday");
                startActivity(intent);
            }
        });

        tuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DayList.this, BookNow.class);
                intent.putExtra("uid", uid);
                intent.putExtra("day", "tuesday");
                startActivity(intent);
            }
        });

        wednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DayList.this, BookNow.class);
                intent.putExtra("uid", uid);
                intent.putExtra("day", "wednesday");
                startActivity(intent);
            }
        });

        thursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DayList.this, BookNow.class);
                intent.putExtra("uid", uid);
                intent.putExtra("day", "thursday");
                startActivity(intent);
            }
        });

        friday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DayList.this, BookNow.class);
                intent.putExtra("uid", uid);
                intent.putExtra("day", "friday");
                startActivity(intent);
            }
        });

    }

}
