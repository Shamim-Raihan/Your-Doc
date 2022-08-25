package com.example.ajhdsajkf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.annotation.IncompleteAnnotationException;
import java.util.HashMap;


public class NearbyDoctorActivity extends AppCompatActivity {

    private GridView NearbyDoctorView;

    private String selectedArea;

    String[] areaName;

    int[] image = {R.drawable.barishal, R.drawable.chittagong, R.drawable.dhaka,R.drawable.khulna
    , R.drawable.mymensingh, R.drawable.rajshahi, R.drawable.rangpur, R.drawable.sylhet};

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_doctor2);

        areaName = getResources().getStringArray(R.array.area_2);

        NearbyDoctorView = (GridView) findViewById(R.id.NearbyDoctorViewID);

        CustomAdapter adapter = new CustomAdapter(this, areaName, image);
        NearbyDoctorView.setAdapter(adapter);


        NearbyDoctorView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectedArea = areaName[position];

                Toast.makeText(getApplicationContext(), selectedArea, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(NearbyDoctorActivity.this, NearbyDoctorListActivity.class);
                intent.putExtra("area", selectedArea);
                intent.putExtra("id", "near");
                startActivity(intent);

            }
        });



    }


}
