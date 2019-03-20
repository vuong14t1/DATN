package com.vuongpq2.datn.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_permission")
public class UserPermissionModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_permission_id")
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumns({ @JoinColumn(name="user_id", referencedColumnName="user_id")})
    private UserModel user;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumns({ @JoinColumn(name="genealogy_id", referencedColumnName="genealogy_id")})
    private GenealogyModel genealogy;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumns({ @JoinColumn(name="pedigree_id", referencedColumnName="pedigree_id")})
    private PedigreeModel pedigree;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumns({ @JoinColumn(name="permission_id", referencedColumnName="permission_id")})
    private PermissionModel  permission;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public GenealogyModel getGenealogyModel() {
        return genealogy;
    }

    public void setGenealogyModel(GenealogyModel genealogyModel) {
        this.genealogy = genealogyModel;
    }

    public PedigreeModel getPedigreeModel() {
        return pedigree;
    }

    public PermissionModel getPermission() {
        return permission;
    }

    public void setPermission(PermissionModel permission) {
        this.permission = permission;
    }

    public void setPedigreeModel(PedigreeModel pedigreeModel) {
        this.pedigree = pedigreeModel;
    }

}
