package com.example.tutorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutorapp.BackEnd.Tutor;
import com.example.tutorapp.Chat.ChatMessenger;
import com.example.tutorapp.Chat.TutorAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Tutor> tutors;
    private TutorAdapter tutorAdapter;
    TutorAdapter.OnClickListener onClickListener1;

    String receiverProfile;
    String senderUid;

    String tutorUsername;

    TextView tutorDisplayUsername;

    BottomNavigationView bottomNavigationView;

    String firstName;
    String lastName;

    DatabaseReference fbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);

        tutors = new ArrayList<>();
        recyclerView = findViewById(R.id.ChatRecycler);

        onClickListener1 = new TutorAdapter.OnClickListener() {
            @Override
            public void onUserClicked(int position) {
                startActivity(new Intent(MainChatActivity.this, ChatMessenger.class)
                .putExtra("Receiver", tutors.get(position).getFirstName())
                .putExtra("Email", tutors.get(position).getEmail())
                .putExtra("ReceiverId", tutors.get(position).getUid())
                .putExtra("ReceiverProfile", tutors.get(position).getImageURL()));

                Toast.makeText(MainChatActivity.this, "Tapped on Tutor" +tutors.get(position).getUid(), Toast.LENGTH_SHORT).show();
            }
        };

        tutorAdapter = new TutorAdapter(tutors, MainChatActivity.this, onClickListener1);

//        Button message = findViewById(R.id.ChatProfile);
//        message.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), ChatMessenger.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        getTutors();
//        tutors.clear();


        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.home_page:
                            startActivity(new Intent(getApplicationContext(), MainHomeActivity.class));
                            overridePendingTransition(0,0);
                            break;

                        case R.id.saved_page:
                            startActivity(new Intent(getApplicationContext(), MainSavedActivity.class));
                            overridePendingTransition(0,0);
                            break;

                        case R.id.chat_page:
                            return true;

                        case R.id.profile_page:
                            startActivity(new Intent(getApplicationContext(), MainProfileActivity.class));
                            overridePendingTransition(0,0);
                            break;
                    }
                    return false;
                }
        );
    }

    private void getTutors(){
        tutors.clear();
        FirebaseDatabase.getInstance().getReference("tutors").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot tutorName : snapshot.getChildren()){
                    tutors.add(tutorName.getValue(Tutor.class));

                }
                tutorAdapter = new TutorAdapter(tutors, MainChatActivity.this, onClickListener1);
                recyclerView.setLayoutManager(new LinearLayoutManager (MainChatActivity.this));
                recyclerView.setAdapter(tutorAdapter);
                recyclerView.setVisibility(View.VISIBLE);

                for(Tutor tutor: tutors){
                    if(tutor.getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        senderUid = tutor.getUid();
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainChatActivity.this, "stopped", Toast.LENGTH_SHORT).show();
            }
        });
    }



}