package com.example.ajhdsajkf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DoctorCatagoryActivity extends AppCompatActivity {

    private GridView DoctorCatagoryView;
    private String selectedCatagory;
    String[] catagory;


    int[] image = {R.drawable.cardio, R.drawable.dentist, R.drawable.hepato, R.drawable.neoro,
    R.drawable.nephro, R.drawable.ortho, R.drawable.psycho, R.drawable.pulmono};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_catagory);

        catagory = getResources().getStringArray(R.array.doctor_catagory);

        DoctorCatagoryView = (GridView) findViewById(R.id.DoctorCatagoryViewID);

        CustomAdapter adapter = new CustomAdapter(this, catagory, image);
        DoctorCatagoryView.setAdapter(adapter);



        DoctorCatagoryView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectedCatagory = catagory[position];

                Intent intent = new Intent(DoctorCatagoryActivity.this, NearbyDoctorListActivity.class);
                intent.putExtra("area", selectedCatagory);
                intent.putExtra("id", "catagory");
                startActivity(intent);

            }
        });


    }


}
