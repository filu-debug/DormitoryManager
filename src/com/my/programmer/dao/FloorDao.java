package com.my.programmer.dao;

import com.my.programmer.entity.Dormitory;
import com.my.programmer.entity.Floor;
import com.my.programmer.entity.HouseParent;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FloorDao {
    public Floor findByFloorNo(String FloorNo);

    public int add(Floor Floor);

    public List<Floor> findList(Map<String,Object> queryMap);

    public Integer getTotal(Map<String,Object> queryMap);

    public int edit(Floor Floor);

    public Integer delete(long id);

    //用于下拉框
    public List<Floor> findAll();

    public String getFloorType(String floorNo);

    public List<Floor> getBoyDorms();

    public List<Floor> getGrilDorms();

    public List<Dormitory> queryByFloorFromDorm(String floorNo);

    public Floor findFloorById(long id);
}
