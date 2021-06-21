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
        calc.addOperation("-");
        calc.addDigit('5');
        List<Token> expected= new ArrayList<>();
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

    public void testEquation() {
        Calculator calc = new Calculator();
        calc.addFunctionCall("sqrt");
        calc.openBracket();
        calc.addDigit("13");
        calc.addOperation("*");
        calc.addDigit("2");
        calc.closeBracket();
        calc.addOperation("-");
        calc.openBracket();
        calc.addFunctionCall("sin");
        calc.addDigit("2");

        float expected =(float) Math.sqrt((13*2) - (Math.sin(2)));
        float actual = calc.calculate();
        float delta = expected - actual;
        assertEquals(expected, actual);
        assertTrue(Math.abs(delta) < 0.1);
    }


}