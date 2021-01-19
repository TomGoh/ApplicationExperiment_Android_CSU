package com.tom.patientservice.NetworkResource;

import com.tom.patientservice.pojo.DoctorReserve;
import com.tom.patientservice.pojo.FullReservation;
import com.tom.patientservice.pojo.Reservation;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class APITest {
    API api=new API();

    @Test
    public void getUrlBytes() {
    }

    @Test
    public void getUrlBytesByPost() {
    }

    @Test
    public void getUrlString() {
    }

    @Test
    public void getUrlStringByPost() {
    }

    @Test
    public void login() {
    }

    @Test
    public void signUp() {
    }

    @Test
    public void getAllDepartmentInfor() {
    }

    @Test
    public void getAllDoctorInfor() throws IOException {
        System.out.println(api.getAllDoctorInfor("5"));
    }

    @Test
    public void  getDoctorSurplusById() throws IOException {
        List<DoctorReserve> doctorReserveList=api.getDoctorSurplusById("1") ;
        for(DoctorReserve doctorReserve:doctorReserveList){
            System.out.println(doctorReserve.getDoctorID()+" "+doctorReserve.getReserveDate()
                    +" "+doctorReserve.getDoctorSurplus());
        }
    }

    @Test
    public void getFullReservation() throws IOException {
        List<FullReservation> fullReservationList=api.getFullReservation("8208180521");
        for(FullReservation reservation:fullReservationList){
            System.out.println(reservation.toString());
        }
    }

    @Test
    public void sendBalanceResult() throws IOException {
        System.out.println(api.sendBalanceResult("8208180507","100"));
    }

    @Test
    public void payForReserve() throws IOException {
        System.out.println(api.payForReserve("8208180507","5"));
    }

    @Test
    public void cancelReservation() throws IOException {
        System.out.println(api.cancelReservation("8208180521","1"));
    }

    @Test
    public  void  getBalanceAmount() throws IOException {
        System.out.println(api.getBalanceAmount("8208180521"));
    }

    @Test
    public void pullReservation() throws IOException {
        System.out.println(api.pullReservation(new Reservation("8208180521","2","2","1","2020-12-24",0)));
    }

    @Test
    public void changePassword() throws IOException {
        System.out.println(api.changePassword("8208180507","123456","123456789"));
        System.out.println(api.changePassword("8208180507","123456789","123456"));
    }
}