package com.vuongpq2.datn.data.cachgoiten;

import com.vuongpq2.datn.data.Enum.Relation;
import com.vuongpq2.datn.data.GioiTinh;

import java.util.*;

public class CachGoiTen {
    private static CachGoiTen instance = null;
    public  HashMap<String,String> container;
//    public ArrayList<String> container = new ArrayList<>();

    private CachGoiTen() {
        // Exists only to defeat instantiation.
        container = new HashMap<>();
        container.put(TenGoi.getKey(GioiTinh.NAM,0,true,false, true, Relation.ME),"Anh ruột - Em ruột");
        container.put(TenGoi.getKey(GioiTinh.NAM,0,false,false, true, Relation.ME),"Em ruột");

        container.put(TenGoi.getKey(GioiTinh.NAM,0,true,false, false, Relation.ME),"Anh họ - Em họ");
        container.put(TenGoi.getKey(GioiTinh.NAM,0,false,false, false, Relation.ME),"Em họ");

        container.put(TenGoi.getKey(GioiTinh.NU,0,true,false, true, Relation.ME),"Chị ruột - Em gái ruột");
        container.put(TenGoi.getKey(GioiTinh.NU,0,false,false, true, Relation.ME),"Em gái ruột");

        container.put(TenGoi.getKey(GioiTinh.NU,0,true,false, false, Relation.ME),"Chị họ - Em họ");
        container.put(TenGoi.getKey(GioiTinh.NU,0,false,false, false, Relation.ME),"Em họ");

        container.put(TenGoi.getKey(GioiTinh.NAM,0,true,true, false, Relation.VO),"Anh rể - Em vợ");
        container.put(TenGoi.getKey(GioiTinh.NAM,0,false,false, false, Relation.ME),"Em vợ");

        container.put(TenGoi.getKey(GioiTinh.NU,0,true,true, false, Relation.CHONG),"Chị dâu - Em chồng");
        container.put(TenGoi.getKey(GioiTinh.NU,0,false,false, false, Relation.ME),"Em chồng");

        container.put(TenGoi.getKey(GioiTinh.NAM,0,false,false,false, Relation.ME),"Chồng - Vợ");
        container.put(TenGoi.getKey(GioiTinh.NU,0,false,false,false, Relation.VO),"Vợ");

        //TH me con/cha con thi them level + 1 vao
        container.put(TenGoi.getKey(GioiTinh.NAM,1,true,false,false, Relation.NONE, 0, Relation.NONE),"Cha");
        container.put(TenGoi.getKey(GioiTinh.NAM,1,true,false,false, Relation.VO, 0, Relation.NONE),"Cha");

        container.put(TenGoi.getKey(GioiTinh.NAM,1,false,false,false, Relation.ME, 0, Relation.NONE),"Con");
        container.put(TenGoi.getKey(GioiTinh.NU,1,false,false,false, Relation.ME, 0, Relation.NONE),"Con");

        container.put(TenGoi.getKey(GioiTinh.NU,1,true,true,true, Relation.VO, 0, Relation.NONE),"Mẹ");
        container.put(TenGoi.getKey(GioiTinh.NU,1,true,true,true, Relation.NONE, 0, Relation.NONE),"Mẹ");
        container.put(TenGoi.getKey(GioiTinh.NU,1,false,false,true, Relation.ME, 0, Relation.NONE),"Con");
        container.put(TenGoi.getKey(GioiTinh.NAM,1,false,false,true, Relation.ME, 0, Relation.NONE),"Con");

        container.put(TenGoi.getKey(GioiTinh.NAM,1,true,true,false, Relation.NONE, 0, Relation.NONE),"Dượng");
        container.put(TenGoi.getKey(GioiTinh.NAM,1,false,true,false, Relation.NONE, 0, Relation.NONE),"Cháu");

        // anh của ba mình
        container.put(TenGoi.getKey(GioiTinh.NAM,1,true,false, false, Relation.NONE, 1, Relation.NONE),"Bác");
        container.put(TenGoi.getKey(GioiTinh.NAM,1,false,false,false, Relation.NONE, 0, Relation.NONE),"Cháu");

        container.put(TenGoi.getKey(GioiTinh.NAM,1,true,false, false, Relation.NONE, -1, Relation.CHA),"Chú");

        // vo cua bac la anh ba minh
        container.put(TenGoi.getKey(GioiTinh.NU,1,true,true,false, Relation.NONE, 1, Relation.NONE),"Thím");
        container.put(TenGoi.getKey(GioiTinh.NU,1,false,true,false, Relation.NONE, 0, Relation.NONE),"Cháu");

        // em của me mình
        container.put(TenGoi.getKey(GioiTinh.NAM,1,true,false, false, Relation.NONE, -1, Relation.ME),"Cậu");
        container.put(TenGoi.getKey(GioiTinh.NAM,1,false,false, false, Relation.NONE, 0, Relation.NONE),"Cháu");

        //vợ của em ba mình
        container.put(TenGoi.getKey(GioiTinh.NU,1,true,true, false, Relation.NONE, -1, Relation.NONE),"Cô");
        container.put(TenGoi.getKey(GioiTinh.NU,1,false,true, false, Relation.NONE, 0, Relation.NONE),"Cháu");


        container.put(TenGoi.getKey(GioiTinh.NU,1,true,false, false, Relation.NONE, -1, Relation.NONE),"Gì");
        container.put(TenGoi.getKey(GioiTinh.NU,1,false,false, false, Relation.NONE, 0, Relation.NONE),"Cháu");

        container.put(TenGoi.getKey(GioiTinh.NAM,2,true,false, false, Relation.NONE),"Ông Nội");
        container.put(TenGoi.getKey(GioiTinh.NAM,2,false,false, false, Relation.NONE),"Cháu");
        container.put(TenGoi.getKey(GioiTinh.NU,2,true,false, false, Relation.NONE),"Bà nội");
        container.put(TenGoi.getKey(GioiTinh.NU,2,false,false, false, Relation.NONE),"Cháu");
        container.put(TenGoi.getKey(GioiTinh.NAM,2,true,true, false, Relation.NONE),"Ông ngoại");
        container.put(TenGoi.getKey(GioiTinh.NAM,2,false,true, false, Relation.NONE),"Cháu");
        container.put(TenGoi.getKey(GioiTinh.NU,2,true,true, false, Relation.NONE),"Bà ngoại");
        container.put(TenGoi.getKey(GioiTinh.NU,2,false,true, false, Relation.NONE),"Cháu");

        container.put(TenGoi.getKey(GioiTinh.NAM,3,true,false, false, Relation.NONE),"Cụ ông");
        container.put(TenGoi.getKey(GioiTinh.NAM,3,false,false, false, Relation.NONE),"Chắt");
        container.put(TenGoi.getKey(GioiTinh.NU,3,true,false, false, Relation.NONE),"Cụ bà");
        container.put(TenGoi.getKey(GioiTinh.NU,3,false,false, false, Relation.NONE),"Chắt");
        container.put(TenGoi.getKey(GioiTinh.NAM,3,true,true, false, Relation.NONE),"Cụ ông");
        container.put(TenGoi.getKey(GioiTinh.NAM,3,false,true, false, Relation.NONE),"Chắt");
        container.put(TenGoi.getKey(GioiTinh.NU,3,true,true, false, Relation.NONE),"Cụ bà");
        container.put(TenGoi.getKey(GioiTinh.NU,3,false,true, false, Relation.NONE),"Chắt");

        container.put(TenGoi.getKey(GioiTinh.NAM,4,true,false, false, Relation.NONE),"Kỵ ông");
        container.put(TenGoi.getKey(GioiTinh.NAM,4,false,false, false, Relation.NONE),"Chút");
        container.put(TenGoi.getKey(GioiTinh.NU,4,true,false, false, Relation.NONE),"Kỵ bà");
        container.put(TenGoi.getKey(GioiTinh.NU,4,false,false, false, Relation.NONE),"Chút");
        container.put(TenGoi.getKey(GioiTinh.NAM,4,true,true, false, Relation.NONE),"Kỵ ông");
        container.put(TenGoi.getKey(GioiTinh.NAM,4,false,true, false, Relation.NONE),"Chút");
        container.put(TenGoi.getKey(GioiTinh.NU,4,true,true, false, Relation.NONE),"Kỵ bà");
        container.put(TenGoi.getKey(GioiTinh.NU,4,false,true, false, Relation.NONE),"Chút");

        container.put(TenGoi.getKey(GioiTinh.NAM,5,true,false, false, Relation.NONE),"Tổ tiên");
        container.put(TenGoi.getKey(GioiTinh.NAM,5,false,false, false, Relation.NONE),"Chít");
        container.put(TenGoi.getKey(GioiTinh.NU,5,true,false, false, Relation.NONE),"Tổ tiên");
        container.put(TenGoi.getKey(GioiTinh.NU,5,false,false, false, Relation.NONE),"Chít");
        container.put(TenGoi.getKey(GioiTinh.NAM,5,true,true, false, Relation.NONE),"Tổ tiên");
        container.put(TenGoi.getKey(GioiTinh.NAM,5,false,true, false, Relation.NONE),"Chít");
        container.put(TenGoi.getKey(GioiTinh.NU,5,true,true, false, Relation.NONE),"Tổ tiên");
        container.put(TenGoi.getKey(GioiTinh.NU,5,false,true, false, Relation.NONE),"Chít");
    }
    /*private CachGoiTen() {
    // Exists only to defeat instantiation.
    container.add(TenGoi.getKey(GioiTinh.NAM,0,true,false, true, Relation.ME));
    container.add(TenGoi.getKey(GioiTinh.NAM,0,false,false, true, Relation.ME));

    container.add(TenGoi.getKey(GioiTinh.NAM,0,true,false, false, Relation.ME));
    container.add(TenGoi.getKey(GioiTinh.NAM,0,false,false, false, Relation.ME));

    container.add(TenGoi.getKey(GioiTinh.NU,0,true,false, true, Relation.ME));
    container.add(TenGoi.getKey(GioiTinh.NU,0,false,false, true, Relation.ME));

    container.add(TenGoi.getKey(GioiTinh.NU,0,true,false, false, Relation.ME));
    container.add(TenGoi.getKey(GioiTinh.NU,0,false,false, false, Relation.ME));

    container.add(TenGoi.getKey(GioiTinh.NAM,0,true,true, false, Relation.VO));
    container.add(TenGoi.getKey(GioiTinh.NAM,0,false,false, false, Relation.ME));

    container.add(TenGoi.getKey(GioiTinh.NU,0,true,true, false, Relation.CHONG));
    container.add(TenGoi.getKey(GioiTinh.NU,0,false,false, false, Relation.ME));

    container.add(TenGoi.getKey(GioiTinh.NAM,0,false,false,false, Relation.ME));
    container.add(TenGoi.getKey(GioiTinh.NU,0,false,false,false, Relation.VO));

    //TH me con/cha con thi them level + 1 vao
    container.add(TenGoi.getKey(GioiTinh.NAM,1,true,false,false, Relation.NONE));
    container.add(TenGoi.getKey(GioiTinh.NAM,1,true,false,false, Relation.VO));

    container.add(TenGoi.getKey(GioiTinh.NAM,1,false,false,false, Relation.ME));
    container.add(TenGoi.getKey(GioiTinh.NU,1,false,false,false, Relation.ME));

    container.add(TenGoi.getKey(GioiTinh.NU,1,true,true,true, Relation.VO));
    container.add(TenGoi.getKey(GioiTinh.NU,1,true,true,true, Relation.NONE));
    container.add(TenGoi.getKey(GioiTinh.NU,1,false,false,true, Relation.ME));
    container.add(TenGoi.getKey(GioiTinh.NAM,1,false,false,true, Relation.ME));

    container.add(TenGoi.getKey(GioiTinh.NAM,1,true,true,false, Relation.NONE));
    container.add(TenGoi.getKey(GioiTinh.NAM,1,false,true,false, Relation.NONE));

    // anh của ba mình
    container.add(TenGoi.getKey(GioiTinh.NAM,1,true,false, false, Relation.NONE, 1));
    container.add(TenGoi.getKey(GioiTinh.NAM,1,false,false,false, Relation.NONE));

    container.add(TenGoi.getKey(GioiTinh.NAM,1,true,false, false, Relation.NONE, -1));

    // vo cua bac la anh ba minh
    container.add(TenGoi.getKey(GioiTinh.NU,1,true,true,false, Relation.NONE, 1));
    container.add(TenGoi.getKey(GioiTinh.NU,1,false,true,false, Relation.NONE));

    // em của me mình
    container.add(TenGoi.getKey(GioiTinh.NAM,1,true,false, false, Relation.NONE, -1));
    container.add(TenGoi.getKey(GioiTinh.NAM,1,false,false, false, Relation.NONE));

    //vợ của em ba mình
    container.add(TenGoi.getKey(GioiTinh.NU,1,true,true, false, Relation.NONE, -1));
    container.add(TenGoi.getKey(GioiTinh.NU,1,false,true, false, Relation.NONE));


    container.add(TenGoi.getKey(GioiTinh.NU,1,true,false, false, Relation.NONE, -1));
    container.add(TenGoi.getKey(GioiTinh.NU,1,false,false, false, Relation.NONE));

    container.add(TenGoi.getKey(GioiTinh.NAM,2,true,false, false, Relation.NONE));
    container.add(TenGoi.getKey(GioiTinh.NAM,2,false,false, false, Relation.NONE));
    container.add(TenGoi.getKey(GioiTinh.NU,2,true,false, false, Relation.NONE));
    container.add(TenGoi.getKey(GioiTinh.NU,2,false,false, false, Relation.NONE));
    container.add(TenGoi.getKey(GioiTinh.NAM,2,true,true, false, Relation.NONE));
    container.add(TenGoi.getKey(GioiTinh.NAM,2,false,true, false, Relation.NONE));
    container.add(TenGoi.getKey(GioiTinh.NU,2,true,true, false, Relation.NONE));
    container.add(TenGoi.getKey(GioiTinh.NU,2,false,true, false, Relation.NONE));

    container.add(TenGoi.getKey(GioiTinh.NAM,3,true,false, false, Relation.NONE));
    container.add(TenGoi.getKey(GioiTinh.NAM,3,false,false, false, Relation.NONE));
    container.add(TenGoi.getKey(GioiTinh.NU,3,true,false, false, Relation.NONE));
    container.add(TenGoi.getKey(GioiTinh.NU,3,false,false, false, Relation.NONE));
    container.add(TenGoi.getKey(GioiTinh.NAM,3,true,true, false, Relation.NONE));
    container.add(TenGoi.getKey(GioiTinh.NAM,3,false,true, false, Relation.NONE));
    container.add(TenGoi.getKey(GioiTinh.NU,3,true,true, false, Relation.NONE));
    container.add(TenGoi.getKey(GioiTinh.NU,3,false,true, false, Relation.NONE));

    container.add(TenGoi.getKey(GioiTinh.NAM,4,true,false, false, Relation.NONE));
    container.add(TenGoi.getKey(GioiTinh.NAM,4,false,false, false, Relation.NONE));
    container.add(TenGoi.getKey(GioiTinh.NU,4,true,false, false, Relation.NONE));
    container.add(TenGoi.getKey(GioiTinh.NU,4,false,false, false, Relation.NONE));
    container.add(TenGoi.getKey(GioiTinh.NAM,4,true,true, false, Relation.NONE));
    container.add(TenGoi.getKey(GioiTinh.NAM,4,false,true, false, Relation.NONE));
    container.add(TenGoi.getKey(GioiTinh.NU,4,true,true, false, Relation.NONE));
    container.add(TenGoi.getKey(GioiTinh.NU,4,false,true, false, Relation.NONE));

    container.add(TenGoi.getKey(GioiTinh.NAM,5,true,false, false, Relation.NONE));
    container.add(TenGoi.getKey(GioiTinh.NAM,5,false,false, false, Relation.NONE));
    container.add(TenGoi.getKey(GioiTinh.NU,5,true,false, false, Relation.NONE));
    container.add(TenGoi.getKey(GioiTinh.NU,5,false,false, false, Relation.NONE));
    container.add(TenGoi.getKey(GioiTinh.NAM,5,true,true, false, Relation.NONE));
    container.add(TenGoi.getKey(GioiTinh.NAM,5,false,true, false, Relation.NONE));
    container.add(TenGoi.getKey(GioiTinh.NU,5,true,true, false, Relation.NONE));
    container.add(TenGoi.getKey(GioiTinh.NU,5,false,true, false, Relation.NONE));
}*/
    public static CachGoiTen getInstance() {
        if(instance == null) {
            instance = new CachGoiTen();
        }
        return instance;
    }
    /*public String getName(GioiTinh gender, int level,boolean isHigher, boolean isOutSide){
        return container.get(TenGoi.getKey(gender, level, isHigher, isOutSide));
    }
    public String getName(int gender, int level,boolean isHigher, boolean isOutSide){
        return container.get(TenGoi.getKey(gender, level, isHigher, isOutSide));
    }

    public String getName(GioiTinh gender, int level,boolean isHigher, boolean isOutSide,boolean isParent){
        return container.get(TenGoi.getKey(gender, level, isHigher, isOutSide,isParent));
    }
    public String getName(int gender, int level,boolean isHigher, boolean isOutSide,boolean isParent){
        return container.get(TenGoi.getKey(gender, level, isHigher, isOutSide,isParent));
    }

    public String getName (int gender, int level, boolean isHigher, boolean isOutSide, boolean isParent, int relation, int isHigherParent) {
        if(relation < 0) relation = 0 ;
        return container.get(TenGoi.getKey(GioiTinh.values()[gender], level, isHigher, isOutSide, isParent, Relation.values()[relation], isHigherParent));
    }*/

    public static void main(String[] args) {
//        System.out.println(CachGoiTen.getInstance().getName(GioiTinh.NAM,1,true,false));
//        System.out.println(CachGoiTen.getInstance().getName(GioiTinh.NAM,1,false,false,true));
//        System.out.println(CachGoiTen.getInstance().getName(GioiTinh.NAM,1,true,true,true));
        HashSet<String> test1 = new HashSet<String>();
        int i = 0;
        for(int i1 = 0; i1 < CachGoiTen.getInstance().container.size(); i1++) {
//            System.out.println(i + "|" + key);
            for(int i2 = 0; i2 < CachGoiTen.getInstance().container.size(); i2++) {
                if(CachGoiTen.getInstance().container.get(i1).equals(CachGoiTen.getInstance().container.get(i2)) && i1 != i2) {
                    System.out.println("key trung " + CachGoiTen.getInstance().container.get(i1) + "|" + i1);
                    i++;
                    break;
                }
            }
        }
        System.out.println("total " + i + "| real " + test1.size());
    }
}