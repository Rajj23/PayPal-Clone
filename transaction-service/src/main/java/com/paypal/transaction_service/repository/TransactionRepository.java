package com.paypal.transaction_service.repository;

import com.paypal.transaction_service.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransactionRepository extends JpaRepository<Transactions,Long> {
}
