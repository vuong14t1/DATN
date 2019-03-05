package com.example.genealogy.data.Enum;

public enum Permission {
    ADMIN(1),
    MOD(2),
    VIEW(3);

    int code;
    private Permission(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public static Permission byCode(int code) {
        return Permission.values()[code];
    }
}
