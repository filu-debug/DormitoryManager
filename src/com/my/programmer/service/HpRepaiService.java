package com.my.programmer.service;

import com.my.programmer.entity.StuRepai;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface HpRepaiService {

    public int add(StuRepai stuRepai);

    public List<StuRepai> findList(Map<String,Object> queryMap);

    public Integer getTotal(Map<String,Object> queryMap);

    public Integer setStuStateById(long id);

    public Integer setHpStateById(long id);

    public Integer letReturnById(long id);

    public StuRepai findById(long id);
}
