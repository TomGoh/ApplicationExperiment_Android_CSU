package com.tom.patientservice.UI.Profile;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.tom.patientservice.R;
import com.tom.patientservice.UI.Home.HomeViewModel;
import com.tom.patientservice.UI.Login.LoginFragment;
import com.tom.patientservice.UI.Login.SignUpFragment;
import com.tom.patientservice.pojo.Patient;
import com.tom.patientservice.utils.FileManagement;

import java.io.File;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private LoginFragment loginFragment;
    private TextView hint;
    private RelativeLayout myReserveLayout,myInforLayout,myBalanceLayout,myChangeLayout;
    private String LoginTag="LOGINTAG";
    private MyReservationFragment myReservationFragment;
    private BalanceFragment balanceFragment;
    private MyInforFragment inforFragment;
    private ChangePasswordFragment changePasswordFragment;
    private ImageView loginIcon;
    HomeViewModel homeViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_profile,container,false);
        profileViewModel=new ViewModelProvider(getActivity()).get(ProfileViewModel.class);
        homeViewModel=new ViewModelProvider(getActivity()).get(HomeViewModel.class);
        Patient current=FileManagement.loadCurrentPatient(getContext());
        ImageView myReservation,myBalance,myInformation,changePassword;
        myReserveLayout=root.findViewById(R.id.profile_menu_reserve);
        myBalanceLayout=root.findViewById(R.id.profile_menu_balance);
        myInforLayout=root.findViewById(R.id.profile_menu_profile);
        final Button logOutButton;
        myReservation=root.findViewById(R.id.reserve_arrow);
        myBalance=root.findViewById(R.id.balance_arrow);
        myInformation=root.findViewById(R.id.information_arrow);
        myChangeLayout=root.findViewById(R.id.profile_menu_password);
        changePassword=root.findViewById(R.id.changePassword_arrow);
        logOutButton=root.findViewById(R.id.LogOutButton);
        loginIcon= root.findViewById(R.id.notLogin);
        hint=root.findViewById(R.id.loginLink);
        if(current!=null){
            profileViewModel.setCurrentPatient(current);
            hint.setText("你好，"+current.getPatientName()+" ！");
            homeViewModel.setCurrentPatient(current);
        }
        loginIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((profileViewModel.getCurrentPatient()==null)){
                    FragmentManager fm=getActivity().getSupportFragmentManager();
                    FragmentTransaction ft=fm.beginTransaction();
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    hide(ft);
                    loginFragment=new LoginFragment();
                    ft.add(R.id.fl_container,loginFragment,LoginTag);
                    ft.commit();
                }else{
                    Toast.makeText(getContext(),"你好，"+profileViewModel.getCurrentPatient().getPatientName(),Toast.LENGTH_SHORT).show();
                }
            }
        });


        profileViewModel.islogined.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    hint.setText("你好，"+profileViewModel.getCurrentPatient().getPatientName()+"！");
                    loginIcon.setImageDrawable(getResources().getDrawable(R.drawable.profile_user));
                }
            }
        });


        profileViewModel.currentPatient.observe(getViewLifecycleOwner(), new Observer<Patient>() {
            @Override
            public void onChanged(Patient patient) {
                if(patient!=null){
                    loginIcon.setImageDrawable(getResources().getDrawable(R.drawable.profile_user));
                    hint.setText("你好，"+profileViewModel.getCurrentPatient().getPatientName()+" !");
                }
            }
        });

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File f=new File(getActivity().getFilesDir(),"CurrentPatient");
                if(f.exists()){
                    f.delete();
                    Toast.makeText(getContext(),"注销成功",Toast.LENGTH_SHORT).show();
                    hint.setText("点击头像登录");
                    profileViewModel.setIslogined(false);
                    profileViewModel.setCurrentPatient(null);
                    homeViewModel.setCurrentPatient(null);
                    loginIcon.setImageDrawable(getResources().getDrawable(R.drawable.logo));
                }else{
                    Toast.makeText(getContext(),"请先登录",Toast.LENGTH_SHORT).show();
                }
            }
        });

        profileViewModel.islogined.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    hint.setText("你好，"+profileViewModel.getCurrentPatient().getPatientName()+" ！");
                    homeViewModel.setCurrentPatient(current);
                    FragmentManager fm=getActivity().getSupportFragmentManager();
                    FragmentTransaction ft=fm.beginTransaction();
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.remove(loginFragment);
                    ft.show(ProfileFragment.this);
                    ft.commit();
                }else{

                }
            }
        });

        profileViewModel.changed.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    hint.setText("请点击头像登录");
                    FragmentManager fm=getActivity().getSupportFragmentManager();
                    FragmentTransaction ft=fm.beginTransaction();
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.remove(changePasswordFragment);
                    ft.show(ProfileFragment.this);
                    ft.commit();
                    loginIcon.setImageDrawable(getResources().getDrawable(R.drawable.logo));
                }
            }
        });

        myReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(profileViewModel.getCurrentPatient()==null){
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("请先登录")
                            .setContentText("请登陆后查看详情")
                            .show();
                    return;
                }
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                hide(ft);
                myReservationFragment=new MyReservationFragment();
                ft.add(R.id.fl_container,myReservationFragment);
                ft.commit();
                profileViewModel.setIsReservationOn(true);
            }
        });

        myReserveLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(profileViewModel.getCurrentPatient()==null){
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("请先登录")
                            .setContentText("请登陆后查看详情")
                            .show();
                    return;
                }
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                hide(ft);
                myReservationFragment=new MyReservationFragment();
                ft.add(R.id.fl_container,myReservationFragment);
                ft.commit();
                profileViewModel.setIsReservationOn(true);
            }
        });

        myBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(profileViewModel.getCurrentPatient()==null){
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("请先登录")
                            .setContentText("请登陆后查看详情")
                            .show();
                    return;
                }
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                hide(ft);
                balanceFragment=new BalanceFragment();
                ft.add(R.id.fl_container,balanceFragment);
                ft.commit();
                profileViewModel.setIsReservationOn(true);
            }
        });

        myBalanceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(profileViewModel.getCurrentPatient()==null){
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("请先登录")
                            .setContentText("请登陆后查看详情")
                            .show();
                    return;
                }
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                hide(ft);
                balanceFragment=new BalanceFragment();
                ft.add(R.id.fl_container,balanceFragment);
                ft.commit();
                profileViewModel.setIsReservationOn(true);
            }
        });

        myInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(profileViewModel.getCurrentPatient()==null){
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("请先登录")
                            .setContentText("请登陆后查看详情")
                            .show();
                    return;
                }
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                hide(ft);
                inforFragment=new MyInforFragment();
                ft.add(R.id.fl_container,inforFragment);
                ft.commit();
                profileViewModel.setIsReservationOn(true);
            }
        });

        myInforLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(profileViewModel.getCurrentPatient()==null){
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("请先登录")
                            .setContentText("请登陆后查看详情")
                            .show();
                    return;
                }
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                hide(ft);
                inforFragment=new MyInforFragment();
                ft.add(R.id.fl_container,inforFragment);
                ft.commit();
                profileViewModel.setIsReservationOn(true);
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(profileViewModel.getCurrentPatient()==null){
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("请先登录")
                            .setContentText("请登陆后查看详情")
                            .show();
                    return;
                }
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                hide(ft);
                changePasswordFragment=new ChangePasswordFragment();
                ft.add(R.id.fl_container,changePasswordFragment);
                ft.commit();
                profileViewModel.setIsReservationOn(true);
            }
        });

        myChangeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(profileViewModel.getCurrentPatient()==null){
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("请先登录")
                            .setContentText("请登陆后查看详情")
                            .show();
                    return;
                }
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                hide(ft);
                changePasswordFragment=new ChangePasswordFragment();
                ft.add(R.id.fl_container,changePasswordFragment);
                ft.commit();
                profileViewModel.setIsReservationOn(true);
            }
        });
        return root;
    }

    private void hide(FragmentTransaction ft){
        ft.hide(this);
    }

    public LoginFragment getLoginFragment(){
        return this.loginFragment;
    }

    public MyReservationFragment getMyReservationFragment(){
        return this.myReservationFragment;
    }

    public BalanceFragment getBalanceFragment(){
        return this.balanceFragment;
    }

    public MyInforFragment getInforFragment(){return this.inforFragment;}

    public ChangePasswordFragment getChangePasswordFragment(){
        return this.changePasswordFragment;
    }

    public SignUpFragment getSignUpFragment(){
        return this.loginFragment.getSignUpFragment();
    }
}
