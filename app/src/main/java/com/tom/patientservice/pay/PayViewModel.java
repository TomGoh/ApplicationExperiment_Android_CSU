package com.tom.patientservice.pay;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PayViewModel extends ViewModel {

    MutableLiveData<Boolean> result;

    public PayViewModel() {
        if(result==null){
            result=new MutableLiveData<>();
            result.setValue(false);
        }
    }

    public MutableLiveData<Boolean> getResult() {
        return result;
    }

    public void setResult(MutableLiveData<Boolean> result) {
        this.result = result;
    }
}
