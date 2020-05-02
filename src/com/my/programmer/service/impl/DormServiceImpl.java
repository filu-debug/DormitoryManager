package com.my.programmer.service.impl;

import com.my.programmer.dao.DormDao;
import com.my.programmer.entity.Dormitory;
import com.my.programmer.entity.Floor;
import com.my.programmer.entity.Student;
import com.my.programmer.service.DormiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DormServiceImpl implements DormiService {
    @Autowired
    DormDao dormDao;

    @Override
    public List<Dormitory> findByDormNo(String dormNo) {
        return dormDao.findByDormNo(dormNo);
    }

    @Override
    public int add(Dormitory dormitory) {
        return dormDao.add(dormitory);
    }

    @Override
    public List<Dormitory> findList(Map<String, Object> queryMap) {
        return dormDao.findList(queryMap);
    }

    @Override
    public Integer getTotal(Map<String, Object> queryMap) {
        return dormDao.getTotal(queryMap);
    }

    @Override
    public int edit(Dormitory dormitory) {
        return dormDao.edit(dormitory);
    }

    @Override
    public Integer delete(long id) {
        return dormDao.delete(id);
    }

    @Override
    public Dormitory findByFloorNo(String floorNo) {
        return dormDao.findByFloorNo(floorNo);
    }

    @Override
    public List<Dormitory> findAll() {
        return dormDao.findAll();
    }

    @Override
    public Integer getBedCount(Map<String, Object> map) {
        return dormDao.getBedCount(map);
    }

    @Override
    public Dormitory queryByIdFromStu(long id) {
        return dormDao.queryByIdFromStu(id);
    }

    @Override
    public List<Student> findByDormMap(Map<String, Object> map) {
        return dormDao.findByDormMap(map);
    }

}
