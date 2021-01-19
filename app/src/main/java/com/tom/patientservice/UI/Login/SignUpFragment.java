package com.tom.patientservice.UI.Login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.tom.patientservice.NetworkResource.API;
import com.tom.patientservice.R;

import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SignUpFragment extends Fragment {

    String account,name,password1,password2;
    LoginViewModel loginViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.fragment_singup,container,false);
        loginViewModel=new ViewModelProvider(getActivity()).get(LoginViewModel.class);
        Button signUpButton=root.findViewById(R.id.signUpButton);
        EditText accountInput=root.findViewById(R.id.accountInput_sign);
        EditText passwordInput=root.findViewById(R.id.passwordInput_sign);
        EditText passwordAgain=root.findViewById(R.id.passwordInputAgain);
        EditText nameInput=root.findViewById(R.id.NameInput_sign);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account=accountInput.getText().toString();
                password1=passwordInput.getText().toString();
                password2=passwordAgain.getText().toString();
                name=nameInput.getText().toString();
                if(password1.equals(password2)){
                    SignUpTask signUpTask=new SignUpTask();
                    signUpTask.execute();
                }else{
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("两次密码不一致")
                            .setContentText("请重新输入")
                            .show();
                }

            }
        });
        return root;
    }

    class SignUpTask extends AsyncTask<Void,Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            API api=new API();
            boolean result=false;
            try {
                result=api.signUp(account,password1,name);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean){
                new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("注册成功")
                        .setContentText("请返回登录")
                        .show();
                loginViewModel.setSignUpResult(aBoolean);
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                hide(ft);
                ft.commit();
            }else{
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("注册失败")
                        .setContentText("似乎有点问题，请重试")
                        .show();
            }
        }
    }
    private void hide(FragmentTransaction ft){
        ft.hide(this);
    }

}
