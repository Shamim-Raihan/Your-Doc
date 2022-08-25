package com.example.ajhdsajkf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class NearbyDocPopUP extends AppCompatActivity {


    private Button automatically;
    private Button manually;
    private String Area, id;
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    private String uid_for_auto;
    private String Area_for_auto, user_name;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_doc_pop_up);


        mAuth = FirebaseAuth.getInstance();
        firebaseUser =  mAuth.getCurrentUser();




        automatically = findViewById(R.id.AutomaticallyButtonID);
        manually = findViewById(R.id.ManuallyButtonID);



        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.7), (int)(height*.28));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);




        Bundle bundle = getIntent().getExtras();

        if(bundle != null)
        {
            Area = bundle.getString("area");
            id = bundle.getString("id");
        }



        automatically.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(NearbyDocPopUP.this, NearbyDoctorListActivity.class);

                intent.putExtra("area", Area);
                intent.putExtra("id", "near_auto");

                startActivity(intent);
                finish();



            }
        });


        manually.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(NearbyDocPopUP.this, NearbyDoctorActivity.class);
                startActivity(intent);
                finish();


            }
        });

    }

}
