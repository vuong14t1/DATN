package com.example.genealogy.data.Enum;

public enum UserStatus {
    ACTIVE(1),
    UNACTIVE(2);
    byte code;
    private UserStatus(int code) {
        this.code  = (byte) code;
    }

    public int getCode() {
        return (int) this.code;
    }

    public static UserStatus byCode(int code) {
        return UserStatus.values()[code];
    }
}
