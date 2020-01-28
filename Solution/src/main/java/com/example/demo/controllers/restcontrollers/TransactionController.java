package com.example.demo.controllers.restcontrollers;

import com.example.demo.exceptions.*;
import com.example.demo.models.card.CardDetails;
import com.example.demo.models.transaction.*;
import com.example.demo.models.user.User;
import com.example.demo.models.user.UserRegistrationDTO;
import com.example.demo.services.TransactionService;
import org.apache.catalina.filters.ExpiresFilter;
import org.json.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerErrorException;

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

    @PostMapping("/internal")
    public Internal createInternal(@RequestBody @Valid TransactionDTO transactionDTO) {
        try {
            return transactionService.createInternal(transactionDTO);
        } catch (EntityNotFoundException | InsufficientFundsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PostMapping("/withdrawal")
    public Withdrawal createWithdrawal(@RequestBody @Valid TransactionDTO transactionDTO) {
        try {
            return transactionService.createWithdrawal(transactionDTO);
        } catch (EntityNotFoundException | InsufficientFundsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PostMapping("/deposit")
    public Deposit createDeposit(@RequestBody @Valid TransactionDTO transactionDTO) {
        final String uri = "http://localhost:8081/payment";
        Deposit deposit = transactionMapper.createDeposit(transactionDTO);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-api-key", "apiKey");
        HttpEntity<Deposit> entityRequest = new HttpEntity<>(deposit, headers);
        try {
            ResponseEntity<Integer> response =
                    restTemplate.exchange(uri, HttpMethod.POST, entityRequest, Integer.class);
        } catch (HttpClientErrorException e) {
            System.out.println(e.getStatusText());
        }
//        System.out.println(response.getStatusCode().value());
//        int statusCode = response.getStatusCode().value();
//        if(statusCode == 200) {
//            try {
//                return deposit;
//            } catch (DuplicateEntityException | InvalidOptionalFieldParameter | InvalidPasswordException e) {
//                throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
//            }
//        } else if (statusCode == 500) {
//            System.out.println("Yes");
//        }
        return deposit;
    }
}
