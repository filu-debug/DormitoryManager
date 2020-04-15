package com.my.programmer.dao;

import com.my.programmer.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserDao {
	public User findByUserName(String username);

	public int add(User user);

	public List<User> findList(Map<String,Object> queryMap);

	public Integer getTotal(Map<String,Object> queryMap);

	public int edit(User user);

	public Integer delete(long id);
}
