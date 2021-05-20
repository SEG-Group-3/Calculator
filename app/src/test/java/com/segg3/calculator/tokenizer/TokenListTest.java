package com.segg3.calculator.tokenizer;

import junit.framework.TestCase;

public class TokenListTest extends TestCase {

    public void testToString(){
        TokenList list = new TokenList();
        assertEquals("", list.toString());
        list.OpenBracket();
        list.AddDigit('1');
        assertEquals("(1", list.toString());
        list.CloseBracket();
        assertEquals("(1)", list.toString());
        list.AddPeriod();
        list.AddDigit('5');
        assertEquals("(1)*0.5", list.toString());
    }

    public void testBracketDepth() {
        TokenList list = new TokenList();
        list.OpenBracket();
        list.AddDigit('1');
        list.OpenBracket();
        list.AddDigit('1');
        list.OpenBracket();
        list.AddDigit('1');
        list.OpenBracket();
        assertEquals(4, list.GetBracketDepth());
        list.AddDigit('1');
        list.CloseBracket();
        list.AddDigit('1');
        list.CloseBracket();
        list.AddDigit('1');
        list.CloseBracket();
        list.AddDigit('1');
        assertEquals(1, list.GetBracketDepth());
    }

    public  void testBracketImplicitMul(){
        TokenList list = new TokenList();
        list.OpenBracket();
        list.AddDigit('1');
        list.OpenBracket();
        list.AddDigit('1');
        list.CloseBracket();
        list.AddDigit('1');
        list.CloseBracket();
        list.AddDigit('1');
        list.OpenBracket();
        list.AddDigit('1');
        list.CloseBracket();
        list.AddDigit('1');
        assertEquals("(1*(1)*1)*1*(1)*1", list.toString());
    }

    public void testAddPeriod() {
        TokenList list = new TokenList();
        list.SubOperation();
        list.AddDigit('1');
        list.AddPeriod();
        list.AddDigit('5');
        list.AddPeriod(); // Nothing should happen
        assertEquals("-1.5", list.toString());
        list.SubOperation();
        list.AddPeriod();
        list.AddDigit('2');
        assertEquals("-1.5-0.2", list.toString());
    }

    public void testFunction(){
        TokenList list = new TokenList();
        list.AddDigit('1');
        list.SubOperation();
        list.FunctionCall("sin");
        list.AddDigit('6');
        list.AddPeriod();
        list.AddDigit('9');
        list.CloseAllBrackets();
        assertEquals("1-sin(6.9)", list.toString());
    }

}