package org.demo.DAO;

import org.demo.Entity.DateDiscussion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DateDiscussionDAO extends CrudRepository<DateDiscussion,Integer> {
    @Query(value = "select * from date_discussion where belong_id = :datingId order by floor", nativeQuery = true)
    public List<DateDiscussion> getDateDiscussionsByDatingId(@Param("datingId") int datingId);
}
