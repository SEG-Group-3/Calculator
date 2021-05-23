package com.segg3.calculator.tokenizer;

import androidx.annotation.NonNull;

public class Token {
    public final TokenType type;
    public String data;

    public Token(TokenType type, String data) {
        this.type = type;
        this.data = data;
    }

    public TokenType getTokenType() {
        return type;
    }

    @NonNull
    @Override
    public String toString() {
        return data;
    }
}
