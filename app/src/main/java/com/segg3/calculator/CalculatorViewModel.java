package com.segg3.calculator;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.segg3.calculator.tokenizer.Token;
import com.segg3.calculator.tokenizer.TokenList;

public class CalculatorViewModel extends ViewModel {

    // Create a LiveData with a String
    private MutableLiveData<Calculator> calculator;

    public MutableLiveData<Calculator> getCalculator() {
        if (calculator == null) {
            Calculator calc  = new Calculator();
            calculator = new MutableLiveData<>();
            calculator.setValue(calc);
        }
        return calculator;
    }
}
