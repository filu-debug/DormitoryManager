package com.my.programmer.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 学生报修表
 */
public class StuRepai {
    private Integer id;
    //申请人姓名
    private String stuName;
    //申请人所在楼栋
    private String floorNo;
    //申请人所在寝室
    private String dormNo;
    //申请人联系方式
    private String phone;
    //维修类别
    private String retype;
    //事项描述
    private String discr;
    //状态
    private String state;
    //发起时间
    private String starttime;
    //任务接取人姓名
    private String reMan;
    //任务接取人的电话
    private String reManPhone;
    //工人工号
    private String workNo;
    //评价
    private String eval;

    public String getEval() {
        return eval;
    }

    public void setEval(String eval) {
        this.eval = eval;
    }

    public String getWorkNo() {
        return workNo;
    }

    public void setWorkNo(String workNo) {
        this.workNo = workNo;
    }

    public String getReMan() {
        return reMan;
    }

    public void setReMan(String reMan) {
        this.reMan = reMan;
    }

    public String getReManPhone() {
        return reManPhone;
    }

    public void setReManPhone(String reManPhone) {
        this.reManPhone = reManPhone;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getFloorNo() {
        return floorNo;
    }

    public void setFloorNo(String floorNo) {
        this.floorNo = floorNo;
    }

    public String getDormNo() {
        return dormNo;
    }

    public void setDormNo(String dormNo) {
        this.dormNo = dormNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRetype() {
        return retype;
    }

    public void setRetype(String retype) {
        this.retype = retype;
    }

    public String getDiscr() {
        return discr;
    }

    public void setDiscr(String discr) {
        this.discr = discr;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "StuRepai{" +
                "id=" + id +
                ", stuName='" + stuName + '\'' +
                ", floorNo='" + floorNo + '\'' +
                ", dormNo='" + dormNo + '\'' +
                ", phone='" + phone + '\'' +
                ", retype='" + retype + '\'' +
                ", discr='" + discr + '\'' +
                ", state='" + state + '\'' +
                ", eval='" + eval + '\'' +
                ", starttime='" + starttime + '\'' +
                ", reMan='" + reMan + '\'' +
                ", reManPhone='" + reManPhone + '\'' +
                ", workNo='" + workNo + '\'' +
                '}';
    }
}
