package com.tom.patientservice.utils;

import android.content.Context;

import com.tom.patientservice.pojo.Patient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileManagement {

    public static void saveCurrentPatient(Context context, Patient patient){
        FileOutputStream fos = null;
        ObjectOutputStream objectOutput=null;
        try {
            fos=context.openFileOutput("CurrentPatient",Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            objectOutput=new ObjectOutputStream(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            objectOutput.writeObject(patient);
            objectOutput.flush();
            objectOutput.close();
            fos.close();
//            Log.i(TAG,"CityList saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Patient loadCurrentPatient(Context context){
        FileInputStream fis= null;
        Patient patient;
        try {
            fis = context.openFileInput("CurrentPatient");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        ObjectInputStream objectInputStream= null;
        try {
            objectInputStream = new ObjectInputStream(fis);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        try {
            patient=(Patient)objectInputStream.readObject();
            return patient;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return null;
        }finally {
            try {
                objectInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
