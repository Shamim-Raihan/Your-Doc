package com.example.ajhdsajkf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class NearbyDoctorListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private List<Doctors> doctorsList;
    DatabaseReference databaseReference;
    private String Area, id;
    private SearchView searchView;

    FirebaseUser firebaseUser;
    String uid;
    FirebaseAuth mAuth;

    String userCategory = "null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_doctor_list);

        recyclerView = findViewById(R.id.RecyclerViewID);
        searchView = findViewById(R.id.doctorSearchId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        doctorsList = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                myAdapter.getFilter().filter(s);
                return false;
            }
        });


        final LoadingDialog loadingDialog = new LoadingDialog(NearbyDoctorListActivity.this);
        //loadingDialog.startLoadingDialog();


        Bundle bundle = getIntent().getExtras();

        if(bundle != null)
        {
            Area = bundle.getString("area");
            id = bundle.getString("id");
        }


        if(firebaseUser != null)
        {
            uid = mAuth.getCurrentUser().getUid();


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
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }



        if(id.equals("near"))
        {
            loadingDialog.startLoadingDialog();

            databaseReference.orderByChild("area").equalTo(Area).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                        Doctors doctors = dataSnapshot1.getValue(Doctors.class);
                        doctorsList.add(doctors);
                        loadingDialog.dismissDialog();

                    }
                    myAdapter = new MyAdapter(NearbyDoctorListActivity.this, doctorsList, userCategory);
                    recyclerView.setAdapter(myAdapter);

                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                    Toast.makeText(getApplicationContext(), "Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }


        else if(id.equals("catagory"))
        {
            loadingDialog.startLoadingDialog();
            databaseReference.orderByChild("specialist").equalTo(Area).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                        Doctors doctors = dataSnapshot1.getValue(Doctors.class);
                        doctorsList.add(doctors);
                        loadingDialog.dismissDialog();
                    }

                    myAdapter = new MyAdapter(NearbyDoctorListActivity.this, doctorsList, userCategory);
                    recyclerView.setAdapter(myAdapter);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                    Toast.makeText(getApplicationContext(), "Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }

        else if(id.equals("near_auto"))
        {
            loadingDialog.startLoadingDialog();

            databaseReference.orderByChild("area").equalTo(Area).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                        Doctors doctors = dataSnapshot1.getValue(Doctors.class);
                        doctorsList.add(doctors);
                        loadingDialog.dismissDialog();

                    }

                    myAdapter = new MyAdapter(NearbyDoctorListActivity.this, doctorsList, userCategory);
                    recyclerView.setAdapter(myAdapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                    Toast.makeText(getApplicationContext(), "Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }



        else if(id.equals("search"))
        {
            loadingDialog.startLoadingDialog();
            databaseReference.orderByChild("firstname").equalTo(Area).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                        Doctors doctors = dataSnapshot1.getValue(Doctors.class);
                        doctorsList.add(doctors);
                        loadingDialog.dismissDialog();

                    }

                    myAdapter = new MyAdapter(NearbyDoctorListActivity.this, doctorsList, userCategory);
                    recyclerView.setAdapter(myAdapter);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                    Toast.makeText(getApplicationContext(), "Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    }




}
