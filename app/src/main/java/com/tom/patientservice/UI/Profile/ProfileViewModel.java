package com.tom.patientservice.UI.Profile;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tom.patientservice.pojo.FullReservation;
import com.tom.patientservice.pojo.Patient;
import com.tom.patientservice.pojo.Reservation;

import java.util.List;

public class ProfileViewModel extends ViewModel {

    MutableLiveData<Patient> currentPatient;
    public MutableLiveData<Boolean> islogined;
    MutableLiveData<Boolean> isReservationOn;
    MutableLiveData<List<FullReservation>> fullReservationList;
    MutableLiveData<Boolean> changed;

    public ProfileViewModel() {
        if (currentPatient == null) {
            changed=new MutableLiveData<>();
            isReservationOn=new MutableLiveData<>();
            currentPatient = new MutableLiveData<>();
            islogined=new MutableLiveData<>();
            fullReservationList=new MutableLiveData<>();
            islogined.setValue(false);
            this.currentPatient.setValue(null);
            isReservationOn.setValue(false);
            this.changed.setValue(false);
            fullReservationList.setValue(null);
        }
    }

    public Patient getCurrentPatient() {
        return currentPatient.getValue();
    }

    public void setCurrentPatient(Patient currentPatient) {
        this.currentPatient.setValue(currentPatient);
    }

    public Boolean getIslogined() {
        return islogined.getValue();
    }

    public void setIslogined(Boolean islogined) {
        this.islogined.setValue(islogined);
    }

    public Boolean getIsReservationOn() {
        return isReservationOn.getValue();
    }

    public void setIsReservationOn(Boolean isReservationOn) {
        this.isReservationOn.setValue(isReservationOn);
    }

    public List<FullReservation> getFullReservationList() {
        return fullReservationList.getValue();
    }

    public void setFullReservationList(List<FullReservation> fullReservationList) {
        this.fullReservationList.setValue(fullReservationList);
    }

    public Boolean getChanged() {
        return changed.getValue();
    }

    public void setChanged(Boolean changed) {
        this.changed.setValue(changed);
    }
}
