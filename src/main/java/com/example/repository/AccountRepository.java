package com.example.repository;

import com.example.entity.Account;
import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    
    @Query("From Account WHERE username = :username")
    Account findByUsername(@Param("username") String username);

    Account findByAccountId(int accountId);
    

}
