package com.example.tutorapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        firebaseAuth = FirebaseAuth.getInstance();
//
////        if (firebaseAuth.getCurrentUser() != null) {
////            Intent intent = new Intent(getApplicationContext(), MainHomeActivity.class);
////            startActivity(intent);
////            finish();
////
////        }else{
////            firebaseAuth.signOut();
////        }
//
//        if (firebaseAuth.getCurrentUser() != null) {
//            firebaseAuth.signOut();
//        }
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        // Initialize Firebase Auth and firebase database

        FirebaseApp.initializeApp(getBaseContext());// initialise firebase app

        final EditText email = findViewById(R.id.email);
        final EditText password = findViewById(R.id.password);
        final Button login =  findViewById(R.id.login);
        final TextView registerNow = findViewById(R.id.registerNow);

        registerNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to signUp Activity
                Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        }); // note

        login.setOnClickListener(view -> {

            final String emailText = email.getText().toString();
            final String passWordText = password.getText().toString();

            if (emailText.isEmpty() || passWordText.isEmpty()){
                Toast.makeText(LoginActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            }

            else{
                // authenticate user
                firebaseAuth.signInWithEmailAndPassword(emailText,passWordText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //add loading screen here
                            Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LoadScreen.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });

    }}

