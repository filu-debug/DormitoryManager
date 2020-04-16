package com.my.programmer.entity;

import org.springframework.stereotype.Component;

@Component
public class HouseParent {
    private Long id;
    private String workNo;
    private String name;
    private String phone;
    private String gender;
    private String password;
    //负责楼栋
    private String floorNo;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWorkNo() {
        return workNo;
    }

    public void setWorkNo(String workNo) {
        this.workNo = workNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFloorNo() {
        return floorNo;
    }

    public void setFloorNo(String floorNo) {
        this.floorNo = floorNo;
    }

    @Override
    public String toString() {
        return "HouseParent{" +
                "id=" + id +
                ", workNo='" + workNo + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", gender='" + gender + '\'' +
                ", password='" + password + '\'' +
                ", floorNo='" + floorNo + '\'' +
                '}';
    }
}
