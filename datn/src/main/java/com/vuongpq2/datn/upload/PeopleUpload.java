package com.vuongpq2.datn.upload;

import java.util.Date;

public class PeopleUpload {

    private int statusUpload;
    private boolean isRealParent;
    private boolean isRealMother;

    private long idPedigree;
    private long id;
    private long idParent;
    private long idMother;
    private int relation;
    private int lifeIndex;
    private String name;
    private String nickName;
    private int gender;
    private Date birthday;
    private Date deadDay;
    private String address;
    private String degree;
    private String img;
    private String des;
    private String dataExtra;
    private int childIndex;


    public PeopleUpload() {

        this.isRealParent = false;
        this.isRealMother = false;
//        this.setGender(GioiTinh.KHONG_RO.ordinal());
//        this.setImg(ExcelImportUtil.getImgDefault(GioiTinh.KHONG_RO));
        this.setIdParent(-1);
        this.setIdMother(-1);
    }

    public int getRelation() {
        return relation;
    }

    public void setRelation(int relation) {
        this.relation = relation;
    }

    public int getStatusUpload() {
        return statusUpload;
    }

    public void setStatusUpload(int statusUpload) {
        this.statusUpload = statusUpload;
    }

    public boolean isRealParent() {
        return isRealParent;
    }

    public void setRealParent(boolean realParent) {
        isRealParent = realParent;
    }

    public long getIdPedigree() {
        return idPedigree;
    }

    public void setIdPedigree(long idPedigree) {
        this.idPedigree = idPedigree;
    }

    public long getId() {
        return id;
    }

    public boolean isRealMother() {
        return isRealMother;
    }

    public void setRealMother(boolean realMother) {
        isRealMother = realMother;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdParent() {
        return idParent;
    }

    public void setIdParent(long idParent) {
        this.idParent = idParent;
    }

    public long getIdMother() {
        return idMother;
    }

    public void setIdMother(long idMother) {
        this.idMother = idMother;
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

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getDataExtra() {
        return dataExtra;
    }

    public void setDataExtra(String dataExtra) {
        this.dataExtra = dataExtra;
    }

    public int getChildIndex() {
        return childIndex;
    }

    public void setChildIndex(int childIndex) {
        this.childIndex = childIndex;
    }
}
