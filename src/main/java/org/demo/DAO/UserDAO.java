package org.demo.DAO;

import java.util.List;

import org.demo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
 
public interface UserDAO extends CrudRepository<User,Integer>{
	@Query(value="select u from User u where u.id = :id ")
	public List<User> test(@Param("id") int id);
	
	@Query(value="select u from User u where u.openId = :openid")
	public List<User> checkOpenid(@Param("openid") String openid);
}