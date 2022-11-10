package com.example.tutorapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import com.example.tutorapp.BackEnd.Token;
import com.example.tutorapp.BackEnd.Tokenizer;

import org.junit.Test;

import java.util.ArrayList;

public class TokenizerTest {
    private static Tokenizer tokenizer;
    private static final String SIMPLE_NAME = "Lydia";
    private static final String SIMPLE_COST = "500";
    private static final String DOLLAR_COST = "$10";

    @Test(timeout=1000)
    public void testSimpleNameToken() {
        tokenizer = new Tokenizer(SIMPLE_NAME);
        assertEquals("wrong token type", Token.Type.STRING, tokenizer.current().getType());
        assertEquals("wrong token value", "Lydia", tokenizer.current().getToken());
    }

    @Test(timeout=1000)
    public void testSimpleCostToken() {
        tokenizer = new Tokenizer(SIMPLE_COST);
        // check the type of the first token
        assertEquals("wrong token type", Token.Type.INT, tokenizer.current().getType());

        // check the actual token value
        assertEquals("wrong token value", "500", tokenizer.current().getToken());
    }


    @Test (timeout = 1000)
    public void testException(){

        //keyboard mash
        assertThrows(Token.IllegalTokenException.class, () ->{
            tokenizer = new Tokenizer("45&@~`!#%^?*");
            tokenizer.next();
        });
    }

}


