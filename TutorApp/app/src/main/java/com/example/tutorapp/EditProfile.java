package com.example.tutorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class EditProfile extends AppCompatActivity {

    DatabaseReference ref;

    EditText signUpFirstName;
    EditText signUpLastName;
    EditText bio;
    EditText courses;
    EditText price; //TODO: need a way of discerning between tutor and student implementation below is for student only for now.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        signUpFirstName = findViewById(R.id.signUpFirstName);
        signUpLastName = findViewById(R.id.signUpLastName);
        bio = findViewById(R.id.bio);
        courses = findViewById(R.id.courses);
        price = findViewById(R.id.price);
        String uid = FirebaseAuth.getInstance().getUid();
        Button submit = findViewById(R.id.submit_edit);
        final HashMap<String, String> courseList  = new HashMap<>();


        ref = FirebaseDatabase.getInstance().getReference("students").child(uid);
        ref.child("firstName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String oldFirstName = (String) snapshot.getValue();
                signUpFirstName.setText(oldFirstName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ref.child("lastName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String oldLastName = (String) snapshot.getValue();
                signUpLastName.setText(oldLastName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // TODO: bio
        ref.child("courses").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ArrayList<String> courseString = (ArrayList<String>) snapshot.getValue();
                courses.setText(courseString.toString().replace("[", "").replace("]", ""));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // TODO: price
        // TODO: courses won't work because it is in some sort of
        // TODO: data structure. SO putting it in edittext won't work
        // TODO: help pls



        // TODO: implement other change updates. Doing name only for now.
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstNameNew = signUpFirstName.getText().toString();
                String lastNameNew = signUpLastName.getText().toString();
                List<String> courseListNew  = new ArrayList<>();
                String courseStringNew = courses.getText().toString();
                Collections.addAll(courseListNew, courseStringNew.split(","));


                if (firstNameNew.isEmpty() || lastNameNew.isEmpty()){
                    Toast.makeText(EditProfile.this, "Please fill out all fields",
                            Toast.LENGTH_LONG).show();
                }

                String uid = FirebaseAuth.getInstance().getUid();

                ref = FirebaseDatabase.getInstance().getReference("students").child(uid);
                ref.child("firstName").setValue(firstNameNew);
                ref.child("lastName").setValue(lastNameNew);
                ref.child("courses").setValue(courseListNew);

                Toast.makeText(EditProfile.this, "Profile Updated", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getApplicationContext(), MainProfileActivity.class));
            }
        });










    }
}