package com.tom.patientservice.pojo;

import java.io.Serializable;

public class Department implements Serializable {

    private String departmentName;

    private String departmentID;

    private String departmentDescription;

    public Department(String departName, String departmentID, String departmentDescription) {
        this.departmentName = departName;
        this.departmentID = departmentID;
        this.departmentDescription = departmentDescription;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }

    public String getDepartmentDescription() {
        return departmentDescription;
    }

    public void setDepartmentDescription(String departmentDescription) {
        this.departmentDescription = departmentDescription;
    }

    public Department() {
    }
}
