package com.example.tutorapp.BackEnd;

import java.util.HashMap;

public interface UserMethod {

    public String getFirstName();
    public String getLastName();
    public String getEmail();
    public String getPassword();
    public String getUid();

    void setFirstName(String firstName);
    void setLastName (String lastName);
    void setEmail(String email);
    void setPassword(String password);
    void setUid(String uid);
    void setImageURL(String imagURL);
    String getImageURL();
    HashMap<String, String> generateFields();
}