package com.my.programmer.service.impl;

import com.my.programmer.dao.FloorDao;
import com.my.programmer.entity.Floor;
import com.my.programmer.entity.HouseParent;
import com.my.programmer.service.FloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FloorServiceImpl implements FloorService {
    @Autowired
    FloorDao  floorDao;

    @Override
    public Floor findByFloorNo(String floorNo) {
        return floorDao.findByFloorNo(floorNo);
    }

    @Override
    public int add(Floor floor) {
        return floorDao.add(floor);
    }

    @Override
    public List<Floor> findList(Map<String, Object> queryMap) {
        return floorDao.findList(queryMap);
    }

    @Override
    public Integer getTotal(Map<String, Object> queryMap) {
        return floorDao.getTotal(queryMap);
    }

    @Override
    public int edit(Floor floor) {
        return floorDao.edit(floor);
    }

    @Override
    public Integer delete(long id) {
        return floorDao.delete(id);
    }

    @Override
    public List<Floor> findAll() {
        return floorDao.findAll();
    }

    @Override
    public String getFloorType(String floorNo) {
        return floorDao.getFloorType(floorNo);
    }

    @Override
    public List<Floor> getBoyDorms() {
        return floorDao.getBoyDorms();
    }

    @Override
    public List<Floor> getGrilDorms() {
        return floorDao.getGrilDorms();
    }
}
