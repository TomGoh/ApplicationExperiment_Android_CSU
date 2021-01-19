package com.tom.patientservice.pojo;

import com.tom.patientservice.R;

import java.util.ArrayList;
import java.util.List;

public class ImageDataBean {

    public Integer imageRes;
    public String title;
    public int viewType;

    public ImageDataBean(Integer imageRes, String title, int viewType) {
        this.imageRes = imageRes;
        this.title = title;
        this.viewType = viewType;
    }

    public static List<ImageDataBean> getTestData() {
        List<ImageDataBean> list = new ArrayList<>();
        list.add(new ImageDataBean(R.drawable.background1, "Wish You Healthy!", 1));
        list.add(new ImageDataBean(R.drawable.background2, "Wish You Healthy!", 3));
        list.add(new ImageDataBean(R.drawable.background3, "Wish You Healthy!", 3));
        list.add(new ImageDataBean(R.drawable.background4, "Wish You Healthy!", 1));
        list.add(new ImageDataBean(R.drawable.background5, "Wish You Healthy!", 1));
        list.add(new ImageDataBean(R.drawable.background6, "Wish You Healthy!", 3));
        return list;
    }

}



