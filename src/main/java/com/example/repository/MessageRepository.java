package com.example.repository;

import com.example.entity.Account;
import com.example.entity.Message;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MessageRepository extends JpaRepository<Message, Integer> {



    //All Messages?
    /* 
    @Query("From Message")
    List<Message> getAllMessage();
    */

    //Message By Id? 
    /* 
    @Query("From Message where messageId = :messageIdVar")
    Message getMessageById(@Param("messageIdVar") Long messageId);
    */
    
    List<Message> findAllByPostedBy(int accoumntId);
    Message findByMessageId(int messageId);
    
}
