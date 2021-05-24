package com.segg3.calculator.tokenizer;

import junit.framework.TestCase;

public class TokenListTest extends TestCase {

    public void testToString(){
        TokenList list = new TokenList();
        assertEquals("", list.toEquationString());
        list.openBracket();
        list.addDigit("1");
        assertEquals("(1", list.toEquationString());
        list.closeBracket();
        assertEquals("(1)", list.toEquationString());
        list.addDigit(".");
        list.addDigit("5");
        assertEquals("(1)*0.5", list.toEquationString());
    }

    public void testBracketDepth() {
        TokenList list = new TokenList();
        list.openBracket();
        list.addDigit("1");
        list.openBracket();
        list.addDigit("1");
        list.openBracket();
        list.addDigit("1");
        list.openBracket();
        assertEquals(4, list.getBracketDepth());
        list.addDigit("1");
        list.closeBracket();
        list.addDigit("1");
        list.closeBracket();
        list.addDigit("1");
        list.closeBracket();
        list.addDigit("1");
        assertEquals(1, list.getBracketDepth());
    }

    public  void testBracketImplicitMul(){
        TokenList list = new TokenList();
        list.openBracket();
        list.addDigit("1");
        list.openBracket();
        list.addDigit("1");
        list.closeBracket();
        list.addDigit("1");
        list.closeBracket();
        list.addDigit("1");
        list.openBracket();
        list.addDigit("1");
        list.closeBracket();
        list.addDigit("1");
        assertEquals("(1*(1)*1)*1*(1)*1", list.toEquationString());
    }

    public void testAddPeriod() {
        TokenList list = new TokenList();
        list.addOp("-");
        list.addDigit("1");
        list.addDigit(".");
        list.addDigit("5");
        list.addDigit("."); // Nothing should happen
        assertEquals("-1.5", list.toEquationString());
        list.addOp("-");
        list.addDigit(".");
        list.addDigit("2");
        assertEquals("-1.5-0.2", list.toEquationString());
    }

    public void testFunction(){
        TokenList list = new TokenList();
        list.addDigit("1");
        list.addOp("-");
        list.addFunction("sin");
        list.addDigit("6");
        list.addDigit(".");
        list.addDigit("9");
        list.closeAllBrackets();
        assertEquals("1-sin(6.9)", list.toEquationString());
    }

}