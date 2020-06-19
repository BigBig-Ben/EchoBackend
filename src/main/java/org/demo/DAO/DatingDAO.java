package org.demo.DAO;

import org.demo.Entity.Dating;
import org.demo.Entity.Tag;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DatingDAO extends CrudRepository<Dating,Integer> {
    @Query(value = "select * from dating order by time desc", nativeQuery = true)
    public List<Dating> getAllDatings();

    @Query(value = "select * from dating where tag_id = :tagId order by time desc", nativeQuery = true)
    public List<Dating> getDatingsByTag(@Param("tagId") int tagId);

    @Modifying
    @Query(value = "delete from t_participant_user where dating_id = :datingId and user_id = :userId", nativeQuery = true)
    public void leaveDatingByOne(@Param("datingId") int datingId, @Param("userId") int userId);

    @Query(value = "select * from t_participant_user, dating where t_participant_user.dating_id = dating.id and t_participant_user.user_id = :userId " +
            "order by dating.time desc",nativeQuery = true)
    public List<Dating> getDatingsByUserId(@Param("userId") int userId);
}
