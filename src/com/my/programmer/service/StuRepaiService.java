package com.my.programmer.service;

import com.my.programmer.entity.StuRepai;
import com.my.programmer.entity.Student;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface StuRepaiService {

    public int add(StuRepai stuRepai);

    public List<StuRepai> findList(Map<String,Object> queryMap);

    public Integer getTotal(Map<String,Object> queryMap);

    public String getStateById(long id);

    public int setEval(Map<String,Object> map);

    public int setEvalToReceive(Map<String,Object> map);

    public String getEvalById(long id);
}
