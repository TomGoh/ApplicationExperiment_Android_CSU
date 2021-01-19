package com.tom.patientservice.UI.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.tom.patientservice.R;

public class MyInforFragment extends Fragment {

    TextView inforName,inforAccount;
    ProfileViewModel profileViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_my_infor,container,false);
        inforName=root.findViewById(R.id.infor_name_text);
        inforAccount=root.findViewById(R.id.infor_account_text);
        profileViewModel=new ViewModelProvider(getActivity()).get(ProfileViewModel.class);
        inforName.setText(profileViewModel.getCurrentPatient().getPatientName());
        inforAccount.setText(profileViewModel.getCurrentPatient().getPatientAccount());
        return root;
    }


}
