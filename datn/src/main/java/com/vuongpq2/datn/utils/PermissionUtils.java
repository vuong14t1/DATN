package com.vuongpq2.datn.utils;

import com.vuongpq2.datn.data.Enum.Permission;

public class PermissionUtils {
    public static  boolean isCanViewPedigree (Permission permission) {
        return permission == Permission.MOD || permission == Permission.ADMIN || permission == Permission.MEMBER;
    }
}
