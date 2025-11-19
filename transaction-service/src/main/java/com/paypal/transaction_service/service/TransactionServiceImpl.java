package com.paypal.transaction_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.transaction_service.entity.Transactions;
import com.paypal.transaction_service.kafka.KafkaEventProducer;
import com.paypal.transaction_service.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService{

    private final TransactionRepository repository;
    private final ObjectMapper mapper;
    private final ObjectMapper objectMapper;
    private final KafkaEventProducer kafkaEventProducer;

    public TransactionServiceImpl(TransactionRepository repository, ObjectMapper mapper, ObjectMapper objectMapper, KafkaEventProducer kafkaEventProducer) {
        this.repository = repository;
        this.mapper = mapper;
        this.objectMapper = objectMapper;
        this.kafkaEventProducer = kafkaEventProducer;
    }

    @Override
    public Transactions createTransaction(Transactions request) {

        Long senderId = request.getSenderId();
        Long receiverId = request.getReceiverId();
        Double amount = request.getAmount();

        Transactions transaction = new Transactions();
        transaction.setSenderId(senderId);
        transaction.setReceiverId(receiverId);
        transaction.setAmount(amount);
        transaction.setTimeStamp(LocalDateTime.now());
        transaction.setStatus("SUCCESS");
        Transactions saved = repository.save(transaction);
        try{
            String key = String.valueOf(saved.getId());
            kafkaEventProducer.sendTransactionEvent(key,saved);
            System.out.println("Kafka message sent");
        }
        catch (Exception e){
            System.err.println("Failed to send Kafka event: "+e.getMessage());
            e.printStackTrace();
        }

        return saved;
    }

    @Override
    public List<Transactions> getAllTransactions() {
        return repository.findAll();
    }
}
