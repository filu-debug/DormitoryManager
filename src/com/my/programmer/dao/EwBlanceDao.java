package com.my.programmer.dao;

import com.my.programmer.entity.Dormitory;
import com.my.programmer.entity.Utilities;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface EwBlanceDao {

    public Utilities findAllByDormNoAndFloorNo(Map<String,Object> map);

    public List<Dormitory> findAllByFloorNo(String floorNo);

    public List<Utilities> findList(Map<String,Object> queryMap);

    public Integer getTotal(Map<String,Object> queryMap);

    public int edit(Utilities utilities);

    public int add(Utilities utilities);

    public Integer delete(long id);
}
