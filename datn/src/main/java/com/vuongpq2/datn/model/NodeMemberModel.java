package com.vuongpq2.datn.model;

import org.thymeleaf.util.ArrayUtils;

import javax.persistence.*;

@Entity
@Table(name = "node_member")
public class NodeMemberModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "node_member_id")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumns({@JoinColumn(name = "pedigree_id", referencedColumnName = "pedigree_id")})
    private PedigreeModel pedigree;
/*
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name="genealogy_id", referencedColumnName="genealogy_id")})
    private GenealogyModel genealogy;*/

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumns({@JoinColumn(name = "parent_id", referencedColumnName = "node_member_id")})
    private NodeMemberModel parent;

    @OneToOne(mappedBy = "nodeMemberModel")
    private DescriptionMemberModel descriptionMemberModel;

    public DescriptionMemberModel getDescriptionMemberModel() {
        return descriptionMemberModel;
    }

    public void setDescriptionMemberModel(DescriptionMemberModel descriptionMemberModel) {
        this.descriptionMemberModel = descriptionMemberModel;
    }

    @Column(name = "mother_father_id")
    private Integer motherFatherId;

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "child_index")
    private Integer childIndex;

    @Column(name = "life_index")
    private Integer lifeIndex;

    @Column(name = "patch_key")
    private String patchKey;

    @Column(name = "image")
    private String image;

    @Column(name = "parent_relation")
    private Integer relation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PedigreeModel getPedigree() {
        return pedigree;
    }

    public void setPedigree(PedigreeModel pedigree) {
        this.pedigree = pedigree;
    }
/*
    public GenealogyModel getGenealogy() {
        return genealogy;
    }

    public void setGenealogy(GenealogyModel genealogy) {
        this.genealogy = genealogy;
    }*/

    public NodeMemberModel getParent() {
        return parent;
    }

    public void setParent(NodeMemberModel parent) {
        this.parent = parent;
    }

    public Integer getMotherFatherId() {
        return motherFatherId;
    }

    public void setMotherFatherId(Integer motherFatherId) {
        this.motherFatherId = motherFatherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getChildIndex() {
        return childIndex;
    }

    public void setChildIndex(int childIndex) {
        this.childIndex = childIndex;
    }

    public Integer getLifeIndex() {
        return lifeIndex;
    }

    public void setLifeIndex(Integer lifeIndex) {
        this.lifeIndex = lifeIndex;
    }

    public String getPatchKey() {
        return patchKey;
    }

    public void setPatchKey(String patchKey) {
        this.patchKey = patchKey;
    }

    public String getImage() {
        return "/img/avatar-default-unknown.png";
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getRelation() {
        return relation;
    }

    public void setRelation(int relation) {
        this.relation = relation;
    }

    public static String getPathkeyByParent(NodeMemberModel nodeMemberModel) {
        if (nodeMemberModel == null) {
            return "r";
        }
        return nodeMemberModel.getPatchKey() + "_" + nodeMemberModel.getId();
    }

    public static String getPathKeyByChild (NodeMemberModel nodeMemberModel) {
        if (nodeMemberModel == null) {
            return "-1";
        }
        String[] arrKey = nodeMemberModel.getPatchKey().split("_");
        String key = "";
        for (int i = 0; i < arrKey.length - 1; i++) {
            if(!key.equals("")) {
                key = String.join("_", key, arrKey[i]);
            }else {
                key = arrKey[i];
            }
        }
        System.out.println("key by child " + key);
        return key;
    }
}
