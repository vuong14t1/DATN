package com.vuongpq2.datn.data.model;

import java.util.ArrayList;
import java.util.List;

public class DInfoFormAddChild {
    private int id;
    private String name;
    private List<DHusbandOrWife> husbandOrWifes;
    private List<Integer> listChildIndex = new ArrayList<>();

    public DInfoFormAddChild() {
        husbandOrWifes = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DHusbandOrWife> getHusbandOrWifes() {
        return husbandOrWifes;
    }

    public void setHusbandOrWifes(List<DHusbandOrWife> husbandOrWifes) {
        this.husbandOrWifes = husbandOrWifes;
    }

    public List<Integer> getListChildIndex() {
        return listChildIndex;
    }

    public void setListChildIndex(List<Integer> listChildIndex) {
        this.listChildIndex = listChildIndex;
    }
}
