package com.vuongpq2.datn.data.Enum;

public enum Relation {
    NONE(0),
    VO(1),
    CHONG(2),
    CHA(3),
    ME(4);

    int code;
    Relation(int i) {
        this.code = i;
    }

    public int getCode(){
        return this.code;
    }

    public static Relation getByCode (int code) {
        return Relation.values()[code];
    }
}
