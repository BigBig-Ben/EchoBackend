package org.demo.service;

import org.demo.DAO.MessageDAO;
import org.demo.Entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.PublicKey;
import java.util.Date;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageDAO messageDAO;

    @Transactional
    public List<Message> getMessagesByUser(int userId){
        return messageDAO.getMessagesByUser(userId);
    }

    @Transactional
    public int getUnreadMessagesCount(int userId){
        return messageDAO.getUnreadMessagesCount(userId).size();
    }

    @Transactional
    public void addMessage(int userId, int voiceId, int commentId){
        messageDAO.addMessage(userId,voiceId, commentId, new Date());
    }

    @Transactional
    public void readMessage(int userId, int commentId){
        messageDAO.readMessage(userId, commentId);
    }
}
