package com.example.tutorapp.BackEnd;

/**
 * Tokenizer splits a string into small units called, 'Tokens', to be passed onto the Parser.
 */
public class Tokenizer {
    private String buffer;          // String to be transformed into tokens each time next() is called.
    private Token currentToken;     // The current token. The next token is extracted when next() is called.

    /**
     * Tokenizer class constructor
     * The constructor extracts the first token and save it to currentToken
     */
    public Tokenizer(String text) {
        buffer = text;          // save input text (string)
        next();                 // extracts the first token.
    }

    /**
     * This function will find and extract a next token from {@code _buffer} and
     * save the token to {@code currentToken}.
     */
    public void next() {
        buffer = buffer.trim();     // remove whitespace

        if (buffer.isEmpty()) {
            currentToken = null;    // if there's no string left, set currentToken null and return
            return;
        }
        char firstChar = buffer.charAt(0);

        //Check if it is an alphabet
        if (Character.isLetter(firstChar)) {
            int i = 0;
            while ((i < buffer.length()) && (Character.isLetter(buffer.charAt(i)))) {
                i++;
            }
            currentToken = new Token(buffer.substring(0, i), Token.Type.STRING);
        } else if (Character.isDigit(firstChar)) {
            int i = 0;
            while ((i < buffer.length()) && (Character.isDigit(buffer.charAt(i)))) {
                i++;
            }
            currentToken = new Token(buffer.substring(0, i), Token.Type.INT);
        } else if (firstChar == '$') {
            //Ignore dollar sign, read numbers after (if any)?
            if (hasNext()) {
                next();
            } else {
                currentToken = new Token("0", Token.Type.INT);
            }
        } else if (firstChar == '@') {
            if (hasNext()) {
                next();
            } else {
                throw new Token.IllegalTokenException("No username entered");
            }
        } else {
            throw new Token.IllegalTokenException("Character does not correlate to any token type provided");
        }

        // Remove the extracted token from buffer
        int tokenLen = currentToken.getToken().length();
        buffer = buffer.substring(tokenLen);
    }

    /**
     * Returns the current token extracted by {@code next()}
     *
     * @return type: Token
     */
    public Token current() {
        return currentToken;
    }

    //TODO: This shit is stupid, currentToken in this case points to nextToken and its all a big mess
    //Will deal w this later, might need help
    /**
     * Check whether tokenizer still has tokens left
     *
     * @return type: boolean
     */
    public boolean hasNext() {
        return currentToken != null;
    }
}
