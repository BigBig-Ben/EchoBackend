package org.demo.DAO;

import org.demo.Entity.DateComment;
import org.springframework.data.repository.CrudRepository;

public interface DateCommentDAO extends CrudRepository<DateComment,Integer> {
}
