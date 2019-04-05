//package com.vuongpq2.datn.data.cachgoiten;
//
//import com.vuongpq2.datn.data.GioiTinh;
//
//import java.util.HashMap;
//
//public class CachGoiTen {
//    private static CachGoiTen instance = null;
//    private  HashMap<String,String> container;
//
//    private CachGoiTen() {
//        // Exists only to defeat instantiation.
//        container = new HashMap<>();
//        container.put(TenGoi.getKey(GioiTinh.NAM,0,true,false),"Anh");
//        container.put(TenGoi.getKey(GioiTinh.NAM,0,false,false),"Em");
//        container.put(TenGoi.getKey(GioiTinh.NU,0,true,false),"Chị");
//        container.put(TenGoi.getKey(GioiTinh.NU,0,false,false),"Em");
//        container.put(TenGoi.getKey(GioiTinh.NAM,0,true,true),"Anh");
//        container.put(TenGoi.getKey(GioiTinh.NAM,0,false,true),"Em");
//        container.put(TenGoi.getKey(GioiTinh.NU,0,true,true),"Chị");
//        container.put(TenGoi.getKey(GioiTinh.NU,0,false,true),"Em");
//
//        container.put(TenGoi.getKey(GioiTinh.NAM,0,false,false,true),"Chồng");
//        container.put(TenGoi.getKey(GioiTinh.NU,0,false,false,true),"Vợ");
//
//        container.put(TenGoi.getKey(GioiTinh.NAM,1,true,false,true),"Cha");
//        container.put(TenGoi.getKey(GioiTinh.NAM,1,false,false,true),"Con");
//
//        container.put(TenGoi.getKey(GioiTinh.NU,1,true,false,true),"Mẹ");
//        container.put(TenGoi.getKey(GioiTinh.NU,1,false,false,true),"Con");
//
//        container.put(TenGoi.getKey(GioiTinh.NAM,1,true,true,true),"Dượng");
//        container.put(TenGoi.getKey(GioiTinh.NAM,1,false,true,true),"Cháu");
//        container.put(TenGoi.getKey(GioiTinh.NU,1,true,true,true),"Thiếm");
//        container.put(TenGoi.getKey(GioiTinh.NU,1,false,true,true),"Cháu");
//
//        container.put(TenGoi.getKey(GioiTinh.NAM,1,true,false),"Bác");
//        container.put(TenGoi.getKey(GioiTinh.NAM,1,false,false),"Cháu");
//        container.put(TenGoi.getKey(GioiTinh.NU,1,true,false),"Cô");
//        container.put(TenGoi.getKey(GioiTinh.NU,1,false,false),"Cháu");
//        container.put(TenGoi.getKey(GioiTinh.NAM,1,true,true),"Cậu");
//        container.put(TenGoi.getKey(GioiTinh.NAM,1,false,true),"Cháu");
//        container.put(TenGoi.getKey(GioiTinh.NU,1,true,true),"Gì");
//        container.put(TenGoi.getKey(GioiTinh.NU,1,false,true),"Cháu");
//
//        container.put(TenGoi.getKey(GioiTinh.NAM,2,true,false),"Ông Nội");
//        container.put(TenGoi.getKey(GioiTinh.NAM,2,false,false),"Cháu");
//        container.put(TenGoi.getKey(GioiTinh.NU,2,true,false),"Bà nội");
//        container.put(TenGoi.getKey(GioiTinh.NU,2,false,false),"Cháu");
//        container.put(TenGoi.getKey(GioiTinh.NAM,2,true,true),"Ông ngoại");
//        container.put(TenGoi.getKey(GioiTinh.NAM,2,false,true),"Cháu");
//        container.put(TenGoi.getKey(GioiTinh.NU,2,true,true),"Bà ngoại");
//        container.put(TenGoi.getKey(GioiTinh.NU,2,false,true),"Cháu");
//
//        container.put(TenGoi.getKey(GioiTinh.NAM,3,true,false),"Cụ ông");
//        container.put(TenGoi.getKey(GioiTinh.NAM,3,false,false),"Chắt");
//        container.put(TenGoi.getKey(GioiTinh.NU,3,true,false),"Cụ bà");
//        container.put(TenGoi.getKey(GioiTinh.NU,3,false,false),"Chắt");
//        container.put(TenGoi.getKey(GioiTinh.NAM,3,true,true),"Cụ ông");
//        container.put(TenGoi.getKey(GioiTinh.NAM,3,false,true),"Chắt");
//        container.put(TenGoi.getKey(GioiTinh.NU,3,true,true),"Cụ bà");
//        container.put(TenGoi.getKey(GioiTinh.NU,3,false,true),"Chắt");
//
//        container.put(TenGoi.getKey(GioiTinh.NAM,4,true,false),"Kỵ ông");
//        container.put(TenGoi.getKey(GioiTinh.NAM,4,false,false),"Chút");
//        container.put(TenGoi.getKey(GioiTinh.NU,4,true,false),"Kỵ bà");
//        container.put(TenGoi.getKey(GioiTinh.NU,4,false,false),"Chút");
//        container.put(TenGoi.getKey(GioiTinh.NAM,4,true,true),"Kỵ ông");
//        container.put(TenGoi.getKey(GioiTinh.NAM,4,false,true),"Chút");
//        container.put(TenGoi.getKey(GioiTinh.NU,4,true,true),"Kỵ bà");
//        container.put(TenGoi.getKey(GioiTinh.NU,4,false,true),"Chút");
//
//        container.put(TenGoi.getKey(GioiTinh.NAM,5,true,false),"Tổ tiên");
//        container.put(TenGoi.getKey(GioiTinh.NAM,5,false,false),"Chít");
//        container.put(TenGoi.getKey(GioiTinh.NU,5,true,false),"Tổ tiên");
//        container.put(TenGoi.getKey(GioiTinh.NU,5,false,false),"Chít");
//        container.put(TenGoi.getKey(GioiTinh.NAM,5,true,true),"Tổ tiên");
//        container.put(TenGoi.getKey(GioiTinh.NAM,5,false,true),"Chít");
//        container.put(TenGoi.getKey(GioiTinh.NU,5,true,true),"Tổ tiên");
//        container.put(TenGoi.getKey(GioiTinh.NU,5,false,true),"Chít");
//    }
//    public static CachGoiTen getInstance() {
//        if(instance == null) {
//            instance = new CachGoiTen();
//        }
//        return instance;
//    }
//    public String getName(GioiTinh gender, int level,boolean isHigher, boolean isOutSide){
//        return container.get(TenGoi.getKey(gender, level, isHigher, isOutSide));
//    }
//    public String getName(int gender, int level,boolean isHigher, boolean isOutSide){
//        return container.get(TenGoi.getKey(gender, level, isHigher, isOutSide));
//    }
//
//    public String getName(GioiTinh gender, int level,boolean isHigher, boolean isOutSide,boolean isParent){
//        return container.get(TenGoi.getKey(gender, level, isHigher, isOutSide,isParent));
//    }
//    public String getName(int gender, int level,boolean isHigher, boolean isOutSide,boolean isParent){
//        return container.get(TenGoi.getKey(gender, level, isHigher, isOutSide,isParent));
//    }
//
//    public static void main(String[] args) {
//        System.out.println(CachGoiTen.getInstance().getName(GioiTinh.NAM,1,true,false));
//        System.out.println(CachGoiTen.getInstance().getName(GioiTinh.NAM,1,false,false,true));
//        System.out.println(CachGoiTen.getInstance().getName(GioiTinh.NAM,1,true,true,true));
//    }
//}