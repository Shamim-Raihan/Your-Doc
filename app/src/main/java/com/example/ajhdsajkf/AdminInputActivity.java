package com.example.ajhdsajkf;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class AdminInputActivity extends AppCompatActivity {

    private ScrollView event;
    private ScrollView dailyTips;
    private ScrollView ambulance;
    private String option;
    private EditText EventTitle;
    private EditText EventDescription;
    private LinearLayout EventTitleImage;
    private LinearLayout EventPosterImage;
    private EditText EventDate;
    private Button EventSelectDateButton;
    private EditText EventLocation;
    private EditText EventTime;
    private Button EventTimeStartButton;
    private Button EventTimeEndButton;
    private EditText EventDoctor;
    private Button EventCreateButton;
    private static final int IMAGE_REQUEST_1 = 1;
    private static final int IMAGE_REQUEST_2 = 2;
    private Uri ImageUri_1, ImageUri_2;
    private int hour, min;
    private String url_1, url_2;
    private String key = "event-list";
    private String key1 = "dailyTip-list";
    DatabaseReference databaseReference;
    StorageReference storageReference;
    private EditText dailyTipsTitle;
    private ImageView dailyTipsImage;
    private EditText dailyTipsDescription;
    private Button dailyTipsPostButton;
    private static final int IMAGE_REQUEST_3 = 3;
    private Uri ImageUri_3;
    private String url_3;
    DatabaseReference databaseReference_for_d_t;
    StorageReference storageReference_for_d_t;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_input);

        event = findViewById(R.id.AddEventsLayoutID);
        dailyTips = findViewById(R.id.AddDailyTipsLayoutID);
        ambulance = findViewById(R.id.AddEmergencyAmbulanceLayoutID);
        EventTitle = findViewById(R.id.EventTitleEditTextID);
        EventDescription = findViewById(R.id.EventDescriptionEditTextID);
        EventTitleImage = findViewById(R.id.EventTitleImageID);
        EventPosterImage = findViewById(R.id.EventPosterImageID);
        EventDate = findViewById(R.id.EventDateTextViewID);
        EventSelectDateButton = findViewById(R.id.EventSelectDateButtonID);
        EventLocation = findViewById(R.id.EventLocationID);
        EventTime = findViewById(R.id.EventTimeID);
        EventTimeStartButton = findViewById(R.id.EventStartTimeButtonID);
        EventTimeEndButton = findViewById(R.id.EventEndTimeButtonID);
        EventDoctor = findViewById(R.id.EventDoctorID);
        EventCreateButton = findViewById(R.id.EventCreateButtonID);
        databaseReference = FirebaseDatabase.getInstance().getReference("Events");
        storageReference = FirebaseStorage.getInstance().getReference("EventImage");
        dailyTipsTitle = findViewById(R.id.DailyTipsTitleID);
        dailyTipsImage = findViewById(R.id.DailyTipsImageID);
        dailyTipsDescription = findViewById(R.id.DailyTipsDescriptionID);
        dailyTipsPostButton = findViewById(R.id.DailyTipsPostButtonID);
        databaseReference_for_d_t = FirebaseDatabase.getInstance().getReference("DailyTips");
        storageReference_for_d_t = FirebaseStorage.getInstance().getReference("DailyTipsImage");

        EventTitleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OpenFileChooser1();
            }
        });

        EventPosterImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OpenFileChooser2();
            }
        });


        //DailyTips:
        dailyTipsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenFileChooser3();
            }
        });

        EventSelectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AdminInputActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth, dateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                EventDate.setText(date);
            }
        };


        EventTimeStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        AdminInputActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                hour = hourOfDay;
                                min = minute;
                                String time = hour + ":" + min;
                                SimpleDateFormat format = new SimpleDateFormat(
                                        "HH:mm"
                                );
                                try {
                                    Date date = format.parse(time);
                                    SimpleDateFormat f12h = new SimpleDateFormat(
                                            "hh:mm aa"
                                    );
                                    EventTime.setText(f12h.format(date) + " to ");

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, 12, 0, false
                );

                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();
            }
        });

        EventTimeEndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        AdminInputActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                hour = hourOfDay;
                                min = minute;
                                String time = hour + ":" + min;
                                SimpleDateFormat format = new SimpleDateFormat(
                                        "HH:mm"
                                );
                                try {
                                    Date date = format.parse(time);
                                    SimpleDateFormat f12h = new SimpleDateFormat(
                                            "hh:mm aa"
                                    );
                                    String pre_time = EventTime.getText().toString();
                                    EventTime.setText(pre_time + f12h.format(date));

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, 12, 0, false
                );

                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();
            }
        });

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            option = bundle.getString("id");
        }


        if (option.equals("Event")) {
            dailyTips.setVisibility(View.GONE);
            ambulance.setVisibility(View.GONE);


            EventFunction();


        }

        if (option.equals("DailyTips")) {
            event.setVisibility(View.GONE);
            ambulance.setVisibility(View.GONE);
        }

        if (option.equals("EmergencyAmbulance")) {
            event.setVisibility(View.GONE);
            dailyTips.setVisibility(View.GONE);
        }


        EventCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UploadEventData();

            }
        });


        dailyTipsPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UploadDailyTipsData();


            }
        });


    }


    public String getFileExtension(Uri imageUri_1) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(ImageUri_1));

    }


    public String getExtension(Uri imageUri_2) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(ImageUri_2));

    }


    public String getfileExtension(Uri imageUri_3) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(ImageUri_3));

    }

    private void UploadDailyTipsData() {


        final String title_d_t = dailyTipsTitle.getText().toString();
        final String description_d_t = dailyTipsDescription.getText().toString();


        final StorageReference ref_3 = storageReference_for_d_t.child("Image" + ImageUri_3.getLastPathSegment() + "." + getfileExtension(ImageUri_3));


        ref_3.putFile(ImageUri_3)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                        ref_3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                url_3 = String.valueOf(uri);
                                DailyTips dailyTips = new DailyTips(title_d_t, url_3, description_d_t, key1);
                                databaseReference_for_d_t.child(title_d_t).setValue(dailyTips);
                                Toast.makeText(AdminInputActivity.this, "successfull", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(AdminInputActivity.this, "failed", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void UploadEventData() {

        final String title = EventTitle.getText().toString();
        final String description = EventDescription.getText().toString();
        final String dates = EventDate.getText().toString();
        final String location = EventLocation.getText().toString();
        final String time = EventTime.getText().toString();
        final String doctor = EventDoctor.getText().toString();

        final StorageReference ref_1 = storageReference.child("Image" + ImageUri_1.getLastPathSegment() + "." + getFileExtension(ImageUri_1));

        ref_1.putFile(ImageUri_1)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref_1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                url_1 = String.valueOf(uri);
                                Toast.makeText(AdminInputActivity.this, "uploded", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminInputActivity.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                });
        final StorageReference ref_2 = storageReference.child("Image" + ImageUri_2.getLastPathSegment() + "." + getExtension(ImageUri_2));
        ref_2.putFile(ImageUri_2)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref_2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                url_2 = String.valueOf(uri);
                                finish();
                                Events events = new Events(title, description, url_1, url_2, dates, location, time, doctor, key);
                                databaseReference.child(title).setValue(events);
                                Toast.makeText(AdminInputActivity.this, "successfull", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }


    private void OpenFileChooser1() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST_1);
    }

    private void OpenFileChooser2() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST_2);
    }


    private void OpenFileChooser3() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST_3);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == IMAGE_REQUEST_1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            ImageUri_1 = data.getData();
        }


        if (requestCode == IMAGE_REQUEST_2 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            ImageUri_2 = data.getData();
        }

        if (requestCode == IMAGE_REQUEST_3 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            ImageUri_3 = data.getData();
        }
    }

    private void EventFunction() {

    }
}
