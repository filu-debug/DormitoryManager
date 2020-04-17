package com.my.programmer.service.impl;

import com.my.programmer.dao.WkRepaiDao;
import com.my.programmer.entity.StuRepai;
import com.my.programmer.service.WkRepaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WkRepaiServiceImpl implements WkRepaiService {
    @Autowired
    WkRepaiDao wkRepaiDao;
    @Override
    public int add(StuRepai wkRepai) {
        return wkRepaiDao.add(wkRepai);
    }

    @Override
    public List<StuRepai> findList(Map<String, Object> queryMap) {
        return wkRepaiDao.findList(queryMap);
    }

    @Override
    public Integer getTotal(Map<String, Object> queryMap) {
        return wkRepaiDao.getTotal(queryMap);
    }


    @Override
    public Integer setStuStateById(long id) {
        return wkRepaiDao.setStuStateById(id);
    }

    @Override
    public Integer setHpStateById(long id) {
        return wkRepaiDao.setHpStateById(id);
    }
}
