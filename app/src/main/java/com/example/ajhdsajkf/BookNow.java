package com.example.ajhdsajkf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class BookNow extends AppCompatActivity {

    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;


    private ListView saturdayList, sundayList, mondayList, tuesdayList, wednesdayList, thursdayList, fridayList, slotList;
    private String uid, day;
    private String data;
    private String[] saturdaySlot, sundaySlot, mondaySlot, tuesdaySlot, wednesdaySlot, thursdaySlot, fridaySlot;
    ArrayAdapter<String> adapter1, adapter2, adapter3, adapter4, adapter5, adapter6, adapter7;

    private String U_slot;

    private String image, name, specialist, qualification,chamber, visitingHour, email, phone, fee, userCategory;

    private String num;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_now);

        mAuth = FirebaseAuth.getInstance();

        firebaseUser = mAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        slotList = findViewById(R.id.ListViewID);


        Bundle bundle = getIntent().getExtras();

        if (bundle != null)
        {
            uid = bundle.getString("uid");
            day = bundle.getString("day");
        }


        if (day.equals("saturday"))
        {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    data = snapshot.child(uid).child("saturday").getValue(String.class);
                    saturdaySlot = data.split(",");
                    adapter1 = new ArrayAdapter<String>(BookNow.this, R.layout.sample_view_for_book, R.id.slotListID,saturdaySlot);
                    slotList.setAdapter(adapter1);

                    Book(saturdaySlot, "saturday");


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        else if (day.equals("sunday"))
        {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    data = snapshot.child(uid).child("sunday").getValue(String.class);
                    sundaySlot = data.split(",");
                    adapter1 = new ArrayAdapter<String>(BookNow.this, R.layout.sample_view_for_book, R.id.slotListID,sundaySlot);
                    slotList.setAdapter(adapter1);

                    Book(sundaySlot, "sunday");


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        else if (day.equals("monday"))
        {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    data = snapshot.child(uid).child("monday").getValue(String.class);
                    mondaySlot = data.split(",");
                    adapter1 = new ArrayAdapter<String>(BookNow.this, R.layout.sample_view_for_book, R.id.slotListID,mondaySlot);
                    slotList.setAdapter(adapter1);

                    Book(mondaySlot, "monday");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        else if (day.equals("tuesday"))
        {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    data = snapshot.child(uid).child("tuesday").getValue(String.class);
                    tuesdaySlot = data.split(",");
                    adapter1 = new ArrayAdapter<String>(BookNow.this, R.layout.sample_view_for_book, R.id.slotListID,tuesdaySlot);
                    slotList.setAdapter(adapter1);

                    Book(tuesdaySlot, "tuesday");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        else if (day.equals("wednesday"))
        {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    data = snapshot.child(uid).child("wednesday").getValue(String.class);
                    wednesdaySlot = data.split(",");
                    adapter1 = new ArrayAdapter<String>(BookNow.this, R.layout.sample_view_for_book, R.id.slotListID,wednesdaySlot);
                    slotList.setAdapter(adapter1);

                    Book(wednesdaySlot, "wednesday");

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


        else if (day.equals("thursday"))
        {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    data = snapshot.child(uid).child("thursday").getValue(String.class);
                    thursdaySlot = data.split(",");
                    adapter1 = new ArrayAdapter<String>(BookNow.this, R.layout.sample_view_for_book, R.id.slotListID,thursdaySlot);
                    slotList.setAdapter(adapter1);

                    Book(thursdaySlot, "thursday");

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        else if (day.equals("friday"))
        {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    data = snapshot.child(uid).child("friday").getValue(String.class);
                    fridaySlot = data.split(",");
                    adapter1 = new ArrayAdapter<String>(BookNow.this, R.layout.sample_view_for_book, R.id.slotListID,fridaySlot);
                    slotList.setAdapter(adapter1);

                    Book(fridaySlot, "friday");

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }






    }

    private void Book(final String[] book, final String day) {


        slotList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final String value = book[position];

                Toast.makeText(BookNow.this, value , Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder1 = new AlertDialog.Builder(BookNow.this);
                builder1.setMessage("Are You Sure ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                String up="";


                                for (int i=0; i<book.length; i++)
                                {
                                    if(book[i] == value)
                                    {
                                        continue;
                                    }
                                    else
                                    {
                                        if(up.equals(""))
                                        {
                                            up = up+book[i];
                                        }
                                        else
                                        {
                                            up = up+","+book[i];
                                        }
                                    }
                                }

                                databaseReference.child(uid).child(day).setValue(up);


//                                Intent intent = new Intent(BookNow.this, DoctorProfileForPatient.class);
//
//                                intent.putExtra("image", image);
//                                intent.putExtra("name",name);
//                                intent.putExtra("specialist", specialist);
//                                intent.putExtra("qualification", qualification);
//                                intent.putExtra("chamber", chamber);
//                                intent.putExtra("visitingHour", visitingHour);
//                                intent.putExtra("email", email);
//                                intent.putExtra("phone", phone);
//                                intent.putExtra("fee", fee);
//                                intent.putExtra("userCategory", userCategory);
//                                intent.putExtra("uid", uid);
//                                intent.putExtra("bookStatus", "booked");
//                                startActivity(intent);

                                Toast.makeText(BookNow.this, "Booked", Toast.LENGTH_SHORT).show();


                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();




            }
        });


    }
}
