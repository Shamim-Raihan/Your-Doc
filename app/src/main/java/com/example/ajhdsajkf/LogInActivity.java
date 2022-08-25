package com.example.ajhdsajkf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {

    private Button SignUpButton;
    private Button LogInButton;
    private EditText Email;
    private EditText Password;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();
        SignUpButton = (Button) findViewById(R.id.SignUpButtonID);
        LogInButton = (Button) findViewById(R.id.LogInButtonID);
        Email = (EditText) findViewById(R.id.LogInEditTextEmailID);
        Password = (EditText) findViewById(R.id.LogInEditTextPasswordID);
        progressBar = (ProgressBar) findViewById(R.id.ProgressBerID1);
        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogIn();
            }
        });

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PopUpActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    private void LogIn() {
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

        final LoadingDialog loadingDialog = new LoadingDialog(this);
        loadingDialog.startLoadingDialog();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                loadingDialog.dismissDialog();
                if(task.isSuccessful())
                {
                        finish();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Log in Successfull", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "LogIn Unseccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
