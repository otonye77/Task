package com.dot.Dot.Task.repository;

import com.dot.Dot.Task.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(String accountNumber);
    boolean existsByAccountNumber(String accountNumber);
    List<Account> findByBalanceLessThan(BigDecimal amount);
    List<Account> findByBalanceGreaterThanEqual(BigDecimal amount);
}
