package com.vuongpq2.datn.data.model;

import java.util.ArrayList;
import java.util.List;

public class DInfoMerger {
    List<DMergerInfoPedigree> infoPedigreeList = new ArrayList<>();
    List<Integer> listChildIndex = new ArrayList<>();

    public List<DMergerInfoPedigree> getInfoPedigreeList() {
        return infoPedigreeList;
    }

    public void setInfoPedigreeList(List<DMergerInfoPedigree> infoPedigreeList) {
        this.infoPedigreeList = infoPedigreeList;
    }

    public List<Integer> getListChildIndex() {
        return listChildIndex;
    }

    public void setListChildIndex(List<Integer> listChildIndex) {
        this.listChildIndex = listChildIndex;
    }
}
