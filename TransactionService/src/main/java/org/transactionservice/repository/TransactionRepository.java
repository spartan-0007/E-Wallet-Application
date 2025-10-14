package org.transactionservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.transactionservice.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}
