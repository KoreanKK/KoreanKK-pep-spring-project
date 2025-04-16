package com.example.service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    MessageRepository messageRepository;
    AccountRepository accountRepository;
    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    //3: Create Message
    public Message createMessageService(Message message) {
        String text = message.getMessageText();

        if (text == null || text.trim().isEmpty() || text.length() > 255) {
            return null;
        }

        if (accountRepository.findByAccountId(message.getPostedBy()) == null) {
            return null;
        }

        return messageRepository.save(message);
    }

    //4: retrieve all message
    public List<Message> getAllMessage() {
        return messageRepository.findAll();
    }

    //5: Get Message by ID
    public Optional<Message> getMessageById(int id) {
        return messageRepository.findById(id);
    }

    //6: Delete Message By ID
    public int deleteMessageById(int messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return 1;
        } else {
            return 0;
        }
    }
    //7: Update message's text
    public int updateMessageText(int messageId, String text) {
        
        /*if (text == null) {
            return 0;
        } else */
         /* 
        if (text.isEmpty() && text.length() > 255 && !messageRepository.existsById(messageId)) {
            return 0;
        } 
            */
        /*
        else if (!messageRepository.existsById(messageId)) {
            return 0;
        }
            */
            
        if (!text.isEmpty() && text.length() < 256 && messageRepository.existsById(messageId)) {
            Message newMessage = messageRepository.findByMessageId(messageId);
            newMessage.setMessageText(text);
            messageRepository.save(newMessage);

            return 1;
        }
        return 0;
    }

    //8: get all messages by accountId
    public List<Message> getMessagesByAccountId(int accountId) {
        return messageRepository.findAllByPostedBy(accountId);
    }

    public Boolean textChecker(String text) {
        if (text.trim().isBlank() || text.trim().isEmpty() || text.length() > 255 || text == null) {
            return false;
        }
        return true;
    }


}
