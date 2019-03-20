package com.vuongpq2.datn.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "pedigree")
public class PedigreeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pedigree_id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "history")
    private String history;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumns({ @JoinColumn(name="genealogy_id", referencedColumnName="genealogy_id")})
    private GenealogyModel genealogyModel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public GenealogyModel getGenealogyModel() {
        return genealogyModel;
    }

    public void setGenealogyModel(GenealogyModel genealogyModel) {
        this.genealogyModel = genealogyModel;
    }
}
