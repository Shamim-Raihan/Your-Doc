package com.example.ajhdsajkf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EventDetail extends AppCompatActivity {

    private String title, image, description, date, location, time, doctor;

    private TextView Title;
    private ImageView posterImage;
    private TextView Description;
    private TextView Date;
    private TextView Location;
    private TextView Time;
    private TextView doctorName;

    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private List<Doctors> DoctorList;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseAuth mAuth;
    private String uid;
    private String userCategory = "null";

    private LinearLayout topLayout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        Title = findViewById(R.id.TitleID);
        posterImage = findViewById(R.id.PosterImageID);
        Description = findViewById(R.id.DescriptionID);
        Date = findViewById(R.id.DAteID);
        Location = findViewById(R.id.LocationID);
        Time = findViewById(R.id.TimeID);
        doctorName = findViewById(R.id.DoctorNameID);
        topLayout = findViewById(R.id.top_layout);

        topLayout.setAnimation(AnimationUtils.loadAnimation(EventDetail.this, R.anim.zoom_in_fade_in));

        recyclerView = findViewById(R.id.EventRecyclerID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DoctorList = new ArrayList<>();


        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();



        Bundle bundle = getIntent().getExtras();

        if(bundle != null)
        {
            title = bundle.getString("title");
            image = bundle.getString("image");
            description = bundle.getString("description");
            date = bundle.getString("date");
            location = bundle.getString("loacation");
            time = bundle.getString("time");
            doctor = bundle.getString("doctor");

        }



        Title.setText(title);

        Picasso.get()
                .load(image)
                .into(posterImage);


        Description.setText(description);
        Date.setText(date);
        Location.setText(location);
        Time.setText(time);


        if(firebaseUser != null)
        {
            uid = mAuth.getCurrentUser().getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference("users");


            if(uid.equals("yh9LTHq7WoT5UjXRwTKbDMknnaj2"))
            {
                userCategory = "patient";
            }

            else
            {

                databaseReference.orderByChild("userCategory").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        userCategory = snapshot.child(uid).child("userCategory").getValue(String.class);
                        //Toast.makeText(EventDetail.this, userCategory, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }


        }





        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        databaseReference.orderByChild("firstname").equalTo(doctor).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                    Doctors doctors = dataSnapshot1.getValue(Doctors.class);
                    DoctorList.add(doctors);
                }

                if(DoctorList.isEmpty())
                {
                    recyclerView.setVisibility(View.GONE);
                    doctorName.setText("Dr. "+doctor);
                }


                else
                {
                    doctorName.setVisibility(View.GONE);
                    myAdapter = new MyAdapter(EventDetail.this, DoctorList, userCategory);
                    recyclerView.setAdapter(myAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
