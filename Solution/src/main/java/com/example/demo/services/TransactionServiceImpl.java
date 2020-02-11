package com.example.demo.services;

import com.example.demo.exceptions.DuplicateIdempotencyKeyException;
import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.exceptions.InsufficientFundsException;
import com.example.demo.models.transaction.*;
import com.example.demo.models.wallet.Wallet;
import com.example.demo.repositories.CardDetailsRepository;
import com.example.demo.repositories.TransactionRepository;
import com.example.demo.repositories.WalletRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.constants.ExceptionConstants.*;
import static com.example.demo.helpers.ApiCommunication.communicateWithApi;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;
    private CardDetailsRepository cardDetailsRepository;
    private WalletRepositoryImpl walletRepository;
    private TransactionMapper transactionMapper;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, CardDetailsRepository cardDetailsRepository,
                                  TransactionMapper transactionMapper, WalletRepositoryImpl walletRepository) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.cardDetailsRepository = cardDetailsRepository;
        this.walletRepository = walletRepository;
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.getTransactions();
    }

    @Override
    public List<Transaction> getTransactionsByUserId(int userId) {
        List<Wallet> wallets = walletRepository.getWalletsbyUserId(userId);
        List<Transaction> result = new ArrayList<>();
        for (Wallet wallet : wallets) {
            result.addAll(transactionRepository.getTransactionsbyWalletId(wallet.getId()));
        }
        return result;
    }

    @Override
    public List<Transaction> getTransactionsbyWalletId(int id) {
        if (checkIfWalletIdExists(id)) {
                throw new EntityNotFoundException(WALLET_WITH_ID_NOT_EXISTS, id);
            }
        return transactionRepository.getTransactionsbyWalletId(id);
    }

    @Override
    public Deposit createDeposit(TransactionDTO transactionDTO) {
        Deposit deposit = getDeposit(transactionDTO);
        if (checkIfIdempotencyKeyExists(deposit.getIdempotencyKey())) {
            throw new DuplicateIdempotencyKeyException(YOU_CANNOT_MAKE_THE_SAME_TRANSACTION_TWICE);
        }
        communicateWithApi(deposit);
        List<Wallet> receiverList = deposit.getSender()
                .getUser()
                .getWallets();
        int receiverId = deposit.getReceiver().getId();
        double balanceReceiver = walletRepository.getById(receiverId).getBalance() + deposit.getAmount();
        //TODO Simplify lines 51-55
        return transactionRepository.createDeposit(deposit, balanceReceiver, receiverId);
    }

    @Override
    public Internal createInternal(TransactionDTO transactionDTO) {
        Internal internal = getInternal(transactionDTO);
        if (checkIfIdempotencyKeyExists(internal.getIdempotencyKey())) {
            throw new DuplicateIdempotencyKeyException(YOU_CANNOT_MAKE_THE_SAME_TRANSACTION_TWICE);
        }
        checkIfFundsAreEnough(internal.getSender(), internal.getAmount());
        int senderId = internal.getSender().getId();
        int receiverId = internal.getReceiver().getId();
        double balanceSender = internal.getSender().getBalance() - internal.getAmount();
        double balanceReceiver = internal.getReceiver().getBalance() + internal.getAmount();
        return transactionRepository.createInternal(internal, balanceSender, balanceReceiver, senderId, receiverId);
    }

    @Override
    public Withdrawal createWithdrawal(TransactionDTO transactionDTO) {
        Withdrawal withdrawal = getWithdrawal(transactionDTO);
        checkIfFundsAreEnough(withdrawal.getSender(), withdrawal.getAmount());
        int senderId = withdrawal.getSender().getId();
        int receiverId = withdrawal.getReceiver().getId();
        //TODO Check if receiver card exists
        double balanceSender = withdrawal.getSender().getBalance() - withdrawal.getAmount();
        return transactionRepository.createWithdrawal(withdrawal, balanceSender, senderId);
    }

    @Override
    public Withdrawal getWithdrawal(TransactionDTO transactionDTO) {
        return transactionMapper.createWithdrawal(transactionDTO);
    }

    @Override
    public Deposit getDeposit(TransactionDTO transactionDTO) {
        return transactionMapper.createDeposit(transactionDTO);
    }

    @Override
    public Internal getInternal(TransactionDTO transactionDTO) {
        return transactionMapper.createInternalTransaction(transactionDTO);
    }

    @Override
    public boolean checkIfIdempotencyKeyExists(String idempotencyKey) {
        return transactionRepository.checkIfIdempotencyKeyExists(idempotencyKey);
    }

    @Override
    public boolean checkIfWalletIdExists(int id) {
        return walletRepository.checkIfWalletIdExists(id);
    }

    @Override
    public void checkIfFundsAreEnough(Wallet sender, double amount) {
        if (sender.getBalance() - amount < 0) {
            throw new InsufficientFundsException(SENDER_FUNDS_ARE_NOT_SUFFICIENT);
        }
    }
    @Override
    public List<Transaction> getFilteredTransactions(String direction, String startDate, String endDate, String recipientSearchString, int userId) {
        if ((!startDate.isEmpty() && !endDate.isEmpty()) && recipientSearchString.isEmpty()) {
            return getTransactionsByDate(direction, startDate, endDate, userId);
        }
        if ((startDate.isEmpty() && endDate.isEmpty()) && !recipientSearchString.isEmpty()) {
            return getTransactionsByRecipient(direction, recipientSearchString, userId);
        }
        if (!startDate.isEmpty() && !endDate.isEmpty()) {
            return getTransactionsByRecipientAndDate(direction, startDate, endDate, recipientSearchString, userId);
        }
        return getTransactionsByUserId(userId);
    }


    @Override
    public List<Transaction> getTransactionsByDate(String direction, String start, String end, int userId) {
        LocalDate startDate = parseDate(start);
        LocalDate endDate = parseDate(end);
        return transactionRepository.getTransactionsByUserId(direction, startDate, endDate, userId);
    }

    @Override
    public List<Transaction> getTransactionsByRecipient(String direction, String recipientSearchString, int userId) {
        return transactionRepository.getTransactionsByUserId(direction, recipientSearchString, userId);
    }

    public List<Transaction> getTransactionsByRecipientAndDate(String direction, String start, String end, String recipientSearchString, int userId) {
        LocalDate startDate = parseDate(start);
        LocalDate endDate = parseDate(end);
        return transactionRepository.getTransactionsByUserId(direction, startDate, endDate, recipientSearchString, userId);
    }

    private LocalDate parseDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(dateString, formatter);
    }
}
