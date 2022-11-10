package com.example.tutorapp.BackEnd;

import java.util.HashMap;

public abstract class User {

    String firstName;
    String lastName;
    String email;
    String password;
    //Had to make this 'uid' instead of 'id' because conflicts with the term 'id' in Android
    String uid;
    String status;
    String imageURL;

    public User(String firstName,String lastName, String email, String password, String uid,
                String status, String imageURL) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.uid = uid;
        this.status = status;
        this.imageURL = imageURL;
    }

    public User() {
    }
}
