package com.example.demo.services.implementations;

import com.example.demo.exceptions.DuplicateIdempotencyKeyException;
import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.exceptions.InsufficientFundsException;
import com.example.demo.exceptions.InvalidPermission;
import com.example.demo.models.transaction.*;
import com.example.demo.models.wallet.Wallet;
import com.example.demo.repositories.contracts.CardDetailsRepository;
import com.example.demo.repositories.contracts.TransactionFilterRepository;
import com.example.demo.repositories.contracts.TransactionRepository;
import com.example.demo.repositories.contracts.WalletRepository;
import com.example.demo.services.contracts.TransactionService;
import com.example.demo.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.example.demo.constants.ExceptionConstants.*;
import static com.example.demo.helpers.ApiCommunication.communicateWithApi;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;
    private TransactionFilterRepository transactionFilterRepository;
    private WalletRepository walletRepository;
    private UserService userService;
    private TransactionMapper transactionMapper;
    private CardDetailsRepository cardDetailsRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionMapper transactionMapper,
                                  WalletRepository walletRepository, CardDetailsRepository cardDetailsRepository,
                                  UserService userService, TransactionFilterRepository transactionFilterRepository) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.userService = userService;
        this.walletRepository = walletRepository;
        this.cardDetailsRepository = cardDetailsRepository;
        this.transactionFilterRepository = transactionFilterRepository;
    }

    @Override
    public List<Transaction> getAllTransactions(int page) {
        return transactionRepository.getTransactions(page);
    }

    @Override
    public List<Transaction> getTransactionsByUserId(int userId, int page) {
        if (!checkIfUserIdExists(userId)) {
            throw new EntityNotFoundException(USER_ID_NOT_FOUND, userId);
        }
        return transactionRepository.getTransactionsByUserId(userId, page);
    }

    @Override
    public List<Transaction> getTransactionsbyWalletId(int walletId, int page) {
        if (!checkIfWalletIdExists(walletId)) {
            throw new EntityNotFoundException(WALLET_WITH_ID_NOT_EXISTS, walletId);
        }
        return transactionRepository.getTransactionsbyWalletId(walletId, page);
    }

    @Override
    public Deposit createDeposit(TransactionDTO transactionDTO, String sender) {
        if (userService.isBlocked(sender)) {
            throw new InvalidPermission(SENDER_IS_BLOCKED, sender);
        }

        Deposit deposit = getDeposit(transactionDTO);
        if (checkIfIdempotencyKeyExists(deposit.getIdempotencyKey())) {
            throw new DuplicateIdempotencyKeyException(YOU_CANNOT_MAKE_THE_SAME_TRANSACTION_TWICE);
        }
        communicateWithApi(deposit);
        int receiverId = deposit.getReceiver().getId();
        if (!checkIfWalletIdExists(receiverId)) {
            throw new EntityNotFoundException(WALLET_WITH_ID_NOT_EXISTS, receiverId);
        }
        double balanceReceiver = walletRepository.getWalletById(receiverId).getBalance() + deposit.getAmount();
        return transactionRepository.createDeposit(deposit, balanceReceiver, receiverId);
    }

    @Override
    public Internal createInternal(TransactionDTO transactionDTO, String sender) {
        if (userService.isBlocked(sender)) {
            throw new InvalidPermission(SENDER_IS_BLOCKED, sender);
        }

        if (userService.isBlocked(transactionDTO.getReceiverName())) {
            throw new InvalidPermission(RECEIVER_IS_BLOCKED, transactionDTO.getReceiverName());
        }

        Internal internal = getInternal(transactionDTO);
        if (checkIfIdempotencyKeyExists(internal.getIdempotencyKey())) {
            throw new DuplicateIdempotencyKeyException(YOU_CANNOT_MAKE_THE_SAME_TRANSACTION_TWICE);
        }
        checkIfFundsAreEnough(internal.getSender(), internal.getAmount());
        int senderId = internal.getSender().getId();
        if (!checkIfWalletIdExists(senderId)) {
            throw new EntityNotFoundException(WALLET_WITH_ID_NOT_EXISTS, senderId);
        }
        int receiverId = internal.getReceiver().getId();
        if (!checkIfWalletIdExists(receiverId)) {
            throw new EntityNotFoundException(WALLET_WITH_ID_NOT_EXISTS, receiverId);
        }
        double balanceSender = internal.getSender().getBalance() - internal.getAmount();
        double balanceReceiver = internal.getReceiver().getBalance() + internal.getAmount();
        return transactionRepository.createInternal(internal, balanceSender, balanceReceiver, senderId, receiverId);
    }

    @Override
    public Withdrawal createWithdrawal(TransactionDTO transactionDTO, String sender) {
        if (userService.isBlocked(sender)) {
            throw new InvalidPermission(SENDER_IS_BLOCKED, sender);
        }
        Withdrawal withdrawal = getWithdrawal(transactionDTO);

        checkIfFundsAreEnough(withdrawal.getSender(), withdrawal.getAmount());
        int senderId = withdrawal.getSender().getId();
        if (!checkIfWalletIdExists(senderId)) {
            throw new EntityNotFoundException(WALLET_WITH_ID_NOT_EXISTS, senderId);
        }
        int receiverId = withdrawal.getReceiver().getId();
        if (!checkIfCardIdExists(receiverId)) {
            throw new EntityNotFoundException(CARD_WITH_ID_NOT_EXISTS, receiverId);
        }
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
    public List<Transaction> getFilteredTransactions(String direction, String startDate, String endDate, String recipientSearchString, int userId, int page, String sort) {
        if ((!startDate.isEmpty() && !endDate.isEmpty()) && recipientSearchString.isEmpty() && direction.isEmpty()) {
            return getTransactionsByDate(direction, startDate, endDate, userId, page, sort);
        }
        if ((startDate.isEmpty() && endDate.isEmpty()) && !recipientSearchString.isEmpty()) {
            return getTransactionsByRecipient(direction, recipientSearchString, userId, page, sort);
        }
        if (!startDate.isEmpty() && !endDate.isEmpty() && !recipientSearchString.isEmpty()) {
            return getTransactionsByRecipientAndDate(direction, startDate, endDate, recipientSearchString, userId, page, sort);
        }
        if (!startDate.isEmpty() && !endDate.isEmpty() && recipientSearchString.isEmpty() && !direction.isEmpty()) {
            return getTransactionsByDate(direction, startDate, endDate, userId, page, sort);
        }
        if (startDate.isEmpty() && endDate.isEmpty() && recipientSearchString.isEmpty() && !direction.isEmpty()) {
            return getTransactionsByDirection(direction, userId, page, sort);
        }
        return getTransactionsByUserId(userId, page);
    }

    @Override
    public List<Transaction> getFilteredTransactionsAdmin(String direction,
                                                          String startDate,
                                                          String endDate,
                                                          String senderSearchString,
                                                          String recipientSearchString,
                                                          int userId,
                                                          int page,
                                                          String sort) {
        if ((!startDate.isEmpty() && !endDate.isEmpty()) && recipientSearchString.isEmpty() && senderSearchString.isEmpty()) {
            return getTransactionsByDate(direction, startDate, endDate, userId, page, sort);
        }
        if ((startDate.isEmpty() && endDate.isEmpty()) && !recipientSearchString.isEmpty() && senderSearchString.isEmpty()) {
            return getTransactionsByRecipient(direction, recipientSearchString, userId, page, sort);
        }
        if ((startDate.isEmpty() && endDate.isEmpty()) && recipientSearchString.isEmpty() && !senderSearchString.isEmpty()) {
            return getTransactionsByRecipient(direction, senderSearchString, userId, page, sort);
        }
        if (!startDate.isEmpty() && !endDate.isEmpty()) {
            return getTransactionsByDirection(direction, userId, page, sort);
        }
        return getAllTransactions(page);
    }

    private List<Transaction> getTransactionsByRecipientAndDate(String direction, String start, String end, String recipientSearchString, int userId, int page, String sort) {
        LocalDate startDate = parseDate(start);
        LocalDate endDate = parseDate(end);
        return transactionFilterRepository.getTransactionsByUserId(direction, startDate, endDate, recipientSearchString, userId, page, sort);
    }

    public List<Transaction> getTransactionsByDate(String direction, String start, String end, int userId, int page, String sort) {
        LocalDate startDate = parseDate(start);
        LocalDate endDate = parseDate(end);
        return transactionFilterRepository.getTransactionsByUserId(direction, startDate, endDate, userId, page, sort);
    }

    private List<Transaction> getTransactionsByRecipient(String direction, String recipientSearchString, int userId, int page, String sort) {
        return transactionFilterRepository.getTransactionsByUserId(direction, recipientSearchString, userId, page, sort);
    }

    private List<Transaction> getTransactionsByDirection(String direction, int userId, int page, String sort) {
        return transactionFilterRepository.getTransactionsByUserId(direction, userId, page, sort);
    }

    private LocalDate parseDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(dateString, formatter);
    }

    public void checkIfFundsAreEnough(Wallet sender, double amount) {
        if (sender.getBalance() - amount < 0) {
            throw new InsufficientFundsException(SENDER_FUNDS_ARE_NOT_SUFFICIENT);
        }
    }

    public boolean checkIfIdempotencyKeyExists(String idempotencyKey) {
        return transactionRepository.checkIfIdempotencyKeyExists(idempotencyKey);
    }

    public boolean checkIfWalletIdExists(int walletId) {
        return walletRepository.checkIfWalletIdExists(walletId);
    }

    public boolean checkIfUserIdExists(int userId) {
        return userService.checkIfUserIdExists(userId);
    }

    public boolean checkIfCardIdExists(int cardId) {
        return cardDetailsRepository.checkIfCardIdExists(cardId);
    }
}
