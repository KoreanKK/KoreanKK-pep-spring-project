package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    AccountRepository accountRepository;
    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /*1: username */
    public Account register(Account account) {
        return accountRepository.save(account);
        
    }

    public int registerDuplicateUserCheck(Account account) {
        if (accountRepository.findByUsername(account.getUsername()) != null) {
            return 1;
        }
        return 0;
    }

    public Account loginService(String username, String password) {
        Account account = accountRepository.findByUsername(username);
        if (account != null && account.getPassword().equals(password)) {
            return account; 
        }
        return null; 
    }

}
