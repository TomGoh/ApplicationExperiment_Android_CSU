package com.tom.patientservice.UI.Reserve;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tom.patientservice.pojo.Department;
import com.tom.patientservice.pojo.Doctor;
import com.tom.patientservice.pojo.DoctorReserve;

import java.util.List;

public class ReserveViewModel extends ViewModel {
    MutableLiveData<List<Department>> departmentList;
    MutableLiveData<Boolean> hasChosen;
    MutableLiveData<Department> chooseDepartment;
    MutableLiveData<Doctor> chooseDoctor;
    MutableLiveData<List<Doctor>> doctorList;
    MutableLiveData<List<DoctorReserve>> doctorReserveList;
    MutableLiveData<DoctorReserve> finalReserve;
    MutableLiveData<ReserveFragment> reserveFragment;

    public ReserveViewModel() {
        if(departmentList==null){
            departmentList=new MutableLiveData<>();
            hasChosen =new MutableLiveData<>();
            chooseDepartment=new MutableLiveData<>();
            chooseDoctor=new MutableLiveData<>();
            doctorList=new MutableLiveData<>();
            finalReserve=new MutableLiveData<>();
            doctorReserveList=new MutableLiveData<>();
            reserveFragment=new MutableLiveData<>();
            departmentList.setValue(null);
            hasChosen.setValue(false);
            chooseDoctor.setValue(null);
            chooseDepartment.setValue(null);
            doctorList.setValue(null);
            doctorReserveList.setValue(null);
            finalReserve.setValue(null);
            reserveFragment.setValue(null);
        }
    }

    public List<Department> getDepartmentList() {
        return departmentList.getValue();
    }

    public void setDepartmentList(List<Department> departmentList) {
        this.departmentList.setValue(departmentList);
    }

    public Boolean getHasChosen() {
        return hasChosen.getValue();
    }

    public void setHasChosen(Boolean hasChosen) {
        this.hasChosen.setValue(hasChosen);
    }

    public Department getChooseDepartment() {
        return chooseDepartment.getValue();
    }

    public void setChooseDepartment(Department chooseDepartment) {
        this.chooseDepartment.setValue(chooseDepartment);
    }

    public Doctor getChooseDoctor() {
        return chooseDoctor.getValue();
    }

    public void setChooseDoctor(Doctor chooseDoctor) {
        this.chooseDoctor.setValue(chooseDoctor);
    }

    public void setDoctorList(List<Doctor> doctorList) {
        this.doctorList.setValue(doctorList);
    }

    public List<Doctor> getDoctorList() {
        return doctorList.getValue();
    }

    public List<DoctorReserve> getDoctorReserveList() {
        return doctorReserveList.getValue();
    }

    public void setDoctorReserveList(List<DoctorReserve> doctorReserveList) {
        this.doctorReserveList.setValue(doctorReserveList);
    }

    public DoctorReserve getFinalReserve() {
        return finalReserve.getValue();
    }

    public void setFinalReserve(DoctorReserve finalReserve) {
        this.finalReserve.setValue(finalReserve);
    }

    public ReserveFragment getReserveFragment() {
        return reserveFragment.getValue();
    }

    public void setReserveFragment(ReserveFragment reserveFragment) {
        this.reserveFragment.setValue(reserveFragment);
    }
}
