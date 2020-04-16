package com.my.programmer.service.impl;

import com.my.programmer.dao.HpRepaiDao;
import com.my.programmer.entity.HpRepai;
import com.my.programmer.entity.StuRepai;
import com.my.programmer.service.HpRepaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HpRepaiServiceImpl implements HpRepaiService {
    @Autowired
    HpRepaiDao hpRepaiDao;

    @Override
    public int add(StuRepai stuRepai) {
        return hpRepaiDao.add(stuRepai);
    }

    @Override
    public List<StuRepai> findList(Map<String, Object> queryMap) {
        return hpRepaiDao.findList(queryMap);
    }

    @Override
    public Integer getTotal(Map<String, Object> queryMap) {
        return hpRepaiDao.getTotal(queryMap);
    }

    @Override
    public Integer setStuStateById(long id) {
        return hpRepaiDao.setStuStateById(id);
    }

    @Override
    public Integer setHpStateById(long id) {
        return hpRepaiDao.setHpStateById(id);
    }

    @Override
    public Integer letReturnById(long id) {
        return hpRepaiDao.letReturnById(id);
    }
}
