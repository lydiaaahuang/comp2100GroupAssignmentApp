package com.example.tutorapp.Chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tutorapp.BackEnd.Student;
import com.example.tutorapp.MainChatActivity;
import com.example.tutorapp.MainHomeActivity;
import com.example.tutorapp.MainProfileActivity;
import com.example.tutorapp.MainSavedActivity;
import com.example.tutorapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatMessenger extends AppCompatActivity {
    private RecyclerView recyclerViewl;
    private EditText editMessage;
    private ImageView sendButton;
    private TextView receiverUser;
    private String receiverProfile;
    private String receiverId;

    private MessageAdapter messageAdapter;

    private ArrayList<ChatMessage> listOfMessages;

    String senderId,emailOfFriend,chatRoomId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_messenger);

        senderId = getIntent().getStringExtra("Receiver");
        emailOfFriend = getIntent().getStringExtra("Email");
        receiverId = getIntent().getStringExtra("ReceiverId");
        receiverProfile = getIntent().getStringExtra("ReceiverProfile");


        recyclerViewl = findViewById(R.id.ChatRecycler);
        editMessage = findViewById(R.id.messageText);
        receiverUser = findViewById(R.id.messageUsername);
        sendButton = findViewById(R.id.messageSend);

        receiverUser.setText(senderId);

        listOfMessages = new ArrayList<>();


        sendButton.setOnClickListener(v -> {
            FirebaseDatabase.getInstance().getReference("messages/" +chatRoomId).push()
                    .setValue(new ChatMessage(FirebaseAuth.getInstance().getCurrentUser().getEmail(), emailOfFriend, editMessage.getText().toString())); //String sender, String receiver, String message, String profile, boolean isseen
            editMessage.setText("");
        });

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


        messageAdapter = new MessageAdapter(listOfMessages, getIntent().getStringExtra("ReceiverProfile"),ChatMessenger.this);
        recyclerViewl.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewl.setAdapter(messageAdapter);
        Glide.with(ChatMessenger.this).load(getIntent().getStringExtra("ReceiverProfile")).placeholder(R.drawable.ic_baseline_profile_icon).error(R.drawable.ic_baseline_profile_icon);
        setUpChatRoomID();
    }

    private void setUpChatRoomID() {
        String uid = FirebaseAuth.getInstance().getUid();

        FirebaseDatabase.getInstance().getReference("students").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String myUID = snapshot.getValue(Student.class).getFirstName();
                if (senderId.compareTo(myUID)>0) {
                    chatRoomId = myUID + receiverId;
                }else if (senderId.compareTo(myUID)==0) {
                    chatRoomId = myUID + receiverId;
                }else {
                    chatRoomId = senderId +myUID;
                }
                attachMessageListener(chatRoomId);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void attachMessageListener(String chatRoomId) {
        FirebaseDatabase.getInstance().getReference("messages/"+chatRoomId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listOfMessages.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    listOfMessages.add(dataSnapshot.getValue(ChatMessage.class));
                }
                messageAdapter.notifyDataSetChanged();
                recyclerViewl.scrollToPosition(listOfMessages.size()-1);
                recyclerViewl.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }

}