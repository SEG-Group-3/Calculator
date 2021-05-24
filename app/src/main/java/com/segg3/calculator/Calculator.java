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
        if(last != null && last.type == TokenType.Number)
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
                operationTokenizer.addDigit(Character.valueOf(digit).toString());
            }
            else
            {
                operationTokenizer.addDigit(".");
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

        if(last.type == TokenType.Number)
        {
            switch(type)
            {
                case PLUS:
                    operationTokenizer.addOp("+");
                    break;
                case MINUS:
                    operationTokenizer.addOp("-");
                    break;
                case MULTIPLY:
                    operationTokenizer.addOp("*");
                    break;
                case DIVIDE:
                    operationTokenizer.addOp("/");
                    break;
                case SIN:
                    operationTokenizer.addFunction("sin");
                    break;
                case COS:
                    operationTokenizer.addFunction("cos");
                    break;
                case TAN:
                    operationTokenizer.addFunction("tan");
                    break;
                case POWER:
                    operationTokenizer.addOp("^");
                    break;
                case OPENBRACKET:
                    operationTokenizer.openBracket();
                    break;
                case CLOSEBRACKET:
                    // implment a way to check if there is an active open bracket
                    operationTokenizer.closeBracket();
                    break;
                case SQRT:
                    operationTokenizer.addFunction("SQRT");
                default:
                    throw new IllegalArgumentException("Invalid OpperationType");
            }
            return;
        }
        System.out.println("Last token needs to be a number");
        //throw new IllegalStateException("Last token needs to be a number");
    }

    public void clear()
    {
        operationTokenizer.clear();
    }

    public void removeLast()
    {
        if(operationTokenizer.size() > 0)
        {
            Token last = operationTokenizer.get(operationTokenizer.size() - 1);

            if(last.type == TokenType.Number && last.data.length() > 1)
            {
                last.data = last.data.substring(0,last.data.length() - 1);
            }
            else
            {
                operationTokenizer.removeLast();
            }

        }
    }

    public String displayString()
    {
        return operationTokenizer.toEquationString();
    }
}
