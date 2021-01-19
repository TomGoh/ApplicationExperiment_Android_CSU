package com.tom.patientservice.pojo;



import java.io.Serializable;

public class Reservation implements Serializable {

    String patientAccount;

    String DoctorID;

    String ReserveID;

    String DepartmentID;

    String ReserveDate;

    Integer Payed;

    public Reservation() {
    }

    public Reservation(String patientAccount, String doctorID,  String departmentID,String date,Integer payed) {
        this.patientAccount = patientAccount;
        DoctorID = doctorID;
        DepartmentID = departmentID;
        ReserveDate= date;
        Payed=payed;
    }

    public Reservation(String patientAccount, String doctorID, String reserveID, String departmentID,String date,Integer payed) {
        this.patientAccount = patientAccount;
        DoctorID = doctorID;
        DepartmentID = departmentID;
        ReserveDate= date;
        this.ReserveID=reserveID;
        Payed=payed;
    }

    public String getPatientAccount() {
        return patientAccount;
    }

    public void setPatientAccount(String patientAccount) {
        this.patientAccount = patientAccount;
    }

    public String getDoctorID() {
        return DoctorID;
    }

    public void setDoctorID(String doctorID) {
        DoctorID = doctorID;
    }

    public String getReserveID() {
        return ReserveID;
    }

    public void setReserveID(String reserveID) {
        ReserveID = reserveID;
    }

    public String getDepartmentID() {
        return DepartmentID;
    }

    public void setDepartmentID(String departmentID) {
        DepartmentID = departmentID;
    }

    public String getReserveDate() {
        return ReserveDate;
    }

    public void setReserveDate(String reserveDate){
        this.ReserveDate=reserveDate;
    }

    public Integer getPayed() {
        return Payed;
    }

    public void setPayed(Integer payed) {
        Payed = payed;
    }
}
