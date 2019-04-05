package com.vuongpq2.datn.data.cachgoiten;

import com.vuongpq2.datn.data.GioiTinh;

public class TenGoi {
    private GioiTinh gender;
    private int level;

    private boolean isHigher;
    private boolean outSide;
    private String name;

    public TenGoi(GioiTinh gender, int level, boolean isHigher, boolean outSide, String name) {
        this.gender = gender;
        this.level = level;
        this.isHigher = isHigher;
        this.outSide = outSide;
        this.name = name;
    }

    public GioiTinh getGender() {
        return gender;
    }

    public void setGender(GioiTinh gender) {
        this.gender = gender;
    }

    public void setGender(int gender) {
        this.gender = GioiTinh.values()[gender];
    }

    public boolean isHigher() {
        return isHigher;
    }

    public void setHigher(boolean higher) {
        isHigher = higher;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isOutSide() {
        return outSide;
    }

    public void setOutSide(boolean outSide) {
        this.outSide = outSide;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getKey(){
        return getKey(gender,level,isHigher,outSide);
    }
    public static String getKey(GioiTinh gioiTinh,int level,boolean isHigher,boolean outSide){
        return getKey(gioiTinh,level,isHigher,outSide,false);
    }

    public static String getKey(GioiTinh gioiTinh,int level,boolean isHigher,boolean outSide, boolean isParent){
        if(level > 5 ) level = 5;
        return gioiTinh.name() + level + isHigher + outSide + isParent;
    }

    public static String getKey(int gioiTinh,int level,boolean isHigher,boolean outSide, boolean isParent){
        return getKey(GioiTinh.values()[gioiTinh],level,isHigher,outSide,isParent);

    }

    public static String getKey(int gioiTinh,int level,boolean isHigher,boolean outSide){
        return getKey(GioiTinh.values()[gioiTinh],level,isHigher,outSide);
    }
}
