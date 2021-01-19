package com.tom.patientservice.pojo;

import java.io.Serializable;

public class Balance implements Serializable {

    String PatientAccount;

    float balance;

    public String getPatientAccount() {
        return PatientAccount;
    }

    public void setPatientAccount(String patientAccount) {
        PatientAccount = patientAccount;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public Balance(String patientAccount, float balance) {
        PatientAccount = patientAccount;
        this.balance = balance;
    }

    public Balance() {
    }
}
