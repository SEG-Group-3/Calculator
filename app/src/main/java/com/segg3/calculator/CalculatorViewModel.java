package com.segg3.calculator;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.segg3.calculator.tokenizer.Token;
import com.segg3.calculator.tokenizer.TokenList;

public class CalculatorViewModel extends ViewModel {

    // Create a LiveData with a String
    private MutableLiveData<TokenList> tokenData;

    public MutableLiveData<TokenList> getCalculatorToken() {
        if (tokenData == null) {
            TokenList list  = new TokenList();
            tokenData = new MutableLiveData<TokenList>();
            tokenData.setValue(list);
        }
        return tokenData;
    }

// Rest of the ViewModel...
}
