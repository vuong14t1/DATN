package com.vuongpq2.datn.data.model;

public class DMemberTree {
    public String name;
    public String birthday;
    public String deadday;
    public int gender;
    public int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
