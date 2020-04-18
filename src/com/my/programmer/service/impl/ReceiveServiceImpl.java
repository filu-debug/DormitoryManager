package com.my.programmer.service.impl;

import com.my.programmer.dao.ReceiveDao;
import com.my.programmer.entity.StuRepai;
import com.my.programmer.service.ReceiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReceiveServiceImpl implements ReceiveService {
    @Autowired
    ReceiveDao receiveDao;


    @Override
    public int add(StuRepai rvRepai) {
        return receiveDao.add(rvRepai);
    }

    @Override
    public List<StuRepai> findList(Map<String, Object> queryMap) {
        return receiveDao.findList(queryMap);
    }

    @Override
    public Integer getTotal(Map<String, Object> queryMap) {
        return receiveDao.getTotal(queryMap);
    }

    @Override
    public Integer setStuStateById(long id) {
        return receiveDao.setStuStateById(id);
    }

    @Override
    public Integer setHpStateById(long id) {
        return receiveDao.setHpStateById(id);
    }

    @Override
    public int setStuReManAndReManPhone(Map<String, Object> map) {
        return receiveDao.setStuReManAndReManPhone(map);
    }

    @Override
    public int setHpReManAndReManPhone(Map<String, Object> map) {
        return receiveDao.setHpReManAndReManPhone(map);
    }

    @Override
    public int setWkstate(long id) {
        return receiveDao.setWkstate(id);
    }

    @Override
    public StuRepai findById(long id) {
        return receiveDao.findById(id);
    }

    @Override
    public Integer setStuStateByMap(Map<String, Object> map) {
        return receiveDao.setStuStateByMap(map);
    }

    @Override
    public Integer setHpStateByMap(Map<String, Object> map) {
        return receiveDao.setHpStateByMap(map);
    }

    @Override
    public int setWkstateByMap(Map<String, Object> map) {
        return receiveDao.setWkstateByMap(map);
    }

    @Override
    public int setReceivedStateByMap(Map<String, Object> map) {
        return receiveDao.setReceivedStateByMap(map);
    }


}
