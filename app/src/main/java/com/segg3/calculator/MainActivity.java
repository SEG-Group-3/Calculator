package com.segg3.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.segg3.calculator.tokenizer.TokenList;
import com.segg3.calculator.tokenizer.TokenType;

import java.net.CacheRequest;
import java.util.EmptyStackException;


public class MainActivity extends AppCompatActivity {
    public CalculatorViewModel model;

    Button btVibrate;
    Vibrator vibrator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this).get(CalculatorViewModel.class);
        setContentView(R.layout.activity_main);

        Calculator calculator = model.getCalculator().getValue();
        ((TextView)findViewById(R.id.calc_input)).setText(calculator.displayString());


        //((TextView)findViewById(R.id.calc_preview)).setText(calculator.calculate());
    }

    public void onButtonClick(View v) {
        // Should add a respective button to the calculator

        // Assigns all buttons to their respective function
        // TODO Change this to an if-statement chain (A-Studio already has a method to do this for us)
        // Select "switch" -> Press Alt-Enter -> Replace 'switch' with 'if'
        Calculator calculator = model.getCalculator().getValue();
        //TokenList operations = calculator.operationTokenizer;
        switch (v.getId()) {
            case R.id.minusBtn:
                calculator.addOperation(OperationType.MINUS);
                break;
            case R.id.plusBtn:
                calculator.addOperation(OperationType.PLUS);
                break;
            case R.id.timesBtn:
                calculator.addOperation(OperationType.MULTIPLY);
                break;
            case R.id.divBtn:
                calculator.addOperation(OperationType.DIVIDE);
                break;
            case R.id.openBtn:
                calculator.addOperation(OperationType.OPENBRACKET);
                break;
            case R.id.closeBtn:
                calculator.addOperation(OperationType.CLOSEBRACKET);
                break;
            case R.id.powBtn:
                calculator.addOperation(OperationType.POWER);
                break;
            case R.id.percentBtn:
                //calculator.add(TokenType.Operation, "%");
                break;
            case R.id.factBtn:
                //calculator.add(TokenType.Operation, "!");
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
                calculator.addDigit(btnText.charAt(0));
                break;
            case R.id.periodBtn:
                calculator.addDigit('.');
                break;
            //case R.id.sinBtn:
            //case R.id.cosBtn:
            //case R.id.tanBtn:
            case R.id.lnBtn:
            case R.id.logBtn:
                btnText = (String) ((Button)findViewById(v.getId())).getText();
                //calculator.functionCall(btnText);
                break;
            case R.id.sqrtBtn:
                //calculator.functionCall("sqrt");
            case R.id.delBtn:
                calculator.removeLast();
                break;
            case R.id.clearBtn:
                calculator.clear();
                break;
            default:
                break;
        }
        // Updates the text views
        ((TextView)findViewById(R.id.calc_input)).setText(calculator.displayString());
        try {
            ((TextView)findViewById(R.id.calc_preview)).setText((Float.toString(calculator.calculate())));
        } catch (Exception e) {
            ((TextView)findViewById(R.id.calc_preview)).setText(e.getMessage());
        }

    }
}