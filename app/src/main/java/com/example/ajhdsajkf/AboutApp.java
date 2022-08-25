package com.example.ajhdsajkf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AboutApp extends AppCompatActivity {

    LinearLayout topLayout;
    LinearLayout bottomLayout;
    FirebaseUser firebaseUser;
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);

        topLayout = findViewById(R.id.top_layout);
        bottomLayout = findViewById(R.id.bottom_layout);
        toolbar = findViewById(R.id.toolbar_id);
        toolbar.setTitle("");
        topLayout.setAnimation(AnimationUtils.loadAnimation(AboutApp.this, R.anim.slide_down));
        bottomLayout.setAnimation(AnimationUtils.loadAnimation(AboutApp.this, R.anim.slide_left));
    }
}
