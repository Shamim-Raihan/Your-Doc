package com.example.ajhdsajkf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ajhdsajkf.admin.AdminPanel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity{

    private ImageView NearbyDoctorView;
    private ImageView DoctorCatagoryView;
    private ImageView EmergencyView;
    private ImageView dailyTips;
    private LinearLayout mainLayout, toolbar;
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    private String userValidation;
    private TextView loginTextView;
    private ImageView loginImageView;
    private ImageView userImage;
    private TextView UserName;
    private TextView UserEmail;
    private Button LogINButton;
    private Button LogOutButton;
    private SearchView searchView;
    private ImageView admin_panel;
    private ImageView event;
    private String userCategory;
    private String user = "";
    private String user_name, email, image, last_name, phone, chember_address, specialist, visiting_hour, qualification, fee, experience, organization;
    private String numberOfPatient, age, location;
    private String userCategoryForBottom;

    private FirebaseDatabase firebaseDatabase;
    View hview;
    String uid;
    String  Area;

    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        admin_panel = findViewById(R.id.AdminPanelID);
        EmergencyView = findViewById(R.id.EmergencyID);
        event = findViewById(R.id.EventID);
        dailyTips = findViewById(R.id.DailyTipsID);
        mainLayout = findViewById(R.id.MainLayoutID);
        toolbar = findViewById(R.id.ToolBarID);
        mAuth = FirebaseAuth.getInstance();
        firebaseUser =  mAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EventActivity.class);
                startActivity(intent);
            }
        });

        dailyTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, DailyTipsList.class);
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.Bottom_Navigation);
        bottomNavigationView.setSelectedItemId(R.id.Home_i);
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
                        return true;
                    case R.id.bookmark_i:
                        if (firebaseUser != null){
                            if (userCategory.equals("patient")){
                                startActivity(new Intent(getApplicationContext(), BookmarkActivity.class));
                                overridePendingTransition(0,0);
                                finish();
                                return true;
                            }
                            else {
                                Toast.makeText(MainActivity.this, "Bookmarks only for patient", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Please LogIn or SignUp as a patient", Toast.LENGTH_SHORT).show();
                        }
                }
                return false;
            }
        });


        NearbyDoctorView = (ImageView) findViewById(R.id.NearbyDoctorViewID);
        DoctorCatagoryView = (ImageView) findViewById(R.id.DoctorCatagoryID);
        searchView = findViewById(R.id.SearchViewID);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(MainActivity.this, NearbyDoctorListActivity.class);
                intent.putExtra("area", query);
                intent.putExtra("id", "search");
                startActivity(intent);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        final DrawerLayout drawerLayout = findViewById(R.id.DrawerLayoutID);

        findViewById(R.id.ImageMenuID).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView =  findViewById(R.id.NavigationViewID);
        hview = navigationView.getHeaderView(0);
        LogINButton = hview.findViewById(R.id.loginButtonID);
        LogOutButton = hview.findViewById(R.id.logOutButtonID);
        UserName = hview.findViewById(R.id.UserNameID);
        UserEmail = hview.findViewById(R.id.UserEmailID);
        userImage = hview.findViewById(R.id.UserImageID);

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (firebaseUser == null)
                    {
                        Toast.makeText(MainActivity.this, "Please! Log In or Sign Up", Toast.LENGTH_SHORT).show();
                    }
                    else if ((firebaseUser != null) && (userCategory.equals("doctor")))
                    {
                        Intent intent = new Intent(MainActivity.this, DoctorProfile.class);
                        intent.putExtra("image", image);
                        intent.putExtra("name", user_name);
                        intent.putExtra("lastname", last_name);
                        intent.putExtra("specialist", specialist);
                        intent.putExtra("qualification", qualification);
                        intent.putExtra("chember", chember_address);
                        intent.putExtra("visitingHour", visiting_hour);
                        intent.putExtra("email", email);
                        intent.putExtra("phone", phone);
                        intent.putExtra("fee", fee);
                        intent.putExtra("experience", experience);
                        intent.putExtra("organization", organization);
                        intent.putExtra("area", Area);
                        startActivity(intent);
                    }
                    else if((firebaseUser != null) && (userCategory.equals("patient")))
                    {
                        Intent intent = new Intent(MainActivity.this, PatientProfile.class);
                        intent.putExtra("image", image);
                        intent.putExtra("name", user_name);
                        intent.putExtra("last_name", last_name);
                        intent.putExtra("location", location);
                        intent.putExtra("email", email);
                        intent.putExtra("phone", phone);
                        intent.putExtra("age", age);
                        startActivity(intent);
                    }

                    else if((firebaseUser != null) && (userCategory.equals("admin")))
                    {
                        Toast.makeText(MainActivity.this, "No Permission", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(MainActivity.this, "Please Wait", Toast.LENGTH_SHORT).show();
                }
            }
        });

        SharedPreferences appsettingprefs = getSharedPreferences("appSettingPrefs", 0);
        final Boolean isNightModeOn = appsettingprefs.getBoolean("nightMode", false);
        final SharedPreferences.Editor save_update = appsettingprefs.edit();

        if (isNightModeOn){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.AboutAppID:
                        Intent intent = new Intent(MainActivity.this, AboutApp.class);
                        startActivity(intent);
                        break;
                    case R.id.ContactUsID:
                        Intent intent1 = new Intent(MainActivity.this, ContactUs.class);
                        startActivity(intent1);
                        break;
                    case R.id.ExitID:
                        onBackPressed();
                        break;
                    case R.id.EventsID:
                        Intent intent2 = new Intent(MainActivity.this, EventActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.DarkModeID:
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                        mainLayout.setBackgroundColor(Color.BLACK);
//                        toolbar.setBackgroundColor(Color.GRAY);
                        save_update.putBoolean("nightMode", true);
                        save_update.apply();
                        finish();
                        break;
                    case R.id.LightModeID:
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        save_update.putBoolean("nightMode", false);
                        save_update.apply();
                        finish();
                        break;
                }
                return false;
            }
        });

        LogINButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                startActivity(intent);
            }
        });

        LogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        navigationView.setItemIconTintList(null);


        DoctorCatagoryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DoctorCatagoryActivity.class);
                startActivity(intent);
            }
        });

        NearbyDoctorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firebaseUser == null)
                {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setCancelable(false);
                    builder.setMessage("Please... LogIn or SignUp");
                    builder.setPositiveButton(Html.fromHtml("<font color='#ffffff'>"+"OK" + "</font"), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });


                    AlertDialog alert=builder.create();
                    alert.show();
                }

                else
                {
                    Intent intent = new Intent(getApplicationContext(), NearbyDocPopUP.class);
                    intent.putExtra("area", Area);
                    intent.putExtra("id", "near_auto");
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                  }
            }
        });

        EmergencyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EmergencyActivity.class);
                startActivity(intent);
            }
        });

        admin_panel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AdminPanel.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        ExtraActivity extraActivity = new ExtraActivity(MainActivity.this);
        extraActivity.Exit();
//        final Dialog dialog = new Dialog(MainActivity.this);
//        dialog.setContentView(R.layout.select_dialog_box);
//        TextView no = dialog.findViewById(R.id.NoID);
//        TextView yes = dialog.findViewById(R.id.YesID);
//
//        no.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.cancel();
//            }
//        });
//
//        yes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
//        dialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        final LoadingDialog loadingDialog = new LoadingDialog(this);

        if(firebaseUser!=null)
        {
            uid = mAuth.getCurrentUser().getUid();
            LogINButton.setVisibility(View.GONE);

//            loadingDialog.startLoadingDialog();
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    userCategory = snapshot.child(uid).child("userCategory").getValue(String.class);
                    if(userCategory.equals("admin"))
                    {
                        admin_panel.setVisibility(View.VISIBLE);
                        databaseReference = FirebaseDatabase.getInstance().getReference("users");
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                user_name = snapshot.child(uid).child("firstname").getValue(String.class);
                                last_name = snapshot.child(uid).child("lastname").getValue(String.class);
                                email = snapshot.child(uid).child("email").getValue(String.class);
                                phone = snapshot.child(uid).child("phone").getValue(String.class);
                                image = snapshot.child(uid).child("imageUri").getValue(String.class);
                                userCategory = snapshot.child(uid).child("userCategory").getValue(String.class);
                                Area = snapshot.child(uid).child("area").getValue(String.class);

                                if(!image.isEmpty())
                                {
                                    Picasso.get()
                                            .load(image)
                                            .into(userImage);
                                }
                                UserName.setText(user_name);
                                UserEmail.setText(email);
//                                loadingDialog.dismissDialog();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        });


                    }

                    else
                    {
                        databaseReference = FirebaseDatabase.getInstance().getReference("users");
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                user_name = snapshot.child(uid).child("firstname").getValue(String.class);
                                last_name = snapshot.child(uid).child("lastname").getValue(String.class);
                                email = snapshot.child(uid).child("email").getValue(String.class);
                                phone = snapshot.child(uid).child("phone").getValue(String.class);
                                image = snapshot.child(uid).child("imageUri").getValue(String.class);
                                userCategory = snapshot.child(uid).child("userCategory").getValue(String.class);

                                if(userCategory.toString().equals("doctor"))
                                {
                                    Area = snapshot.child(uid).child("area").getValue(String.class);
                                    chember_address = snapshot.child(uid).child("chamberAddress").getValue(String.class);
                                    specialist = snapshot.child(uid).child("specialist").getValue(String.class);
                                    visiting_hour = snapshot.child(uid).child("visitingHour").getValue(String.class);
                                    qualification = snapshot.child(uid).child("qualification").getValue(String.class);
                                    fee = snapshot.child(uid).child("fee").getValue(String.class);
                                    experience = snapshot.child(uid).child("experience").getValue(String.class);
                                    organization = snapshot.child(uid).child("organizaton").getValue(String.class);
                                }
                                else if(userCategory.toString().equals("patient"))
                                {
                                    Area = snapshot.child(uid).child("location").getValue(String.class);
                                    age = snapshot.child(uid).child("age").getValue(String.class);
                                    location = snapshot.child(uid).child("location").getValue(String.class);
                                }

                                if(!image.isEmpty())
                                {
                                    Picasso.get()
                                            .load(image)
                                            .into(userImage);
                                }

                                UserName.setText(user_name);
                                UserEmail.setText(email);
//                                loadingDialog.dismissDialog();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
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


//    private void status(String status){
//
//        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.getUid());
//
//        HashMap<String, Object> hashMap = new HashMap<>();
//
//        hashMap.put("status", status);
//        databaseReference.updateChildren(hashMap);
//
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        status("online");
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        status("ofline");
//    }

}






























