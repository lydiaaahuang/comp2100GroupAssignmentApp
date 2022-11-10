package com.example.tutorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainProfileActivity extends AppCompatActivity {

    TextView name_field;
    TextView bio;
    TextView courses;

    DatabaseReference fbRef;
    FirebaseAuth auth;

    String firstName;
    String lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        FirebaseAuth user = FirebaseAuth.getInstance();
        String uid = FirebaseAuth.getInstance().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(
                "https://voluntutor-fe1aa-default-rtdb.firebaseio.com/");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name_field = findViewById(R.id.name_field);
                ArrayList<String> idList = new ArrayList<>();

                int i = 0;
                for (DataSnapshot id : snapshot.child("students").getChildren()) {
                    idList.add(i, id.child("id").getValue().toString()); //TODO: Check if its key or value???
                    i++;
                }
                if (idList.contains(uid)) {
                    firstName = snapshot.child("students").child(uid).child("firstName").getValue().toString();
                    lastName = snapshot.child("students").child(uid).child("lastName").getValue().toString();
                    name_field.setText(firstName + " " + lastName);
                } else {
                    firstName = snapshot.child("tutors").child(uid).child("firstName").getValue().toString();
                    lastName = snapshot.child("tutors").child(uid).child("lastName").getValue().toString();
                    name_field.setText(firstName + " " + lastName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        Button logout = findViewById(R.id.logoutButtonProfile);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

        Button editProfile = findViewById(R.id.edit_profileButton);
        editProfile.setOnClickListener(new View.OnClickListener() { // goes to edit profile
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EditProfile.class));
            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.home_page:
                            startActivity(new Intent(getApplicationContext(), MainHomeActivity.class));
                            overridePendingTransition(10, 0);
                            break;

                        case R.id.saved_page:
                            startActivity(new Intent(getApplicationContext(), MainSavedActivity.class));
                            overridePendingTransition(0, 0);
                            break;
                        case R.id.chat_page:
                            startActivity(new Intent(getApplicationContext(), MainChatActivity.class));
                            overridePendingTransition(0, 0);
                            break;
                        case R.id.profile_page:
                            Toast.makeText(this, "already on profile", Toast.LENGTH_SHORT).show();
                            return true;

                    }
                    return false;
                }
        );
    }


}