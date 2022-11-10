package com.example.tutorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class otherUserProfile extends AppCompatActivity {


    TextView name_field;
    TextView bio;
    TextView courses;
    TextView price;

    DatabaseReference fbRef;
    FirebaseAuth auth;

    String uid;
    String courseInfo = "";

    String bioString;
    String coursesString;
    String pricesString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user_profile);

        ArrayList<String> tutorInfo = new ArrayList<>();
        name_field = findViewById(R.id.other_name_field);
        bio = findViewById(R.id.other_saved);
        courses = findViewById(R.id.other_courses);
        price = findViewById(R.id.other_price);

        //this is the query string from previous Activity
        uid = getIntent().getExtras().getString("UID");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(
                "https://voluntutor-fe1aa-default-rtdb.firebaseio.com/tutors");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot tutorName : snapshot.getChildren()) {
                    int i = 0;
                    if (tutorName.child("id").getValue().toString().equals(uid)) {
                        String fullName = tutorName.child("firstName").getValue() + " " + tutorName.child("lastName").getValue();
                        tutorInfo.add(i, fullName);
                        i++;
                        tutorInfo.add(i, tutorName.child("price").getValue().toString());
                        i++;
                        for (DataSnapshot course : tutorName.child("courses").getChildren()) {
                            courseInfo += course.getValue().toString() + " ";
                            i++;
                        }
                        break;
                    }
                }
                name_field.setText(tutorInfo.get(0));
                price.setText("Hourly Rate: " + tutorInfo.get(1));
                courses.setText("Currently teaching: " + courseInfo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        Button savedButton = findViewById(R.id.other_saved);
        savedButton.setOnClickListener(new View.OnClickListener() { // goes to edit profile
            @Override
            public void onClick(View view) {
                savedButton.setText("saved");
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