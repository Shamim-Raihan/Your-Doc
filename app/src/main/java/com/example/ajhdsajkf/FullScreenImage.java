package com.example.ajhdsajkf;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;

public class FullScreenImage extends AppCompatActivity {

    private String image;
    private ImageView fullImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        fullImage = findViewById(R.id.FullImageID);

        Bundle bundle = getIntent().getExtras();

        if (bundle!= null)
        {
            image = bundle.getString("image");


        }


        Picasso.get()
                .load(image)
                .into(fullImage);


    }
}
