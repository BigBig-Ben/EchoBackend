package org.demo.DAO;

import org.demo.Entity.Tag;
import org.springframework.data.repository.CrudRepository;

public interface TagDAO extends CrudRepository<Tag,Integer>{
	
}
