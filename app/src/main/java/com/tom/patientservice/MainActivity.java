package com.tom.patientservice;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.tom.patientservice.UI.Home.HomeFragment;
import com.tom.patientservice.UI.Profile.ProfileFragment;
import com.tom.patientservice.UI.Profile.ProfileViewModel;
import com.tom.patientservice.UI.Reserve.ReserveFragment;
import com.tom.patientservice.UI.Reserve.ReserveViewModel;
import com.tom.patientservice.pojo.Patient;
import com.tom.patientservice.utils.FileManagement;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {

    private RadioGroup mRgGroup;
    private FragmentManager fragmentManager;
    ProfileViewModel profileViewModel;


    private static final String[] FRAGMENT_TAG = {"tab_home","tab_reserve", "tab_profile"};

    private final int show_tab_car = 1;
    private final int show_tab_map = 0;
    private final int show_tab_find = 2;
    private int mrIndex = show_tab_map;

    private int index = -100;
    private boolean hasChanged=false;

    private static final String PRV_SELINDEX = "PREV_SELINDEX";
    private ProfileFragment profileFragment;
    private ReserveFragment reserveFragment;
    private HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setContentView(R.layout.activity_main);
        profileViewModel=new ViewModelProvider(this).get(ProfileViewModel.class);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Patient patient= FileManagement.loadCurrentPatient(this);
        if(patient!=null){
            profileViewModel.setCurrentPatient(patient);
        }
        fragmentManager = getSupportFragmentManager();
        
        if (savedInstanceState != null) {

            mrIndex = savedInstanceState.getInt(PRV_SELINDEX, mrIndex);
            profileFragment = (ProfileFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG[2]);
           reserveFragment = (ReserveFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG[1]);
            homeFragment = (HomeFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG[0]);
        }

        if(!isNetWorkAvailable(this)){
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("无法连接服务器")
                    .setContentText("请检查网络连接！")
                    .show();
        }
        initView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(PRV_SELINDEX, mrIndex);
        super.onSaveInstanceState(outState);
    }
    protected void initView() {

        mRgGroup = findViewById(R.id.rg_group);

        setTabSelection(show_tab_map);
        mRgGroup.clearCheck();

        mRgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_reserve:
                        setTabSelection(show_tab_car);
                        mRgGroup.clearCheck();
                        break;
                    case R.id.rb_home:
                        setTabSelection(show_tab_map);

                        mRgGroup.clearCheck();
                        break;
                    case R.id.rb_profile:
                        setTabSelection(show_tab_find);

                        mRgGroup.clearCheck();
                        break;
                    default:
                        break;
                }
            }
        });

    }

    /**
     * 根据传入的index参数来设置选中的tab页。
     *
     * @param id 传入的选择的fragment
     */
    private void setTabSelection(int id) {    //根据传入的index参数来设置选中的tab页。
        ReserveViewModel reserveViewModel=new ViewModelProvider(this).get(ReserveViewModel.class);
        ProfileViewModel profileViewModel=new ViewModelProvider(this).get(ProfileViewModel.class);
        if (id == index && reserveViewModel.getChooseDepartment()==null && profileFragment==null &&homeFragment.getDepartmentIntroFragment()==null) {
            return;
        }

        index = id;

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        hideFragments(transaction);
        if(reserveFragment!=null&&reserveFragment.hasStarted()&&reserveFragment.getDoctorReserveFragment()!=null){
            if(reserveFragment.getDoctorReserveFragment().getDateReserveFragment()!=null){
                transaction.remove(reserveFragment.getDoctorReserveFragment().getDateReserveFragment());
            }
            transaction.remove(reserveFragment.getDoctorReserveFragment());
        }
        if(!profileViewModel.getIslogined()&&profileFragment!=null&&profileFragment.getLoginFragment()!=null){
            transaction.remove(profileFragment.getLoginFragment());
            if(profileFragment.getLoginFragment().getSignUpFragment()!=null){
                transaction.remove(profileFragment.getLoginFragment().getSignUpFragment());
            }
        }

        if(profileViewModel.getIsReservationOn()&&profileFragment!=null&&profileFragment.getMyReservationFragment()!=null){
            transaction.remove(profileFragment.getMyReservationFragment());
        }

        if(profileFragment!=null && profileFragment.getBalanceFragment()!=null){
            transaction.remove(profileFragment.getBalanceFragment());
        }
        if(profileFragment!=null&&profileFragment.getInforFragment()!=null){
            transaction.remove(profileFragment.getInforFragment());
        }
        if(profileFragment!=null&&profileFragment.getChangePasswordFragment()!=null){
            transaction.remove(profileFragment.getChangePasswordFragment());
        }
        if(homeFragment!=null&&homeFragment.getDepartmentIntroFragment()!=null){
            transaction.remove(homeFragment.getDepartmentIntroFragment());
        }

        switch (index) {
            case show_tab_find:
                mRgGroup.check(R.id.rb_profile);
                if (profileFragment == null) {
                    profileFragment = new ProfileFragment();
                    transaction.add(R.id.fl_container, profileFragment, FRAGMENT_TAG[index]);
                } else {
                    if(!profileViewModel.getIslogined()&&profileFragment.getLoginFragment()!=null){
                        transaction.remove(profileFragment.getLoginFragment());
                    }
                    transaction.show(profileFragment);
                }
                transaction.commit();
                break;
            case show_tab_car:
                mRgGroup.check(R.id.rb_reserve);
                if (reserveFragment == null) {
                    reserveFragment = new ReserveFragment();
                    transaction.add(R.id.fl_container, reserveFragment, FRAGMENT_TAG[index]);
                } else {
                    if(reserveFragment.hasStarted()){
                        if(reserveFragment.getDoctorReserveFragment().getDateReserveFragment()!=null){
                            transaction.remove(reserveFragment.getDoctorReserveFragment().getDateReserveFragment());
                        }
                        transaction.remove(reserveFragment.getDoctorReserveFragment());
                    }
                    transaction.show(reserveFragment);
                }
                transaction.commit();
                if(profileViewModel.getCurrentPatient()==null){
                    new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("请先登录")
                            .setContentText("请登陆后查看详情")
                            .show();
                    return;
                }
                break;
            case show_tab_map:
                mRgGroup.check(R.id.rb_home);
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.fl_container, homeFragment, FRAGMENT_TAG[index]);
                } else {
                    transaction.show(homeFragment);
                }
                transaction.commit();
                break;
            default:
                break;
        }
    }

    /**
     * 隐藏fragment
     *
     * @param transaction
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (profileFragment != null) {
            transaction.hide(profileFragment);
        }
        if (reserveFragment != null) {
            transaction.hide(reserveFragment);
        }
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }

    }

    public static boolean isNetWorkAvailable(Context context){
        boolean isAvailable = false ;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isAvailable()){
            isAvailable = true;
        }
        return isAvailable;
    }


}