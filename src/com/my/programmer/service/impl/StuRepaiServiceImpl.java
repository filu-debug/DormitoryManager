package com.my.programmer.service.impl;

import com.my.programmer.dao.StuRepaiDao;
import com.my.programmer.entity.StuRepai;
import com.my.programmer.service.StuRepaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StuRepaiServiceImpl implements StuRepaiService {
    @Autowired
    StuRepaiDao stuRepaiDao;
    @Override
    public int add(StuRepai stuRepai) {
        return stuRepaiDao.add(stuRepai);
    }

    @Override
    public List<StuRepai> findList(Map<String, Object> queryMap) {
        return stuRepaiDao.findList(queryMap);
    }

    @Override
    public Integer getTotal(Map<String, Object> queryMap) {
        return stuRepaiDao.getTotal(queryMap);
    }
}
