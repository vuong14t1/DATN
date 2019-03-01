package data;

public enum Relation {
    NONE(0),
    CHA(1),
    ME(2),
    VO(3),
    CHONG(4);
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
