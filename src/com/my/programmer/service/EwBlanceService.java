package com.my.programmer.service;

import com.my.programmer.entity.Dormitory;
import com.my.programmer.entity.Student;
import com.my.programmer.entity.Utilities;

import java.util.List;
import java.util.Map;

/**
 * 水电费余额操作
 */
public interface EwBlanceService {

    //学生登录时，只获取当前宿舍的水电费信息
    public Utilities findAllByDormNoAndFloorNo(Map<String,Object> map);

    //宿管登录时，提供当前楼栋所有宿舍的宿舍列表
    public List<Dormitory> findAllByFloorNo(String floorNo);

    public List<Utilities> findList(Map<String,Object> queryMap);

    public Integer getTotal(Map<String,Object> queryMap);

    public int edit(Utilities utilities);

    public int add(Utilities utilities);

    public Integer delete(long id);
}
