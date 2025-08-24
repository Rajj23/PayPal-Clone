package com.paypal.transaction_service.controller;

import com.paypal.transaction_service.entity.Transaction;
import com.paypal.transaction_service.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody Transaction transaction){

        Transaction created = service.createTransaction(transaction);

        return ResponseEntity.ok(created);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Transaction>> getAll(){
        return ResponseEntity.ok(service.getAllTransaction());
    }

}
