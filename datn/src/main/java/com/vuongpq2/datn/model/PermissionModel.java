package com.vuongpq2.datn.model;

import javax.persistence.*;

@Entity
@Table(name = "permission")
public class PermissionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "permission_id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private Integer code;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
