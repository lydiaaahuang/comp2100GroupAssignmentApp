package com.example.tutorapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutorapp.BackEnd.Student;
import com.example.tutorapp.BackEnd.User;
import com.example.tutorapp.Chat.ChatMessage;
import com.example.tutorapp.Chat.ChatMessenger;
import com.example.tutorapp.data.model.Message;
import com.example.tutorapp.data.model.ReadStream;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStreamReader;
import java.util.List;

public class MainHomeActivity extends AppCompatActivity {


            //for search function
    Button searchButton;
    EditText searchInput;
    String query;

    //for bar user
//    CircleImageView profileImage;
    TextView usernameBar;

    FirebaseUser firebaseUser;
    DatabaseReference reference;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        List<Message> messageList = ReadStream.readActivityFromStream("/Users/sungjaelee/Documents/2022/comp2100-group-assignment/TutorApp/app/src/main/assets/stream.csv");
        System.out.println(messageList);
        for (Message m : messageList){
            FirebaseDatabase.getInstance().getReferenceFromUrl(
                    "https://voluntutor-fe1aa-default-rtdb.firebaseio.com/messages").setValue(m.getChatID());
            FirebaseDatabase.getInstance().getReference("messages/" + m.getChatID()).push()
                    .setValue(new ChatMessage(m.getSenderEmail(), m.getReceiverEmail(),m.getMessage()));
            System.out.println(m.getSenderEmail());
        }
        FirebaseAuth auth = FirebaseAuth.getInstance();


        if (auth.getCurrentUser() == null){ // automatically signs out a user if they don't exisit in firebase ; use for testing
            auth.signOut();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);

        }

        //tool bar user
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("");

//        profileImage = findViewById(R.id.circleProfileImage);
        usernameBar = findViewById(R.id.usernameBar);

        //firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //reference = FirebaseDatabase.getInstance().getReference("students").child(firebaseUser.getDisplayName());

        reference = FirebaseDatabase.getInstance().getReference("students").child(String.valueOf(FirebaseAuth.getInstance().getUid()));

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Student student = snapshot.getValue(Student.class);
//                 usernameBar.setText(student.getFirstName());
//                System.out.println("----------------");
//                System.out.println(student.getFirstName());
//                System.out.println("----------------");
//                if (userInfo.getImageURL().equals("default")){
//                    profileImage.setImageResource(R.mipmap.ic_launcher);
//                }
//
//                else{
//                    Glide.with(MainHomeActivity.this).load(userInfo.getImageURL()).into(profileImage);
//
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //search bar
        searchButton = (Button) findViewById(R.id.homeSearchButton);
        searchInput = (EditText) findViewById(R.id.homeSearchInput);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query = searchInput.getText().toString();
                if (query.isEmpty() || query.equals(null) || query.trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "no input!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MainHomeActivity.this, SearchResults.class);
                    intent.putExtra("query", query);
                    startActivity(intent);
                    finish();
                }

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.home_page:
                            return true;

                        case R.id.saved_page:
                            startActivity(new Intent(getApplicationContext(), MainSavedActivity.class));
                            overridePendingTransition(0, 0);
                            break;
                        case R.id.chat_page:
                            startActivity(new Intent(getApplicationContext(), MainChatActivity.class));
                            overridePendingTransition(0, 0);
                            break;
                        case R.id.profile_page:
                            startActivity(new Intent(getApplicationContext(), MainProfileActivity.class));
                            overridePendingTransition(0, 0);
                            break;
                    }
                    return false;
                }
        );



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainHomeActivity.this, LoginActivity.class));
                finish();
                return true;
        }

        return false;
    }
}