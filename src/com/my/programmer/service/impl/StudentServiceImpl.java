package com.my.programmer.service.impl;

import com.my.programmer.dao.StudentDao;
import com.my.programmer.entity.Student;
import com.my.programmer.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentDao studentDao;

    @Override
    public Student findByStuName(String stuName) {
        return studentDao.findByStuName(stuName);
    }

    @Override
    public int add(Student student) {
        return studentDao.add(student);
    }

    @Override
    public List<Student> findList(Map<String, Object> queryMap) {
        return studentDao.findList(queryMap);
    }

    @Override
    public Integer getTotal(Map<String, Object> queryMap) {
        return studentDao.getTotal(queryMap);
    }

    @Override
    public int edit(Student student) {
        return studentDao.edit(student);
    }

    @Override
    public Integer delete(long id) {
        return studentDao.delete(id);
    }

    @Override
    public Integer liveCount(Map<String,Object> map) {
        return studentDao.liveCount(map);
    }

    @Override
    public Student findByStuNo(String stuNo) {
        return studentDao.findByStuNo(stuNo);
    }

    @Override
    public void setStayCount(Map<String, Object> bedMap) {
        studentDao.setStayCount(bedMap);
    }

    @Override
    public Student getDormNoById(long id) {
        return studentDao.getDormNoById(id);
    }

    @Override
    public void subStayCount(Map<String, Object> bedMap) {
        studentDao.subStayCount(bedMap);
    }

    @Override
    public Student findByPhone(String phone) {
        return studentDao.findByPhone(phone);
    }
}
