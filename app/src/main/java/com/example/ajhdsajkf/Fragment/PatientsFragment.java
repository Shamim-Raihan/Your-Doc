package com.example.ajhdsajkf.Fragment;

import android.content.Intent;
import android.media.Image;
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
import com.example.ajhdsajkf.Adapter.PatientAdapter;
import com.example.ajhdsajkf.PatientSignUp;
import com.example.ajhdsajkf.Patients;
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


public class PatientsFragment extends Fragment {

    private RecyclerView recyclerView;
    private PatientAdapter patientAdapter;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    private List<Patients> patientList;
    ImageView addPatient;
    TextView patientCount;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patients, container, false);

        recyclerView = view.findViewById(R.id.user_control_patient_fragment_recyclerView_id);
        addPatient = view.findViewById(R.id.addPatientId);
        patientCount = view.findViewById(R.id.patientCount);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        patientList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.orderByChild("userCategory").equalTo("patient").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                patientList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Patients patients = dataSnapshot.getValue(Patients.class);
                    patientList.add(patients);
                }
                patientAdapter = new PatientAdapter(getContext(), patientList);
                recyclerView.setAdapter(patientAdapter);
                patientCount.setText("Patients : " + String.valueOf(patientList.size()));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });



        addPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PatientSignUp.class);
                startActivity(intent);
            }
        });
        return view;
    }
}