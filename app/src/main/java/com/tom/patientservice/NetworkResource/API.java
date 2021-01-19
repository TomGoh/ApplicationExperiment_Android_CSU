package com.tom.patientservice.NetworkResource;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.tom.patientservice.pojo.Department;
import com.tom.patientservice.pojo.Doctor;
import com.tom.patientservice.pojo.DoctorReserve;
import com.tom.patientservice.pojo.FullReservation;
import com.tom.patientservice.pojo.Patient;
import com.tom.patientservice.pojo.Reservation;
import com.tom.patientservice.utils.Message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class API {

    private final static String PATIENT_ACCOUNT="PatientAccount";
    private final static String PATIENT_PASSWORD="PatientPassword";
    private final static String EMPTY_FEEDBACK="EmptyInput";
    private final static String WRONG_LOGIN="WrongPasswordORWrongAccount";
    private final static String SUCCESS_LOGIN="LoginSuccess";
    private final static String PATIENT_NAME="PatientName";
    private final static String DUPLICATED ="DuplicatedAccount";
    private final static String SUCCESS_SIGNUP="SignUpSuccess";
    private final static String REGISTER_FAILED ="registerFailed";
    private final static String REQUEST_TAG="request";
    private final static String REQUEST_FOR_ALL_DEP="allDepartment";
    private final static String REQUEST_FOR_DOCTOR_BY_DEP="requestForDoctorByDep";
    private final static String REQUEST_DEPARTMENT_ID="departmentID";
    private final static String REQUEST_GET_SURPLUS="getSurplusByDoctor";
    private final static String REQUEST_DOCTOR="doctorID";
    private final static String REQUEST_SAVE_RESERVATION="savePatientReservation";
    private final static String REQUEST_PATIENT_ID="patientID";
    private final static String REQUEST_DATE="date";
    private final static String GET_FULL_RESERVATION="getFullReservation";
    private static final String REQUEST_ADD="addBalance";
    private final static String BALANCE_AMOUNT="balanceAmount";
    private final static String PAY_BILL="payBill";
    private final static String RESERVE_ID="reserveID";
    private final static String REQUEST_FOR_CANCELLING="cancelReservation";
    private final static String REQUEST_CHECK="checkBalance";
    private final static String OLD_PASSWORD="oldPassword";
    private final static String NEW_PASSWORD="newPassword";
    private final static String ACCOUNT="patientAccount";


    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url=new URL(urlSpec);
        HttpURLConnection connection =(HttpURLConnection) url.openConnection();
        try{
            ByteArrayOutputStream out=new ByteArrayOutputStream();
            InputStream in=connection.getInputStream();
            if(connection.getResponseCode()!=HttpURLConnection.HTTP_OK){
                throw new IOException(connection.getResponseMessage()+": with"+urlSpec);
            }
            int bytesRead=0;
            byte[] buffer=new byte[1024000];

            while((bytesRead=in.read(buffer))>0){
                out.write(buffer,0,bytesRead);
            }
            out.close();
            return out.toByteArray();
        }finally {
            connection.disconnect();
        }
    }

    public byte[] getUrlBytesByPost(String urlSpec) throws IOException {
        URL url=new URL(urlSpec);
        HttpURLConnection connection =(HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        try{
            ByteArrayOutputStream out=new ByteArrayOutputStream();
            InputStream in=connection.getInputStream();
            if(connection.getResponseCode()!=HttpURLConnection.HTTP_OK){
                throw new IOException(connection.getResponseMessage()+": with"+urlSpec);
            }

            int bytesRead=0;
            byte[] buffer=new byte[1024000];

            while((bytesRead=in.read(buffer))>0){
                out.write(buffer,0,bytesRead);
            }
            out.close();
            return out.toByteArray();
        }finally {
            connection.disconnect();
        }
    }


    /**
     * 将getUrlBytes中的字节数组转化为字符串以便后续使用fastjson处理
     * @param urlSpec 需要获得数据的url
     * @return API获得的字符串数组，此处以JSON格式
     * @throws IOException 抛出异常
     */
    public String getUrlString(String urlSpec) throws IOException{
        return new String (getUrlBytes(urlSpec));
    }

    public String getUrlStringByPost(String urlSpec) throws IOException{
        return new String (getUrlBytesByPost(urlSpec));
    }

    /**
     * 登录的工具函数，返回登陆的结果
     * @param patientAccount 用户账户
     * @param password 用户密码
     * @return 登陆成功与否
     * @throws IOException 抛出异常
     */
    public Patient login(String patientAccount,String password) throws IOException {
        String urlSpace="http://172.20.10.4:8080/login?"+PATIENT_ACCOUNT+"="+patientAccount+"&"+PATIENT_PASSWORD+"="+password;
        String result=getUrlStringByPost(urlSpace);
        Log.i("loginTest",result);
        Patient patient= JSON.parseObject(result,Patient.class);
        if(patient!=null){
            Log.i("loginTest",patient.toString());
        }
        return patient;
    }

    public boolean signUp(String patientAccount,String password,String patientName) throws IOException {
        String urlSpace="http://172.20.10.4:8080/signup?"+PATIENT_ACCOUNT+"="+patientAccount+"&"+PATIENT_PASSWORD+"="+password+"&"+PATIENT_NAME+"="+patientName;
        String result=getUrlStringByPost(urlSpace);
        Log.i("signUpTest",result);
        Message message=JSON.parseObject(result,Message.class);
        return message.getMessage().equals("SignUpSuccess");
    }

    public List<Department> getAllDepartmentInfor() throws IOException {
        String urlSpace="http://172.20.10.4:8080/request?"+REQUEST_TAG+"="+REQUEST_FOR_ALL_DEP;
        String result=getUrlString(urlSpace);
        Log.i("queryForAllDep",result);
        List<Department> departmentList=JSON.parseArray(result,Department.class);
        return departmentList;
    }

    public List<Doctor> getAllDoctorInfor(String departmentID) throws IOException {
        String urlSpace="http://172.20.10.4:8080/request?"+REQUEST_TAG+"="+REQUEST_FOR_DOCTOR_BY_DEP+"&"+REQUEST_DEPARTMENT_ID+"="+departmentID;
        String result=getUrlString(urlSpace);
        Log.i("queryForDoctorIn"+departmentID,result);
        List<Doctor> doctors=JSON.parseArray(result,Doctor.class);
        return  doctors;
    }

    public List<DoctorReserve> getDoctorSurplusById(String doctorId) throws IOException {
        //http://localhost:8080/reservation?request=getSurplusByDoctor&doctorID=1
        String urlSpace="http://172.20.10.4:8080/reservation?"+REQUEST_TAG+"="+REQUEST_GET_SURPLUS+"&"+REQUEST_DOCTOR+"="+doctorId;
        String result=getUrlString(urlSpace);
        Log.i("Request for doctor surplus:"+doctorId,result);
        List<DoctorReserve> doctorReserveList=JSON.parseArray(result,DoctorReserve.class);
        return doctorReserveList;
    }

    public Message pullReservation(Reservation reservation) throws IOException {
        String urlSpace="http://172.20.10.4:8080/reservation?"+REQUEST_TAG+"="+REQUEST_SAVE_RESERVATION+"&"+REQUEST_DOCTOR
                +"="+reservation.getDoctorID()+"&"+REQUEST_PATIENT_ID+"="+reservation.getPatientAccount()
                +"&"+REQUEST_DATE+"="+reservation.getReserveDate();
        String result=getUrlString(urlSpace);
        Log.i("Pull Reservation Result",result);
        Message message=JSON.parseObject(result,Message.class);
        return message;
    }

    public List<FullReservation> getFullReservation(String patientID) throws IOException {
        String urlSpace="http://172.20.10.4:8080/reservation?"+REQUEST_TAG+"="+GET_FULL_RESERVATION+"&"+REQUEST_PATIENT_ID+"="+patientID;
        String result=getUrlString(urlSpace);
        Log.i("Pull Reservation Result",result);
        List<FullReservation> fullReservationList=JSON.parseArray(result,FullReservation.class);
        return fullReservationList;
    }

    public boolean sendBalanceResult(String patientID,String amount) throws IOException {
        String urlSpace="http://172.20.10.4:8080/balance?"+REQUEST_TAG+"="+REQUEST_ADD+"&"+REQUEST_PATIENT_ID+"="+patientID+"&"+BALANCE_AMOUNT+"="+amount;
        String result=getUrlString(urlSpace);
        Log.i("Add Balance",result);
        Message message=JSON.parseObject(result,Message.class);
        return message.getMessage().equals("SUCCESS");
    }

    public boolean payForReserve(String patientID,String reservationID) throws IOException {
        String urlSpace="http://172.20.10.4:8080/balance?"+REQUEST_TAG+"="+PAY_BILL+"&"+REQUEST_PATIENT_ID+"="+patientID+"&"+RESERVE_ID+"="+reservationID;
        String result=getUrlString(urlSpace);
        Log.i("Add Balance",result);
        Message message=JSON.parseObject(result,Message.class);
        if(message!=null){
            return message.getMessage().equals("SUCCESS");
        }else{
            return false;
        }

    }

    public boolean cancelReservation(String patientID,String  reservationID) throws IOException {
        String urlSpace="http://172.20.10.4:8080/reservation?"+REQUEST_TAG+"="+REQUEST_FOR_CANCELLING+"&"+REQUEST_PATIENT_ID+"="+patientID+"&"+RESERVE_ID+"="+reservationID;
        String result=getUrlString(urlSpace);
        Log.i("Add Balance",result);
        Message message=JSON.parseObject(result,Message.class);
        return message.getMessage().equals("SUCCESS");
    }

    public String getBalanceAmount(String patientID) throws IOException {
        String urlSpace="http://172.20.10.4:8080/balance?"+REQUEST_TAG+"="+REQUEST_CHECK+"&"+REQUEST_PATIENT_ID+"="+patientID;
        String result=getUrlString(urlSpace);
        Log.i("Add Balance",result);
        Message message=JSON.parseObject(result,Message.class);
        return message.getMessage();
    }

    public boolean changePassword(String account,String oldPassword,String newPassword) throws IOException {
        String urlSpace="http://172.20.10.4:8080/change?"+OLD_PASSWORD+"="+oldPassword+"&"+NEW_PASSWORD+"="+newPassword+"&"+ACCOUNT+"="+account;
        String result=getUrlStringByPost(urlSpace);
        Log.i("Change Password",result);
        Message message=JSON.parseObject(result,Message.class);
        return message.getMessage().equals("SUCCESS");
    }

}
