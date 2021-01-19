package com.tom.patientservice.UI.Profile;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tom.patientservice.NetworkResource.API;
import com.tom.patientservice.R;
import com.tom.patientservice.pojo.FullReservation;

import java.io.IOException;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MyReservationFragment extends Fragment {

    RecyclerView recyclerView;
    ProfileViewModel profileViewModel;
    String balance;
//    ImageView wrongImage;
//    TextView wrongText;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        profileViewModel=new ViewModelProvider(getActivity()).get(ProfileViewModel.class);
        View root=inflater.inflate(R.layout.fragment_my_reservation,container,false);
        recyclerView=root.findViewById(R.id.my_reserve_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileViewModel=new ViewModelProvider(getActivity()).get(ProfileViewModel.class);
        FetchReservation fetchReservation=new FetchReservation();
        fetchReservation.execute();
        FetchBalance fetchBalance=new FetchBalance();
        fetchBalance.execute();
    }

    @Override
    public void onStart() {
        super.onStart();
        profileViewModel.fullReservationList.observe(getViewLifecycleOwner(), new Observer<List<FullReservation>>() {
            @Override
            public void onChanged(List<FullReservation> fullReservations) {
                if(profileViewModel.getFullReservationList()!=null){
                    //wrongImage.setVisibility(View.INVISIBLE);
                    //wrongText.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    class ListHolders extends RecyclerView.ViewHolder{

        private TextView departmentName,doctorName,date,payment,goToPay,reserveID,cancelButton;

        public ListHolders(@NonNull View itemView) {
            super(itemView);
            departmentName=itemView.findViewById(R.id.my_reserve_department);
            doctorName=itemView.findViewById(R.id.my_reserve_doctor);
            date=itemView.findViewById(R.id.reserve_date);
            payment=itemView.findViewById(R.id.reserve_payment);
            goToPay=itemView.findViewById(R.id.pay_Button);
            reserveID=itemView.findViewById(R.id.reserve_id);
            cancelButton=itemView.findViewById(R.id.Cancel_Button);
//            wrongImage =itemView.findViewById(R.id.my_reserve_wrong);
//            wrongText =itemView.findViewById(R.id.my_reserve_text);
        }
    }

    class ListAdapter extends RecyclerView.Adapter<ListHolders>{

        private List<FullReservation> reservations;

        public ListAdapter(List<FullReservation> reservations){
            profileViewModel.setFullReservationList(reservations);
            this.reservations=reservations;
        }

        @NonNull
        @Override
        public ListHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater=LayoutInflater.from(getActivity());
            View view =layoutInflater.inflate(R.layout.my_reserve_item,parent,false);
            return new ListHolders(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ListHolders holder, int position) {
            FullReservation reservation=reservations.get(position);
            String doctorName=reservation.getDoctorName();
            String patientName=reservation.getPatientName();
            String departmentName=reservation.getDepartmentName();
            String date=reservation.getDate();
            boolean payment=reservation.isPayment();
            if(payment){
                holder.payment.setText("已支付");
                holder.goToPay.setVisibility(View.GONE);
            }else{
                holder.payment.setText("未支付");
                holder.goToPay.setText("支付");
                holder.goToPay.setVisibility(View.VISIBLE);
            }

            holder.date.setText(date);
            holder.doctorName.setText(doctorName);
            holder.departmentName.setText(departmentName);
            holder.reserveID.setText("序号： "+reservation.getReserveID());


            holder.goToPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(payment){
                        return;
                    }else{
                        if(Float.parseFloat(balance)<10){
                            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("余额不足")
                                    .setContentText("余额不足，请充值")
                                    .show();
                        }else{
                            FullReservation reservation1=reservations.get(position);
                            Pay pay=new Pay();
                            pay.execute(reservation1.getReserveID());
                        }
                    }
                }
            });

            holder.cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FullReservation reservation1=reservations.get(position);
                    CancelTask cancelTask=new CancelTask();
                    cancelTask.execute(reservation1.getReserveID());
                }
            });

        }

        @Override
        public int getItemCount() {
            return reservations.size();
        }
    }

    class FetchReservation extends AsyncTask<Void,Void,List<FullReservation>>{
        @Override
        protected List<FullReservation> doInBackground(Void... voids) {
            API api=new API();
            List<FullReservation> fullReservationList=null;
            try {
                fullReservationList=api.getFullReservation(profileViewModel.getCurrentPatient().getPatientAccount());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return fullReservationList;
        }

        @Override
        protected void onPostExecute(List<FullReservation> reservations) {
            if(reservations!=null){
                ListAdapter listAdapter=new ListAdapter(reservations);
                recyclerView.setAdapter(listAdapter);
            }
        }
    }

    class CancelTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            API api=new API();
            boolean result=false;
            try {
                result=api.cancelReservation(profileViewModel.getCurrentPatient().getPatientAccount(),strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean){
                new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("取消成功")
                        .setContentText("您的预约已经被取消")
                        .show();
                FetchReservation fetchReservation=new FetchReservation();
                fetchReservation.execute();
            }
        }
    }

    class Pay extends AsyncTask<String,Void,Boolean>{
        @Override
        protected Boolean doInBackground(String... strings) {
            API api= new API();
            boolean result=false;
            try {
               result= api.payForReserve(profileViewModel.getCurrentPatient().getPatientAccount(),strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean){
                new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("支付成功")
                        .setContentText("您已完成预约支付，请按时候诊")
                        .show();
                FetchBalance fetchBalance=new FetchBalance();
                fetchBalance.execute();
                FetchReservation fetchReservation=new FetchReservation();
                fetchReservation.execute();
            }
        }
    }

    public MyReservationFragment getMyReservationFragment(){
        return this;
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
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("出错了")
                        .setContentText("查询余额出现问题，请稍后重试")
                        .show();
            }else{
                balance=s;
            }
        }
    }
}
