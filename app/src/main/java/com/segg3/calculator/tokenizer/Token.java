package com.segg3.calculator.tokenizer;

import androidx.annotation.NonNull;

public class Token {
    public final TokenType type;
    public String data;

    public Token(TokenType type, String data) {
        this.type = type;
        this.data = data;
    }
    @Override
    public boolean equals(Object o){
        if (!(o instanceof Token)) {
            return false;
        }
        Token other=(Token) o;
        return other.type.equals(this.type)&&other.data.equals(this.data);
    }

    @NonNull
    @Override
    public String toString() {
        return data ;
    }
}
