package com.example.ajhdsajkf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private int progress;
    Animation logoAnim, textAnim;
    private ImageView logoImg;
    private TextView titleTv;
    private TextView sloganTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        logoImg = findViewById(R.id.logo_id);
        titleTv = findViewById(R.id.title_id);
        sloganTv = findViewById(R.id.slogan_id);

        logoAnim = AnimationUtils.loadAnimation(this, R.anim.splash_screen_logo_anim);
        textAnim = AnimationUtils.loadAnimation(this, R.anim.splash_screen_text_anim);

        logoImg.setAnimation(logoAnim);
        titleTv.setAnimation(textAnim);
        sloganTv.setAnimation(textAnim);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);

    }


}
