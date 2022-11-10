package com.example.tutorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutorapp.BackEnd.Student;
import com.example.tutorapp.BackEnd.Tutor;
import com.example.tutorapp.BackEnd.User;
import com.example.tutorapp.BackEnd.UserFactory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    // create object of database reference to access firebase real time data
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();
        EditText firstName = findViewById(R.id.signUpFirstName);
        EditText lastName = findViewById(R.id.signUpLastName);
        EditText email = findViewById(R.id.signUpEmail);
        EditText courses = findViewById(R.id.courses);
        EditText price = findViewById(R.id.price);
        EditText password = findViewById(R.id.signUpPassword);
        EditText confirmPassword = findViewById(R.id.confirmPassword);
        CheckBox isTutor = findViewById(R.id.signUpTutor);
        Button signUp = findViewById(R.id.signUpBtn);
        TextView login = findViewById(R.id.loginNow);
        price.setEnabled(false);


        isTutor.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    price.setEnabled(true);

                } else {
                    price.setEnabled(false);
                }


            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish(); // goes back to login activity
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get data from editText

                final String firstNameText = firstName.getText().toString();
                final String lastNameText = lastName.getText().toString();
                final String emailText = email.getText().toString();
                final String password1 = password.getText().toString();
                final String coursesText = Objects.requireNonNull(courses).getText().toString();
                final String priceText = Objects.requireNonNull(price).getText().toString();
                final String password2 = confirmPassword.getText().toString();
                final String imageURL = "default";

                // check if user fills in all fields
                if (firstNameText.isEmpty() || lastNameText.isEmpty() || emailText.isEmpty()
                        || password1.isEmpty() || password2.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please fill out all fields",
                            Toast.LENGTH_SHORT).show();
                }

                try {
                    Integer.parseInt(priceText);
                } catch (Exception e) {
                    Toast.makeText(SignUpActivity.this, "Enter an integer number", Toast.LENGTH_SHORT).show();
                }

                // passwords too short
                if (password1.length() < 6) {
                    Toast.makeText(SignUpActivity.this, "Password should be at least " +
                            "6 characters", Toast.LENGTH_SHORT).show();
                }
                // passwords do not match
                if (!(password1.equals(password2))) {
                    Toast.makeText(SignUpActivity.this, "Passwords don't match",
                            Toast.LENGTH_SHORT).show();
                }


                // register user in firebase authentication
                firebaseAuth.createUserWithEmailAndPassword(emailText, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            boolean signTutor = isTutor.isChecked();
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            assert firebaseUser != null;
                            String userID = firebaseUser.getUid();
                            List<String> courseList = new ArrayList<>();
                            List<String> savedList = new ArrayList<>(); // TODO: this is a test

                            //TODO: UserFactory

                            UserFactory userFactory = new UserFactory();
                            Tutor tutor;
                            Student student;

                            UserProfileChangeRequest profileUpdates =
                                    new UserProfileChangeRequest.Builder().setDisplayName(userID).build();
                            firebaseUser.updateProfile(profileUpdates);


                            if (signTutor) {
                                tutor = (Tutor) userFactory.createUser(firstNameText, lastNameText,
                                        emailText, password2, userID, "offline", signTutor, imageURL);
                                tutor.setFee(Integer.parseInt(priceText));
                                tutor.setUserType("tutor");
                                Collections.addAll(courseList, coursesText.split(","));

                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("tutors").child(userID);
                                reference.setValue(tutor.generateFields()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                    | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();// go back to login
                                        }
                                    }
                                });
                                reference.child("courses").setValue(courseList);
                            } else {
                                student = (Student) userFactory.createUser(firstNameText, lastNameText,
                                        emailText, password2, userID, "offline", signTutor, imageURL);
                                student.setUserType("student");
                                Collections.addAll(courseList, coursesText.split(","));
                                savedList.add("test!");


                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("students").child(userID);
                                reference.setValue(student.generateFields()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                    | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();// go back to login
                                        }
                                    }
                                });
                                reference.child("courses").setValue(courseList);
                                reference.child("saved").setValue(savedList);

                            }
                        } else {
                            Toast.makeText(SignUpActivity.this, "Error" +
                                    task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}

