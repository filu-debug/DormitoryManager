package com.my.programmer.service;

import com.my.programmer.entity.StuRepai;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface WkRepaiService {

    public int add(StuRepai wkRepai);

    public List<StuRepai> findList(Map<String,Object> queryMap);

    public Integer getTotal(Map<String,Object> queryMap);

    public Integer setStuStateById(long id);

    public Integer setHpStateById(long id);
}
