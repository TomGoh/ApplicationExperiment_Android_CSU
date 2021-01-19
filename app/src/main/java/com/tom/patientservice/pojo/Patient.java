package com.tom.patientservice.pojo;

import java.io.Serializable;

public class Patient implements Serializable {

    private String patientName;

    private String patientPassword;

    private String patientAccount;

    public Patient(String patientName, String password, String accountID) {
        this.patientName = patientName;
        this.patientPassword = password;
        this.patientAccount = accountID;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientPassword() {
        return patientPassword;
    }

    public void setPatientPassword(String patientPassword) {
        this.patientPassword = patientPassword;
    }

    public String getPatientAccount() {
        return patientAccount;
    }

    public void setPatientAccount(String patientAccount) {
        this.patientAccount = patientAccount;
    }

    public Patient() {
    }

    public Patient(String patientAccount,String patientPassword){
        this.patientAccount=patientAccount;
        this.patientPassword=patientPassword;
        this.patientName="LOGIN PATIENT";
    }
}
