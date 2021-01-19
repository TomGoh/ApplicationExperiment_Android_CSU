package com.tom.patientservice.pojo;

import java.io.Serializable;

public class Doctor implements Serializable {

    private String DoctorID;

    private String DoctorName;

    private String DoctorDescription;

    private String DoctorDegree;

    private String DepartmentID;

    public Doctor(String doctorID, String doctorName, String doctorDescription, String doctorDegree, String departmentID) {
        DoctorID = doctorID;
        DoctorName = doctorName;
        DoctorDescription = doctorDescription;
        DoctorDegree = doctorDegree;
        DepartmentID = departmentID;
    }

    public String getDoctorID() {
        return DoctorID;
    }

    public void setDoctorID(String doctorID) {
        DoctorID = doctorID;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String doctorName) {
        DoctorName = doctorName;
    }

    public String getDoctorDescription() {
        return DoctorDescription;
    }

    public void setDoctorDescription(String doctorDescription) {
        DoctorDescription = doctorDescription;
    }

    public String getDoctorDegree() {
        return DoctorDegree;
    }

    public void setDoctorDegree(String doctorDegree) {
        DoctorDegree = doctorDegree;
    }

    public String getDepartmentID() {
        return DepartmentID;
    }

    public void setDepartmentID(String departmentID) {
        DepartmentID = departmentID;
    }

    public Doctor() {
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "DoctorID='" + DoctorID + '\'' +
                ", DoctorName='" + DoctorName + '\'' +
                ", DoctorDescription='" + DoctorDescription + '\'' +
                ", DoctorDegree='" + DoctorDegree + '\'' +
                ", DepartmentID='" + DepartmentID + '\'' +
                '}';
    }
}
