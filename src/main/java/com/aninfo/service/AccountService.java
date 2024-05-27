package com.aninfo.service;

import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aninfo.exceptions.DepositNegativeSumException;
import com.aninfo.exceptions.InsufficientFundsException;
import com.aninfo.model.Account;
import com.aninfo.model.Transaction;
import com.aninfo.model.TransactionType;
import com.aninfo.repository.AccountRepository;


@Service
public class AccountService {

    @Autowired
    private TransactionService transactionService;
    
    @Autowired
    private AccountRepository accountRepository;

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Collection<Account> getAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> findById(Long cbu) {
        return accountRepository.findById(cbu);
    }

    public void save(Account account) {
        accountRepository.save(account);
    }

    public void deleteById(Long cbu) {
        accountRepository.deleteById(cbu);
    }

    @Transactional
    public Account withdraw(Long cbu, Double sum) {
        Account account = accountRepository.findAccountByCbu(cbu);

        if (account.getBalance() < sum) {
            throw new InsufficientFundsException("Insufficient funds");
        }

        Transaction transaction = new Transaction( 
            TransactionType.WITHDRAW,
            sum,
            account);

        transactionService.createTransaction(transaction);

        account.setBalance(account.getBalance() - sum);
        accountRepository.save(account);

        return account;
    }

    @Transactional
    public Account deposit(Long cbu, Double sum) {

        if (sum <= 0) {
            throw new DepositNegativeSumException("Cannot deposit negative sums");
        }

        if (sum >= 2000) {
            double bonus = sum * 0.1;
            if (bonus > 500) {
                bonus = 500;
            }
            sum = sum + bonus;
        }

        Account account = accountRepository.findAccountByCbu(cbu);

        Transaction transaction = new Transaction( 
            TransactionType.DEPOSIT,
            sum,
            account);

        transactionService.createTransaction(transaction);

        account.setBalance(account.getBalance() + sum);
        accountRepository.save(account);

        return account;
    }

}
