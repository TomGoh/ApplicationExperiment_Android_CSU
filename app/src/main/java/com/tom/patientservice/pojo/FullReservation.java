package com.tom.patientservice.pojo;

public class FullReservation {
    String doctorName;
    String departmentName;
    String patientName;
    boolean payment;
    String date;
    String reserveID;

    public FullReservation() {
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public boolean isPayment() {
        return payment;
    }

    public FullReservation(String doctorName, String departmentName, String patientName, boolean payment, String date,String reserveID) {
        this.doctorName = doctorName;
        this.departmentName = departmentName;
        this.patientName = patientName;
        this.payment = payment;
        this.date = date;
        this.reserveID=reserveID;
    }

    public void setPayment(boolean payment) {
        this.payment = payment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReserveID() {
        return reserveID;
    }

    public void setReserveID(String reserveID) {
        this.reserveID = reserveID;
    }

    @Override
    public String toString() {
        return "FullReservation{" +
                "doctorName='" + doctorName + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", patientName='" + patientName + '\'' +
                ", payment=" + payment +
                ", date='" + date + '\'' +
                ", reserveID='" + reserveID + '\'' +
                '}';
    }
}
