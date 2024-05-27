package com.aninfo.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long code;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private Double amount;

    @ManyToOne
    @JoinColumn(name = "account_cbu")
    private Account account;
    
    public Transaction(){
    }

    public Transaction(TransactionType type, Double amount, Account account) {
        this.type = type;
        this.amount = amount;
        this.account = account;
    }

    public Long getCode() {
        return code;
    }

    public TransactionType getType() {
        return type;
    }

    public Double getAmount() {
        return amount;
    }

    public Account getAccount() {
        return account;
    }

}