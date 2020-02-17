package com.example.demo.controllers.restcontrollers;

import com.example.demo.exceptions.DuplicateIdempotencyKeyException;
import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.exceptions.InsufficientFundsException;
import com.example.demo.exceptions.InvalidPermission;
import com.example.demo.models.transaction.*;
import com.example.demo.services.contracts.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

import static com.example.demo.helpers.UserHelper.currentPrincipalName;

@RestController
@RequestMapping("api/transaction")

public class TransactionController {

    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<Transaction> getAllTransactions(@RequestParam(required = false, defaultValue = "1") Integer page) {
        return transactionService.getAllTransactions(page);
    }

    @GetMapping("/{id}")
    public List<Transaction> getTransactionsByWalletId(@PathVariable int id, int page) {
        try {
            return transactionService.getTransactionsbyWalletId(id, page);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PostMapping("/internal")
    public Internal createInternal(@RequestBody @Valid TransactionDTO transactionDTO) {
        try {
            return transactionService.createInternal(transactionDTO, currentPrincipalName());
        } catch (DuplicateIdempotencyKeyException | EntityNotFoundException | InsufficientFundsException | InvalidPermission e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PostMapping("/withdrawal")
    public Transaction createWithdrawal(@RequestBody @Valid TransactionDTO transactionDTO) {
        try {
            return transactionService.createWithdrawal(transactionDTO, currentPrincipalName());
        } catch (DuplicateIdempotencyKeyException | EntityNotFoundException | InsufficientFundsException | InvalidPermission e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PostMapping("/deposit")
    public Transaction createDeposit(@RequestBody @Valid TransactionDTO transactionDTO) {
        try {
            return transactionService.createDeposit(transactionDTO, currentPrincipalName());
        } catch (DuplicateIdempotencyKeyException | EntityNotFoundException | HttpClientErrorException | InvalidPermission e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PostMapping("/walletstransaction")
    public Internal createWalletsTransaction(@RequestBody @Valid TransactionDTO transactionDTO) {
        try {
            return transactionService.createInternal(transactionDTO, currentPrincipalName());
        } catch (DuplicateIdempotencyKeyException | EntityNotFoundException | HttpClientErrorException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }
}

