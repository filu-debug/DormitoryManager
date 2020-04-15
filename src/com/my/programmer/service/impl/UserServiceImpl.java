package com.my.programmer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.programmer.dao.UserDao;
import com.my.programmer.entity.User;
import com.my.programmer.service.UserService;

import java.util.List;
import java.util.Map;

/**
 * 实现实体类的操作（调用dao去实现，而dao是通过和mapper文件结合来实现对数据库的操作的）
 * @author X1YOGA
 *
 */
@Service
public class UserServiceImpl implements UserService {
	
	//spring容器已经帮我们准好了对象，使用@Autowired可以获取容器中的对象
	@Autowired
	private UserDao userDao;

	@Override
	public User findByUserName(String username) {
		// TODO Auto-generated method stub
		return userDao.findByUserName(username);
	}

	@Override
	public int add(User user) {
		return userDao.add(user);
	}

	@Override
	public List<User> findList(Map<String,Object> queryMap) {
		return userDao.findList(queryMap);
	}

	@Override
	public Integer getTotal(Map<String,Object> queryMap) {
		return userDao.getTotal(queryMap);
	}

	@Override
	public int edit(User user) {
		return userDao.edit(user);
	}

	@Override
	public Integer delete(long id) {
		return userDao.delete(id);
	}

}
