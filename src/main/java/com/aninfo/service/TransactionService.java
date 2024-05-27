package com.aninfo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aninfo.model.Account;
import com.aninfo.model.Transaction;
import com.aninfo.model.TransactionType;
import com.aninfo.repository.AccountRepository;
import com.aninfo.repository.TransactionRepository;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Optional<Transaction> getTransaction(Long code) {
        return transactionRepository.findById(code);
    }

    public void deleteTransaction(Long code) {
        Transaction transaction = transactionRepository.findById(code).orElse(null);
        Account account = transaction.getAccount();

        if (transaction.getType() == TransactionType.DEPOSIT) {
            account.setBalance(account.getBalance() - transaction.getAmount());
        } else {
            account.setBalance(account.getBalance() + transaction.getAmount());
        }

        accountRepository.save(account);
        transactionRepository.deleteById(code);
    }

    public List<Transaction> getAccountTransactions(Long cbu) {
        return transactionRepository.findByAccountCbu(cbu);
    }

}
