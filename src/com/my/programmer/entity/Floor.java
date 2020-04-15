package com.my.programmer.entity;

import org.springframework.stereotype.Component;

@Component
public class Floor {
    private Long id;
    private String floorNo;
    private String floorType;
    //楼管
    private String floorManager;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFloorNo() {
        return floorNo;
    }

    public void setFloorNo(String floorNo) {
        this.floorNo = floorNo;
    }

    public String getFloorType() {
        return floorType;
    }

    public void setFloorType(String floorType) {
        this.floorType = floorType;
    }

    public String getFloorManager() {
        return floorManager;
    }

    public void setFloorManager(String floorManager) {
        this.floorManager = floorManager;
    }

    @Override
    public String toString() {
        return "Floor{" +
                "id=" + id +
                ", floorNo='" + floorNo + '\'' +
                ", floorType='" + floorType + '\'' +
                ", floorManager='" + floorManager + '\'' +
                '}';
    }
}
