package org.demo.DAO;

import java.util.Date;
import java.util.List;

import org.demo.Entity.Message;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MessageDAO extends CrudRepository<Message, Integer> {
    @Query(value = "select * from message where user_id = :userId order by time desc", nativeQuery = true)
    public List<Message> getMessagesByUser(@Param("userId") int userId);

    @Query(value = "select * from message where user_id = :userId and be_read = 0", nativeQuery = true)
    public List<Message> getUnreadMessagesCount(@Param("userId") int userId);

    @Modifying
    @Query(value = "insert into message (user_id, voice_id, comment_id, time, be_read) values(:userId, :voiceId, :commentId, :ttime, 0)", nativeQuery = true)
    public void addMessage(@Param("userId") int userId, @Param("voiceId") int voiceId, @Param("commentId") int commentId, @Param("ttime") Date time);

    @Modifying
    @Query(value = "update message set be_read = 1 where user_id = :userId and comment_id = :commentId ", nativeQuery = true)
    public void readMessage(@Param("userId") int userId, @Param("commentId") int commentId);
}
