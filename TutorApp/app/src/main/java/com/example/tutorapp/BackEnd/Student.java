package com.example.tutorapp.BackEnd;

import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Student extends User implements UserMethod {

    //Can use this field to recommend tutors
    //Current courses the student is studying

    ArrayList<String> studying = new ArrayList<>();

    public String userType;

    public Student(String firstName,String lastName, String email, String password, String uid, String status, ArrayList<String> studying, String userType,
                   boolean isTutor, String imageURL) {
        super(firstName,lastName, email, password, uid, status, imageURL);
        this.studying = studying;
    }
    public Student (){}; // empty constructor for use in firebase

    public void addStudy(String course){
        this.studying.add(course);
    }

    public ArrayList<String> getStudy() {
        return this.studying;
    }

    //Maybe don't need most getter methods if we use this implementation... or vice versa, can remove this if we stick w getter methods.
    public ArrayList<String> getInfo() {
        ArrayList<String> info = new ArrayList<>();
        info.add(this.firstName);
        info.add(this.email);
        info.add(this.password);
        info.add(this.uid);
        info.add(this.status);
        return info;
    }

    public String getUserType() {
        return this.userType;
    }

    public void setUserType(String userType){
        this.userType = userType;
    }

    @Override
    public String getFirstName() {
        assert firstName != null;
        return this.firstName;
    }

    @Override
    public String getLastName() {
      return this.lastName;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    public String getPassword() {return this.password;}

    @Override
    public String getUid() {
        return this.uid;
    }

    public String getStatus() {
        return this.status;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ArrayList<String> getStudying() {
        return studying;
    }

    public void setStudying(ArrayList<String> studying) {
        this.studying = studying;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {this.password = password;}

    @Override
    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public void setImageURL(String imagURL) {
        this.imageURL = imagURL;
    }

    @Override
    public String getImageURL() {
        return this.imageURL;
    }

    @Override
    public HashMap<String, String> generateFields() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", this.getUid());
        hashMap.put("firstName", this.getFirstName());
        hashMap.put("lastName", this.getLastName());
        hashMap.put("imageURL", this.getImageURL());
        hashMap.put("status", "offline");
        hashMap.put("email", this.getEmail());
        hashMap.put("password", this.getPassword());
        hashMap.put("courses", null);
        hashMap.put("saved",null);
        hashMap.put("userType", this.userType);
        return hashMap;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}