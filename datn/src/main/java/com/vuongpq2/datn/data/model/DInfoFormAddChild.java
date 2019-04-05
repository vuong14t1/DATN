package com.vuongpq2.datn.data.model;

import java.util.ArrayList;
import java.util.List;

public class DInfoFormAddChild {
    private int id;
    private String name;
    private List<DHusbandOrWife> husbandOrWives;

    public DInfoFormAddChild() {
        husbandOrWives = new ArrayList<>();
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

    public List<DHusbandOrWife> getHusbandOrWives() {
        return husbandOrWives;
    }

    public void setHusbandOrWives(List<DHusbandOrWife> husbandOrWives) {
        this.husbandOrWives = husbandOrWives;
    }
}
