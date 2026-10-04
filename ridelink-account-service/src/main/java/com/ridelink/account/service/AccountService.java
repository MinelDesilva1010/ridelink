package com.ridelink.account.service;

import com.ridelink.account.model.Account;
import com.ridelink.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account registerAccount(Account account) {
        if (accountRepository.findByEmail(account.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        return accountRepository.save(account);
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}
