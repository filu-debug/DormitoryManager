package com.my.programmer.service;

import com.my.programmer.entity.Floor;
import com.my.programmer.entity.Student;
import com.my.programmer.entity.User;
import com.my.programmer.entity.Worker;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface WorkerService {
    public Worker findByWorkerName(String name);

    public int add(Worker worker);

    public List<Worker> findList(Map<String,Object> queryMap);

    public Integer getTotal(Map<String,Object> queryMap);

    public int edit(Worker worker);

    public Integer delete(long id);

    public Worker findByWorkNo(String workNo);

    public Worker findByPhone(String phone);
}
