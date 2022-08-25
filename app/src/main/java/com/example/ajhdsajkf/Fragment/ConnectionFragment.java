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
import com.example.ajhdsajkf.Model.ChatList;
import com.example.ajhdsajkf.Notification.Token;
import com.example.ajhdsajkf.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;


public class ConnectionFragment extends Fragment {

    private RecyclerView recyclerView;

    private UserAdapter userAdapter;
    private List<Doctors> mUsers;

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    private List<ChatList> userList;

    private String userCategory;
    private String uid;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_connection, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewID);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userList = new ArrayList<>();


        databaseReference = FirebaseDatabase.getInstance().getReference("ChatList").child(firebaseUser.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ChatList chatList = dataSnapshot.getValue(ChatList.class);
                    userList.add(chatList);
                }
                chatList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        updateToken(FirebaseInstanceId.getInstance().getToken());

        return view;
    }



    private void updateToken(String token){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Tokens");

        Token token1 = new Token(token);
        databaseReference.child(firebaseUser.getUid()).setValue(token1);

    }






    private void chatList() {
        mUsers = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");;
        uid = firebaseUser.getUid();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userCategory = snapshot.child(uid).child("userCategory").getValue(String.class);

                if (userCategory.equals("patient")){
                    databaseReference.orderByChild("userCategory").equalTo("doctor").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            mUsers.clear();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                Doctors user = dataSnapshot.getValue(Doctors.class);
                                for (ChatList chatList : userList){
                                    assert user != null;
                                    if (user.getUid().equals(chatList.getUid())){
                                        mUsers.add(user);
                                    }
                                }
                            }

                            userAdapter = new UserAdapter(getContext(), mUsers, true);
                            recyclerView.setAdapter(userAdapter);
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
                            mUsers.clear();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                Doctors user = dataSnapshot.getValue(Doctors.class);
                                for (ChatList chatList : userList){
                                    assert user != null;
                                    if (user.getUid().equals(chatList.getUid())){
                                        mUsers.add(user);
                                    }
                                }
                            }
                            userAdapter = new UserAdapter(getContext(), mUsers, true);
                            recyclerView.setAdapter(userAdapter);
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




















