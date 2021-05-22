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

    /**
     * addDigit is a method to add a digit to the end of the previous number or to create a new number in TokenList
     *
     * @param digit is a char that will be either appended to the end of the last token number or add new number to the tokenList
     *
     * @exception IllegalArgumentException is thrown when digit is not a number or a period
     *
     */
    public void addDigit(char digit)
    {
        //checking for invalid inputs
        if(!char.isDigit(digit) && digit != '.')
        {
            throw new IllegalArgumentException("digit char needs to be a number or a '.'");
        }

        Token last = tokenList.get(tokenList.size() - 1);

        //if last token is a number then the digit will be appended to the existing number
        if(last.getTokenType() == TokenType.Number)
        {
            if(char.isDigit(digit) || (digit == '.' && !last.data.contains(".")))
            {
                last.data = last.data + digit.toString();
            }
        }
        //else new token is added to the end of token list
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
