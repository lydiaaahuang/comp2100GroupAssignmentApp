package com.example.tutorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import com.example.tutorapp.Saved.SavedAdapter;
import com.example.tutorapp.Saved.SavedInfo;
import com.example.tutorapp.Saved.SavedRecyclerInterface;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainSavedActivity extends AppCompatActivity implements SavedRecyclerInterface {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ArrayList<SavedInfo> savedInfoList;
    SavedAdapter savedAdapter;
    DatabaseReference databaseReference;

    // TODO: find out if this is firebase integrated or still manual. I an see FB integration but can't really tell?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_saved);



        initRecyclerView();

        Button logout = findViewById(R.id.logoutButton);
        initRecyclerView();
        FirebaseAuth user = FirebaseAuth.getInstance();
        savedInfoList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("students").child(user.getUid()).child("savedInfo");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    SavedInfo savedInfo = dataSnapshot.getValue(SavedInfo.class);
                    savedInfoList.add(savedInfo);
                }
                savedAdapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.home_page:
                            startActivity(new Intent(getApplicationContext(), MainHomeActivity.class));
                            overridePendingTransition(0,0);
                            break;

                        case R.id.saved_page:
                            return true;

                        case R.id.chat_page:
                            startActivity(new Intent(getApplicationContext(), MainChatActivity.class));
                            overridePendingTransition(0,0);
                            break;

                        case R.id.profile_page:
                            startActivity(new Intent(getApplicationContext(), MainProfileActivity.class));
                            overridePendingTransition(0,0);
                            break;
                    }
                    return false;
                }
        );



    }



    private void initRecyclerView() {
        recyclerView = findViewById(R.id.saved_recycler_view);
        linearLayoutManager = new LinearLayoutManager(this); // make the display a linear layout
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL); // make the display vertical
        recyclerView.setLayoutManager(linearLayoutManager);
        savedAdapter = new SavedAdapter(savedInfoList, this, this);
        recyclerView.setAdapter(savedAdapter);


    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainSavedActivity.this, otherUserProfile.class);
        intent.putExtra("NAME", savedInfoList.get(position).getName_saved_profile());
        startActivity(intent);

    }
}