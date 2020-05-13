package com.my.programmer.entity;

/**
 * 水电费信息实体类
 */
public class Utilities {
    private Integer id;
    //电费余额
    private String  eleBalance;
    //水费余额
    private String waterBalance;
    //宿舍编号
    private String dormNo;

    private String floorNo;

    public String getFloorNo() {
        return floorNo;
    }

    public void setFloorNo(String floorNo) {
        this.floorNo = floorNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDormNo() {
        return dormNo;
    }

    public void setDormNo(String dormNo) {
        this.dormNo = dormNo;
    }

    public String getEleBalance() {
        return eleBalance;
    }

    public void setEleBalance(String eleBalance) {
        this.eleBalance = eleBalance;
    }

    public String getWaterBalance() {
        return waterBalance;
    }

    public void setWaterBalance(String waterBalance) {
        this.waterBalance = waterBalance;
    }
}
