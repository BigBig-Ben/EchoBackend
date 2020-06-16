package org.demo.DAO;

import org.demo.Entity.Dating;
import org.demo.Entity.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DatingDAO extends CrudRepository<Dating,Integer> {
//    @Query(value="select d from Dating u where u.tag = :tag")
    @Query(value = "select * from dating where tag_id = :tagId", nativeQuery = true)
    public List<Dating> getDatingsByTag(@Param("tagId") int tagId);

    //pass
}
