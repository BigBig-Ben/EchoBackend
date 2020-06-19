package org.demo.DAO;

import java.util.List;

import org.demo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
 
public interface UserDAO extends CrudRepository<User,Integer>{
	@Query(value="select u from User u where u.openId = :openid")
	public List<User> checkOpenid(@Param("openid") String openid);

	@Modifying
	@Query(value = "delete from t_voice_like where user_id = :userId and voice_id = :voiceId", nativeQuery = true)
	public void dislikeVoice(@Param("userId") int userId, @Param("voiceId") int voiceId);

	@Modifying
	@Query(value = "insert into t_voice_like values(:userId, :voiceId)", nativeQuery = true)
	public void likeVoice(@Param("userId") int userId, @Param("voiceId") int voiceId);

	@Modifying
	@Query(value = "delete from t_comment_like where user_id = :userId and comment_id = :commentId", nativeQuery = true)
	public void dislikeComment(@Param("userId") int userId, @Param("commentId") int commentId);

	@Modifying
	@Query(value = "insert into t_comment_like values(:userId, :commentId)", nativeQuery = true)
	public void likeComment(@Param("userId") int userId, @Param("commentId") int commentId);
}