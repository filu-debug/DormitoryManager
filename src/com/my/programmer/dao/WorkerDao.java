package com.my.programmer.dao;

import com.my.programmer.entity.Worker;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface WorkerDao {

    public Worker findByWorkerName(String name);

    public int add(Worker worker);

    public List<Worker> findList(Map<String,Object> queryMap);

    public Integer getTotal(Map<String,Object> queryMap);

    public int edit(Worker worker);

    public Integer delete(long id);

    public Worker findByWorkNo(String workNo);
}
