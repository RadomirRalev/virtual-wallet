package com.example.demo.controllers.restcontrollers;

import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.models.card.CardDetails;
import com.example.demo.models.transaction.Transaction;
import com.example.demo.models.transaction.TransactionDTO;
import com.example.demo.models.user.UserRegistrationDTO;
import com.example.demo.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/transaction")

public class TransactionController {

    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

//    @PostMapping(path = "localhost:8081/payment")
//    public Transaction getById(@RequestBody TransactionDTO transactionDTO ) {
//        try {
//            return transactionService.createTransaction(transactionDTO);
//        } catch (EntityNotFoundException e) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
//        }
//    }

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    public List<Transaction> getTransactionsByWalletId(@PathVariable int id) {
        return transactionService.getTransactionsbyWalletId(id);
    }
}
