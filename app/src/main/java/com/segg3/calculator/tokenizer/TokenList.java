package com.segg3.calculator.tokenizer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class TokenList{
    public List<Token> tokens = new ArrayList<>(0);
    private int bracketDepth = 0;

    public TokenList(){

    }

    public  int GetBracketDepth(){
        return  bracketDepth;
    }

    // region Brackets

    public boolean OpenBracket(){
        if(tokens.size() > 0){
            Token last = tokens.get(tokens.size()-1);
            if (last.type == TokenType.Bracket && last.data.equals("(")){ // Ensure brackets must have something inside before having an inner bracket
                return false;
            }
            if (last.type == TokenType.Bracket || last.type == TokenType.Number){
                MulOperation();
            }
        }


        bracketDepth++;
        tokens.add(new Token(TokenType.Bracket, "("));
        return true;
    }

    public boolean CloseBracket(){
        if (bracketDepth == 0) // So we don't have negative bracket depth
            return false;

        Token last = tokens.get(tokens.size()-1);
        if (last.type == TokenType.Bracket && last.data.equals("(")){ // Ensure brackets must have something inside before being closed
            return false;
        }


        bracketDepth--;
        tokens.add(new Token(TokenType.Bracket, ")"));
        return  true;
    }

    // endregion

    // region Operations

    private void AddOperationToken(Token token){
        if(tokens.size() > 0) {
            Token last = tokens.get(tokens.size()-1);
            if (last.type == TokenType.Operation) {
                tokens.remove(last);
            }
        }
        tokens.add(token);
    }


    public void AddOperation(){
        // Can't start with plus
        if (tokens.size() == 0)
            return;

        // Override last operations
        AddOperationToken(new Token(TokenType.Operation, "+"));
    }

    public void SubOperation(){
        if (tokens.size() == 0){
            tokens.add(new Token(TokenType.Number, "-"));
            return;
        }

        Token last = tokens.get(tokens.size()-1);
        switch (last.type){
            case Bracket:
                if (last.data.equals("("))
                    tokens.add(new Token(TokenType.Number, "-"));
                else
                    tokens.add(new Token(TokenType.Operation, "-"));
                break;
            case Operation:
                if (!last.data.equals("-"))
                    tokens.add(new Token(TokenType.Number, "-"));
                else
                    tokens.add(new Token(TokenType.Operation, "-"));
                break;
            case Number:
                tokens.add(new Token(TokenType.Operation, "-"));
        }
    }

    public void MulOperation(){
        // Can't start with mul
        if (tokens.size() == 0)
            return;

        // Override last operations
        AddOperationToken(new Token(TokenType.Operation, "*"));
    }

    public void DivOperation(){
        // Can't start with div
        if (tokens.size() == 0)
            return;

        // Override last operations
        AddOperationToken(new Token(TokenType.Operation, "/"));
    }

    public void PowOperation(){
        // Can't start with pow
        if (tokens.size() == 0)
            return;

        // Override last operations
        AddOperationToken(new Token(TokenType.Operation, "^"));
    }

    // endregion

    // region Numbers

    public void AddDigit(char digit){
        if(tokens.size() > 0) {
            Token last = tokens.get(tokens.size()-1);
            if (last.type == TokenType.Number) {
                // Continue writing to last number
                last.data += digit;
            } else{
                if (last.data.equals(")"))
                    MulOperation();
                tokens.add(new Token(TokenType.Number, ""+digit));
            }
        } else{
            tokens.add(new Token(TokenType.Number, ""+digit));
        }
    }

    public void AddDigit(String digit){
        if(tokens.size() > 0) {
            Token last = tokens.get(tokens.size()-1);
            if (last.type == TokenType.Number) {
                // Continue writing to last number
                last.data += digit;
            } else{
                if (last.data.equals(")"))
                    MulOperation();
                tokens.add(new Token(TokenType.Number, digit));
            }
        } else{
            tokens.add(new Token(TokenType.Number, digit));
        }
    }


    public void AddPeriod(){
        if(tokens.size() > 0) {
            Token last = tokens.get(tokens.size()-1);
            if (last.type == TokenType.Number) {
                // Add period to last number if it already doesn't have one
                for (char c : last.data.toCharArray()) {
                    if (c == '.') // Number already has period, do nothing
                        return;
                }
                last.data += '.';
            } else {
                if (last.data.equals(")"))
                    MulOperation();
                tokens.add(new Token(TokenType.Number, "0."));
            }
        }  else{
            tokens.add(new Token(TokenType.Number, "0."));
        }
    }

    // endregion

    // region Functions

    public void FunctionCall(String fun_name){
        //Only for tokenization, functions will be implemented in Calculator class
        tokens.add(new Token(TokenType.Function, fun_name));
        OpenBracket();
    }


    // endregion

    // region Function Cleanup

    // TODO Add a way to collapse redundant brackets

    public void CleanUp(){
        CloseAllBrackets();
        // CleanRedundancy();
    }

    public void CloseAllBrackets(){
        // Note that this may cause redundant brackets that may be cleaned later
        for (int i = 0; i < bracketDepth; i++) {
            tokens.add(new Token(TokenType.Bracket, ")"));
        }
        bracketDepth = 0;
    }

    // endregion

    // region Required
    @NonNull
    @Override
    public String toString() {
        return String.join("", this.tokens.stream().map(Token::toString).collect(Collectors.joining()) );
    }


    public int size() {
        return tokens.size();
    }

    public boolean add(String value) {
        return tokens.add(new Token(TokenType.Unknown, value));
    }

    public boolean add(TokenType type, String value) {
        return tokens.add(new Token(type, value));
    }

    public boolean add(Token token) {
        return tokens.add(token);
    }

    public  boolean remove(){
        if (size() > 0){
            tokens.remove(size()-1);
            return  true;
        }
        return false;
    }

    public Token[] toArray() {
        return (Token[])tokens.toArray();
    }

    public boolean remove(Token o) {
        return tokens.remove(o);
    }

    public void clear() {
        tokens.clear();
    }

    public Token get(int pos){
        return tokens.get(pos);
    }

    public Iterator<Token> getIterator() {
        return tokens.iterator();
    }



    // endregion
}
