package com.my.programmer.dao;

import com.my.programmer.entity.Student;
import com.my.programmer.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface StudentDao {
    public Student findByStuName(String stuName);

    public int add(Student student);

    public List<Student> findList(Map<String,Object> queryMap);

    public Integer getTotal(Map<String,Object> queryMap);

    public int edit(Student student);

    public Integer delete(long id);

    //获取宿舍已入住人数
    public Integer liveCount(Map<String,Object> map);

    public Student findByStuNo(String stuNo);

    public void setStayCount(Map<String,Object> bedMap);

    public Student getDormNoById(long id);

    public void subStayCount(Map<String,Object> bedMap);
}
