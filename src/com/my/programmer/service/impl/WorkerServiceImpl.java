package com.my.programmer.service.impl;

import com.my.programmer.dao.WorkerDao;
import com.my.programmer.entity.Worker;
import com.my.programmer.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WorkerServiceImpl implements WorkerService {
    @Autowired
    WorkerDao workerDao;

    @Override
    public Worker findByWorkerName(String name) {
        return workerDao.findByWorkerName(name);
    }

    @Override
    public int add(Worker worker) {
        return workerDao.add(worker);
    }

    @Override
    public List<Worker> findList(Map<String, Object> queryMap) {
        return workerDao.findList(queryMap);
    }

    @Override
    public Integer getTotal(Map<String, Object> queryMap) {
        return workerDao.getTotal(queryMap);
    }

    @Override
    public int edit(Worker worker) {
        return workerDao.edit(worker);
    }

    @Override
    public Integer delete(long id) {
        return workerDao.delete(id);
    }

    @Override
    public Worker findByWorkNo(String workNo) {
        return workerDao.findByWorkNo(workNo);
    }

    @Override
    public Worker findByPhone(String phone) {
        return workerDao.findByPhone(phone);
    }
}
