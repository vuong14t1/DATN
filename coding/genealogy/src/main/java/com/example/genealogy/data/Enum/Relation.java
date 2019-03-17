package com.example.genealogy.data.Enum;

public enum Relation {
    NONE(0),
    VO(1),
    CHONG(2),
    CHA(3),
    ME(4);

    byte code;
    Relation(int i) {
        this.code = (byte)i;
    }

    public byte getCode(){
        return this.code;
    }

    public static Relation getByCode (int code) {
        return Relation.values()[code];
    }
}
