package com.tom.patientservice.UI.Home;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.tom.patientservice.NetworkResource.API;
import com.tom.patientservice.R;
import com.tom.patientservice.UI.Login.LoginFragment;
import com.tom.patientservice.UI.Profile.MyReservationFragment;
import com.tom.patientservice.UI.Slide.ImageAdapter;
import com.tom.patientservice.pojo.Department;
import com.tom.patientservice.pojo.FullReservation;
import com.tom.patientservice.pojo.ImageDataBean;
import com.tom.patientservice.pojo.Patient;
import com.tom.patientservice.utils.FileManagement;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.util.LogUtils;

import java.io.IOException;
import java.util.List;

public class HomeFragment extends Fragment {

    Banner banner;

    ImageView Neurosurgery,Neurlogy,thoracicSurgery,Pediatrics,Otorhinolaryngology,
            Cardiology,surgicalOncology,Gastroenterology;

    TextView welcomeText,welcomeReservation;

    HomeViewModel homeViewModel;
    DepartmentIntroFragment departmentIntroFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_home,container,false);
        banner=root.findViewById(R.id.banner);
        ImageAdapter adapter = new ImageAdapter(ImageDataBean.getTestData());
        homeViewModel=new ViewModelProvider(getActivity()).get(HomeViewModel.class);
        banner.setAdapter(adapter)
                .addBannerLifecycleObserver(this)//添加生命周期观察者
                .setIndicator(new CircleIndicator(getActivity()))//设置指示器
                .setOnBannerListener((data, position) -> {
                    Snackbar.make(banner, ((ImageDataBean) data).title, Snackbar.LENGTH_SHORT).show();
                    LogUtils.d("position：" + position);
                });
        Neurosurgery=root.findViewById(R.id.department_image_1);
        Neurlogy=root.findViewById(R.id.department_image_2);
        thoracicSurgery=root.findViewById(R.id.department_image_3);
        Pediatrics=root.findViewById(R.id.department_image_4);
        Otorhinolaryngology=root.findViewById(R.id.department_image_5);
        Cardiology=root.findViewById(R.id.department_image_6);
        surgicalOncology=root.findViewById(R.id.department_image_7);
        Gastroenterology=root.findViewById(R.id.department_image_8);
        welcomeText=root.findViewById(R.id.welcome_text);
        welcomeReservation=root.findViewById(R.id.welcome_reservation);
        Patient currentPatient= FileManagement.loadCurrentPatient(getContext());
        if(currentPatient==null){
            welcomeText.setText("请登录");
        }else{
            welcomeText.setText("你好，"+currentPatient.getPatientName()+" ！");
            welcomeReservation.setText("如需查看预约，请进入“我的预约”查看\n如需预约请点击下方“预约”");
            homeViewModel.setCurrentPatient(currentPatient);
        }

        FetchDepartmentInfor fetchDepartmentInfor=new FetchDepartmentInfor();
        fetchDepartmentInfor.execute();
        Neurosurgery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm=getActivity().getSupportFragmentManager();

                FragmentTransaction ft=fm.beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                hide(ft);
                homeViewModel.setCheckedItem("神经外科");
                departmentIntroFragment=new DepartmentIntroFragment();
                ft.add(R.id.fl_container,departmentIntroFragment);
                ft.commit();
            }
        });

        Neurlogy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                homeViewModel.setCheckedItem("神经内科");
                hide(ft);
                departmentIntroFragment=new DepartmentIntroFragment();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.add(R.id.fl_container,departmentIntroFragment);
                ft.commit();
            }
        });

        thoracicSurgery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                homeViewModel.setCheckedItem("胸外科");
                hide(ft);
                departmentIntroFragment=new DepartmentIntroFragment();
                ft.add(R.id.fl_container,departmentIntroFragment);
                ft.commit();
            }
        });

        Pediatrics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                homeViewModel.setCheckedItem("儿科");
                hide(ft);
                departmentIntroFragment=new DepartmentIntroFragment();
                ft.add(R.id.fl_container,departmentIntroFragment);
                ft.commit();
            }
        });

        Otorhinolaryngology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                homeViewModel.setCheckedItem("耳鼻喉科");
                hide(ft);
                departmentIntroFragment=new DepartmentIntroFragment();
                ft.add(R.id.fl_container,departmentIntroFragment);
                ft.commit();
            }
        });

        Cardiology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                homeViewModel.setCheckedItem("心血管科");
                hide(ft);
                departmentIntroFragment=new DepartmentIntroFragment();
                ft.add(R.id.fl_container,departmentIntroFragment);
                ft.commit();
            }
        });

        surgicalOncology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                homeViewModel.setCheckedItem("肿瘤科");
                hide(ft);
                departmentIntroFragment=new DepartmentIntroFragment();
                ft.add(R.id.fl_container,departmentIntroFragment);
                ft.commit();
            }
        });

        Gastroenterology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                homeViewModel.setCheckedItem("消化内科");
                hide(ft);
                departmentIntroFragment=new DepartmentIntroFragment();
                ft.add(R.id.fl_container,departmentIntroFragment);
                ft.commit();
            }
        });

        homeViewModel.currentPatient.observe(getViewLifecycleOwner(), new Observer<Patient>() {
            @Override
            public void onChanged(Patient patient) {
                if(patient==null){
                    welcomeText.setText("请登录");
                    welcomeReservation.setText("");
                }else{
                    welcomeText.setText("你好，"+patient.getPatientName()+" ！");
                    welcomeReservation.setText("如需查看预约，请进入“我的预约”查看\n如需预约请点击下方“预约”");
                    FetchReservation fetchReservation=new FetchReservation();
                    fetchReservation.execute();
                }
            }
        });
        FetchReservation fetchReservation=new FetchReservation();
        fetchReservation.execute();
        return root;
    }

    private void test(FragmentTransaction ft){
        ft.hide(this);
    }

    public void useBanner() {

    }

    private void hide(FragmentTransaction ft){
        ft.hide(this);
    }

    public DepartmentIntroFragment getDepartmentIntroFragment(){
        return this.departmentIntroFragment;
    }

    class FetchDepartmentInfor extends AsyncTask<Void,Void, List<Department>> {
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

    class FetchReservation extends AsyncTask<Void,Void,List<FullReservation>>{
        @Override
        protected List<FullReservation> doInBackground(Void... voids) {
            API api=new API();
            List<FullReservation> fullReservationList=null;
            try {
                if(homeViewModel.getCurrentPatient()!=null){
                    fullReservationList=api.getFullReservation(homeViewModel.getCurrentPatient().getPatientAccount());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return fullReservationList;
        }

        @Override
        protected void onPostExecute(List<FullReservation> reservations) {

        }
    }

}
