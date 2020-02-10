package com.example.demo.controllers.restcontrollers;

import com.example.demo.exceptions.DuplicateIdempotencyKeyException;
import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.exceptions.InsufficientFundsException;
import com.example.demo.models.transaction.*;
import com.example.demo.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/transaction")

public class TransactionController {

    private TransactionService transactionService;
    private TransactionMapper transactionMapper;


    @Autowired
    public TransactionController(TransactionService transactionService, TransactionMapper transactionMapper) {
        this.transactionService = transactionService;
        this.transactionMapper = transactionMapper;
    }

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    public List<Transaction> getTransactionsByWalletId(@PathVariable int id) {
        try {
            return transactionService.getTransactionsbyWalletId(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PostMapping("/internal")
    public Internal createInternal(@RequestBody @Valid TransactionDTO transactionDTO) {
        try {
            return transactionService.createInternal(transactionDTO);
        } catch (DuplicateIdempotencyKeyException | EntityNotFoundException | InsufficientFundsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PostMapping("/withdrawal")
    public Withdrawal createWithdrawal(@RequestBody @Valid TransactionDTO transactionDTO) {
        try {
            return transactionService.createWithdrawal(transactionDTO);
        } catch (DuplicateIdempotencyKeyException | EntityNotFoundException | InsufficientFundsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PostMapping("/deposit")
    public Deposit createDeposit(@RequestBody @Valid TransactionDTO transactionDTO) {
        try {
            return transactionService.createDeposit(transactionDTO);
        } catch (DuplicateIdempotencyKeyException | EntityNotFoundException | HttpClientErrorException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PostMapping("/walletstransaction")
    public Internal createWalletsTransaction(@RequestBody @Valid TransactionDTO transactionDTO) {
        try {
            return transactionService.createInternal(transactionDTO);
        } catch (DuplicateIdempotencyKeyException | EntityNotFoundException | HttpClientErrorException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }
}

