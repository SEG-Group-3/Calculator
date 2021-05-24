package com.segg3.calculator;

import com.segg3.calculator.tokenizer.Token;
import com.segg3.calculator.tokenizer.TokenType;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class CalculatorTest extends TestCase {
    public void testToRPN(){
        Calculator calc=new Calculator();
        calc.addDigit('1');
        calc.addOperation(OperationType.MINUS);
        calc.addDigit('5');
        List<Token> expected=new ArrayList<Token>();
        expected.add(new Token(TokenType.Number,"1"));
        expected.add(new Token(TokenType.Number,"5"));
        expected.add(new Token(TokenType.Operation,"-"));
        List<Token> actual=calc.getRPN();
        int i=0;
        for (Token elem:actual){
            assertEquals(expected.get(i),elem);
            i++;
        }
    }

}