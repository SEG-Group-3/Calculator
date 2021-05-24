package com.segg3.calculator.tokenizer;

import androidx.annotation.NonNull;

import java.util.Dictionary;
import java.util.Hashtable;

public class Token {
    public final TokenType type;
    public String data;

    private static Dictionary<String, Integer> OperationOrder = new Hashtable<String, Integer>();
    private static boolean initialized = false;
    public Token(TokenType type, String data) {
        this.type = type;
        this.data = data;

        if(!Token.initialized)
        {
            Token.initialize();
            Token.initialized = true;
        }
    }

    private static void initialize()
    {
        Token.OperationOrder.put("+",0);
        Token.OperationOrder.put("-",0);

        Token.OperationOrder.put("*",1);
        Token.OperationOrder.put("/",1);

        Token.OperationOrder.put("^",2);
        Token.OperationOrder.put("%",2);
        Token.OperationOrder.put("!",2);

        Token.OperationOrder.put("sin",0);
        Token.OperationOrder.put("cos",0);
        Token.OperationOrder.put("tan",0);
        Token.OperationOrder.put("abs",0);
        Token.OperationOrder.put("ln",0);
        Token.OperationOrder.put("log",0);
        Token.OperationOrder.put("sqrt",0);
    }

    public int CompareTo(Token t)
    {
        if(t == null)
        {
            throw new NullPointerException();
        }

        if(this.type != TokenType.Number && t.type != TokenType.Number)
        {
            if(Token.OperationOrder.get(this.data) < Token.OperationOrder.get(t.data))
            {
                return 1;
            }
            else if(Token.OperationOrder.get(this.data) == Token.OperationOrder.get(t.data))
            {
                return 0;
            }
            else
            {
                return -1;
            }
        }
        else if(this.type == TokenType.Number && t.type == TokenType.Number)
        {
            int i1, i2;

            i1 = Integer.valueOf(this.data);
            i2 = Integer.valueOf(t.data);

            if(i1 < i2)
            {
                return 1;
            }
            else if(i1 == i2)
            {
                return 0;
            }
            else
            {
                return -1;
            }
        }

        throw new IllegalArgumentException();
    }

    @NonNull
    @Override
    public String toString() {
        return data ;
    }
}
