package com.tom.patientservice.pojo;


import java.io.Serializable;

public class DoctorReserve  implements Serializable {

    String DoctorID;

    int DoctorSurplus;

    String ReserveDate;

    public DoctorReserve() {
    }

    public DoctorReserve(String doctorID, int doctorSurplus,String date) {
        DoctorID = doctorID;
        DoctorSurplus = doctorSurplus;
        ReserveDate= date;
    }

    public String getDoctorID() {
        return DoctorID;
    }

    public void setDoctorID(String doctorID) {
        DoctorID = doctorID;
    }

    public int getDoctorSurplus() {
        return DoctorSurplus;
    }

    public void setDoctorSurplus(int doctorSurplus) {
        DoctorSurplus = doctorSurplus;
    }

    public String getReserveDate() {
        return ReserveDate;
    }

    public void setReserveDate(String reserveDate) {
        ReserveDate = reserveDate;
    }
}
