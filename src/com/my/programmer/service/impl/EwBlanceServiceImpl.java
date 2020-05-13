package com.my.programmer.service.impl;

import com.my.programmer.dao.EwBlanceDao;
import com.my.programmer.entity.Dormitory;
import com.my.programmer.entity.Utilities;
import com.my.programmer.service.EwBlanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EwBlanceServiceImpl implements EwBlanceService {
    @Autowired
    EwBlanceDao ewBlanceDao;

    @Override
    public Utilities findAllByDormNoAndFloorNo(Map<String, Object> map) {
        return ewBlanceDao.findAllByDormNoAndFloorNo(map);
    }

    @Override
    public List<Dormitory> findAllByFloorNo(String floorNo) {
        return ewBlanceDao.findAllByFloorNo(floorNo);
    }

    @Override
    public List<Utilities> findList(Map<String, Object> queryMap) {
        return ewBlanceDao.findList(queryMap);
    }

    @Override
    public Integer getTotal(Map<String, Object> queryMap) {
        return ewBlanceDao.getTotal(queryMap);
    }

    @Override
    public int edit(Utilities utilities) {
        return ewBlanceDao.edit(utilities);
    }

    @Override
    public int add(Utilities utilities) {
        return ewBlanceDao.add(utilities);
    }

    @Override
    public Integer delete(long id) {
        return ewBlanceDao.delete(id);
    }
}
