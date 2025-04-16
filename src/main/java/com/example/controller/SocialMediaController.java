package com.example.controller;

import java.util.*;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController

public class SocialMediaController {
    /*
     * @GetMapping("/example401")
            public ResponseEntity example(){
            return ResponseEntity.status(401).body("Unauthorized resource!");
        }
     * 
     * 
     */
    MessageService messageService;
    AccountService accountService;

    public SocialMediaController(MessageService ms, AccountService as) {
        this.messageService = ms;
        this.accountService = as;
    }

    //1: account registration
    @PostMapping("register")
    public ResponseEntity<Account> registerUser(@RequestBody Account account) {
        //Todo: register them here
        String username = account.getUsername();
        String password = account.getPassword();
        /* Response status 400: any other reasons such as password */
        if (username == null || username.trim().isEmpty() || password == null || password.length() < 4) {
            return ResponseEntity.badRequest().build();
        }
        /*Status code 409 case: user exists */
        if (accountService.registerDuplicateUserCheck(account) == 1) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Account newAccount = accountService.register(account);
        return ResponseEntity.ok(newAccount);
    }

    //2: Login
    @PostMapping("login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        
        Account found = accountService.loginService(account.getUsername(), account.getPassword());
        if (found != null) {
            return ResponseEntity.ok(found); 
        } else {
            return ResponseEntity.status(401).build(); 
        }
        //return ResponseEntity.status(401).build(); 
    }

    //3: create message
    @PostMapping("messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message savedMessage = messageService.createMessageService(message);
        if (savedMessage != null) {
            return ResponseEntity.ok(savedMessage); 
        } else {
            return ResponseEntity.badRequest().build(); 
        }
    }
    

    //4: get All Messages
    @GetMapping("messages")
    public ResponseEntity<List<Message>> retrieveAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessage());
    }

    //5: get message by its ID
    //how to "extreact" messageId? 
    @GetMapping("messages/{messageId}")
    public ResponseEntity<Message> retrieveMessageById(@PathVariable int messageId) {
        return ResponseEntity.ok(messageService.getMessageById(messageId).orElse(null));
    }

    //6: Delete Message by its ID
    @DeleteMapping("messages/{messageId}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable int messageId) {
        int deletedCheck = messageService.deleteMessageById(messageId);
        if (deletedCheck == 1) {
            return ResponseEntity.ok(deletedCheck); 
        } else {
            return ResponseEntity.ok().build(); 
        }
    }

    //7: updateText
    @PatchMapping("messages/{messageId}")
    public ResponseEntity<Integer> updateMessage(@PathVariable int messageId, @RequestBody String text) throws JsonProcessingException {
        ObjectMapper ob = new ObjectMapper();
        String test = ob.readTree(text).path("messageText").asText();
        // text = messageText: ,
        int updateCheck = messageService.updateMessageText(messageId, test);
        if (updateCheck == 1) {
            return ResponseEntity.ok(1);
            //return ResponseEntity.status(400).build();
        }
        return ResponseEntity.status(400).build(); //    what if text == "\n"
    }

    //8: Retrieve all messages by user ID
    @GetMapping("accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByAccountId(@PathVariable int accountId) {
        List<Message> messages = messageService.getMessagesByAccountId(accountId);
        return ResponseEntity.ok(messages);
    }



    

}
