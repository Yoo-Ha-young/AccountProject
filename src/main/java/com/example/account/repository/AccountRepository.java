package com.example.account.repository;

import com.example.account.domain.Account;
import com.example.account.domain.AccountUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository                                              // 엔터티와 프라이머키의 자료형
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findFirstByOrderByIdDesc(); // 인터페이스, 역순으로 ID를 가져옴 쿼리를 생성해서 가져옴

    Integer countByAccountUser(AccountUser accountUser);

    Optional<Account> findByAccountNumber(String AccountNumber); // 인터페이스, 역순으로 ID를 가져옴 쿼리를 생성해서 가져옴

    List<Account> findByAccountUser(AccountUser accountUser);
}
