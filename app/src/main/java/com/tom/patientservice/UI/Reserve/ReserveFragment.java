package com.tom.patientservice.UI.Reserve;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tom.patientservice.MainActivity;
import com.tom.patientservice.NetworkResource.API;
import com.tom.patientservice.R;
import com.tom.patientservice.UI.Profile.ProfileViewModel;
import com.tom.patientservice.pojo.Department;

import java.io.IOException;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ReserveFragment extends Fragment {

    RecyclerView recyclerView;
    ReserveViewModel reserveViewModel;
    ProfileViewModel profileViewModel;
    DoctorReserveFragment doctorReserveFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        profileViewModel=new ViewModelProvider(getActivity()).get(ProfileViewModel.class);
        super.onCreate(savedInstanceState);
        FetchDepartmentInfor fetchDepartmentInfor=new FetchDepartmentInfor();
        fetchDepartmentInfor.execute();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.department_list,container,false);
        reserveViewModel=new ViewModelProvider(getActivity()).get(ReserveViewModel.class);
        reserveViewModel.setReserveFragment(this);
        recyclerView=root.findViewById(R.id.department_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        reserveViewModel.hasChosen.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    FragmentManager fm=getActivity().getSupportFragmentManager();
                    FragmentTransaction ft=fm.beginTransaction();
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.remove(doctorReserveFragment);
                    ft.show(ReserveFragment.this);
                    ft.commit();
                }
            }
        });
        return root;
    }

    class ListHolder extends RecyclerView.ViewHolder{

        private TextView departmentName,departmentDescription;
        private ImageView departmentImage;

        public ListHolder(@NonNull View itemView) {
            super(itemView);
            departmentName=itemView.findViewById(R.id.department_name);
            departmentDescription=itemView.findViewById(R.id.department_description);
            departmentImage=itemView.findViewById(R.id.department_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //getActivity().getSupportFragmentManager().popBackStack(null,1);
                    Department department=reserveViewModel.getDepartmentList().get(getAdapterPosition());
                    FragmentManager fm=getActivity().getSupportFragmentManager();
                    FragmentTransaction ft=fm.beginTransaction();
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    hide(ft);
                    doctorReserveFragment=new DoctorReserveFragment();
                    ft.add(R.id.fl_container,doctorReserveFragment);
                    ft.commit();
                    reserveViewModel.setChooseDepartment(department);
                }
            });
        }
    }

    class ListAdapter extends RecyclerView.Adapter<ListHolder>{

        private List<Department> departmentList;

        public ListAdapter(List<Department> departmentList) {
            this.departmentList = departmentList;
        }

        @NonNull
        @Override
        public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater=LayoutInflater.from(getActivity());
            View view=inflater.inflate(R.layout.department_list_item,parent,false);
            return new ListHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ListHolder holder, int position) {
            Department department=departmentList.get(position);
            String departmentName=department.getDepartmentName();
            String departmentDescription=department.getDepartmentDescription();
            holder.departmentName.setText(departmentName);
            if(departmentDescription==null||departmentDescription.equals("")){
                holder.departmentDescription.setText("暂时没有科室简介");
            }else{
                holder.departmentDescription.setText(departmentDescription);
            }
            holder.departmentImage.setImageDrawable(getResources().getDrawable(bindDrawable(departmentName)));
        }

        @Override
        public int getItemCount() {
            return departmentList.size();
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
                reserveViewModel.setDepartmentList(departments);
                ListAdapter adapter =new ListAdapter(departments);
                recyclerView.setAdapter(adapter);
            }else{
                Log.i("Reservation Fragment","Fetch department failed");
            }
        }

    }

    public boolean hasStarted(){
        return this.doctorReserveFragment!=null;
    }

    public DoctorReserveFragment getDoctorReserveFragment(){
        return this.doctorReserveFragment;
    }

    private void hide(FragmentTransaction ft){
        ft.hide(this);
    }

    public ReserveFragment returnThis(){
        return this;
    }

    private int bindDrawable(String departmentName){
        switch (departmentName){
            case  "神经外科":
                return R.drawable.sjwk;
            case "神经内科":
                return R.drawable.sjnk;
            case "妇产科":
                return R.drawable.fck;

            case "胸外科":
                return R.drawable.xwk;

            case "眼科":
                return R.drawable.yk;
                
            case "口腔科":
                return R.drawable.kqk;
                
            case "心血管科":
                return R.drawable.xxgk;
                
            case "耳鼻喉科":
                return R.drawable.erbh;
                
            case "骨科":
                return R.drawable.gk;
                
            case "放射科":
                return R.drawable.fsk;
                
            case "肿瘤科":
                return R.drawable.zlk;
                
            case "消化内科":
                return  R.drawable.xhnk;
                
            case "儿科":
                return R.drawable.erk;
                
            default:
                return R.drawable.others_department;

        }
    }

}
