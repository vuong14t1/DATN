package com.vuongpq2.datn.data.Enum;

public enum Permission {
    NO_REGISTER(0),
    ADMIN(1),
    MOD(2),
    REGISTERED(3),
    MEMBER(4);

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
