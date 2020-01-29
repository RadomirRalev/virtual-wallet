package com.example.demo.services;

import com.example.demo.exceptions.InsufficientFundsException;
import com.example.demo.models.transaction.*;
import com.example.demo.models.wallet.Wallet;
import com.example.demo.repositories.CardDetailsRepository;
import com.example.demo.repositories.TransactionRepository;
import com.example.demo.repositories.WalletRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Transaction> getTransactionsbyWalletId(int id) {
        return transactionRepository.getTransactionsbyWalletId(id);
    }

    @Override
    public Deposit createDeposit(TransactionDTO transactionDTO) {
        Deposit deposit = transactionMapper.createDeposit(transactionDTO);
        communicateWithApi(deposit);
        List<Wallet> receiverList = deposit.getCardSender()
                .getUser()
                .getWallets();
        int receiverId = receiverList.get(0).getId();
        int balanceReceiver = walletRepository.getById(receiverId).getBalance() + deposit.getAmount();
        //TODO Simplify lines 51-55
        return transactionRepository.createDeposit(deposit, balanceReceiver, receiverId);
    }

    @Override
    public Internal createInternal(TransactionDTO transactionDTO) {
        Internal internal = transactionMapper.createInternalTransaction(transactionDTO);
        if (internal.getSender().getBalance() - internal.getAmount() < 0) {
            throw new InsufficientFundsException(SENDER_FUNDS_ARE_NOT_SUFFICIENT);
        }
        int senderId = internal.getSender().getId();
        int receiverId = internal.getReceiver().getId();
        int balanceSender = internal.getSender().getBalance() - internal.getAmount();
        int balanceReceiver = internal.getReceiver().getBalance() + internal.getAmount();
        return transactionRepository.createInternal(internal, balanceSender, balanceReceiver, senderId, receiverId);
    }

    @Override
    public Withdrawal createWithdrawal(TransactionDTO transactionDTO) {
        Withdrawal withdrawal = transactionMapper.createWithdrawal(transactionDTO);
        if (withdrawal.getSender().getBalance() - withdrawal.getAmount() < 0) {
            throw new InsufficientFundsException(SENDER_FUNDS_ARE_NOT_SUFFICIENT);
        }
        int senderId = withdrawal.getSender().getId();
        int receiverId = withdrawal.getReceiver().getId();
        //TODO Check if receiver card exists
        int balanceSender = withdrawal.getSender().getBalance() - withdrawal.getAmount();
        return transactionRepository.createWithdrawal(withdrawal, balanceSender, senderId);
    }

//    private void checkIfCardExists(int id) {
//        if (cardDetailsRepository.getById(id) == null) {
//            throw new EntityNotFoundException(CARD_WITH_ID_NOT_EXISTS, id);
//        }
//    }
//
//    private void checkIfWalletExists(int id) {
//        if (walletRepository.getById(id) == null) {
//            throw new EntityNotFoundException(WALLET_WITH_ID_NOT_EXISTS, id);
//        }
//    }
}
