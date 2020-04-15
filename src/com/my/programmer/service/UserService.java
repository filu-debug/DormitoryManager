package com.my.programmer.service;

import org.springframework.stereotype.Service;

import com.my.programmer.entity.User;

import java.util.List;
import java.util.Map;

/**
 * service作用：声明该实体应该有哪些操作
 * @author X1YOGA
 *
 */
@Service
public interface UserService {
	//这里返回的是该用户名对应的一条记录（一个完整的User对象）
	public User findByUserName(String username);

	public int add(User user);

	public List<User> findList(Map<String,Object> queryMap);

	public Integer getTotal(Map<String,Object> queryMap);

	public int edit(User user);

	public Integer delete(long id);
}
