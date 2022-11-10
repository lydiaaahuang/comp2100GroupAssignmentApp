package com.example.tutorapp.data.model;

public class Message {

    private String chatID;
    private String senderEmail;
    private String receiverEmail;
    private String message;


    public Message(String chatId, String senderEmail, String receiverEmail, String message){
        this.chatID = chatId;
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
        this.message = message;
    }

    public void setChatID(String chatID) {
        this.chatID = chatID;
    }

    public String getChatID() {
        return this.chatID;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "chatID='" + chatID + '\'' +
                ", senderEmail='" + senderEmail + '\'' +
                ", receiverEmail='" + receiverEmail + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
