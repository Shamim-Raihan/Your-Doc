package com.example.ajhdsajkf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class EditProfile extends AppCompatActivity {


    private EditText firstName, lastName, qualification, experience, organization, chemberAddress, visitingHour;
    private Button visitingStartTime, visitingEndTime;
    private EditText phoneNumber, fee, slotEditText;
    private Button slotStartTime, slotEndTime, confirmButton, editProfileButton;
    private Spinner SpecialistSpinner, AreaSpinner, selectDaySpinner;
    private EditText Fee;

    String[] Specialist, Area, Day;
    private String specialist;
    private String area;
    private String day;

    private String name_edit;
    private String lastname_edit;
    private String specialist_edit;
    private String qualification_edit;
    private String chember_edit;
    private String visiting_hour_edit;
    private String phone_edit;
    private String fee_edit;
    private String experience_edit;
    private String organization_edit;
    private String area_edit;
    private int hour, min;

    TextView toolbar_txt;
    LinearLayout linearLayout;

    private String saturday = "", sunday = "", monday = "", tuesday = "", wednesday = "", thursday = "", friday = "";
    private String uid;

    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        firstName = findViewById(R.id.DoctorEditProfileFirstNameID);
        lastName = findViewById(R.id.DoctorEditProfileLastNameID);
        qualification = findViewById(R.id.DoctorEditProfileQualificationID);
        experience = findViewById(R.id.DoctorEditProfileExperienceID);
        organization = findViewById(R.id.DoctorEditProfileOrganizationID);
        chemberAddress = findViewById(R.id.DoctorEditProfileChemberAddressID);
        visitingHour = findViewById(R.id.DoctorEditProfileVisitingHourID);
        visitingStartTime = findViewById(R.id.EditVisitingHourStartTimeID);
        visitingEndTime = findViewById(R.id.EditVisitingHourEndTimeID);
        phoneNumber = findViewById(R.id.DoctorEditProfilePhoneID);
        fee = findViewById(R.id.DoctorEditProfileFeesID);
        slotEditText = findViewById(R.id.DoctorEditProfileShowDayID);
        slotStartTime = findViewById(R.id.DoctorEditProfileStartTimeButtonID);
        slotEndTime = findViewById(R.id.DoctorEditProfileEndTimeButtonID);
        confirmButton = findViewById(R.id.DoctorEditProfileConfirmButtonID);
        editProfileButton = findViewById(R.id.DoctorEditProfileEditButtonID);
        SpecialistSpinner = findViewById(R.id.DoctorEditProfileSpecialistID);
        AreaSpinner = findViewById(R.id.DoctorEditProfileAreaID);
        selectDaySpinner = findViewById(R.id.DoctorEditProfileSelectDayID);

        Specialist = getResources().getStringArray(R.array.specialist);
        Area = getResources().getStringArray(R.array.area);
        Day = getResources().getStringArray(R.array.day);

        toolbar_txt = findViewById(R.id.toolbar_txt);
        linearLayout = findViewById(R.id.top_layout);

        toolbar_txt.setAnimation(AnimationUtils.loadAnimation(EditProfile.this, R.anim.zoom_in_fade_in));
        linearLayout.setAnimation(AnimationUtils.loadAnimation(EditProfile.this, R.anim.slide_down));

        Bundle bundle = getIntent().getExtras();

        if(bundle != null)
        {

            name_edit = bundle.getString("name");
            lastname_edit = bundle.getString("lastname");
            specialist_edit = bundle.getString("specialist");
            qualification_edit = bundle.getString("qualification");
            chember_edit = bundle.getString("chember");
            visiting_hour_edit = bundle.getString("visitingHour");
            phone_edit = bundle.getString("phone");
            fee_edit = bundle.getString("fee");
            experience_edit = bundle.getString("experience");
            organization_edit = bundle.getString("organization");
            area_edit = bundle.getString("area");

        }



        firstName.setText(name_edit);
        lastName.setText(lastname_edit);
        qualification.setText(qualification_edit);
        experience.setText(experience_edit);
        organization.setText(organization_edit);
        chemberAddress.setText(chember_edit);
        visitingHour.setText(visiting_hour_edit);
        phoneNumber.setText(phone_edit);
        fee.setText(fee_edit);



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_sample_layout, R.id.spinnerSampleID, Specialist);
        SpecialistSpinner.setAdapter(adapter);
        SpecialistSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                specialist = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.spinner_sample_layout, R.id.spinnerSampleID, Area);
        AreaSpinner.setAdapter(adapter1);
        AreaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                area = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_sample_layout, R.id.spinnerSampleID, Day);
        selectDaySpinner.setAdapter(adapter2);
        selectDaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                day = parent.getItemAtPosition(position).toString();
                //Toast.makeText(parent.getContext(), area, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (day.equals("select day"))
                {
                    Toast.makeText(EditProfile.this, "Please select day", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(day.equals("saturday"))
                {
                    saturday = slotEditText.getText().toString();
                }
                if(day.equals("sunday"))
                {
                    sunday = slotEditText.getText().toString();
                }
                if(day.equals("monday"))
                {
                    monday = slotEditText.getText().toString();
                }
                if(day.equals("tuesday"))
                {
                    tuesday = slotEditText.getText().toString();
                }
                if(day.equals("wednesday"))
                {
                    wednesday = slotEditText.getText().toString();
                }
                if(day.equals("thursday"))
                {
                    thursday = slotEditText.getText().toString();
                }
                if(day.equals("friday"))
                {
                    friday = slotEditText.getText().toString();
                }

                slotEditText.setText("");

            }
        });




        visitingStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        EditProfile.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                hour = hourOfDay;
                                min = minute;
                                String time = hour + ":" + min;
                                SimpleDateFormat format = new SimpleDateFormat(
                                        "HH:mm"
                                );
                                try {
                                    Date date = format.parse(time);
                                    SimpleDateFormat f12h = new SimpleDateFormat(
                                            "hh:mm aa"
                                    );
                                    visitingHour.setText(f12h.format(date) + " to ");

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        },12, 0, false
                );

                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();


            }
        });


        visitingEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        EditProfile.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                hour = hourOfDay;
                                min = minute;
                                String time = hour + ":" + min;
                                SimpleDateFormat format = new SimpleDateFormat(
                                        "HH:mm"
                                );
                                try {
                                    Date date = format.parse(time);
                                    SimpleDateFormat f12h = new SimpleDateFormat(
                                            "hh:mm aa"
                                    );
                                    String pre_time = visitingHour.getText().toString();
                                    visitingHour.setText(pre_time + f12h.format(date));

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        },12, 0, false
                );

                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();




            }
        });


        slotStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        EditProfile.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                hour = hourOfDay;
                                min = minute;
                                String time = hour + ":" + min;
                                SimpleDateFormat format = new SimpleDateFormat(
                                        "HH:mm"
                                );
                                try {
                                    Date date = format.parse(time);
                                    SimpleDateFormat f12h = new SimpleDateFormat(
                                            "hh:mm aa"
                                    );
                                    String pre_time = slotEditText.getText().toString();

                                    if(pre_time.equals(""))
                                    {
                                        slotEditText.setText(pre_time + f12h.format(date) + " to ");
                                    }

                                    else
                                    {
                                        slotEditText.setText(pre_time + "," + f12h.format(date) + " to ");
                                    }



                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        },12, 0, false
                );

                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();




            }
        });



        slotEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        EditProfile.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                hour = hourOfDay;
                                min = minute;
                                String time = hour + ":" + min;
                                SimpleDateFormat format = new SimpleDateFormat(
                                        "HH:mm"
                                );
                                try {
                                    Date date = format.parse(time);
                                    SimpleDateFormat f12h = new SimpleDateFormat(
                                            "hh:mm aa"
                                    );
                                    String pre_time = slotEditText.getText().toString();
                                    slotEditText.setText(pre_time + f12h.format(date));

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        },12, 0, false
                );

                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();


            }
        });


        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String u_firstName = firstName.getText().toString();
                final String u_lastName = lastName.getText().toString();
                final String u_qualification = qualification.getText().toString();
                final String u_experience = experience.getText().toString();
                final String u_organization = organization.getText().toString();
                final String u_chemberAddress = chemberAddress.getText().toString();
                final String u_phone = phoneNumber.getText().toString();
                final String u_fee = fee.getText().toString();
                final String u_visitingHour = visitingHour.getText().toString();



                uid = mAuth.getCurrentUser().getUid();


                AlertDialog.Builder builder1 = new AlertDialog.Builder(EditProfile.this);
                builder1.setMessage("Are You Sure ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                if (!u_firstName.equals(name_edit))
                                {
                                    databaseReference.child(uid).child("firstname").setValue(u_firstName);
                                }

                                if(!u_lastName.equals(lastname_edit))
                                {
                                    databaseReference.child(uid).child("lastname").setValue(u_lastName);
                                }

                                if(!u_qualification.equals(qualification_edit))
                                {
                                    databaseReference.child(uid).child("qualification").setValue(u_qualification);
                                }

                                if (!u_experience.equals(experience_edit))
                                {
                                    databaseReference.child(uid).child("experience").setValue(u_experience);
                                }

                                if (!u_organization.equals(organization_edit))
                                {
                                    databaseReference.child(uid).child("organization").setValue(u_organization);
                                }

                                if (!u_chemberAddress.equals(chember_edit))
                                {
                                    databaseReference.child(uid).child("chamberAddress").setValue(u_chemberAddress);
                                }

                                if (!u_phone.equals(phone_edit))
                                {
                                    databaseReference.child(uid).child("phone").setValue(u_phone);
                                }

                                if (!u_fee.equals(fee_edit))
                                {
                                    databaseReference.child(uid).child("fee").setValue(u_fee);
                                }

                                if (!u_visitingHour.equals(visiting_hour_edit))
                                {
                                    databaseReference.child(uid).child("visitingHour").setValue(u_visitingHour);
                                }

                                if(!saturday.equals(""))
                                {
                                    databaseReference.child(uid).child("saturday").setValue(saturday);
                                }

                                if (!sunday.equals(""))
                                {
                                    databaseReference.child(uid).child("sunday").setValue(sunday);
                                }

                                if (!monday.equals(""))
                                {
                                    databaseReference.child(uid).child("monday").setValue(monday);
                                }

                                if (!tuesday.equals(""))
                                {
                                    databaseReference.child(uid).child("tuesday").setValue(tuesday);
                                }

                                if (!wednesday.equals(""))
                                {
                                    databaseReference.child(uid).child("wednesday").setValue(wednesday);
                                }

                                if (!thursday.equals(""))
                                {
                                    databaseReference.child(uid).child("thursday").setValue(thursday);
                                }

                                if (!friday.equals(""))
                                {
                                    databaseReference.child(uid).child("friday").setValue(friday);
                                }



                                if (!specialist.equals("Select Specialist"))
                                {
                                    databaseReference.child(uid).child("specialist").setValue(friday);
                                }

                                if(!area.equals("Select Area"))
                                {
                                    databaseReference.child(uid).child("area").setValue(friday);
                                }

                                Intent intent = new Intent(EditProfile.this, MainActivity.class);
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
