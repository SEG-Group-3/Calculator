package com.segg3.calculator;

import com.segg3.calculator.tokenizer.Token;
import com.segg3.calculator.tokenizer.TokenList;
import com.segg3.calculator.tokenizer.TokenType;

public class Calculator {

    private TokenList operationTokenizer;

    public Calculator()
    {
        operationTokenizer = new TokenList();
    }

    public float calculate() throws Exception {
        //throw new Exception("You tried to divide by 0");
        throw new UnsupportedOperationException();
    }

    /**
     * addDigit is a method to add a digit to the end of the previous number or to create a new number in TokenList
     *
     * @param digit is a char that will be either appended to the end of the last token number or add new number to the operationTokenizer
     *
     * @exception IllegalArgumentException is thrown when digit is not a number or a period
     */
    public void addDigit(char digit)
    {
        //checking for invalid inputs
        if(!Character.isDigit(digit) && digit != '.')
        {
            throw new IllegalArgumentException("digit char needs to be a number or a '.'");
        }

        Token last;

        if(operationTokenizer.size() !=  0)
        {
            last = operationTokenizer.get(operationTokenizer.size() - 1);
        }
        else
        {
            last = null;
        }

        //if last token is a number then the digit will be appended to the existing number
        if(last != null && last.getTokenType() == TokenType.Number)
        {
            if(Character.isDigit(digit) || (digit == '.' && !last.data.contains(".")))
            {
                last.data = last.data + digit;//new Character(digit).toString();
            }
        }
        //else new token is added to the end of token list
        else
        {
            if(Character.isDigit(digit))
            {
                operationTokenizer.add(TokenType.Number, new Character(digit).toString());
            }
            else
            {
                operationTokenizer.add(TokenType.Number, "0.");
            }
        }
    }

    /**
     * addOperations adds a function, math operation to TokenList
     *
     * @param type is an OperationType that will be added to the TokenList
     *
     * @exception IllegalArgumentException is thrown if the type is a valid type from list
     *
     * @exception IllegalStateException
     */
    public void addOperation(OperationType type)
    {
        Token last = operationTokenizer.get(operationTokenizer.size() - 1);

        if(last.getTokenType() == TokenType.Number)
        {
            switch(type)
            {
                case PLUS:
                    operationTokenizer.add(TokenType.Operation, "+");
                    break;
                case MINUS:
                    operationTokenizer.add(TokenType.Operation, "-");
                    break;
                case MULTIPLY:
                    operationTokenizer.add(TokenType.Operation, "*");
                    break;
                case DIVIDE:
                    operationTokenizer.add(TokenType.Operation, "/");
                    break;
                case SIN:
                    operationTokenizer.add(TokenType.Operation, "sin");
                    operationTokenizer.add(TokenType.Operation, "(");
                    break;
                case COS:
                    operationTokenizer.add(TokenType.Operation, "cos");
                    operationTokenizer.add(TokenType.Operation, "(");
                    break;
                case TAN:
                    operationTokenizer.add(TokenType.Operation, "tan");
                    operationTokenizer.add(TokenType.Operation, "(");
                    break;
                case POWER:
                    operationTokenizer.add(TokenType.Operation, "^");
                    break;
                case OPENBRACKET:
                    operationTokenizer.add(TokenType.Operation, "(");
                    break;
                case CLOSEBRACKET:
                    // implment a way to check if there is an active open bracket
                    operationTokenizer.add(TokenType.Operation, ")");
                    break;
                default:
                    throw new IllegalArgumentException("Invalid OpperationType");
            }
            return;
        }
        throw new IllegalStateException("Last token needs to be a number");
    }

    public String displayString()
    {
        return operationTokenizer.toEquationString();
    }
}
