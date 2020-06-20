package org.demo.DAO;

import org.demo.Entity.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentDAO extends CrudRepository<Comment,Integer>{
	@Query(value = "select * from comment where belong_id = :voiceId order by floor", nativeQuery = true)
    public List<Comment> getCommentsByVoiceId(@Param("voiceId")int voiceId);

    @Query(value = "select * from comment where belong_id = :voiceId order by floor", nativeQuery = true)
    public List<Comment> test(@Param("voiceId") int voiceId);

    @Query(value = "select * from comment where belong_id = :voiceId and floor = :floor",nativeQuery = true)
    public List<Comment> getTheNewCommentByVoiceFloor(@Param("voiceId") int voiceId, @Param("floor")int floor);
}
