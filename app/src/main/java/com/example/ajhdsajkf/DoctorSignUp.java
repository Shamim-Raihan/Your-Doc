package com.example.ajhdsajkf;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DoctorSignUp extends AppCompatActivity {



    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    StorageReference storageReference;


    private EditText FirstName;
    private EditText LastName;
    private EditText Age;
    private RadioGroup radioGroup;
    private RadioButton GenderButton;
    private String sex;
    private EditText Qualification;
    private Spinner SpecialistSpinner;
    private String specialist;
    private Spinner AreaSpinner;
    private String area;
    private EditText Experience;
    private EditText Organization;
    private ImageView UploadImageButton;
    private static final int IMAGE_REQUEST = 1;
    private Uri ImageUri;
    StorageTask storageTask;
    private String url;
    private EditText ChamberAddress;
    private EditText VisitingHour;
    private EditText Phone;
    private EditText Email;
    private EditText Password;
    private Button SignUpButton;
    String[] Specialist, Area;
    private String uid;
    private int num = 0;
    private Button startTime, endTime;


    ProgressBar progressBar;

    private String UserCategory = "doctor";

    private int hour, min;


    String fee = "";
    String saturday = "";
    String sunday = "";
    String monday = "";
    String tuesday = "";
    String wednesday = "";
    String thursday = "";
    String friday = "";
    String status = "online";
    private String fullName;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_sign_up);



        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        storageReference = FirebaseStorage.getInstance().getReference("Image");

        FirstName = (EditText) findViewById(R.id.DoctorSignUpFirstNameID);
        LastName = (EditText) findViewById(R.id.DoctorSignUpLastNameID);
        Age = (EditText) findViewById(R.id.DoctorSignUpAgeID);
        radioGroup = (RadioGroup) findViewById(R.id.RadioGroupID);
        Qualification = (EditText) findViewById(R.id.DoctorSignUpQualificationID);
        UploadImageButton = (ImageView) findViewById(R.id.UploadImageButtonID);


        //Experience = (EditText) findViewById(R.id.DoctorSignUpExperienceID);
        //Organization = (EditText) findViewById(R.id.DoctorSignUpOrganizationID);
        ChamberAddress = (EditText) findViewById(R.id.DoctorSIgnUpChamberAddressID);
        VisitingHour = (EditText) findViewById(R.id.DoctorSignUpVisitingHourID);
        Phone = (EditText) findViewById(R.id.DoctorSignUpPhoneID);
        Email = (EditText) findViewById(R.id.DoctorSignUpEmailID);
        Password = (EditText) findViewById(R.id.DoctorSignUpPasswordID);
        SignUpButton = (Button) findViewById(R.id.DoctorSignUpButttonID);
        Experience = findViewById(R.id.DoctorSignUpExperienceID);
        Organization = findViewById(R.id.DoctorSignUpOrganizationID);

        SpecialistSpinner = (Spinner) findViewById(R.id.SpecialistSpinnerID);
        AreaSpinner = (Spinner) findViewById(R.id.AreaSpinnerID);
        Specialist = getResources().getStringArray(R.array.specialist);
        Area = getResources().getStringArray(R.array.area);

        startTime = findViewById(R.id.SignUpVisitingHourStartTimeID);
        endTime = findViewById(R.id.SignUpVisitingHourEndTimeID);



        progressBar = findViewById(R.id.ProgressBerID1);




        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        DoctorSignUp.this,
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
                                    VisitingHour.setText(f12h.format(date) + " to ");

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        },12, 0, false
                );

                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();


            }
        });



        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        DoctorSignUp.this,
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
                                    String pre_time = VisitingHour.getText().toString();
                                    VisitingHour.setText(pre_time + f12h.format(date));

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        },12, 0, false
                );

                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();



            }
        });




        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_sample_layout, R.id.spinnerSampleID, Specialist);
        SpecialistSpinner.setAdapter(adapter);
        SpecialistSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                specialist = parent.getItemAtPosition(position).toString();
                //Toast.makeText(parent.getContext(), specialist, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.spinner_sample_layout, R.id.spinnerSampleID, Area);
        AreaSpinner.setAdapter(adapter1);
        AreaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                area = parent.getItemAtPosition(position).toString();
                //Toast.makeText(parent.getContext(), area, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });







        UploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OpenFileChooser();

            }
        });








        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = Email.getText().toString().trim();
                final String password = Password.getText().toString().trim();


                if(email.isEmpty())
                {
                    Email.setError("Enter an email address");
                    Email.requestFocus();
                    return;
                }


                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    Email.setError("Enter a valid email address");
                    Email.requestFocus();
                    return;
                }

                if(password.isEmpty())
                {
                    Password.setError("Enter a password");
                    Password.requestFocus();
                    return;
                }

                if(password.length()<6)
                {
                    Password.setError("Minimum length of a password should be 6");
                    Password.requestFocus();
                    return;
                }


                progressBar.setVisibility(View.VISIBLE);


                final int SexId = radioGroup.getCheckedRadioButtonId();
                GenderButton = (RadioButton) findViewById(SexId);
                sex = GenderButton.getText().toString();

                //Toast.makeText(getApplicationContext(), sex, Toast.LENGTH_SHORT).show();


                final String firstname = FirstName.getText().toString().trim();
                final String lastname = LastName.getText().toString().trim();
                final String age = Age.getText().toString().trim();
                final String qualification = Qualification.getText().toString().trim();

                final String experience = Experience.getText().toString().trim();
                final String organization = Organization.getText().toString().trim();
                final String chamberAddress = ChamberAddress.getText().toString().trim();
                final String visitingHour = VisitingHour.getText().toString().trim();
                final String phone = Phone.getText().toString().trim();



                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if(task.isSuccessful())
                        {
                            uid = mAuth.getCurrentUser().getUid();

                            finish();
                            if (storageTask!=null && storageTask.isInProgress())
                            {
                                Toast.makeText(getApplicationContext(), "Uploading in progress", Toast.LENGTH_SHORT).show();
                            }

                            else
                            {
                                saveData(firstname, lastname, age, sex, qualification, specialist, area, chamberAddress, visitingHour, phone, email, password, experience, organization);
                            }

                        }

                        else
                        {
                            Toast.makeText(getApplicationContext(), "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }
        });


    }

    private void OpenFileChooser() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);

    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {

            ImageUri = data.getData();
        }

    }



    public String getFileExtension(Uri ImageUri)
    {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(ImageUri));

    }






    private void saveData(final String firstname, final String lastname, final String age, final String sex, final String qualification, final String specialist, final String area,  final String chamberAddress, final String visitingHour, final String phone, final String email, final String password, final String experience, final String organization) {

        final StorageReference ref = storageReference.child("Image"+ImageUri.getLastPathSegment()+"."+getFileExtension(ImageUri));

        ref.putFile(ImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content


                        finish();

                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while(!uriTask.isSuccessful());
                        Uri downloadUri = uriTask.getResult();



                        url = downloadUri.toString();

                        fullName = firstname.toLowerCase(Locale.ROOT) + " " + lastname.toLowerCase(Locale.ROOT);

                        Doctors doctors = new Doctors(firstname, lastname, age, sex, qualification, specialist, area, url,  chamberAddress, visitingHour, phone, email, password, UserCategory, fee, saturday, sunday, monday, tuesday, wednesday, tuesday, friday, experience, organization, uid, status, fullName);

                        databaseReference.child(uid).setValue(doctors);

                        progressBar.setVisibility(View.GONE);

                        Toast.makeText(getApplicationContext(), "Doctor's info is added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DoctorSignUp.this, MainActivity.class);
                        startActivity(intent);

                    }




                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {

                    }
                });


    }
}
