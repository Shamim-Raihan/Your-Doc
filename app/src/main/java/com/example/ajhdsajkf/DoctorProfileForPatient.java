package com.example.ajhdsajkf;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ajhdsajkf.Message.MessageActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.HashMap;

public class DoctorProfileForPatient extends AppCompatActivity {

    private ImageView Image;
    private TextView Name;
    private TextView Specialist;
    private TextView Qualification;
    private TextView Chamber;
    private  TextView VisitingHour;
    private TextView Email;
    private TextView Phone;
    private TextView Fee;
    private Button message, bookNow, confirmBook;

    private String image;
    private String name;
    private String specialist;
    private String qualification;
    private String chamber;
    private String visitingHour;
    private String email;
    private String phone;
    private String fee;
    private String userCategory;
    private String uid;
    private String bookStatus = "null";
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    int width_i, height_i;
    private LinearLayout topLayout;
    private LinearLayout bottomLayout;
    private TextView doctor_profile_txt;



    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile_for_patient);



        Image = findViewById(R.id.DoctorProfleImageID);
        Name = findViewById(R.id.DoctorProfleNameID);
        Specialist = findViewById(R.id.DoctorProfleSpecialistID);
        Qualification = findViewById(R.id.DoctorProfleQualificationID);
        Chamber = findViewById(R.id.DoctorProfleChamberID);
        VisitingHour = findViewById(R.id.DoctorProfleTimeID);
        Email = findViewById(R.id.DoctorProfleEmailID);
        Phone = findViewById(R.id.DoctorProflePhoneID);
        Fee = findViewById(R.id.feeId);
        message = findViewById(R.id.DoctorProfileMessegeButtonID);
        bookNow = findViewById(R.id.DoctorProfileBookNowButtonID);
        confirmBook = findViewById(R.id.ConfirmBookID);
        topLayout = findViewById(R.id.top_layout);
        bottomLayout = findViewById(R.id.bottom_layout);
        doctor_profile_txt = findViewById(R.id.doctor_profile_txt);

        topLayout.setAnimation(AnimationUtils.loadAnimation(DoctorProfileForPatient.this, R.anim.fade_scale_animation));
        bottomLayout.setAnimation(AnimationUtils.loadAnimation(DoctorProfileForPatient.this, R.anim.zoom_in));
        doctor_profile_txt.setAnimation(AnimationUtils.loadAnimation(DoctorProfileForPatient.this, R.anim.slide_left));

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        loadingDialog = new LoadingDialog(DoctorProfileForPatient.this);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            image = bundle.getString("image");
            name = bundle.getString("name");
            specialist = bundle.getString("specialist");
            qualification = bundle.getString("qualification");
            chamber = bundle.getString("chamber");
            visitingHour = bundle.getString("visitingHour");
            email = bundle.getString("email");
            phone = bundle.getString("phone");
            fee = bundle.getString("fee");
            userCategory = bundle.getString("userCategory");
            uid = bundle.getString("uid");
            bookStatus = bundle.getString("bookStatus");
        }

        if (userCategory.equals("patient"))
        {
            Picasso.get()
                    .load(image)
                    .fit()
                    .centerCrop()
                    .into(Image);

            Name.setText("Dr."+name);
            Specialist.setText(specialist);
            Qualification.setText(qualification);
            Chamber.setText(chamber);
            VisitingHour.setText(visitingHour);
            Email.setText(email);
            Phone.setText(phone);
            Fee.setText(fee);
        }

        else
        {
            Picasso.get()
                    .load(image)
                    .fit()
                    .centerCrop()
                    .into(Image);

            Name.setText("Dr."+name);
            Specialist.setText(specialist);
            Qualification.setText(qualification);
            Chamber.setText(chamber);
            VisitingHour.setText(visitingHour);
            Email.setText(email);
            Phone.setText(phone);
            Fee.setText(fee);
            message.setVisibility(View.GONE);
            bookNow.setVisibility(View.GONE);
        }

        bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (firebaseUser == null)
                {
                    Toast.makeText(DoctorProfileForPatient.this, "Please Log In or Sign Up", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    Intent intent = new Intent(DoctorProfileForPatient.this, DayList.class);
                    intent.putExtra("uid", uid);
                    startActivity(intent);
                }
            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorProfileForPatient.this, MessageActivity.class);
                intent.putExtra("userid", uid);
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

    public void showDialogBox(String image) {

        Dialog dialog = new Dialog(DoctorProfileForPatient.this);
        dialog.setContentView(R.layout.image_dialog_box);
        ImageView Image = dialog.findViewById(R.id.DialogImageID);
        Picasso.get()
                .load(image)
                .into(Image);
        dialog.show();
    }

}
