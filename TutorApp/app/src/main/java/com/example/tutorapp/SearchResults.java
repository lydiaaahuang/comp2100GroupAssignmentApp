package com.example.tutorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutorapp.BackEnd.AVLTree;
import com.example.tutorapp.BackEnd.Exp;
import com.example.tutorapp.BackEnd.Parser;
import com.example.tutorapp.BackEnd.Token;
import com.example.tutorapp.BackEnd.Tokenizer;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class SearchResults extends AppCompatActivity {

    TextView searchString;
    String value;
    Button goBackButton;
    AVLTree<String> nameTree = new AVLTree<>(" ", new ArrayList<>());
    AVLTree<String> feeTree = new AVLTree<>("51", new ArrayList<>());
    HashMap<String, ArrayList<String>> results = new HashMap<>();
    ArrayList<String> finalResults = new ArrayList<>();
    String sendToProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        searchString = findViewById(R.id.searchResultText);
        goBackButton = findViewById(R.id.searchResultReturn);

        //this is the query string from previous Activity
        value = getIntent().getExtras().getString("query");

        searchString.setText(value);

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchResults.this, MainHomeActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        //This part goes in MainHomeActivity (to be done during first sign in)
        //Won't be able to pass it in intent... Could serialize it?
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(
                "https://voluntutor-fe1aa-default-rtdb.firebaseio.com/tutors");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot tutorName : snapshot.getChildren()) {
                    int i = 0;
                    ArrayList<String> tutorInfo = new ArrayList<>();

                    String fullName = tutorName.child("firstName").getValue() + " " + tutorName.child("lastName").getValue();
                    tutorInfo.add(i, fullName);
                    i++;
                    tutorInfo.add(i, tutorName.child("price").getValue().toString());
                    i++;
                    sendToProfile = tutorName.child("id").getValue().toString();
                    for (DataSnapshot course : tutorName.child("courses").getChildren()) {
                        tutorInfo.add(i, course.getValue().toString());
                        i++;
                        nameTree = nameTree.insert(course.getValue().toString(), tutorInfo);
                    }
                    nameTree = nameTree.insert(fullName, tutorInfo);
                    feeTree = feeTree.insert(tutorName.child("price").getValue().toString(), tutorInfo);
                }
                Tokenizer token = new Tokenizer(value);
                Parser parser = new Parser(token);
                if (token.current().getType().equals(Token.Type.STRING)) {
                    results = nameTree.myFind(parser.parseExp().show());
                } else {
                    results = feeTree.myFind(parser.parseExp().show());
                }

                int i = 0;
                for (String key : results.keySet()) {
                    String f = "";
                    for (String s : Objects.requireNonNull(results.get(key))) {
                        f += s + " ";
                    }
                    finalResults.add(i, f);
                    i++;
                }

                ListView listView = findViewById(R.id.searchResultList);
                ArrayAdapter listViewAdapter = new ArrayAdapter(SearchResults.this,
                        android.R.layout.simple_list_item_1, finalResults);
                listView.setAdapter(listViewAdapter);

                AdapterView.OnItemClickListener listViewListener = new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String result = (String) adapterView.getAdapter().getItem(i);
                        StringBuilder tutorName = new StringBuilder();
                        for (int j = 0; j < result.length(); j++) {
                            if (Character.isDigit(result.charAt(j))) {
                                break;
                            } else {
                                tutorName.append(result.charAt(j));
                            }
                        }

                        if (tutorName.toString().equals("") || tutorName == null) {
                            Toast.makeText(SearchResults.this, "No result", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(SearchResults.this, otherUserProfile.class);
                            //Decide what to put in!!!
                            intent.putExtra("UID", sendToProfile);
                            startActivity(intent);
                            finish();
                        }
                    }
                };
                listView.setOnItemClickListener(listViewListener);
                listView.setAdapter(listViewAdapter);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        //All part above is populating the tree w/ info.

        //TODO: Viewer for search results!

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.home_page:
                            startActivity(new Intent(getApplicationContext(), MainHomeActivity.class));
                            overridePendingTransition(0, 0);
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
                            startActivity(new Intent(getApplicationContext(), MainProfileActivity.class));
                            overridePendingTransition(0, 0);
                            break;
                    }
                    return false;
                }
        );
    }
}