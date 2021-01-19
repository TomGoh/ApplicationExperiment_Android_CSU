package com.tom.patientservice.pojo;

import com.tom.patientservice.R;

import java.util.ArrayList;
import java.util.List;

public class ImageDataBean2 {

    public Integer imageRes;
    public String title;
    public int viewType;

    public ImageDataBean2(Integer imageRes, String title, int viewType) {
        this.imageRes = imageRes;
        this.title = title;
        this.viewType = viewType;
    }

    public static List<ImageDataBean> getTestData() {
        List<ImageDataBean> list = new ArrayList<>();
        list.add(new ImageDataBean(R.drawable.bg1, "Wish You Healthy!", 1));
        list.add(new ImageDataBean(R.drawable.bg2, "Wish You Healthy!", 3));
        list.add(new ImageDataBean(R.drawable.bg3, "Wish You Healthy!", 3));
        list.add(new ImageDataBean(R.drawable.bg4, "Wish You Healthy!", 1));
        return list;
    }

}



