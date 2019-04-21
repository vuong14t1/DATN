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
//        container.put(TenGoi.getKey(GioiTinh.NAM,0,false,false, true, Relation.ME),"Em ruột");

        container.put(TenGoi.getKey(GioiTinh.NAM,0,true,false, false, Relation.ME),"Anh họ - Em họ");
//        container.put(TenGoi.getKey(GioiTinh.NAM,0,false,false, false, Relation.ME),"Em họ");

        container.put(TenGoi.getKey(GioiTinh.NU,0,true,false, true, Relation.ME),"Chị ruột - Em ruột");
//        container.put(TenGoi.getKey(GioiTinh.NU,0,false,false, true, Relation.ME),"Em gái ruột");

        container.put(TenGoi.getKey(GioiTinh.NU,0,true,false, false, Relation.ME),"Chị họ - Em họ");
//        container.put(TenGoi.getKey(GioiTinh.NU,0,false,false, false, Relation.ME),"Em họ");

        container.put(TenGoi.getKey(GioiTinh.NAM,0,true,true, false, Relation.VO),"Anh rể - Em vợ");
//        container.put(TenGoi.getKey(GioiTinh.NAM,0,false,false, false, Relation.ME),"Em vợ");

        container.put(TenGoi.getKey(GioiTinh.NU,0,true,true, false, Relation.CHONG),"Chị dâu - Em chồng");
//        container.put(TenGoi.getKey(GioiTinh.NU,0,false,false, false, Relation.ME),"Em chồng");

        container.put(TenGoi.getKey(GioiTinh.NAM,0,false,false,false, Relation.ME),"Chồng - Vợ");
        container.put(TenGoi.getKey(GioiTinh.NU,0,false,true,false, Relation.VO),"Vợ - Chồng");
        container.put(TenGoi.getKey(GioiTinh.NAM,0,false,true,false, Relation.CHONG),"Chồng - Vợ");

        //TH me con/cha con thi them level + 1 vao
        container.put(TenGoi.getKey(GioiTinh.NAM,1,true,false,false, Relation.NONE, 0, Relation.CHA),"Cha");
        container.put(TenGoi.getKey(GioiTinh.NAM,1,true,false,false, Relation.VO, 0, Relation.ME),"Cha");

        container.put(TenGoi.getKey(GioiTinh.NAM,1,false,false,false, Relation.ME, 0, Relation.NONE),"Con");
        container.put(TenGoi.getKey(GioiTinh.NU,1,false,false,false, Relation.ME, 0, Relation.NONE),"Con");

        container.put(TenGoi.getKey(GioiTinh.NU,1,true,true,true, Relation.VO, 0, Relation.CHA),"Mẹ");
        container.put(TenGoi.getKey(GioiTinh.NU,1,true,true,true, Relation.NONE, 0, Relation.ME),"Mẹ");
        container.put(TenGoi.getKey(GioiTinh.NU,1,false,false,true, Relation.ME, 0, Relation.NONE),"Con");
        container.put(TenGoi.getKey(GioiTinh.NAM,1,false,false,true, Relation.ME, 0, Relation.NONE),"Con");

        container.put(TenGoi.getKey(GioiTinh.NAM,1,true,true,false, Relation.CHONG, 0, Relation.CHA),"Dượng");
        container.put(TenGoi.getKey(GioiTinh.NAM,1,true,true,false, Relation.CHONG, 0, Relation.ME),"Dượng");
        container.put(TenGoi.getKey(GioiTinh.NAM,1,false,true,false, Relation.NONE, 0, Relation.NONE),"Cháu");

        // anh của ba mình
        container.put(TenGoi.getKey(GioiTinh.NAM,1,true,false, false, Relation.ME, 1, Relation.CHA),"Bác");
        container.put(TenGoi.getKey(GioiTinh.NAM,1,false,false,false, Relation.NONE, 0, Relation.NONE),"Cháu");
        // vợ của bác
        container.put(TenGoi.getKey(GioiTinh.NU,1,true,true,false, Relation.VO, 1, Relation.CHA),"Bác gái");

        container.put(TenGoi.getKey(GioiTinh.NAM,1,true,false, false, Relation.ME, -1, Relation.CHA),"Chú");

        // là vợ của chú
        container.put(TenGoi.getKey(GioiTinh.NU,1,true,true,false, Relation.VO, -1, Relation.CHA),"Thím");
        container.put(TenGoi.getKey(GioiTinh.NU,1,false,true,false, Relation.NONE, 0, Relation.NONE),"Cháu");

        // em của me mình
        container.put(TenGoi.getKey(GioiTinh.NAM,1,true,false, false, Relation.ME, -1, Relation.ME),"Cậu");
        container.put(TenGoi.getKey(GioiTinh.NAM,1,false,false, false, Relation.NONE, 0, Relation.NONE),"Cháu");
        
        //vợ của cậu
        container.put(TenGoi.getKey(GioiTinh.NU,1,true,true, false, Relation.VO, -1, Relation.ME),"Mợ");


        //chị hoặc e gái của ba mình
        container.put(TenGoi.getKey(GioiTinh.NU,1,true,false, false, Relation.ME, -1, Relation.CHA),"Cô");
        container.put(TenGoi.getKey(GioiTinh.NU,1,true,false, false, Relation.ME, 1, Relation.CHA),"Cô");
        container.put(TenGoi.getKey(GioiTinh.NU,1,false,true, false, Relation.NONE, 0, Relation.NONE),"Cháu");

        //chị hoặc e gái của mẹ mình
        container.put(TenGoi.getKey(GioiTinh.NU,1,true,false, false, Relation.NONE, -1, Relation.ME),"Gì");
        container.put(TenGoi.getKey(GioiTinh.NU,1,true,false, false, Relation.NONE, 1, Relation.ME),"Gì");
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
    public static CachGoiTen getInstance() {
        if(instance == null) {
            instance = new CachGoiTen();
        }
        return instance;
    }
    public String getName(GioiTinh gender, int level,boolean isHigher, boolean isOutSide){
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

    public String getName (int gender, int level, boolean isHigher, boolean isOutSide, boolean isParent, int relation, int isHigherParent, Relation sideRelation) {
        if(relation < 0) relation = 0 ;
        return container.get(TenGoi.getKey(GioiTinh.values()[gender], level, isHigher, isOutSide, isParent, Relation.values()[relation], isHigherParent, sideRelation));
    }

    public static void main(String[] args) {
//        System.out.println(CachGoiTen.getInstance().getName(GioiTinh.NAM,1,true,false));
//        System.out.println(CachGoiTen.getInstance().getName(GioiTinh.NAM,1,false,false,true));
//        System.out.println(CachGoiTen.getInstance().getName(GioiTinh.NAM,1,true,true,true));

    }
}