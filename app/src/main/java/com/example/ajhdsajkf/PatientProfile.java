package com.example.ajhdsajkf;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class PatientProfile extends AppCompatActivity {

    private ImageView Image;
    private TextView Name, Location, Email, Phone, Age;
    private Button EditProfile;

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    private String image, user_name, last_name, location, email, phone, age;

    private LinearLayout topLayout;
    private LinearLayout bottomLayout;
    private TextView patient_profile_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);


        Image = findViewById(R.id.PatientProfleImageID);
        Name = findViewById(R.id.PatientProfleNameID);
        Location = findViewById(R.id.PatientProfleLocationID);
        Email = findViewById(R.id.PatientProfleEmailID);
        Phone = findViewById(R.id.PatientProflePhoneID);
        Age = findViewById(R.id.PatientProfileAgeID);
        EditProfile = findViewById(R.id.PatientEditProfileButtonID);
        topLayout = findViewById(R.id.top_layout);
        bottomLayout = findViewById(R.id.bottom_layout);
        patient_profile_txt = findViewById(R.id.toolbar_txt);

        topLayout.setAnimation(AnimationUtils.loadAnimation(PatientProfile.this, R.anim.fade_scale_animation));
        bottomLayout.setAnimation(AnimationUtils.loadAnimation(PatientProfile.this, R.anim.zoom_in));
        patient_profile_txt.setAnimation(AnimationUtils.loadAnimation(PatientProfile.this, R.anim.slide_left));


        Bundle bundle = getIntent().getExtras();

        if (bundle != null)
        {
            image = bundle.getString("image");
            user_name = bundle.getString("name");
            last_name = bundle.getString("last_name");
            location = bundle.getString("location");
            email = bundle.getString("email");
            phone = bundle.getString("phone");
            age = bundle.getString("age");
        }



        Picasso.get()
                .load(image)
                .fit()
                .centerCrop()
                .into(Image);
        Name.setText(user_name +" " + last_name);
        Location.setText(location);
        Email.setText(email);
        Phone.setText(phone);
        Age.setText(age);




        EditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientProfile.this, PatientEditProfile.class);
                startActivity(intent);
            }
        });

        Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialogBox(image);

            }
        });




    }

    private void showDialogBox(String Iamge_n) {


        Dialog dialog = new Dialog(PatientProfile.this);

        dialog.setContentView(R.layout.image_dialog_box);

        ImageView Image = dialog.findViewById(R.id.DialogImageID);

        Picasso.get()
                .load(image)
                .into(Image);

        dialog.show();


    }

}
