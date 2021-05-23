package com.segg3.calculator;

import android.util.Log;

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

    public TokenList operationTokenizer;
    public List<Operation> operations;

    public Calculator() {
        operationTokenizer = new TokenList();
        operations = new ArrayList<>();

        operations.add(new Operation("sin", 0, 1, (in) -> (float) Math.sin(in.get(0))));
        operations.add(new Operation("cos", 0, 1, (in) -> (float) Math.cos(in.get(0))));
        operations.add(new Operation("tan", 0, 1, (in) -> (float) Math.tan(in.get(0))));

        operations.add(new Operation("abs", 0, 1, (in) -> Math.abs(in.get(0))));
        operations.add(new Operation("ln", 0, 1, (in) -> (float) Math.log(in.get(0))));
        operations.add(new Operation("log", 0, 1, (in) -> (float) Math.log10(in.get(0))));
        operations.add(new Operation("sqrt", 0, 1, (in) -> (float) Math.sqrt(in.get(0))));

        operations.add(new Operation("+", 0, 2, (in) -> in.get(1) + in.get(0)));
        operations.add(new Operation("-", 0, 2, (in) -> in.get(1) - in.get(0)));
        operations.add(new Operation("/", 1, 2, (in) -> in.get(1) / in.get(0)));
        operations.add(new Operation("*", 1, 2, (in) -> in.get(1) * in.get(0)));
        operations.add(new Operation("!", 2, 1, (in) -> (float) Gamma.gamma(in.get(0) + 1)));
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

    public void clear(){
        operationTokenizer.clear();
    }

    public void removeLast(){
        operationTokenizer.removeLast();
    }


    public void addDigit(String digit){
        operationTokenizer.addDigit(digit);
    }

    public void addFunction(String fun_name){
        operationTokenizer.addFunction(fun_name);
    }

    public void addOp(String fun_name){
        operationTokenizer.addOp(fun_name);
    }

    public void openBracket(){
        operationTokenizer.openBracket();
    }

    public void closeBracket(){
        operationTokenizer.closeBracket();
    }

    public List<Token> toRPN() {
        Queue<Token> outQueue = new ArrayDeque<>();
        Stack<Token> opStack = new Stack<>();


        for (Token tok : operationTokenizer.tokens) {
            if (tok.getTokenType().equals(TokenType.Number))
                outQueue.add(tok);
            else if (tok.data.equals("("))
                opStack.add(tok);
            else if (tok.data.equals(")")) {
                while (!opStack.lastElement().data.equals("("))
                    outQueue.add(opStack.pop());
                opStack.pop();
            } else if (getOperation(tok.data) != null) {

                if (!tok.getTokenType().equals(TokenType.Function)) {
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
        if (operationTokenizer.size() == 0 || operationTokenizer.getBracketDepth() != 0)
            return 0;
        List<Token> rpnTokens = toRPN();
        Stack<Token> valStack = new Stack<>();
        for (Token tok : rpnTokens) {
            if (tok.getTokenType().equals(TokenType.Number)) {
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
