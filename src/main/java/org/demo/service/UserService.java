package org.demo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.demo.DAO.UserDAO;
import org.demo.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
	private UserDAO userDAO;
	
	
	@Transactional
	public void save(User user)
	{
		userDAO.save(user);
	}
	
	@Transactional
	public void deleteById(int id)
	{
		userDAO.deleteById(id);
	}
	
	@Transactional
	public Iterable<User> getAll()
	{
		return userDAO.findAll();
	}

	@Transactional
	public User findById(int id) {
		return userDAO.findById(id).get();
	}
	
	@Transactional
	public int count()
	{
		return (int) userDAO.count();
	}

	@Transactional
	public List<User> checkOpenid(String openid)
	{
		return userDAO.checkOpenid(openid);
	}
	
	
	
	//test
	@Transactional
	public List<User> test(int id)
	{
		return userDAO.test(id);
	}
	
	
}
