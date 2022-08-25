package com.example.ajhdsajkf;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

import java.util.Locale;

public class PatientSignUp extends AppCompatActivity {

    String[] area;
    private Spinner spinner;

    private FirebaseAuth mAuth;

    DatabaseReference databaseReference;
    StorageReference storageReference;

    private EditText FirstName;
    private EditText LastName;
    private EditText Age;
    private ImageButton UploadImageButton;
    private EditText Phone;
    private EditText Email;
    private EditText Password;
    private Button SignUpBUtton;
    private static final int IMAGE_REQUEST = 1;
    private Uri ImageUri;
    StorageTask storageTask;
    private String url;
    private String uid;
    private String location;
    private String events = "", dailytips = "";
    private String fullName;


    ProgressBar progressBar;

    private String UserCategory = "patient";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_sign_up);


        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        storageReference = FirebaseStorage.getInstance().getReference("Image");


        FirstName = (EditText) findViewById(R.id.PatientSignUpFirstNameEditTextID);
        LastName = (EditText) findViewById(R.id.PatientSignUpLastNameEditTextID);
        Age = (EditText) findViewById(R.id.PatientSignUpAgeEditTextID);
        UploadImageButton = (ImageButton) findViewById(R.id.PatientSignUpUploadImageID);
        Phone = (EditText) findViewById(R.id.PatientSignUpPhoneEditTextID);
        Email = (EditText) findViewById(R.id.PatientSignUpEmailEditTextID);
        Password = (EditText) findViewById(R.id.PatientSignUpPasswordEditTextID);
        SignUpBUtton = (Button) findViewById(R.id.SignUpButtonID);


        area = getResources().getStringArray(R.array.area);

        spinner = findViewById(R.id.AreaSpinnerID);


        progressBar = (ProgressBar) findViewById(R.id.ProgressBerID1);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_sample_layout, R.id.spinnerSampleID, area);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                location = parent.getItemAtPosition(position).toString();
                //Toast.makeText(parent.getContext(), location, Toast.LENGTH_SHORT).show();
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


        SignUpBUtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = Email.getText().toString().trim();
                final String password = Password.getText().toString().trim();


                if (email.isEmpty()) {
                    Email.setError("Enter an email address");
                    Email.requestFocus();
                    return;
                }


                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Email.setError("Enter a valid email address");
                    Email.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    Password.setError("Enter a password");
                    Password.requestFocus();
                    return;
                }

                if (password.length() < 6) {
                    Password.setError("Minimum length of a password should be 6");
                    Password.requestFocus();
                    return;
                }


                final String firstname = FirstName.getText().toString().trim();
                final String lastname = LastName.getText().toString().trim();
                final String age = Age.getText().toString().trim();
                final String phone = Phone.getText().toString().trim();

                progressBar.setVisibility(View.VISIBLE);


                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful()) {

                            uid = mAuth.getCurrentUser().getUid();

                            finish();
                            if (storageTask != null && storageTask.isInProgress()) {
                                Toast.makeText(getApplicationContext(), "Uploading in progress", Toast.LENGTH_SHORT).show();
                            } else {
                                saveData(firstname, lastname, age, phone, email, password, uid);
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "Error" + task.getException(), Toast.LENGTH_SHORT).show();
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

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            ImageUri = data.getData();


        }

    }


    public String getFileExtension(Uri ImageUri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(ImageUri));

    }


    private void saveData(final String firstname, final String lastname, final String age, final String phone, final String email, final String password, final String uid) {


        final StorageReference ref = storageReference.child("Image" + ImageUri.getLastPathSegment() + "." + getFileExtension(ImageUri));


        ref.putFile(ImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content


                        finish();

                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                url = String.valueOf(uri);

                                fullName = firstname.toLowerCase(Locale.ROOT) + " " + lastname.toLowerCase(Locale.ROOT);

                                Patients patients = new Patients(firstname, lastname, age, phone, email, password, url, location, UserCategory, events, dailytips, uid, fullName);

                                databaseReference.child(uid).setValue(patients);
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "Patient's info is added", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(PatientSignUp.this, MainActivity.class);
                                startActivity(intent);

                            }
                        });


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {

                    }
                });
    }

}
