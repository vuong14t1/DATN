package com.vuongpq2.datn.data.model;

import com.vuongpq2.datn.data.Enum.Permission;

public class DGenealogyModel {
    private int id;
    private String name;
    private String history;
    private int permission;

    public DGenealogyModel() {
        permission = Permission.NO_REGISTER.getCode();
    }

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

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int action) {
        this.permission = action;
    }
}
