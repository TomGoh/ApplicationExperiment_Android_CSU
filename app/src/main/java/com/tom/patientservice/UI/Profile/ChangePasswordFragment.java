package com.tom.patientservice.UI.Profile;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.tom.patientservice.NetworkResource.API;
import com.tom.patientservice.R;
import com.tom.patientservice.UI.Login.LoginViewModel;

import java.io.File;
import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ChangePasswordFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_change_password,container,false);
        EditText accountInput,oldPasswordInput,newPasswordInput;
        Button submitButton;
        accountInput=root.findViewById(R.id.accountInput_change);
        newPasswordInput=root.findViewById(R.id.passwordChangeAgain);
        oldPasswordInput=root.findViewById(R.id.passwordInput_change);
        submitButton=root.findViewById(R.id.changePasswordButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account=accountInput.getText().toString();
                String oldPassword=oldPasswordInput.getText().toString();
                String newPassword=newPasswordInput.getText().toString();

                if(account.equals("") || oldPassword.equals("") || newPassword.equals("")){
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("输入不得为空")
                            .setContentText("请检查账号密码输入")
                            .show();
                    return;
                }
                ChangeTask changeTask=new ChangeTask();
                changeTask.execute(new String[]{account,oldPassword,newPassword});

            }
        });
        return root;
    }

    class ChangeTask extends AsyncTask<String,Void,Boolean>{
        @Override
        protected Boolean doInBackground(String... strings) {
            API api=new API();
            boolean result=false;
            try {
                result=api.changePassword(strings[0],strings[1],strings[2]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean){
                new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("修改成功")
                        .setContentText("请重新登录")
                        .show();
                ProfileViewModel profileViewModel=new ViewModelProvider(getActivity()).get(ProfileViewModel.class);
                profileViewModel.setCurrentPatient(null);
                profileViewModel.setIslogined(false);
                profileViewModel.setChanged(true);
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                hide(ft);
                ft.commit();
                File f=new File(getActivity().getFilesDir(),"CurrentPatient");
                if(f.exists()) {
                    f.delete();
                }
                LoginViewModel loginViewModel=new ViewModelProvider(getActivity()).get(LoginViewModel.class);
                loginViewModel.setLoginResult(false);
            }else{
                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("修改失败")
                        .setContentText("请稍后重试")
                        .show();
            }
        }
    }

    private void hide(FragmentTransaction ft){
        ft.hide(this);
    }
}
