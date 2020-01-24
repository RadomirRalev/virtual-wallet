package com.example.demo.models.transaction;

import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class TransactionMapper {

    private UserService userService;

    @Autowired
    public TransactionMapper(UserService userService) {
        this.userService = userService;
    }

    public Transaction createTransaction(TransactionDTO transactionDTO) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String currentDate = formatter.format(date);

        Transaction transaction = new Transaction();
        transaction.setDate(currentDate);
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setReceiver(userService.getById(transactionDTO.getReceiverId()));
        transaction.setSender(userService.getById(transactionDTO.getSenderId()));
        transaction.setType(transactionDTO.getType());
        return transaction;
    }
}
