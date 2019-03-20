package com.vuongpq2.datn.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "description_member")
public class DescriptionMemberModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "description_member_id")
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumns({ @JoinColumn(name="node_member_id", referencedColumnName="node_member_id")})
    private NodeMemberModel nodeMemberModel;

    @Column(name = "nickname")
    private String nickName;

    @Column(name = "degree")
    private String degree;

    @Column(name = "description")
    private String description;

    @Column(name = "extra_data")
    private String extraData;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "dead_day")
    private Date deadDay;

    @Column(name = "address")
    private String address;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExtraData() {
        return extraData;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
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

    public NodeMemberModel getNodeMemberModel() {
        return nodeMemberModel;
    }

    public void setNodeMemberModel(NodeMemberModel nodeMemberModel) {
        this.nodeMemberModel = nodeMemberModel;
    }
}
