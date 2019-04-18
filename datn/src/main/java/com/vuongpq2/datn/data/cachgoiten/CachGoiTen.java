package com.vuongpq2.datn.data.cachgoiten;

import com.vuongpq2.datn.data.Enum.Relation;
import com.vuongpq2.datn.data.GioiTinh;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class CachGoiTen {
    private static CachGoiTen instance = null;
    public  HashMap<String,String> container;

    private CachGoiTen() {
        // Exists only to defeat instantiation.
        container = new HashMap<>();
        container.put(TenGoi.getKey(GioiTinh.NAM,0,true,false, true, Relation.ME),"Anh ruột");
        container.put(TenGoi.getKey(GioiTinh.NAM,0,false,false, true, Relation.ME),"Em trai ruột");

        container.put(TenGoi.getKey(GioiTinh.NAM,0,true,false, false, Relation.ME),"Anh họ");
        container.put(TenGoi.getKey(GioiTinh.NAM,0,false,false, false, Relation.ME),"Em họ");

        container.put(TenGoi.getKey(GioiTinh.NU,0,true,false, true, Relation.ME),"Chị ruột");
        container.put(TenGoi.getKey(GioiTinh.NU,0,false,false, true, Relation.ME),"Em gái ruột");

        container.put(TenGoi.getKey(GioiTinh.NU,0,true,false, false, Relation.ME),"Chị họ");
        container.put(TenGoi.getKey(GioiTinh.NU,0,false,false, false, Relation.ME),"Em họ");

        container.put(TenGoi.getKey(GioiTinh.NAM,0,true,true, false, Relation.VO),"Anh rể");
        container.put(TenGoi.getKey(GioiTinh.NAM,0,false,true, false, Relation.ME),"Em vợ");

        container.put(TenGoi.getKey(GioiTinh.NU,0,true,true, false, Relation.CHONG),"Chị dâu");
        container.put(TenGoi.getKey(GioiTinh.NU,0,false,true, false, Relation.ME),"Em chồng");

        container.put(TenGoi.getKey(GioiTinh.NAM,0,false,false,false, Relation.ME),"Chồng");
        container.put(TenGoi.getKey(GioiTinh.NU,0,false,false,false, Relation.VO),"Vợ");

        //TH me con/cha con thi them level + 1 vao
        container.put(TenGoi.getKey(GioiTinh.NAM,1,true,false,false, Relation.ME),"Cha");
        container.put(TenGoi.getKey(GioiTinh.NAM,1,false,false,false, Relation.ME),"Con");

        container.put(TenGoi.getKey(GioiTinh.NU,1,true,false,true, Relation.NONE),"Mẹ");
        container.put(TenGoi.getKey(GioiTinh.NU,1,false,false,true, Relation.NONE),"Con");

        container.put(TenGoi.getKey(GioiTinh.NAM,1,true,true,false, Relation.NONE),"Dượng");
        container.put(TenGoi.getKey(GioiTinh.NAM,1,false,true,false, Relation.NONE),"Cháu");

        // anh của ba mình
        container.put(TenGoi.getKey(GioiTinh.NAM,1,true,false, false, Relation.NONE, 1),"Bác");
        container.put(TenGoi.getKey(GioiTinh.NAM,1,false,false,false, Relation.NONE),"Cháu");

        container.put(TenGoi.getKey(GioiTinh.NAM,1,true,false, false, Relation.NONE, -1),"Chú");

        // vo cua bac la anh ba minh
        container.put(TenGoi.getKey(GioiTinh.NU,1,true,true,false, Relation.NONE, 1),"Thím");
        container.put(TenGoi.getKey(GioiTinh.NU,1,false,true,false, Relation.NONE),"Cháu");

        // em của me mình
        container.put(TenGoi.getKey(GioiTinh.NAM,1,true,false, false, Relation.NONE, -1),"Cậu");
        container.put(TenGoi.getKey(GioiTinh.NAM,1,false,false, false, Relation.NONE),"Cháu");

        //vợ của em ba mình
        container.put(TenGoi.getKey(GioiTinh.NU,1,true,true, false, Relation.NONE, -1),"Cô");
        container.put(TenGoi.getKey(GioiTinh.NU,1,false,true, false, Relation.NONE),"Cháu");


        container.put(TenGoi.getKey(GioiTinh.NU,1,true,false, false, Relation.NONE, -1),"Gì");
        container.put(TenGoi.getKey(GioiTinh.NU,1,false,false, false, Relation.NONE),"Cháu");

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

    public static void main(String[] args) {
//        System.out.println(CachGoiTen.getInstance().getName(GioiTinh.NAM,1,true,false));
//        System.out.println(CachGoiTen.getInstance().getName(GioiTinh.NAM,1,false,false,true));
//        System.out.println(CachGoiTen.getInstance().getName(GioiTinh.NAM,1,true,true,true));
        HashSet<String> test = new HashSet<>();
        int i = 0;
        Iterator<Map.Entry<String, String>> iterator = CachGoiTen.getInstance().container.entrySet().iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next().getKey());
            i ++;
            test.add(iterator.next().getKey());
        }
        System.out.println("total " + i + "| real " + test.size());
    }
}