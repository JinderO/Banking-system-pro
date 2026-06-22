package com.jindero.banking.features.account;

import com.jindero.banking.features.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

  Account findByAccountNumber(String accountNumber);
  List<Account> findByUser(User user);
  Optional<Account> findByAccountNumberAndUserId(String accountNumber, UUID user_id);

}
