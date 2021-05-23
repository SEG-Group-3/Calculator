package com.segg3.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.segg3.calculator.tokenizer.TokenList;
import com.segg3.calculator.tokenizer.TokenType;


public class MainActivity extends AppCompatActivity {
    public CalculatorViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this).get(CalculatorViewModel.class);
        setContentView(R.layout.activity_main);

        updateTextValues();
    }


    public void onButtonClick(View v) {
        // Should add a respective button to the calculator

        // Assigns all buttons to their respective function
        // TODO Change this to an if-statement chain (A-Studio already has a method to do this for us)
        // Select "switch" -> Press Alt-Enter -> Replace 'switch' with 'if'
        Calculator calculator = model.getCalculator().getValue();
        TokenList operations = calculator.operationTokenizer;
        int id = v.getId();
        if (id == R.id.minusBtn) {
            operations.subOperation();
        } else if (id == R.id.plusBtn) {
            operations.addOperation();
        } else if (id == R.id.timesBtn) {
            operations.mulOperation();
        } else if (id == R.id.divBtn) {
            operations.divOperation();
        } else if (id == R.id.openBtn) {
            operations.openBracket();
        } else if (id == R.id.closeBtn) {
            operations.closeBracket();
        } else if (id == R.id.powBtn) {
            operations.powOperation();
        } else if (id == R.id.percentBtn) {
            operations.add(TokenType.Operation, "%");
        } else if (id == R.id.factBtn) {
            operations.add(TokenType.Operation, "!");
        } else if (id == R.id.zeroBtn || id == R.id.oneBtn || id == R.id.twoBtn || id == R.id.threeBtn || id == R.id.fourBtn || id == R.id.fiveBtn || id == R.id.sixBtn || id == R.id.sevenBtn || id == R.id.eightBtn || id == R.id.nineBtn) {
            String btnText = (String) ((Button) findViewById(v.getId())).getText();
            operations.addDigit(btnText.charAt(0));
        } else if (id == R.id.periodBtn) {
            operations.addDigit('.');
        } else if (id == R.id.sinBtn || id == R.id.cosBtn || id == R.id.tanBtn || id == R.id.lnBtn || id == R.id.logBtn || id == R.id.absBtn) {
            String btnText;
            btnText = (String) ((Button) findViewById(v.getId())).getText();
            operations.functionCall(btnText);
        } else if (id == R.id.sqrtBtn) {
            operations.functionCall("sqrt");
        } else if (id == R.id.delBtn) {
            operations.removeLast();
        } else if (id == R.id.clearBtn) {
            operations.clear();
        } else if (id == R.id.equalsBtn) {
            try {
                // If last calculation was successful bring it to the main Token
                float result = calculator.calculate();
                calculator.operationTokenizer.clear();
                calculator.operationTokenizer.addDigit(Float.toString(result));
            } catch (Exception e) {
            }
        }
        // Updates the text views
        updateTextValues();
    }

    public void updateTextValues() {
        Calculator calculator = model.getCalculator().getValue();
        ((TextView) findViewById(R.id.calc_input)).setText(calculator.operationTokenizer.toEquationString());
        try {
            ((TextView) findViewById(R.id.calc_preview)).setText(Float.toString(calculator.calculate()));
        } catch (Exception e) {
            ((TextView) findViewById(R.id.calc_preview)).setText("Bad expression!");
        }
    }
}