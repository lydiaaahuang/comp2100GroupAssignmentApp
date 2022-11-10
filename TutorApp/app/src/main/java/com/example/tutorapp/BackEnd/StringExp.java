package com.example.tutorapp.BackEnd;

public class StringExp extends Exp {
    private String value;

    public StringExp(String value) {
        this.value = value;
    }

    @Override
    public String show() {
        return value;
    }

    @Override
    public int evaluate() {
        return 0;
    }
}
