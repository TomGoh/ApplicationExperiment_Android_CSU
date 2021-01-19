package com.tom.patientservice.UI.Profile;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.tom.patientservice.NetworkResource.API;
import com.tom.patientservice.R;
import com.tom.patientservice.pay.PayActivity;

import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class BalanceFragment extends Fragment {

    private static final int RESULT_OK = 999;
    ProfileViewModel profileViewModel;

    TextView balanceAmount;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        profileViewModel=new ViewModelProvider(getActivity()).get(ProfileViewModel.class);
        View root=inflater.inflate(R.layout.fragment_balance,container,false);
        Button button=root.findViewById(R.id.money_button);
        balanceAmount=root.findViewById(R.id.balance_quantity);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), PayActivity.class);
                startActivityForResult(intent,9);
            }
        });
        FetchBalance fetchBalance=new FetchBalance();
        fetchBalance.execute();
        return root;
    }

    class FetchBalance extends AsyncTask<Void,Void,String>{
        @Override
        protected String doInBackground(Void... voids) {
            API api = new API();
            String balance=null;
            try {
                balance = api.getBalanceAmount(profileViewModel.getCurrentPatient().getPatientAccount());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return balance;
        }

        @Override
        protected void onPostExecute(String s) {
            if(s==null){
                balanceAmount.setText("0");
            }else{
                balanceAmount.setText("￥"+s);
            }
        }
    }

    class UploadResult extends AsyncTask<String,Void,Boolean>{
        @Override
        protected Boolean doInBackground(String... strings) {
            API api=new API();
            Boolean result=false;
            try {
                result=api.sendBalanceResult(profileViewModel.getCurrentPatient().getPatientAccount(),strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            FetchBalance fetchBalance=new FetchBalance();
            fetchBalance.execute();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 9 && resultCode == RESULT_OK){
            UploadResult uploadResult=new UploadResult();
            uploadResult.execute(data.getStringExtra("amount"));
            FetchBalance fetchBalance=new FetchBalance();
            fetchBalance.execute();
            new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("支付成功")
                    .setContentText("请返回查看")
                    .show();
        }
    }
}
