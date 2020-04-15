package com.my.programmer.service;

import com.my.programmer.entity.Floor;
import com.my.programmer.entity.HouseParent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface HouseParentService {
    public HouseParent findByWorkNo(String workNo);

    public int add(HouseParent houseParent);

    public List<HouseParent> findList(Map<String,Object> queryMap);

    public Integer getTotal(Map<String,Object> queryMap);

    public int edit(HouseParent houseParent);

    public Integer delete(long id);

    public HouseParent findByFloorNo(String floorNo);

    public HouseParent findByPhone(String phone);

    //下拉列表
    public List<HouseParent> findAll();
}
