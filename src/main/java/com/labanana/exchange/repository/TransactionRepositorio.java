package com.labanana.exchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.labanana.exchange.entity.Transaction;

public interface TransactionRepositorio extends JpaRepository<Transaction, Long> {

}
