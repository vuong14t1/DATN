package com.vuongpq2.datn.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "user")
public class UserModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Integer id;

    @NotEmpty(message = "*Please provide your name")
    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    private String gender;

    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "{model.user.email}")
    @Column(name = "email")
    private String email;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "image")
    private String image;

    @Length(min = 5, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "*Please provide your password")
//    @Transient
//    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "active")
    private Integer active;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.MERGE})
//    @JoinTable(name = "user_role",
//            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleModel> roles;

    public Set<RoleModel> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleModel> roles) {
        this.roles = roles;
    }

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }
    public String getBirthdayString(){
        return "";
    }
    @Override
    public String toString() {
        return this.name + "| " + this.email + "|" + this.password;
    }
}
