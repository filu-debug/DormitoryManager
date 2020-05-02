package com.my.programmer.service;

import com.my.programmer.entity.Dormitory;
import com.my.programmer.entity.Floor;
import com.my.programmer.entity.Student;
import com.my.programmer.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface DormiService {
    public List<Dormitory> findByDormNo(String dormNo);

    public int add(Dormitory dormitory);

    public List<Dormitory> findList(Map<String,Object> queryMap);

    public Integer getTotal(Map<String,Object> queryMap);

    public int edit(Dormitory dormitory);

    public Integer delete(long id);

    public Dormitory findByFloorNo(String floorNo);

    //用于下拉框
    public List<Dormitory> findAll();

    public Integer getBedCount(Map<String,Object> map);

    public Dormitory queryByIdFromStu(long id);

    public List<Student> findByDormMap(Map<String,Object> map);
}
