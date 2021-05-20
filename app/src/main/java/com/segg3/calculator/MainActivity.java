package com.segg3.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.segg3.calculator.tokenizer.TokenList;
import com.segg3.calculator.tokenizer.TokenType;


public class MainActivity extends AppCompatActivity {
    public CalculatorViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this).get(CalculatorViewModel.class);
        setContentView(R.layout.activity_main);

        TokenList calculatorExpr = model.getCalculatorToken().getValue();
        ((TextView)findViewById(R.id.calc_input)).setText(calculatorExpr.toEquationString());
        ((TextView)findViewById(R.id.calc_preview)).setText(calculatorExpr.toString());
    }


    public void onButtonClick(View v) {
        // Should add a respective button to the calculator

        // Assigns all buttons to their respective function
        // TODO Change this to an if-statement chain (A-Studio already has a method to do this for us)
        // Select "switch" -> Press Alt-Enter -> Replace 'switch' with 'if'
        TokenList calculatorExpr = model.getCalculatorToken().getValue();
        switch (v.getId()) {
            case R.id.minusBtn:
                calculatorExpr.subOperation();
                break;
            case R.id.plusBtn:
                calculatorExpr.addOperation();
                break;
            case R.id.timesBtn:
                calculatorExpr.mulOperation();
                break;
            case R.id.divBtn:
                calculatorExpr.divOperation();
                break;
            case R.id.openBtn:
                calculatorExpr.openBracket();
                break;
            case R.id.closeBtn:
                calculatorExpr.closeBracket();
                break;
            case R.id.powBtn:
                calculatorExpr.powOperation();
                break;
            case R.id.percentBtn:
                calculatorExpr.add(TokenType.Operation, "%");
                break;
            case R.id.factBtn:
                calculatorExpr.add(TokenType.Operation, "!");
                break;
            case R.id.zeroBtn:
            case R.id.oneBtn:
            case R.id.twoBtn:
            case R.id.threeBtn:
            case R.id.fourBtn:
            case R.id.fiveBtn:
            case R.id.sixBtn:
            case R.id.sevenBtn:
            case R.id.eightBtn:
            case R.id.nineBtn:
                String btnText = (String) ((Button)findViewById(v.getId())).getText();
                calculatorExpr.addDigit(btnText);
                break;
            case R.id.periodBtn:
                calculatorExpr.addPeriod();
                break;
            case R.id.sinBtn:
            case R.id.cosBtn:
            case R.id.tanBtn:
            case R.id.lnBtn:
            case R.id.logBtn:
                btnText = (String) ((Button)findViewById(v.getId())).getText();
                calculatorExpr.functionCall(btnText);
                break;
            case R.id.sqrtBtn:
                calculatorExpr.functionCall("sqrt");
            case R.id.delBtn:
                calculatorExpr.removeLast();
                break;
            case R.id.clearBtn:
                calculatorExpr.clear();
                break;
            default:
                break;
        }
        // Updates the text views
        ((TextView)findViewById(R.id.calc_input)).setText(calculatorExpr.toEquationString());
        ((TextView)findViewById(R.id.calc_preview)).setText(calculatorExpr.toString());
    }
}