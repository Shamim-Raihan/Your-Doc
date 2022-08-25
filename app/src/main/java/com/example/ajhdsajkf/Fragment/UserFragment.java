package com.example.ajhdsajkf.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.ajhdsajkf.Adapter.UserAdapter;
import com.example.ajhdsajkf.Doctors;
import com.example.ajhdsajkf.Model.Users;
import com.example.ajhdsajkf.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class UserFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Doctors> usersList;
    private UserAdapter userAdapter;

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    private String userCategory;
    private String uid;

    private EditText searchView;
    private String tag = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewID);
        searchView = view.findViewById(R.id.search_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        usersList = new ArrayList<>();
        readUser();

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchUsers(charSequence.toString().toLowerCase(Locale.ROOT));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        return view;
    }


    private void searchUsers(String s) {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Query query = FirebaseDatabase.getInstance().getReference("users").orderByChild("fullName").startAt(s).endAt(s+"\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Doctors doctors = dataSnapshot.getValue(Doctors.class);

//                    assert doctors != null;
//                    assert firebaseUser != null;
//                    if (!doctors.getUid().equals(firebaseUser.getUid())){
                        usersList.add(doctors);
//                    }
                }

                userAdapter = new UserAdapter(getContext(), usersList, false);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void readUser(){

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        uid = firebaseUser.getUid();


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userCategory = snapshot.child(uid).child("userCategory").getValue(String.class);


                if (userCategory.equals("patient")){
                    databaseReference.orderByChild("userCategory").equalTo("doctor").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (searchView.getText().toString().equals("")) {
                                usersList.clear();
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    Doctors doctors = dataSnapshot.getValue(Doctors.class);
                                    assert doctors != null;
                                    String id = doctors.getUid();
                                    usersList.add(doctors);
                                }
                                userAdapter = new UserAdapter(getContext(), usersList, false);
                                recyclerView.setAdapter(userAdapter);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else if (userCategory.equals("doctor")){
                    databaseReference.orderByChild("userCategory").equalTo("patient").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (searchView.getText().toString().equals("")) {
                                usersList.clear();
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    Doctors doctors = dataSnapshot.getValue(Doctors.class);
                                    assert doctors != null;
                                    String id = doctors.getUid();

                                    usersList.add(doctors);

                                }
                                userAdapter = new UserAdapter(getContext(), usersList, false);
                                recyclerView.setAdapter(userAdapter);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}


























