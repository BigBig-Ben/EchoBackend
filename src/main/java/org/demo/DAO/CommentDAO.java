package org.demo.DAO;

import org.demo.Entity.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentDAO extends CrudRepository<Comment,Integer>{
	
}
