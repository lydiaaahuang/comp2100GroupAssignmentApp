package com.example.tutorapp.BackEnd;

import java.util.Scanner;

/**
 * Parser takes a bunch of tokens and evaluates them based on their inferred type
 */
public class Parser {

    //if not a token then error
    public static class IllegalProductionException extends IllegalArgumentException {
        public IllegalProductionException(String errorMessage) {
            super(errorMessage);
        }
    }

    // The tokenizer (class field) this parser will use.
    Tokenizer tokenizer;


    public Parser(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public Exp parseExp() {
        return parseInput();
    }

    public Exp parseInput() {
        try {
            if (tokenizer.current().getType() == Token.Type.STRING) {
                return new StringExp(tokenizer.current().getToken());
            } else {
                //(tokenizer.current().getType() == Token.Type.INT) condition
                return new IntExp(Integer.parseInt(tokenizer.current().getToken()));
            }
        } catch (Exception e) {
            throw new IllegalProductionException("Invalid input");
        }
    }
}

