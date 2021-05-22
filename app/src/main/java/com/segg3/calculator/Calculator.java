package com.segg3.calculator;

import com.segg3.calculator.tokenizer.TokenList;

public class Calculator {

    public TokenList tokenList;

    public Calculator()
    {
        tokenList = new TokenList();
    }

    public float calculate()
    {
        throw new UnsupportedOperationException();
    }

    public void addDigit(char digit)
    {
        Token last = tokenList.get(tokenList.size() - 1);

        if(!char.isDigit(digit) && digit != '.')
        {
            throw new IllegalArgumentException("digit char needs to be a number or a '.'");
        }

        if(last.getTokenType() == TokenType.Number)
        {
            if(char.isDigit(digit) || (digit == '.' && !last.data.contains(".")))
            {
                last.data = last.data + digit.toString();
            }
        }
        else
        {
            if(char.isDigit(digit))
            {
                tokenList.add(TokenType.Number, digit.toString());
            }
            else
            {
                tokenList.add(TokenType.Number, "0.");
            }
        }

    }

    public void addOperations(String str)
    {
        throw new UnsupportedOperationException();
    }
}
