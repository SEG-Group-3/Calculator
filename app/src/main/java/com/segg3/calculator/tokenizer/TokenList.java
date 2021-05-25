package com.segg3.calculator.tokenizer;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TokenList {
    public final List<Token> tokens = new ArrayList<>(0);

    // region Brackets

    public int getBracketDepth() {
        int depth = 0;
        for (Token tok : tokens) {
            if (tok.data.equals("("))
                depth++;
            else if (tok.data.equals(")"))
                depth--;
        }
        return depth;
    }

    public void openBracket() {
        if (tokens.size() > 0) {
            Token last = tokens.get(tokens.size() - 1);
            if (last.data.equals(")") || last.type == TokenType.Number) {
                addToken(TokenType.Operation, "*");
            }
        }

        tokens.add(new Token(TokenType.Bracket, "("));
    }

    public void closeBracket() {
        if (getBracketDepth() == 0) // So we don't have negative bracket depth
            return;

        Token last = tokens.get(tokens.size() - 1);
        if (last.type == TokenType.Bracket && last.data.equals("(")) { // Ensure brackets must have something inside before being closed
            return;
        }

        tokens.add(new Token(TokenType.Bracket, ")"));
    }

    public void closeAllBrackets() {
        // Note that this may cause redundant brackets that may be cleaned later
        for (int i = 0; i < getBracketDepth(); i++) {
            tokens.add(new Token(TokenType.Bracket, ")"));
        }
    }

    // endregion

    // region Adding tokens

    public void addDigit(String digit) {
        if (digit.equals(".")){
            addPeriod();
            return;
        }
        if (tokens.size() > 0) {
            Token last = tokens.get(tokens.size() - 1);
            if (last.type == TokenType.Number) {
                // Continue writing to last number
                last.data += digit;
                return;
            } else if (last.type.equals(TokenType.Operation) && last.data.equals("-")){

                if (tokens.size()>=2){
                    Token beforeLast = tokens.get(tokens.size() - 2);
                    // If there is not a number before "last" convert minus to number
                    // [ 1, +, (, -, 1, )] becomes [ 1, +, (, -1, )]
                    // [ 1, -, 1] stays the same
                    if (beforeLast.data.equals("(")){
                        removeLast();
                        tokens.add(new Token(TokenType.Number, "-"+digit));
                        return;
                    }
                } else{
                    // Start with -n
                    removeLast();
                    tokens.add(new Token(TokenType.Number, "-"+digit));
                    return;
                }
            } else if (last.data.equals(")"))
                addToken(TokenType.Operation, "*");

        }
        tokens.add(new Token(TokenType.Number, digit));
    }

    private void addPeriod() {
        if (tokens.size() > 0) {
            Token last = tokens.get(tokens.size() - 1);
            if (last.type == TokenType.Number) {
                // Add period to last number if it already doesn't have one
                for (char c : last.data.toCharArray()) {
                    if (c == '.') // Number already has period, do nothing
                        return;
                }
                last.data += '.';
            } else {
                if (last.data.equals(")"))
                    addToken(TokenType.Operation, "*");
                tokens.add(new Token(TokenType.Number, "0."));
            }
        } else {
            tokens.add(new Token(TokenType.Number, "0."));
        }
    }

    public void addFunction(String identifier) {
        //Only for tokenization, functions will be implemented in Calculator class
        if (tokens.size() > 0) {
            Token last = tokens.get(tokens.size() - 1);
            if (!(last.data.equals("(")) && (last.type != TokenType.Operation)) {
                addToken(TokenType.Operation, "*");
            }
        }
        addToken(TokenType.Function, identifier);
        addToken(TokenType.Bracket, "(");
    }

    public void addOp(String op_name) {
        if (tokens.size() > 0) {
            Token last = tokens.get(tokens.size() - 1);
            if (last.type == TokenType.Operation) {
                tokens.remove(last);
            }
        }
        addToken(TokenType.Operation, op_name);
    }

    // endregion

    // region Utility

    @NonNull
    @Override
    public String toString() {
        return tokens.toString();
    }

    public void removeLast() {
        if (tokens.size() > 0)
            tokens.remove(tokens.size() - 1);
    }

    public String toEquationString() {
        return String.join(" ", this.tokens.stream().map(Token::toString).collect(Collectors.joining()));
    }

    public void addToken(TokenType type, String value) {
        tokens.add(new Token(type, value));
    }

    public void clear() {
        tokens.clear();
    }

    public int size(){
        return tokens.size();
    }

    public Token get(int index){
        return tokens.get(index);
    }
    // endregion
}