package com.my.programmer.dao;

import com.my.programmer.entity.StuRepai;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface StuRepaiDao {

    public int add(StuRepai stuRepai);

    public List<StuRepai> findList(Map<String,Object> queryMap);

    public Integer getTotal(Map<String,Object> queryMap);

    public String getStateById(long id);

    public int setEval(Map<String,Object> map);

    public int setEvalToReceive(Map<String,Object> map);

    public String getEvalById(long id);
}
