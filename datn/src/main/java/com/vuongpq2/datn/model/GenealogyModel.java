package com.vuongpq2.datn.model;

import javax.persistence.*;

@Entity
@Table(name = "genealogy")
public class GenealogyModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "genealogy_id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "history")
    private String history;

    @Column(name = "thuy_to")
    private String thuyTo;

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

    public String getThuyTo() {
        return thuyTo;
    }

    public void setThuyTo(String thuyTo) {
        this.thuyTo = thuyTo;
    }
}
