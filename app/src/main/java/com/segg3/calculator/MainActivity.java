package com.segg3.calculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.Objects;


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
        Calculator calculator =  Objects.requireNonNull(model.getCalculator().getValue());

        int id = v.getId();
        if (id == R.id.minusBtn) {
            calculator.addOp("-");
        } else if (id == R.id.plusBtn) {
            calculator.addOp("+");
        } else if (id == R.id.timesBtn) {
            calculator.addOp("*");
        } else if (id == R.id.divBtn) {
            calculator.addOp("/");
        } else if (id == R.id.openBtn) {
            calculator.openBracket();
        } else if (id == R.id.closeBtn) {
            calculator.closeBracket();
        } else if (id == R.id.powBtn) {
            calculator.addOp("^");
        } else if (id == R.id.percentBtn) {
            calculator.addOp("%");
        } else if (id == R.id.factBtn) {
            calculator.addOp("!");
        } else if (id == R.id.zeroBtn ||
                id == R.id.oneBtn ||
                id == R.id.twoBtn ||
                id == R.id.threeBtn ||
                id == R.id.fourBtn ||
                id == R.id.fiveBtn ||
                id == R.id.sixBtn ||
                id == R.id.sevenBtn ||
                id == R.id.eightBtn ||
                id == R.id.nineBtn) {
            String btnText = (String) ((Button) findViewById(v.getId())).getText();
            calculator.addDigit(btnText);
        } else if (id == R.id.periodBtn) {
            calculator.addDigit(".");
        } else if (id == R.id.sinBtn || id == R.id.cosBtn || id == R.id.tanBtn || id == R.id.lnBtn || id == R.id.logBtn || id == R.id.absBtn) {
            String btnText;
            btnText = (String) ((Button) findViewById(v.getId())).getText();
            calculator.addFunction(btnText);
        } else if (id == R.id.sqrtBtn) {
            calculator.addFunction("sqrt");
        } else if (id == R.id.delBtn) {
            calculator.removeLast();
        } else if (id == R.id.clearBtn) {
            calculator.clear();
        } else if (id == R.id.equalsBtn) {
            // If last calculation was successful bring it to the main Token
            try {
                float result = calculator.calculate();
                calculator.operationTokenizer.clear();
                calculator.operationTokenizer.addDigit(Float.toString(result));
            } catch (Exception ignored) { }
        }
        // Updates the text views
        updateTextValues();
    }

    @SuppressLint("SetTextI18n")
    public void updateTextValues() {
        Calculator calculator =  Objects.requireNonNull(model.getCalculator().getValue());
        ((TextView) findViewById(R.id.calc_input)).setText(calculator.operationTokenizer.toEquationString());
        try {
            String result = Float.toString(calculator.calculate());
            ((TextView) findViewById(R.id.calc_preview)).setText(result);
        } catch (Exception e) {
            ((TextView) findViewById(R.id.calc_preview)).setText("Bad expression!");
        }
    }
}