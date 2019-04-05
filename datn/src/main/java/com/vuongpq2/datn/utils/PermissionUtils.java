package com.vuongpq2.datn.utils;

import com.vuongpq2.datn.data.Enum.Permission;

public class PermissionUtils {
    public static  boolean isCanViewPedigree (Permission permission) {
        return permission == Permission.MOD || permission == Permission.ADMIN || permission == Permission.MEMBER || permission == Permission.REGISTERED;
    }

    public static  boolean isCanEditPedigree (Permission permission) {
        return permission == Permission.MOD || permission == Permission.ADMIN;
    }

    public static boolean isCanAddMemberTree (Permission permission) {
        return permission == Permission.MOD || permission == Permission.ADMIN;
    }
}
