package com.paypal.transaction_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.transaction_service.entity.Transaction;
import com.paypal.transaction_service.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService{

    private final TransactionRepository repository;
    private final ObjectMapper objectMapper;

    @Override
    public Transaction createTransaction(Transaction request) {

        Long senderId = request.getSenderId();
        Long receiverId = request.getReceiverId();
        Double amount = request.getAmount();

        Transaction transaction = new Transaction();
        transaction.setSenderId(senderId);
        transaction.setReceiverId(receiverId);
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setStatus("SUCCESS");

        Transaction saved = repository.save(transaction);


        return saved;
    }

    @Override
    public List<Transaction> getAllTransaction() {
        return repository.findAll() ;
    }
}
