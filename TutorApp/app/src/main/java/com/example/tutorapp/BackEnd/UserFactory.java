package com.example.tutorapp.BackEnd;

import java.util.ArrayList;

public class UserFactory {
    String userType = "";
    int fee = 0;
    ArrayList<String> courses = new ArrayList<>();
    //This courses parameter u need to get from app

    //Extract from firebase/listview (however you are getting the student's courses
    ArrayList<String> studying = new ArrayList<>();


    public User createUser(String name, String lastName, String email, String password, String uid, String status, boolean isTutor, String imageURL) {
        if (isTutor) {
            //This fee parameter u need to get from app
            return new Tutor(name, lastName, email, password, uid, status, courses, fee, isTutor, userType, imageURL);
        } else {
            return new Student(name, lastName, email, password, uid, status, studying, userType, isTutor, imageURL);
        }
    }
}
