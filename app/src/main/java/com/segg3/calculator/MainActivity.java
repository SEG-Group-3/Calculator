package com.segg3.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.segg3.calculator.tokenizer.TokenList;
import com.segg3.calculator.tokenizer.TokenType;

import java.net.CacheRequest;


public class MainActivity extends AppCompatActivity {
    public CalculatorViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this).get(CalculatorViewModel.class);
        setContentView(R.layout.activity_main);

        Calculator calculator = model.getCalculator().getValue();
        ((TextView)findViewById(R.id.calc_input)).setText(calculator.operationTokenizer.toEquationString());
        //((TextView)findViewById(R.id.calc_preview)).setText(calculator.calculate());
    }


    public void onButtonClick(View v) {
        // Should add a respective button to the calculator

        // Assigns all buttons to their respective function
        // TODO Change this to an if-statement chain (A-Studio already has a method to do this for us)
        // Select "switch" -> Press Alt-Enter -> Replace 'switch' with 'if'
        Calculator calculator = model.getCalculator().getValue();
        TokenList operations = calculator.operationTokenizer;
        switch (v.getId()) {
            case R.id.minusBtn:
                operations.subOperation();
                break;
            case R.id.plusBtn:
                operations.addOperation();
                break;
            case R.id.timesBtn:
                operations.mulOperation();
                break;
            case R.id.divBtn:
                operations.divOperation();
                break;
            case R.id.openBtn:
                operations.openBracket();
                break;
            case R.id.closeBtn:
                operations.closeBracket();
                break;
            case R.id.powBtn:
                operations.powOperation();
                break;
            case R.id.percentBtn:
                operations.add(TokenType.Operation, "%");
                break;
            case R.id.factBtn:
                operations.add(TokenType.Operation, "!");
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
                operations.addDigit(btnText);
                break;
            case R.id.periodBtn:
                operations.addPeriod();
                break;
            case R.id.sinBtn:
            case R.id.cosBtn:
            case R.id.tanBtn:
            case R.id.lnBtn:
            case R.id.logBtn:
                btnText = (String) ((Button)findViewById(v.getId())).getText();
                operations.functionCall(btnText);
                break;
            case R.id.sqrtBtn:
                operations.functionCall("sqrt");
            case R.id.delBtn:
                operations.removeLast();
                break;
            case R.id.clearBtn:
                operations.clear();
                break;
            default:
                break;
        }
        // Updates the text views
        ((TextView)findViewById(R.id.calc_input)).setText(operations.toEquationString());
        //((TextView)findViewById(R.id.calc_preview)).setText(calculator.calculate());
    }
}