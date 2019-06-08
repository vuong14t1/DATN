package com.vuongpq2.datn.data.Enum;

import com.vuongpq2.datn.config.tree.Child;

import java.util.Comparator;

public class CompareNodeMember implements Comparator<Child> {


    @Override
    public int compare(Child o1, Child o2) {
        return o1.getChildrenDropLevel() < o2.getChildrenDropLevel() ? -1 : o1.getChildrenDropLevel() == o2.getChildrenDropLevel() ? 0 : 1;
    }

}
