package com.tom.patientservice.UI.Home;

import androidx.core.graphics.PathUtils;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tom.patientservice.pojo.Department;
import com.tom.patientservice.pojo.Doctor;
import com.tom.patientservice.pojo.Patient;

import java.util.List;

public class HomeViewModel extends ViewModel {

    MutableLiveData<String> checkedItem;
    MutableLiveData<List<Department>> departmentList;
    MutableLiveData<List<Doctor>> doctorList;
    MutableLiveData<Patient> currentPatient;

    public HomeViewModel() {
        if(checkedItem==null){
            checkedItem=new MutableLiveData<>();
            checkedItem.setValue(null);
            doctorList=new MutableLiveData<>();
            doctorList.setValue(null);
            departmentList=new MutableLiveData<>();
            departmentList.setValue(null);
            currentPatient=new MutableLiveData<>();
            currentPatient.setValue(null);
        }
    }

    public String getCheckedItem() {
        return checkedItem.getValue();
    }

    public void setCheckedItem(String checkedItem) {
        this.checkedItem.setValue(checkedItem);
    }

    public List<Doctor> getDoctorList() {
        return doctorList.getValue();
    }

    public void setDoctorList(List<Doctor> doctorList) {
        this.doctorList.setValue(doctorList);
    }

    public List<Department> getDepartmentList() {
        return departmentList.getValue();
    }

    public void setDepartmentList(List<Department> departmentList) {
        this.departmentList.setValue(departmentList);
    }

    public Patient getCurrentPatient() {
        return currentPatient.getValue();
    }

    public void setCurrentPatient(Patient currentPatient) {
        this.currentPatient.setValue(currentPatient);
    }
}
