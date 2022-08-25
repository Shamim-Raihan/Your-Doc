package com.example.ajhdsajkf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ajhdsajkf.Message.TestActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import java.util.HashMap;
import java.util.PriorityQueue;

public class DoctorProfile extends AppCompatActivity {


    private ImageView Image;
    private TextView Name;
    private TextView Specialist;
    private TextView Qualification;
    private TextView Chember;
    private  TextView VisitingHour;
    private TextView Email;
    private TextView Phone;
    private TextView Fee;
    private TextView numberOfPatient;
    private Button editProfile;
    private TextView test;
    private String image;
    private String name;
    private String lastname;
    private String specialist;
    private String qualification;
    private String chember;
    private String visitingHour;
    private String email;
    private String phone;
    private String fee;
    private String experience;
    private String organization;
    private String area;
    private String name_edit;
    private String specialist_edit;
    private String qualification_edit;
    private String chember_edit;
    private String visiting_Hour_edit;
    private String phone_edit;
    private String fee_edit;

    private LinearLayout topLayout;
    private LinearLayout bottomLayout;
    private TextView doctor_profile_txt;

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);

        Image = findViewById(R.id.DoctorProfleImageID);
        Name = findViewById(R.id.DoctorProfleNameID);
        Specialist = findViewById(R.id.DoctorProfleSpecialistID);
        Qualification = findViewById(R.id.DoctorProfleQualificationID);
        Chember = findViewById(R.id.DoctorProfleChamberID);
        VisitingHour = findViewById(R.id.DoctorProfleTimeID);
        Email = findViewById(R.id.DoctorProfleEmailID);
        Phone = findViewById(R.id.DoctorProflePhoneID);
        editProfile = findViewById(R.id.EditProfileButtonID);
        Fee = findViewById(R.id.feeID);
        numberOfPatient = findViewById(R.id.PatientID);
        topLayout = findViewById(R.id.top_layout);
        bottomLayout = findViewById(R.id.bottom_layout);
        doctor_profile_txt = findViewById(R.id.toolbar_txt);

        topLayout.setAnimation(AnimationUtils.loadAnimation(DoctorProfile.this, R.anim.fade_scale_animation));
        bottomLayout.setAnimation(AnimationUtils.loadAnimation(DoctorProfile.this, R.anim.zoom_in));
        doctor_profile_txt.setAnimation(AnimationUtils.loadAnimation(DoctorProfile.this, R.anim.slide_left));

        Bundle bundle = getIntent().getExtras();

        if(bundle != null)
        {
            image = bundle.getString("image");
            name = bundle.getString("name");
            lastname = bundle.getString("lastname");
            specialist = bundle.getString("specialist");
            qualification = bundle.getString("qualification");
            chember = bundle.getString("chember");
            visitingHour = bundle.getString("visitingHour");
            email = bundle.getString("email");
            phone = bundle.getString("phone");
            fee = bundle.getString("fee");
            experience = bundle.getString("experience");
            organization = bundle.getString("organization");
            area = bundle.getString("area");


        }

        Picasso.get()
                .load(image)
                .fit()
                .centerCrop()
                .into(Image);


        Name.setText("Dr."+name);
        Specialist.setText(specialist);
        Qualification.setText(qualification);
        Chember.setText(chember);
        VisitingHour.setText(visitingHour);
        Email.setText(email);
        Phone.setText(phone);
        Fee.setText(fee);



        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DoctorProfile.this, EditProfile.class);

                intent.putExtra("name", name);
                intent.putExtra("lastname", lastname);
                intent.putExtra("specialist", specialist);
                intent.putExtra("qualification", qualification);
                intent.putExtra("chember", chember);
                intent.putExtra("visitingHour", visitingHour);
                intent.putExtra("phone", phone);
                intent.putExtra("fee", fee);
                intent.putExtra("experience", experience);
                intent.putExtra("organization", organization);
                intent.putExtra("area", area);


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

    private void showDialogBox(String image) {

        Dialog dialog = new Dialog(DoctorProfile.this);

        dialog.setContentView(R.layout.image_dialog_box);

        ImageView Image = dialog.findViewById(R.id.DialogImageID);

        Picasso.get()
                .load(image)
                .into(Image);

        dialog.show();


    }


}
