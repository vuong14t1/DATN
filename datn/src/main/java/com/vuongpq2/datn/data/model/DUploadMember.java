package com.vuongpq2.datn.data.model;

import com.vuongpq2.datn.data.Enum.Relation;
import com.vuongpq2.datn.data.GioiTinh;

import java.util.Date;

public class DUploadMember {
    private int id;
    private int idParent;
    private int idMotherOrFather;
    private int liftIdx;
    private Relation relation;
    private String name;
    private String nickName;
    private GioiTinh gender;
    private int childIdx;
    private Date birthDay;
    private Date deadDay;
    private String address;
    private String degree;
    private String image;
    private String des;
    private String extraData;


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

    public int getLiftIdx() {
        return liftIdx;
    }

    public void setLiftIdx(int liftIdx) {
        this.liftIdx = liftIdx;
    }

    public Relation getRelation() {
        return relation;
    }

    public void setRelation(Relation relation) {
        this.relation = relation;
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

    public GioiTinh getGender() {
        return gender;
    }

    public void setGender(GioiTinh gender) {
        this.gender = gender;
    }

    public int getChildIdx() {
        return childIdx;
    }

    public void setChildIdx(int childIdx) {
        this.childIdx = childIdx;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public Date getDeadDay() {
        return deadDay;
    }

    public void setDeadDay(Date deadDay) {
        this.deadDay = deadDay;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getExtraData() {
        return extraData;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }
}
