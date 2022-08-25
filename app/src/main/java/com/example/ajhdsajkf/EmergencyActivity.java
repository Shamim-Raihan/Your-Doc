package com.example.ajhdsajkf;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class EmergencyActivity extends AppCompatActivity {


    private TextView ambulanceList;
    private TextView doctorList;
    private TextView bloodList;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        ambulanceList = findViewById(R.id.AmbulanceID);
        doctorList = findViewById(R.id.DoctorID);
        bloodList = findViewById(R.id.BloodID);


        ambulanceList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(EmergencyActivity.this);
                builder.setCancelable(false);
                builder.setMessage("Under Development");
                builder.setPositiveButton(Html.fromHtml("<font color='#ffffff'>"+"OK" + "</font"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


                AlertDialog alert=builder.create();
                alert.show();
            }
        });


        doctorList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(EmergencyActivity.this);
                builder.setCancelable(false);
                builder.setMessage("Under Development");
                builder.setPositiveButton(Html.fromHtml("<font color='#ffffff'>"+"OK" + "</font"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


                AlertDialog alert=builder.create();
                alert.show();
            }
        });


        bloodList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(EmergencyActivity.this);
                builder.setCancelable(false);
                builder.setMessage("Under Development");
                builder.setPositiveButton(Html.fromHtml("<font color='#ffffff'>"+"OK" + "</font"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


                AlertDialog alert=builder.create();
                alert.show();
            }
        });


    }

}
