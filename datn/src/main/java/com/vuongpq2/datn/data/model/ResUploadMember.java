package com.vuongpq2.datn.data.model;

public class ResUploadMember {
    private int id;
    private int idParent;
    private int idMotherOrFather;
    private int relation;
    private int lifeIndex;
    private String name;
    private String nickName;
    private String birthday;
    private String deadday;
    private int gender;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdParent() {
        return idParent;
    }

    public void setIdParent(int idParent) {
        this.idParent = idParent;
    }

    public int getIdMotherOrFather() {
        return idMotherOrFather;
    }

    public void setIdMotherOrFather(int idMotherOrFather) {
        this.idMotherOrFather = idMotherOrFather;
    }

    public int getRelation() {
        return relation;
    }

    public void setRelation(int relation) {
        this.relation = relation;
    }

    public int getLifeIndex() {
        return lifeIndex;
    }

    public void setLifeIndex(int lifeIndex) {
        this.lifeIndex = lifeIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDeadday() {
        return deadday;
    }

    public void setDeadday(String deadday) {
        this.deadday = deadday;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}
