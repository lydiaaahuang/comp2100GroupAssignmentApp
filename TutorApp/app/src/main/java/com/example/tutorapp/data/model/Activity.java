package com.example.tutorapp.data.model;

import androidx.annotation.NonNull;

/**
 * This is a constructor for an activity in an avtivity log.
 * An activity is defined by the following:
 * <p>
 * Has the following features (columns): ID, Status, Time.
 * Uniquely mapped per row ie every row is a new log.
 * <p>
 * The features are stored in a String[]. ID, Status, and Time features are the order
 * of the columns. For instance, fetures[1] will return the ID of this row.
 */
public class Activity {

    private String ID; // ID of the tutor
    private String Status; // The active status of the tutor
    private String Time; // time of the status


    public Activity(String ID, String Status, String Time) {
        this.ID = ID;
        this.Status = Status;
        this.Time = Time;
    }


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Action) {
        this.Status = Status;
    }


    public String getTime() {
        return Time;
    }

    // TODO: determine if time should be final. Logs shouldn't really be manipulated etc.
    public void setTime(String time) {
        Time = time;
    }

    @NonNull
    @Override
    public String toString() {
        return "Tutor: " + ID + ", Status: " + Status + ", Time: " + Time;
    }
    //TODO: print out in tabular format?

    //TODO: Think about how to implement this. We may need to do some sort of left join.
}
