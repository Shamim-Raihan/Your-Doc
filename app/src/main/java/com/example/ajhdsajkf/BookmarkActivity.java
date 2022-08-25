package com.example.ajhdsajkf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class BookmarkActivity extends AppCompatActivity {

    private Button eventsButton, dailyTipsButton;
    private RecyclerView eventRecyclerview, dailyTipsRecyclerview;
    String[] eventslist;
    String[] dailyTipslist;
    private List<Events> eventsBookmarkList;
    private List<DailyTips> dailyTipsBookmarkList;
    String eventBookmark;
    String dailyTipsBookmark;

    DatabaseReference databaseReference;
    DatabaseReference databaseReference_1;
    DatabaseReference databaseReference_2;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    String uid;
    int k=0, l=0;
    String check = "0";
    String dailyTipscheck = "0";

    private EventBookmarkAdapter eventBookmarkAdapter;
    private DailyTipsBookmarkAdapter dailyTipsBookmarkAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        eventsButton = findViewById(R.id.BookmarkEventButtonID);
        dailyTipsButton = findViewById(R.id.BookmarkDailyTipsButtonID);
        eventRecyclerview = findViewById(R.id.BookmarkEventRecyclerviewID);
        dailyTipsRecyclerview = findViewById(R.id.BookmarkDailyTipsRecyclerviewID);

        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference_1 = FirebaseDatabase.getInstance().getReference("Events");
        databaseReference_2 = FirebaseDatabase.getInstance().getReference("DailyTips");
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        uid = firebaseUser.getUid();

        eventRecyclerview.setHasFixedSize(true);
        eventRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        eventsBookmarkList = new ArrayList<>();

        dailyTipsRecyclerview.setHasFixedSize(true);
        dailyTipsRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        dailyTipsBookmarkList = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null)
        {
            check = bundle.getString("check");
            dailyTipscheck = bundle.getString("dailytipscheck");
        }


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dailyTipsBookmark = snapshot.child(uid).child("dailytips").getValue(String.class);
                dailyTipslist = dailyTipsBookmark.split(",");

                for (int i=0; i<dailyTipslist.length; i++)
                {
                    databaseReference_2.orderByChild("title").equalTo(dailyTipslist[i]).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                                DailyTips dailyTips = dataSnapshot1.getValue(DailyTips.class);
                                dailyTipsBookmarkList.add(dailyTips);
                            }

                            dailyTipsBookmarkAdapter = new DailyTipsBookmarkAdapter(BookmarkActivity.this, dailyTipsBookmarkList);
                            dailyTipsRecyclerview.setAdapter(dailyTipsBookmarkAdapter);
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


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                eventBookmark = snapshot.child(uid).child("events").getValue(String.class);
                eventslist = eventBookmark.split(",");

                for (int i=0; i<eventslist.length; i++)
                {
                    databaseReference_1.orderByChild("title").equalTo(eventslist[i]).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                                Events events = dataSnapshot1.getValue(Events.class);
                                eventsBookmarkList.add(events);
                            }
                            eventBookmarkAdapter = new EventBookmarkAdapter(BookmarkActivity.this, eventsBookmarkList);
                            eventRecyclerview.setAdapter(eventBookmarkAdapter);
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

        dailyTipsButtonClick();

        eventButtonClick(check);

        BottomNavigationView bottomNavigationView = findViewById(R.id.Bottom_Navigation);
        bottomNavigationView.setSelectedItemId(R.id.bookmark_i);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.chat_i:
                        startActivity(new Intent(getApplicationContext(), ChatActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case R.id.Home_i:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case R.id.bookmark_i:
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton(Html.fromHtml("<font color='#ffffff'>"+"Yes" + "</font"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton(Html.fromHtml("<font color='#ffffff'>"+"No" + "</font"),new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert=builder.create();
        alert.show();
    }

    private void dailyTipsButtonClick() {


        if (dailyTipscheck.equals("1"))
        {
            int noOfSecond = 1;
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    dailyTipsButton.performClick();
                }
            }, noOfSecond * 0);
        }

        dailyTipsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (l==0)
                {
                    dailyTipsRecyclerview.setVisibility(View.VISIBLE);
                    l=1;
                }
                else if (l==1)
                {
                    dailyTipsRecyclerview.setVisibility(View.GONE);
                    l=0;
                }
            }
        });
    }

    private void eventButtonClick(String check) {
        if (check.equals("1"))
        {
            int noOfSecond = 1;
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    //TODO Set your button auto perform click.
                    eventsButton.performClick();
                }
            }, noOfSecond * 0);
        }

        eventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (k==0)
                {
                    eventRecyclerview.setVisibility(View.VISIBLE);
                    k=1;
                }
                else if (k==1)
                {
                    eventRecyclerview.setVisibility(View.GONE);
                    k=0;
                }
            }
        });
    }
}
