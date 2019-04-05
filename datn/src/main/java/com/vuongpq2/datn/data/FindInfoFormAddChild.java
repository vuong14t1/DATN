package com.vuongpq2.datn.data;
import java.util.ArrayList;
import java.util.List;

public class FindInfoFormAddChild {
    private long idParent;
    private String nameParent;
    private List<FindHusbandWife> findHusbandWifes;

    public FindInfoFormAddChild() {
        findHusbandWifes = new ArrayList<>();
    }

    public long getIdParent() {
        return idParent;
    }

    public void setIdParent(long idParent) {
        this.idParent = idParent;
    }

    public String getNameParent() {
        return nameParent;
    }

    public void setNameParent(String nameParent) {
        this.nameParent = nameParent;
    }

    public List<FindHusbandWife> getFindHusbandWifes() {
        return findHusbandWifes;
    }

    public void setFindHusbandWifes(List<FindHusbandWife> findHusbandWifes) {
        this.findHusbandWifes = findHusbandWifes;
    }
}
