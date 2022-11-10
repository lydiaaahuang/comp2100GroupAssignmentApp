package com.example.tutorapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.tutorapp.BackEnd.Student;
import com.example.tutorapp.BackEnd.Tutor;
import com.example.tutorapp.BackEnd.User;
import com.example.tutorapp.data.model.Activity;
import com.example.tutorapp.data.model.Message;
import com.example.tutorapp.data.model.ReadStream;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class snippet_top_profile extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    RelativeLayout snippet_top_profile;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        assert currentUser != null;
        String email  = currentUser.getEmail();
        final String[] status = {null};

        Timer timer = new Timer();
        List<Message> activities = ReadStream.readActivityFromStream("stream.csv");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (Message a : activities){
                    if(a.getChatID().equals(email)){
                        status[0] = a.getMessage();
                    }
                    if(status[0].equals("Active")){
                        Drawable drawable = AppCompatResources.getDrawable(getApplicationContext(), R.drawable.ic_activity_status_circle);
                        assert drawable != null;
                        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
                        DrawableCompat.setTint(wrappedDrawable, Color.GREEN);
                    }
                    else if (status[0].equals("Away")){
                        Drawable drawable = AppCompatResources.getDrawable(getApplicationContext(), R.drawable.ic_activity_status_circle);
                        assert drawable != null;
                        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
                        DrawableCompat.setTint(wrappedDrawable, Color.RED);
                    }
                    else if(status[0].equals("Offline")){
                        Drawable drawable = AppCompatResources.getDrawable(getApplicationContext(), R.drawable.ic_activity_status_circle);
                        assert drawable != null;
                        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
                        DrawableCompat.setTint(wrappedDrawable, Color.GRAY);
                    }
                }

            }
        },0,5000);

    }

    /*// main method
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void main(String[] args) throws InterruptedException {
        String email = "U6677355@anu.edu.au";
        final String[] status = {null};
        final String[] time = {null};
        List<Activity> activities = ReadStream.readActivityFromStream("/Users/sungjaelee/Documents/2022/comp2100-group-assignment/TutorApp/app/src/main/assets/stream.csv");
        for (Activity a : activities){
            if(a.getID().equals(email)){
                status[0] = a.getStatus();
                time[0] = a.getTime();
                if(status[0].equals("Online")){
                    System.out.println("User changed status to 'Online' at " + time[0]);
                }
                else if (status[0].equals("Away")){
                    System.out.println("User changed status to 'Away' at " + time[0]);
                }
                else if(status[0].equals("Offline")){
                    System.out.println("User changed status to 'Offline' at " + time[0]);
                }
            }
            Thread.sleep(3000);

        }
    }// main method end */
}