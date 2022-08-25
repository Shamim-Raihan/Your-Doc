package com.example.ajhdsajkf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class PatientEditProfile extends AppCompatActivity {

    private EditText firstName, lastName, phone, age;
    private Spinner location;
    private Button editProfile;


    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    private String uid;

    private String First_name, Last_name, Location, Phone, Age;

    private String[] Area;
    private String area;

    private String e_first_name, e_last_name, e_location, e_phone, e_age;


    private LinearLayout topLayout;
    private TextView patient_profile_txt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_edit_profile);


        firstName = findViewById(R.id.PatientEditProfileFirstNameID);
        lastName = findViewById(R.id.PatientEditProfileLastNameID);
        location = findViewById(R.id.PatientEditProfileLocationID);
        phone = findViewById(R.id.PatientEditProfilePhoneID);
        age = findViewById(R.id.PatientEditProfileAgeID);
        editProfile = findViewById(R.id.PatientProfileEditButtonID);
        topLayout = findViewById(R.id.top_layout);
        patient_profile_txt = findViewById(R.id.toolbar_txt);

        topLayout.setAnimation(AnimationUtils.loadAnimation(PatientEditProfile.this, R.anim.slide_down));
        patient_profile_txt.setAnimation(AnimationUtils.loadAnimation(PatientEditProfile.this, R.anim.slide_left));

        Area = getResources().getStringArray(R.array.area);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();


        if (firebaseUser != null)
        {

            uid = mAuth.getCurrentUser().getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference("users");

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    First_name = snapshot.child(uid).child("firstname").getValue(String.class);
                    Last_name = snapshot.child(uid).child("lastname").getValue(String.class);
                    Location = snapshot.child(uid).child("location").getValue(String.class);
                    Phone = snapshot.child(uid).child("phone").getValue(String.class);
                    Age = snapshot.child(uid).child("age").getValue(String.class);


                    firstName.setText(First_name);
                    lastName.setText(Last_name);
                    phone.setText(Phone);
                    age.setText(Age);



                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



        }

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.spinner_sample_layout, R.id.spinnerSampleID, Area);
        location.setAdapter(adapter1);
        location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                area = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                e_first_name = firstName.getText().toString();
                e_last_name = lastName.getText().toString();
                e_phone = phone.getText().toString();
                e_age = age.getText().toString();


                AlertDialog.Builder builder1 = new AlertDialog.Builder(PatientEditProfile.this);
                builder1.setMessage("Are You Sure ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                                if (!e_first_name.equals(First_name))
                                {
                                    databaseReference.child(uid).child("firstname").setValue(e_first_name);
                                }

                                if (!e_last_name.equals(Last_name))
                                {
                                    databaseReference.child(uid).child("lastname").setValue(e_last_name);
                                }

                                if (!area.equals("Select Area"))
                                {
                                    databaseReference.child(uid).child("location").setValue(area);
                                }

                                if (!e_phone.equals(Phone))
                                {
                                    databaseReference.child(uid).child("phone").setValue(e_phone);
                                }

                                if (!e_age.equals(Age))
                                {
                                    databaseReference.child(uid).child("age").setValue(e_age);
                                }



                                Intent intent = new Intent(PatientEditProfile.this, MainActivity.class);
                                startActivity(intent);

                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

            }
        });
    }

}
