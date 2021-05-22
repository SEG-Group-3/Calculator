package com.segg3.calculator.tokenizer;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class TokenList{
    public List<Token> tokens = new ArrayList<>(0);

    public TokenList(){

    }

    public List<Token> getTokens(){
        return tokens;
    }

    public int getBracketDepth(){
        int depth = 0;
        for (Token tok: tokens) {
            if (tok.data.equals("("))
                depth++;
            else if (tok.data.equals(")"))
                depth--;
        }
        return depth;
    }

    public void removeLast(){
        if (tokens.size() > 0)
            tokens.remove(tokens.size()-1);
    }

    // region Brackets

    public boolean openBracket(){
        if(tokens.size() > 0){
            Token last = tokens.get(tokens.size()-1);
            if (last.data.equals(")") || last.getTokenType() == TokenType.Number){
                mulOperation();
            }
        }

        tokens.add(new Token(TokenType.Bracket, "("));
        return true;
    }

    public boolean closeBracket(){
        if (getBracketDepth() == 0) // So we don't have negative bracket depth
            return false;

        Token last = tokens.get(tokens.size()-1);
        if (last.getTokenType() == TokenType.Bracket && last.data.equals("(")){ // Ensure brackets must have something inside before being closed
            return false;
        }

        tokens.add(new Token(TokenType.Bracket, ")"));
        return  true;
    }

    // endregion

    // region Operations

    private void addOperationToken(Token token){
        if(tokens.size() > 0) {
            Token last = tokens.get(tokens.size()-1);
            if (last.getTokenType() == TokenType.Operation) {
                tokens.remove(last);
            }
        }
        tokens.add(token);
    }


    public void addOperation(){
        // Can't start with plus
        if (tokens.size() == 0)
            return;

        Token last = tokens.get(tokens.size()-1);
        if (last.getTokenType() == TokenType.Number && last.data.equals("-")){
            removeLast();
            return;
        }


        // Override last operations
        addOperationToken(new Token(TokenType.Operation, "+"));
    }

    public void subOperation(){
        if (tokens.size() == 0){
            tokens.add(new Token(TokenType.Number, "-"));
            return;
        }

        Token last = tokens.get(tokens.size()-1);
        if (last.getTokenType() == TokenType.Number && last.data.equals("-")){
            return;
        }
        addOperationToken(new Token(TokenType.Operation, "-"));

    }

    public void mulOperation(){
        // Can't start with mul
        if (tokens.size() == 0)
            return;

        Token last = tokens.get(tokens.size()-1);
        if (last.getTokenType() == TokenType.Number && last.data.equals("-")){
            removeLast();
            return;
        }

        // Override last operations
        addOperationToken(new Token(TokenType.Operation, "*"));
    }

    public void divOperation(){
        // Can't start with div
        if (tokens.size() == 0)
            return;

        Token last = tokens.get(tokens.size()-1);
        if (last.getTokenType() == TokenType.Number && last.data.equals("-")){
            removeLast();
            return;
        }

        // Override last operations
        addOperationToken(new Token(TokenType.Operation, "/"));
    }

    public void powOperation(){
        // Can't start with pow
        if (tokens.size() == 0)
            return;

        // Override last operations
        addOperationToken(new Token(TokenType.Operation, "^"));
    }

    // endregion

    // region Numbers

    public void addDigit(char digit){
        if(tokens.size() > 0) {
            Token last = tokens.get(tokens.size()-1);
            if (last.getTokenType() == TokenType.Number) {
                // Continue writing to last number
                last.data += digit;
            } else{
                if (last.data.equals(")"))
                    mulOperation();
                tokens.add(new Token(TokenType.Number, ""+digit));
            }
        } else{
            tokens.add(new Token(TokenType.Number, ""+digit));
        }
    }

    public void addDigit(String digit){
        if(tokens.size() > 0) {
            Token last = tokens.get(tokens.size()-1);
            if (last.getTokenType() == TokenType.Number) {
                // Continue writing to last number
                last.data += digit;
            } else{
                if (last.data.equals(")"))
                    mulOperation();
                tokens.add(new Token(TokenType.Number, digit));
            }
        } else{
            tokens.add(new Token(TokenType.Number, digit));
        }
    }


    public void addPeriod(){
        if(tokens.size() > 0) {
            Token last = tokens.get(tokens.size()-1);
            if (last.getTokenType() == TokenType.Number) {
                // Add period to last number if it already doesn't have one
                for (char c : last.data.toCharArray()) {
                    if (c == '.') // Number already has period, do nothing
                        return;
                }
                last.data += '.';
            } else {
                if (last.data.equals(")"))
                    mulOperation();
                tokens.add(new Token(TokenType.Number, "0."));
            }
        }  else{
            tokens.add(new Token(TokenType.Number, "0."));
        }
    }

    // endregion

    // region Functions

    public void functionCall(String fun_name){
        //Only for tokenization, functions will be implemented in Calculator class
        if (tokens.size() > 0){
            Token last = tokens.get(tokens.size()-1);
            if ( !(last.data.equals("(")) &&  (last.getTokenType() != TokenType.Operation)){
                mulOperation();
            }
        }
        tokens.add(new Token(TokenType.Function, fun_name));
        openBracket();
    }


    // endregion

    // region Function Cleanup

    // TODO Add a way to collapse redundant brackets

    public void optimize(){
        closeAllBrackets();
        // CleanRedundancy();
    }

    public void closeAllBrackets(){
        // Note that this may cause redundant brackets that may be cleaned later
        for (int i = 0; i < getBracketDepth(); i++) {
            tokens.add(new Token(TokenType.Bracket, ")"));
        }
    }

    // endregion

    // region Required
    @NonNull
    @Override
    public String toString() {
        return tokens.toString();
    }

    public String toEquationString() {
        return String.join(" ", this.tokens.stream().map(Token::toString).collect(Collectors.joining()) );
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
