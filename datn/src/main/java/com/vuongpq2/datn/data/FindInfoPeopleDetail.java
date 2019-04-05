package com.vuongpq2.datn.data;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FindInfoPeopleDetail {
    private String nameParent;
    private String name;
    private int relation;
    private String nameMother;
    private int lifeIndex;
    private String nickName;
    private int gender;
    private int childIndex;
    private String address;
    private String des;
    private String birthDay;
    private String deadDay;
    private String degree;
    private String dataExtra;
    private String img;

    public FindInfoPeopleDetail() {
        name = "";
        nameMother = "";
        nameParent = "";
        nickName  = "";
        address = "";
        des = "";
        birthDay = "?";
        deadDay = "?";
        degree = "";
        dataExtra ="";
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getChildIndex() {
        return childIndex;
    }

    public void setChildIndex(int childIndex) {
        this.childIndex = childIndex;
    }

    public String getNameParent() {
        return nameParent;
    }

    public void setNameParent(String nameParent) {
        this.nameParent = nameParent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRelation() {
        return relation;
    }

    public void setRelation(int relation) {
        this.relation = relation;
    }

    public String getNameMother() {
        return nameMother;
    }

    public void setNameMother(String nameMother) {
        this.nameMother = nameMother;
    }

    public int getLifeIndex() {
        return lifeIndex;
    }

    public void setLifeIndex(int lifeIndex) {
        this.lifeIndex = lifeIndex;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getDeadDay() {
        return deadDay;
    }

    public void setDeadDay(String deadDay) {
        this.deadDay = deadDay;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getDataExtra() {
        return dataExtra;
    }

    public void setDataExtra(String dataExtra) {
        this.dataExtra = dataExtra;
    }
}
