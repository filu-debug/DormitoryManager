package com.my.programmer.service;

import com.my.programmer.entity.StuRepai;

import java.util.List;
import java.util.Map;

/**
 * 针对工人的“已接受任务列表”
 */
public interface ReceiveService {

    public int add(StuRepai rvRepai);

    public List<StuRepai> findList(Map<String,Object> queryMap);

    public Integer getTotal(Map<String,Object> queryMap);

    public Integer setStuStateById(long id);

    public Integer setHpStateById(long id);

    public int setStuReManAndReManPhone(Map<String,Object> map);

    public int setHpReManAndReManPhone(Map<String,Object> map);

    public int setWkstate(long id);
}
