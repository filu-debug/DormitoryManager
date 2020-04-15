package com.my.programmer.entity;

import org.springframework.stereotype.Component;

@Component
public class Dormitory {
    private Long id;
    private String dormNo;
    private String floorNo;
    private Integer bedCount;
    private Integer stayCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDormNo() {
        return dormNo;
    }

    public void setDormNo(String dormNo) {
        this.dormNo = dormNo;
    }

    public String getFloorNo() {
        return floorNo;
    }

    public void setFloorNo(String floorNo) {
        this.floorNo = floorNo;
    }

    public Integer getBedCount() {
        return bedCount;
    }

    public void setBedCount(Integer bedCount) {
        this.bedCount = bedCount;
    }

    public Integer getStayCount() {
        return stayCount;
    }

    public void setStayCount(Integer stayCount) {
        this.stayCount = stayCount;
    }

    @Override
    public String toString() {
        return "Dormitory{" +
                "id=" + id +
                ", dormNo='" + dormNo + '\'' +
                ", floorNo='" + floorNo + '\'' +
                ", bedCount=" + bedCount +
                ", stayCount=" + stayCount +
                '}';
    }
}
