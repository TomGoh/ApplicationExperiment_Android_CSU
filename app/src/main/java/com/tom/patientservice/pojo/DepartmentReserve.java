package com.tom.patientservice.pojo;


import java.io.Serializable;
import java.util.Date;

public class DepartmentReserve implements Serializable {

    String DepartmentID;

    int DepartmentSurplus;

    String ReserveDate;

    public DepartmentReserve() {
    }


    public String getDepartmentID() {
        return DepartmentID;
    }

    public void setDepartmentID(String departmentID) {
        DepartmentID = departmentID;
    }

    public int getDepartmentSurplus() {
        return DepartmentSurplus;
    }

    public void setDepartmentSurplus(int departmentReserve) {
        DepartmentSurplus = departmentReserve;
    }

    public String getReserveDate() {
        return ReserveDate;
    }

    public void setReserveDate(String reserveDate) {
        ReserveDate = reserveDate;
    }
}
