package com.my.programmer.service.impl;

import com.my.programmer.dao.HouseParentDao;
import com.my.programmer.entity.Floor;
import com.my.programmer.entity.HouseParent;
import com.my.programmer.service.HouseParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HouseParentServiceImpl implements HouseParentService {

    @Autowired
    HouseParentDao houseParentDao;


    @Override
    public HouseParent findByWorkNo(String workNo) {
        return houseParentDao.findByWorkNo(workNo);
    }

    @Override
    public int add(HouseParent houseParent) {
        return houseParentDao.add(houseParent);
    }

    @Override
    public List<HouseParent> findList(Map<String, Object> queryMap) {
        return houseParentDao.findList(queryMap);
    }

    @Override
    public Integer getTotal(Map<String, Object> queryMap) {
        return houseParentDao.getTotal(queryMap);
    }

    @Override
    public int edit(HouseParent houseParent) {
        return houseParentDao.edit(houseParent);
    }

    @Override
    public Integer delete(long id) {
        return houseParentDao.delete(id);
    }

    @Override
    public HouseParent findByFloorNo(String floorNo) {
        return houseParentDao.findByFloorNo(floorNo);
    }

    @Override
    public HouseParent findByPhone(String phone) {
        return houseParentDao.findByPhone(phone);
    }

    @Override
    public List<HouseParent> findAll() {
        return houseParentDao.findAll();
    }
}
