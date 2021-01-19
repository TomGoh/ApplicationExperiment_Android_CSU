package com.tom.patientservice.UI.Login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.tom.patientservice.NetworkResource.API;
import com.tom.patientservice.R;
import com.tom.patientservice.UI.Home.HomeViewModel;
import com.tom.patientservice.UI.Profile.ProfileViewModel;
import com.tom.patientservice.pojo.Patient;
import com.tom.patientservice.utils.FileManagement;

import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginFragment extends Fragment {

    LoginViewModel loginViewModel;
    SignUpFragment signUpFragment;
    HomeViewModel homeViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_login,container,false);

        loginViewModel=new ViewModelProvider(getActivity()).get(LoginViewModel.class);
        homeViewModel=new ViewModelProvider(getActivity()).get(HomeViewModel.class);
        EditText accountInput=root.findViewById(R.id.accountInput);
        EditText passwordInput=root.findViewById(R.id.passwordInput);
        Button loginButton=root.findViewById(R.id.loginButton);
        TextView toSignUp=root.findViewById(R.id.toSignUp);

        loginViewModel.signUpResult.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    Toast.makeText(getContext(),"注册成功，请登录",Toast.LENGTH_SHORT).show();
                    FragmentManager fm=getActivity().getSupportFragmentManager();
                    FragmentTransaction ft=fm.beginTransaction();
                    if(signUpFragment!=null){
                        ft.remove(signUpFragment);
                        ft.show(LoginFragment.this);
                        ft.commit();
                    }
                }
            }
        });
        loginViewModel.loginResult.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    Toast.makeText(getContext(),"登录成功",Toast.LENGTH_SHORT).show();
                }
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginViewModel.setAccount(accountInput.getText().toString());
                loginViewModel.setPassword(passwordInput.getText().toString());
                LoginProcess loginProcess=new LoginProcess();
                loginProcess.execute();
                if(loginViewModel.getLoginResult()){
                    //getActivity().finish();
                }
            }
        });

        toSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft= fm.beginTransaction();
                hide(ft);
                signUpFragment=new SignUpFragment();
                ft.add(R.id.fl_container,signUpFragment);
                ft.commit();
            }
        });

        return root;
    }
    /**
     * 处理Internet数据的异步类
     */
    class LoginProcess extends AsyncTask<Void,Void, Patient> {
        @Override
        protected Patient doInBackground(Void... voids) {
            Patient result=null;
            API api=new API();

            try {
                result=api.login(loginViewModel.getAccount(),loginViewModel.getPassword());
                //Log.i("LOGIN STRUCTURE",result.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(result!=null){
                FileManagement.saveCurrentPatient(getContext(),result);
            }else{
                //Log.i("LOGIN STRUCTURE","login failed");
            }
            return result;
        }

        @Override
        protected void onPostExecute(Patient patient) {
            super.onPostExecute(patient);
            if(patient!=null){
                loginViewModel.setAccount(patient.getPatientAccount());
                loginViewModel.setPassword(patient.getPatientPassword());
                loginViewModel.setLoginResult(true);
                Log.i("LOGIN STRUCTURE",patient.toString());
                ProfileViewModel profileViewModel=new ViewModelProvider(getActivity()).get(ProfileViewModel.class);
                profileViewModel.setCurrentPatient(patient);
                profileViewModel.setIslogined(true);
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                hide(ft);
                ft.commit();
                homeViewModel.setCurrentPatient(patient);
            }else{
                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("登陆失败")
                        .setContentText("请检查账号密码")
                        .show();
            }

        }
    }

    private void hide(FragmentTransaction ft){
        ft.hide(this);
    }

    public SignUpFragment getSignUpFragment(){
        return this.signUpFragment;
    }

}
