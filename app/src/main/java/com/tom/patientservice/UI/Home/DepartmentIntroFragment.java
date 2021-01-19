package com.tom.patientservice.UI.Home;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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

import com.google.android.material.snackbar.Snackbar;
import com.tom.patientservice.NetworkResource.API;
import com.tom.patientservice.R;
import com.tom.patientservice.UI.Reserve.DateReserveFragment;
import com.tom.patientservice.UI.Reserve.DoctorReserveFragment;
import com.tom.patientservice.UI.Reserve.ReserveFragment;
import com.tom.patientservice.UI.Slide.ImageAdapter;
import com.tom.patientservice.pojo.Department;
import com.tom.patientservice.pojo.Doctor;
import com.tom.patientservice.pojo.ImageDataBean;
import com.tom.patientservice.pojo.ImageDataBean2;
import com.tom.patientservice.utils.FileManagement;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.util.LogUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class DepartmentIntroFragment extends Fragment {

    RecyclerView recyclerView;
    HomeViewModel homeViewModel;
    Banner banner;
    TextView intro_title,intro_desc,intro_top_title;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_department_intro,container,false);
        recyclerView=root.findViewById(R.id.intro_list);
        banner=root.findViewById(R.id.intro_banner);
        ImageAdapter adapter = new ImageAdapter(ImageDataBean2.getTestData());
        banner.setAdapter(adapter)
                .addBannerLifecycleObserver(this)//添加生命周期观察者
                .setIndicator(new CircleIndicator(getActivity()))//设置指示器
                .setOnBannerListener((data, position) -> {
                    Snackbar.make(banner, ((ImageDataBean) data).title, Snackbar.LENGTH_SHORT).show();
                    LogUtils.d("position：" + position);
                });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        intro_title=root.findViewById(R.id.intro_title);
        intro_desc=root.findViewById(R.id.intro_desc);
        intro_top_title=root.findViewById(R.id.intro_top_title);
        display(homeViewModel.getCheckedItem());
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeViewModel=new ViewModelProvider(getActivity()).get(HomeViewModel.class);
        FetchDepartmentInfor fetchDepartmentInfor=new FetchDepartmentInfor();
        fetchDepartmentInfor.execute();
        FetchDoctor fetchDoctor= new FetchDoctor();
        fetchDoctor.execute();
    }

    class ListHolder extends RecyclerView.ViewHolder{

        private TextView doctorName,doctoeDescription,doctorDegree;
        public ListHolder(@NonNull View itemView) {
            super(itemView);
            doctoeDescription=itemView.findViewById(R.id.doctor_description);
            doctorDegree=itemView.findViewById(R.id.doctor_degree);
            doctorName=itemView.findViewById(R.id.doctor_name);
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

    class FetchDoctor extends AsyncTask<Void,Void,List<Doctor>> {
        @Override
        protected void onPostExecute(List<Doctor> doctors) {
            if(doctors!=null){
                homeViewModel.setDoctorList(doctors);
                ListAdapter listAdapter=new ListAdapter(doctors);
                recyclerView.setAdapter(listAdapter);
            }
        }

        @Override
        protected List<Doctor> doInBackground(Void... voids) {
            API api=new API();
            List<Doctor> doctorList=null;
            try {
                int index=-1;
                //doctorList=api.getAllDoctorInfor(homeViewModel.getCheckedItem());
                for(int i=0;i<homeViewModel.getDepartmentList().size();i++){
                    if(homeViewModel.getDepartmentList().get(i).getDepartmentName().equals(homeViewModel.getCheckedItem())){
                        index=i;
                    }
                }
                doctorList=api.getAllDoctorInfor(homeViewModel.getDepartmentList().get(index).getDepartmentID());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return doctorList;
        }
    }

    class FetchDepartmentInfor extends AsyncTask<Void,Void,List<Department>> {
        @Override
        protected List<Department> doInBackground(Void... voids) {
            API api=new API();
            List<Department> departments=null;
            try {
                departments=api.getAllDepartmentInfor();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return departments;
        }

        @Override
        protected void onPostExecute(List<Department> departments) {
            if(departments!=null){
                homeViewModel.setDepartmentList(departments);
                Log.i("Reservation Fragment","Fetch department failed");
            }
        }

    }

    private void display(String department){
        switch (department){
            case "肿瘤科":
                intro_title.setText("肿瘤科");
                intro_top_title.setText("肿瘤科");
                intro_desc.setText(R.string.ZLK);
                break;
            case "消化内科":
                intro_title.setText("消化内科");
                intro_top_title.setText("消化内科");
                intro_desc.setText(R.string.XHNK);
                break;
            case "胸外科":
                intro_title.setText("胸外科");
                intro_top_title.setText("胸外科");
                intro_desc.setText(R.string.XWK);
                break;
            case "神经外科":
                intro_title.setText("神经外科");
                intro_top_title.setText("神经外科");
                intro_desc.setText(R.string.SJWK);
                break;
            case "神经内科":
                intro_title.setText("神经内科");
                intro_top_title.setText("神经内科");
                intro_desc.setText(R.string.SJNK);
                break;
            case "心血管科":
                intro_title.setText("心血管科");
                intro_top_title.setText("心血管科");
                intro_desc.setText(R.string.XXGK);
                break;
            case "耳鼻喉科":
                intro_title.setText("耳鼻喉科");
                intro_top_title.setText("耳鼻喉科");
                intro_desc.setText(R.string.ERBHK);
                break;
            case "儿科":
                intro_title.setText("儿科");
                intro_top_title.setText("儿科");
                intro_desc.setText(R.string.ERK);
                break;
        }
    }


}
