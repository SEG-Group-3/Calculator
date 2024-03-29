package com.segg3.calculator;

import com.segg3.calculator.tokenizer.Token;
import com.segg3.calculator.tokenizer.TokenList;
import com.segg3.calculator.tokenizer.TokenType;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Stack;

public class Calculator {

    private final TokenList operationTokenizer;
    public final List<Operation> operations;


    public Calculator()
    {
        operationTokenizer=new TokenList();
        operations= new ArrayList<>();

        operations.add(new Operation("sin", 99, 1, (in) -> (float) Math.sin(in.get(0))));
        operations.add(new Operation("cos", 99, 1, (in) -> (float) Math.cos(in.get(0))));
        operations.add(new Operation("tan", 99, 1, (in) -> (float) Math.tan(in.get(0))));

        operations.add(new Operation("abs", 99, 1, (in) -> Math.abs(in.get(0))));
        operations.add(new Operation("ln", 99, 1, (in) -> (float) Math.log(in.get(0))));
        operations.add(new Operation("log", 99, 1, (in) -> (float) Math.log10(in.get(0))));
        operations.add(new Operation("sqrt", 99, 1, (in) -> (float) Math.sqrt(in.get(0))));

        operations.add(new Operation("+", 0, 2, (in) -> in.get(1) + in.get(0)));
        operations.add(new Operation("-", 0, 2, (in) -> in.get(1) - in.get(0)));
        operations.add(new Operation("/", 1, 2, (in) -> in.get(1) / in.get(0)));
        operations.add(new Operation("*", 1, 2, (in) -> in.get(1) * in.get(0)));
        operations.add(new Operation("!", 2, 1, (in) -> {
            float x = 1 + in.get(0);
            double ser = 1.0 + 76.18009173 / (x + 0) - 86.50532033 / (x + 1)
                    + 24.01409822 / (x + 2) - 1.231739516 / (x + 3)
                    + 0.00120858003 / (x + 4) - 0.00000536382 / (x + 5);
            float lnGamma = (float) ((x - 0.5) * Math.log(x + 4.5) - (x + 4.5) + Math.log(ser * Math.sqrt(2 * Math.PI)));
            return (float) Math.exp(lnGamma);
        } ));
        operations.add(new Operation("%", 2, 1, (in) -> in.get(0) / 100));
        operations.add(new Operation("^", 2, 2, (in) -> (float) Math.pow(in.get(1), in.get(0))));
    }



    private Operation getOperation(String identifier) {
        for (Operation op :
                operations) {
            if (op.identifier.equals(identifier))
                return op;
        }
        return null;
    }
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

//    public void addOperation(OperationType type)
//    {
//        Token last = null;
//        if(operationTokenizer.size() > 0)
//        {
//            last = operationTokenizer.get(operationTokenizer.size() - 1);
//        }
//
//        if(last != null && (last.type == TokenType.Number || last.data.equals(")")))
//        {
//            switch(type)
//            {
//                case PLUS:
//                    operationTokenizer.addOp("+");
//                    break;
//                case MINUS:
//                    operationTokenizer.addOp("-");
//                    break;
//                case MULTIPLY:
//                    operationTokenizer.addOp("*");
//                    break;
//                case DIVIDE:
//                    operationTokenizer.addOp("/");
//                    break;
//                case POWER:
//                    operationTokenizer.addOp("^");
//                    break;
//                case CLOSEBRACKET:
//                    operationTokenizer.closeBracket();
//                    break;
//                default:
//                    throw new IllegalArgumentException("Invalid OperationType");
//            }
//        }
//    }

    public void addFunctionCall(String identifier){
        // Dude... no need to change this
        operationTokenizer.addFunction(identifier);
    }

    public void addOperation(String identifier){
        // No need to change this either... Okay?
        operationTokenizer.addOp(identifier);
    }

    public void addDigit(String digit) {operationTokenizer.addDigit(digit);}

    public void openBracket(){
        operationTokenizer.openBracket();
    }

    public void closeBracket(){
        operationTokenizer.closeBracket();
    }

// --Commented out by Inspection START (5/24/2021 11:04 PM):
//    public void addBracketAndFunctions(OperationType type)
//    {
//        switch (type)
//        {
//            case SIN:
//                operationTokenizer.addFunction("sin");
//                operationTokenizer.openBracket();
//                return;
//            case COS:
//                operationTokenizer.addFunction("cos");
//                operationTokenizer.openBracket();
//                return;
//            case TAN:
//                operationTokenizer.addFunction("tan");
//                operationTokenizer.openBracket();
//                return;
//            case OPENBRACKET:
//                operationTokenizer.openBracket();
//                return;
//            case SQRT:
//                operationTokenizer.addFunction("sqrt");
//                operationTokenizer.openBracket();
//                return;
//        }
//        throw new IllegalStateException();
//    }
// --Commented out by Inspection STOP (5/24/2021 11:04 PM)

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

    public List<Token> getRPN() {
        Queue<Token> outQueue = new ArrayDeque<>();
        Stack<Token> opStack = new Stack<>();


        for (Token tok : operationTokenizer.tokens) {
            if (tok.type.equals(TokenType.Number))
                outQueue.add(tok);
            else if (tok.data.equals("("))
                opStack.add(tok);
            else if (tok.data.equals(")")) {
                while (!opStack.lastElement().data.equals("("))
                    outQueue.add(opStack.pop());
                opStack.pop();
            } else if (getOperation(tok.data) != null) {

                if (!tok.type.equals(TokenType.Function)) {
                    Operation curOp = getOperation(tok.data);
                    if( curOp != null)
                        while (opStack.size() > 0 &&
                                !opStack.lastElement().data.equals("(")){

                            if (!(Objects.requireNonNull(getOperation(opStack.lastElement().data)).priority >= curOp.priority))
                                break;
                            outQueue.add(opStack.pop());
                        }
                }

                opStack.add(tok);
            }
        }
        while (opStack.iterator().hasNext()) {
            outQueue.add(opStack.pop());
        }

        return new ArrayList<>(outQueue);
    }

    public float calculate() {
        if (operationTokenizer.size() == 0)
            throw new ArithmeticException("Enter an equation!");

        List<Token> rpnTokens = getRPN();
        Stack<Token> valStack = new Stack<>();
        for (Token tok : rpnTokens) {
            if (tok.type.equals(TokenType.Number)) {
                valStack.add(tok);
            } else {
                Operation op = getOperation(tok.data);
                if( op != null){
                    List<Float> args = new ArrayList<>(op.inputCount);
                    for (int i = 0; i < op.inputCount; i++) {
                        args.add(Float.parseFloat(valStack.pop().data));
                    }
                    float result = op.implementation.op(args);
                    valStack.add(new Token(TokenType.Number, Float.toString(result)));
                }
            }
        }

        return Float.parseFloat((valStack.pop().data));
    }
}
