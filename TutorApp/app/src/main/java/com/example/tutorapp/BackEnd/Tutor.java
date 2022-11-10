package com.example.tutorapp.BackEnd;

import java.util.ArrayList;
import java.util.HashMap;

public class Tutor extends User implements UserMethod {

    public ArrayList<String> courses = new ArrayList<>();
    //Hourly rate
    public String userType;
    public int fee;
    public Tutor(String firstName, String lastName, String email, String password, String uid, String status, ArrayList<String> courses, int fee, boolean isTutor,
                 String userType, String imageURL) {
        super(firstName, lastName,email, password, uid, status, imageURL);
        this.courses = courses;
        this.fee = fee;
        this.userType = userType;
    }

    public Tutor(){}; //empty constructor for use in firebase

    public void addCourse(String course) {
        this.courses.add(course);
    }

    public ArrayList<String> getCourse() {
        return this.courses;
    }

    public void setUserType(String userType){
        this.userType = userType;
    }

    public String getUserType(){
        return this.userType;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public int getFee() {
        return this.fee;
    }

    @Override
    public String getFirstName() {
        return this.firstName;
    }

    @Override
    public String getLastName() {return this.lastName;
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


    public void setFirstName(String name) {
        this.firstName = name;
    }

    public void setLastName(String name) {
        this.lastName = name;

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
        hashMap.put("price", Integer.toString(this.getFee()));
        hashMap.put("userType",this.getUserType());
        return hashMap;
    }

    public void setStatus(String uid) {
        this.status = status;
    }
}