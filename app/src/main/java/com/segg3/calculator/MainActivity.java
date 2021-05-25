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
import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    public CalculatorViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this).get(CalculatorViewModel.class);
        setContentView(R.layout.activity_main);

        UpdateTextView();
    }

    public void onButtonClick(View v) {
        // Should add a respective button to the calculator
        Calculator calculator = Objects.requireNonNull(model.getCalculator().getValue());
        int id = v.getId();
        if (id == R.id.minusBtn) {
            calculator.addOperation("-");
        } else if (id == R.id.plusBtn) {
            calculator.addOperation("+");
        } else if (id == R.id.timesBtn) {
            calculator.addOperation("*");
        } else if (id == R.id.divBtn) {
            calculator.addOperation("/");
        } else if (id == R.id.openBtn) {
            calculator.openBracket();
        } else if (id == R.id.closeBtn) {
            calculator.closeBracket();
        } else if (id == R.id.powBtn) {
            calculator.addOperation("^");
        } else if (id == R.id.percentBtn) {
            calculator.addOperation("%");
        } else if (id == R.id.factBtn) {
            calculator.addOperation("!");
        } else if (id == R.id.zeroBtn || id == R.id.oneBtn || id == R.id.twoBtn || id == R.id.threeBtn || id == R.id.fourBtn || id == R.id.fiveBtn || id == R.id.sixBtn || id == R.id.sevenBtn || id == R.id.eightBtn || id == R.id.nineBtn || id == R.id.periodBtn) {
            String btnText = (String) ((Button) findViewById(v.getId())).getText();
            calculator.addDigit(btnText.charAt(0));
        } else if (id == R.id.sinBtn || id == R.id.cosBtn || id == R.id.tanBtn || id == R.id.lnBtn || id == R.id.logBtn || id == R.id.sqrtBtn) {
            String btnText;
            btnText = (String) ((Button) findViewById(v.getId())).getText();
            calculator.addFunctionCall(btnText);
        } else if (id == R.id.delBtn) {
            calculator.removeLast();
        } else if (id == R.id.clearBtn) {
            calculator.clear();
        }

        UpdateTextView();
    }

    public void UpdateTextView(){
        Calculator calculator = Objects.requireNonNull(model.getCalculator().getValue());
        // Updates the text views
        ((TextView)findViewById(R.id.calc_input)).setText(calculator.displayString());
        try {
            ((TextView)findViewById(R.id.calc_preview)).setText((Float.toString(calculator.calculate())));
        } catch (Exception e) {
            ((TextView)findViewById(R.id.calc_preview)).setText(e.getMessage());
        }
    }
}