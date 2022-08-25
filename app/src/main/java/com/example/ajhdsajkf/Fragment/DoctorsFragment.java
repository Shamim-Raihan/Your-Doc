package com.example.ajhdsajkf.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ajhdsajkf.Adapter.DoctorAdapter;
import com.example.ajhdsajkf.DoctorSignUp;
import com.example.ajhdsajkf.Doctors;
import com.example.ajhdsajkf.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class DoctorsFragment extends Fragment {

    private RecyclerView recyclerView;
    private DoctorAdapter doctorAdapter;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    private List<Doctors> doctorList;
    private ImageView addDoctor;
    private TextView doctorCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctors, container, false);

        recyclerView = view.findViewById(R.id.user_control_doctor_fragment_recyclerView_id);
        addDoctor = view.findViewById(R.id.addDoctorId);
        doctorCount = view.findViewById(R.id.doctorCount);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        doctorList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.orderByChild("userCategory").equalTo("doctor").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                doctorList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Doctors doctors = dataSnapshot.getValue(Doctors.class);
                    doctorList.add(doctors);
                }
                doctorAdapter = new DoctorAdapter(getContext(), doctorList);
                recyclerView.setAdapter(doctorAdapter);
                doctorCount.setText("Doctors : " + String.valueOf(doctorList.size()));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });




        addDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DoctorSignUp.class);
                startActivity(intent);
            }
        });



        return view;
    }
}