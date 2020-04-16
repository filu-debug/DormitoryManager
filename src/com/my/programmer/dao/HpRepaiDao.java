package com.my.programmer.dao;

import com.my.programmer.entity.HpRepai;
import com.my.programmer.entity.StuRepai;

import java.util.List;
import java.util.Map;

public interface HpRepaiDao {

    public int add(StuRepai stuRepai);

    public List<StuRepai> findList(Map<String,Object> queryMap);

    public Integer getTotal(Map<String,Object> queryMap);

    public Integer setStuStateById(long id);

    public Integer setHpStateById(long id);

    public Integer letReturnById(long id);
}
