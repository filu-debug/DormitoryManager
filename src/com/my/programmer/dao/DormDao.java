package com.my.programmer.dao;

import com.my.programmer.entity.Dormitory;
import com.my.programmer.entity.Floor;
import com.my.programmer.entity.Student;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DormDao {
    public List<Dormitory> findByDormNo(String dormNo);

    public int add(Dormitory dormitory);

    public List<Dormitory> findList(Map<String,Object> queryMap);

    public Integer getTotal(Map<String,Object> queryMap);

    public int edit(Dormitory dormitory);

    public Integer delete(long id);

    public Dormitory findByFloorNo(String floorNo);

    public List<Dormitory> findAll();

    public Integer getBedCount(Map<String,Object> map);

    public Dormitory queryByIdFromStu(long id);

    public List<Student> findByDormMap(Map<String,Object> map);

}
