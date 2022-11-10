package com.example.tutorapp.Chat;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.bumptech.glide.Glide;
import com.example.tutorapp.BackEnd.Tutor;
import com.example.tutorapp.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TutorAdapter extends RecyclerView.Adapter <TutorAdapter.UserHolder> {

    private ArrayList <Tutor> tutors;
    private Context context;
    private OnClickListener onClickListener;

    public interface OnClickListener {
        void onUserClicked (int position);
    }

    public TutorAdapter(ArrayList<Tutor> tutors, Context context, OnClickListener onClickListener) { //constructor
        this.tutors = tutors;
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_user_info,parent,false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        //get the tutor name from firebase
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(
                "https://voluntutor-fe1aa-default-rtdb.firebaseio.com/tutors");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name;
                for (DataSnapshot id : snapshot.getChildren()){
                    name = id.child("firstName").getValue() + " " + id.child("lastName").getValue();
                    System.out.println(name);
                    holder.textUsername.setText(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //holder.textUsername.setText(tutors.get(position).getUid());

//        holder.textUsername.setText(tutors.get(position).getUid());  //.UID = .getUsername //Add glide after
//        Glide.with(context).load(tutors.get(position).getProfile()).error(R.drawable.ic_baseline_profile_icon).placeholder(R.drawable.ic_baseline_profile_icon).into(holder.imgView); //puts into Image box

    }

    @Override
    public int getItemCount() {
        return tutors.size();
    }

    class UserHolder extends RecyclerView.ViewHolder {
        TextView textUsername;
        ImageView imgView;

        public UserHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onUserClicked(getAbsoluteAdapterPosition());
                }
            });
            textUsername = itemView.findViewById(R.id.ChatUsernameRecycler);
            imgView = itemView.findViewById(R.id.ChatProfile);


        }
    }
}