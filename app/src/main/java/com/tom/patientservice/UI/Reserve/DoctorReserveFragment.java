package com.tom.patientservice.UI.Reserve;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tom.patientservice.NetworkResource.API;
import com.tom.patientservice.R;
import com.tom.patientservice.pojo.Department;
import com.tom.patientservice.pojo.Doctor;
import com.tom.patientservice.pojo.DoctorReserve;
import com.tom.patientservice.pojo.Reservation;
import com.tom.patientservice.utils.FileManagement;
import com.tom.patientservice.utils.Message;

import java.io.IOException;
import java.util.List;

public class DoctorReserveFragment extends Fragment {

    private ReserveViewModel reserveViewModel;
    RecyclerView recyclerView;
    DateReserveFragment dateReserveFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.doctor_list,container,false);
        //reserveViewModel=new ViewModelProvider(getActivity()).get(ReserveViewModel.class);
        recyclerView=root.findViewById(R.id.doctor_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dateReserveFragment=null;
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reserveViewModel=new ViewModelProvider(getActivity()).get(ReserveViewModel.class);
        FetchDoctor fetchDoctor=new FetchDoctor();
        fetchDoctor.execute();
    }

    class ListHolder extends RecyclerView.ViewHolder{

        private TextView doctorName,doctoeDescription,doctorDegree;
        public ListHolder(@NonNull View itemView) {
            super(itemView);
            doctoeDescription=itemView.findViewById(R.id.doctor_description);
            doctorDegree=itemView.findViewById(R.id.doctor_degree);
            doctorName=itemView.findViewById(R.id.doctor_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Doctor doctor=reserveViewModel.getDoctorList().get(getAdapterPosition());
                    reserveViewModel.setChooseDoctor(doctor);
                    FragmentManager fm=getActivity().getSupportFragmentManager();
                    FragmentTransaction ft=fm.beginTransaction();
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    hide(ft);
                    dateReserveFragment=new DateReserveFragment();
                    ft.add(R.id.fl_container,dateReserveFragment);
                    ft.commit();
                }
            });
        }
    }

    class ListAdapter extends RecyclerView.Adapter<ListHolder>{

        List<Doctor> doctorList;

        public ListAdapter(List<Doctor> doctors) {
            this.doctorList=doctors;
        }

        @NonNull
        @Override
        public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater=LayoutInflater.from(getActivity());
            View view=inflater.inflate(R.layout.doctor_list_item,parent,false);
            return new ListHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ListHolder holder, int position) {
            Doctor doctor=doctorList.get(position);
            String doctorName=doctor.getDoctorName();
            String doctorDescription=doctor.getDoctorDescription();
            String doctorDegree=doctor.getDoctorDegree();
            holder.doctorName.setText(doctorName);
            if(doctorDescription!=null&&!doctorDescription.equals("")){
                holder.doctoeDescription.setText(doctorDescription);
            }else{
                holder.doctoeDescription.setText("暂时无医生简介");
            }

            holder.doctorDegree.setText(doctorDegree);
        }

        @Override
        public int getItemCount() {
            return doctorList.size();
        }
    }

    class FetchDoctor extends AsyncTask<Void,Void,List<Doctor>>{
        @Override
        protected void onPostExecute(List<Doctor> doctors) {
            if(doctors!=null){
                reserveViewModel.setDoctorList(doctors);
                ListAdapter listAdapter=new ListAdapter(doctors);
                recyclerView.setAdapter(listAdapter);
            }
        }

        @Override
        protected List<Doctor> doInBackground(Void... voids) {
            API api=new API();
            List<Doctor> doctorList=null;
            try {
                doctorList=api.getAllDoctorInfor(reserveViewModel.getChooseDepartment().getDepartmentID());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return doctorList;
        }
    }


    private void hide (FragmentTransaction ft){
        ft.hide(this);
    }

    public DateReserveFragment getDateReserveFragment(){
        return this.dateReserveFragment;
    }
}
