package com.tom.patientservice.UI.Login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {

    private static final String TAG="@#$##@$#@$";

    MutableLiveData<String> account;
    MutableLiveData<String> password;
    MutableLiveData<Boolean> signUpResult;
    MutableLiveData<Boolean> loginResult;

    public String getAccount() {
        return account.getValue();
    }

    public void setAccount(String account) {
        this.account.setValue(account);
    }

    public String getPassword() {
        return password.getValue();
    }

    public void setPassword(String password) {
        this.password.setValue(password);
    }

    public Boolean getSignUpResult() {
        return signUpResult.getValue();
    }

    public void setSignUpResult(Boolean signUpResult) {
        this.signUpResult.setValue(signUpResult);
    }

    public Boolean getLoginResult() {
        return loginResult.getValue();
    }

    public void setLoginResult(Boolean loginResult) {
        this.loginResult.setValue(loginResult);
    }

    public LoginViewModel() {
        if(account==null){
            account=new MutableLiveData<>();
            password=new MutableLiveData<>();
            signUpResult=new MutableLiveData<>();
            loginResult=new MutableLiveData<>();
            account.setValue(TAG);
            password.setValue(TAG);
            signUpResult.setValue(false);
            loginResult.setValue(false);
        }
    }

}
