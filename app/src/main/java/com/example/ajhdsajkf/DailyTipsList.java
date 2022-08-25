package com.example.ajhdsajkf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DailyTipsList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DailyTipsAdaper dailyTipsAdaper;
    private List<DailyTips> DailyTipsList;

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_tips_list);

        recyclerView = findViewById(R.id.DailyTipsRecyclerViewID);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DailyTipsList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("DailyTips");

        databaseReference.orderByChild("key1").equalTo("dailyTip-list" ).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                    DailyTips dailyTips = dataSnapshot1.getValue(DailyTips.class);
                    DailyTipsList.add(dailyTips);
                }

                dailyTipsAdaper = new DailyTipsAdaper(DailyTipsList.this, DailyTipsList);
                recyclerView.setAdapter(dailyTipsAdaper);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getApplicationContext(), "Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


        }


}
