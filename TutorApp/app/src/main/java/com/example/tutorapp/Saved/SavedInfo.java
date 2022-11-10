package com.example.tutorapp.Saved;

/**
 * Class for storing tutor's info to display within the recycler view of MainSaved Activity
 */
public class SavedInfo {
    // Used to store info of each saved tutor profile
    private int profileImage;
    private int statusIcon;
    private String name_saved_profile;
    private String saved_bio;
    private String saved_recommend;


    public SavedInfo(int profileImage, int statusIcon, String name_saved_profile, String saved_bio, String saved_recommend){
        this.profileImage = profileImage;
        this.statusIcon = statusIcon;
        this.name_saved_profile = name_saved_profile;
        this.saved_bio = saved_bio;
        this.saved_recommend= saved_recommend;
    }


    public int getProfileImage() {
        return profileImage;
    }

    public int getStatusIcon() {
        return statusIcon;
    }

    public String getName_saved_profile() {
        return name_saved_profile;
    }

    public String getSaved_bio() {
        return saved_bio;
    }

    public String getSaved_recommend() {
        return saved_recommend;
    }

    public void setProfileImage(int profileImage) {
        this.profileImage = profileImage;
    }

    public void setStatusIcon(int statusIcon) {
        this.statusIcon = statusIcon;
    }

    public void setName_saved_profile(String name_saved_profile) {
        this.name_saved_profile = name_saved_profile;
    }

    public void setSaved_bio(String saved_bio) {
        this.saved_bio = saved_bio;
    }

    public void setSaved_recommend(String saved_recommend) {
        this.saved_recommend = saved_recommend;
    }
}
