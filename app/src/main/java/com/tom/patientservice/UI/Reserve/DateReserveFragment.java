package com.tom.patientservice.UI.Reserve;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tom.patientservice.MainActivity;
import com.tom.patientservice.NetworkResource.API;
import com.tom.patientservice.R;
import com.tom.patientservice.UI.Profile.ProfileViewModel;
import com.tom.patientservice.pojo.Doctor;
import com.tom.patientservice.pojo.DoctorReserve;
import com.tom.patientservice.pojo.Reservation;
import com.tom.patientservice.utils.FileManagement;
import com.tom.patientservice.utils.Message;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DateReserveFragment extends Fragment {

    RecyclerView recyclerView;
    ReserveViewModel reserveViewModel;
    ProfileViewModel profileViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_date_reserve,container,false);
        //reserveViewModel=new ViewModelProvider(getActivity()).get(ReserveViewModel.class);
        TextView doctorName,doctorDegree,doctorDescription;
        doctorName=root.findViewById(R.id.date_doctor_name);
        doctorDegree=root.findViewById(R.id.date_doctor_degree);
        doctorDescription=root.findViewById(R.id.date_doctor_description);
        doctorName.setText(reserveViewModel.getChooseDoctor().getDoctorName());
        doctorDegree.setText(reserveViewModel.getChooseDoctor().getDoctorDegree());
        doctorDescription.setText(reserveViewModel.getChooseDoctor().getDoctorDescription());
        recyclerView=root.findViewById(R.id.date_container);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        profileViewModel=new ViewModelProvider(getActivity()).get(ProfileViewModel.class);
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reserveViewModel=new ViewModelProvider(getActivity()).get(ReserveViewModel.class);
        FetchDate fetchDate=new FetchDate();
        fetchDate.execute();
    }

    class ListHolder extends RecyclerView.ViewHolder{

        private TextView dateView;
        public ListHolder(@NonNull View itemView) {
            super(itemView);
            dateView=itemView.findViewById(R.id.date_text);
            dateView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(profileViewModel.getCurrentPatient()==null){
                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("请先登录")
                                .setContentText("登录后即可预约")
                                .show();
                    }else{
                        String date=dateView.getText().toString().split("\n")[1];
                        DoctorReserve doctorReserve=reserveViewModel.getDoctorReserveList().get(getAdapterPosition());
                        reserveViewModel.setFinalReserve(doctorReserve);
                        DoRequest doRequest=new DoRequest();
                        doRequest.execute();
                    }
                }
            });
        }
    }

    class ListAdapter extends RecyclerView.Adapter<ListHolder>{

        List<DoctorReserve> doctorReserveList;

        public ListAdapter(List<DoctorReserve> doctorReserveList) {
            this.doctorReserveList = doctorReserveList;
        }

        @NonNull
        @Override
        public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater=LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.date_list_item,parent,false);
            return new ListHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ListHolder holder, int position) {
            DoctorReserve doctorReserve=doctorReserveList.get(position);
            String date=doctorReserve.getReserveDate();
            String surplus=doctorReserve.getDoctorSurplus()+"";
            holder.dateView.setText(date+"\n"+"剩余量："+surplus);
        }

        @Override
        public int getItemCount() {
            return doctorReserveList.size();
        }
    }

    class FetchDate extends AsyncTask<Void,Void,List<DoctorReserve>>{
        @Override
        protected List<DoctorReserve> doInBackground(Void... voids) {
            API api=new API();
            List<DoctorReserve> doctorReserves=null;
            try {
                doctorReserves=api.getDoctorSurplusById(reserveViewModel.getChooseDoctor().getDoctorID());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return doctorReserves;
        }

        @Override
        protected void onPostExecute(List<DoctorReserve> doctorReserves) {
            if(doctorReserves!=null){
                reserveViewModel.setDoctorReserveList(doctorReserves);
                ListAdapter listAdapter=new ListAdapter(doctorReserves);
                recyclerView.setAdapter(listAdapter);
            }
        }
    }

    class DoRequest extends AsyncTask<Void,Void, Message>{
        @Override
        protected Message doInBackground(Void... voids) {
            API api=new API();
            String account= FileManagement.loadCurrentPatient(getContext()).getPatientAccount();
            Reservation reservation=new Reservation(account,reserveViewModel.getChooseDoctor().getDoctorID(),
                    reserveViewModel.getChooseDepartment().getDepartmentID(),reserveViewModel.getFinalReserve().getReserveDate(),0);
            Message  message=null;
            try {
                message=api.pullReservation(reservation);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return message;
        }

        @Override
        protected void onPostExecute(Message aMessage) {
            new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("预约成功")
                    .setContentText("请注意及时缴费")
                    .show();
            FetchDate fetchDate=new FetchDate();
            fetchDate.execute();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        FetchDate fetchDate=new FetchDate();
        fetchDate.execute();
    }

    private void hide(FragmentTransaction ft){
        ft.hide(this);
    }


}
