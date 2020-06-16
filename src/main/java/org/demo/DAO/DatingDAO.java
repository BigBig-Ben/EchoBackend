package org.demo.DAO;

import org.demo.Entity.Dating;
import org.springframework.data.repository.CrudRepository;

public interface DatingDAO extends CrudRepository<Dating,Integer> {
    //pass
}
