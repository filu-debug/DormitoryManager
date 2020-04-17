package com.my.programmer.dao;

import com.my.programmer.entity.StuRepai;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ReceiveDao {

    public int add(StuRepai rvRepai);

    public List<StuRepai> findList(Map<String,Object> queryMap);

    public Integer getTotal(Map<String,Object> queryMap);

    public Integer setStuStateById(long id);

    public Integer setHpStateById(long id);

    public int setStuReManAndReManPhone(Map<String,Object> map);

    public int setHpReManAndReManPhone(Map<String,Object> map);

    public int setWkstate(long id);
}
