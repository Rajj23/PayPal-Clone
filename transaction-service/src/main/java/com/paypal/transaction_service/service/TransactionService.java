package com.paypal.transaction_service.service;

import com.paypal.transaction_service.entity.Transactions;

import java.util.List;

public interface TransactionService {

    Transactions createTransaction(Transactions transaction);

    List<Transactions> getAllTransactions();
}
