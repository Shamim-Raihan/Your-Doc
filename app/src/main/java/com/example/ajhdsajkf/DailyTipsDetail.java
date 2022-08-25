package com.example.ajhdsajkf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class DailyTipsDetail extends AppCompatActivity {

    private TextView title;
    private ImageView image;
    private TextView des;
    private String Title, Image, Des;
    LinearLayout linearLayout;

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_tips_detail);

        title = findViewById(R.id.DailyTipsDetailTitleID);
        image = findViewById(R.id.DailyTipsDetailImageID);
        des = findViewById(R.id.DailyTipsDetailDesID);
        linearLayout = findViewById(R.id.top_layout);

        linearLayout.setAnimation(AnimationUtils.loadAnimation(DailyTipsDetail.this, R.anim.zoom_in_fade_in));

        Bundle bundle = getIntent().getExtras();

        if (bundle != null)
        {
            Title = bundle.getString("title");
            Image = bundle.getString("image");
            Des = bundle.getString("des");
        }
        title.setText(Title);
        Picasso.get()
                .load(Image)
                .into(image);

        des.setText(Des);
    }



}
