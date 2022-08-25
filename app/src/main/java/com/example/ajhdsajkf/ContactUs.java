package com.example.ajhdsajkf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ContactUs extends AppCompatActivity {

    LinearLayout topLayout;
    LinearLayout bottomLayout;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        topLayout = findViewById(R.id.top_layout);
        bottomLayout = findViewById(R.id.bottom_layout);

        topLayout.setAnimation(AnimationUtils.loadAnimation(ContactUs.this, R.anim.slide_down));
        bottomLayout.setAnimation(AnimationUtils.loadAnimation(ContactUs.this, R.anim.slide_left));
    }


}
