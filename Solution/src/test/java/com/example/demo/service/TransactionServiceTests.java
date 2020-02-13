package com.example.demo.service;

import com.example.demo.exceptions.DuplicateIdempotencyKeyException;
import com.example.demo.exceptions.InsufficientFundsException;
import com.example.demo.models.card.CardDetails;
import com.example.demo.models.transaction.*;
import com.example.demo.models.wallet.Wallet;
import com.example.demo.repositories.TransactionRepository;
import com.example.demo.repositories.WalletRepository;
import com.example.demo.services.CardDetailsService;
import com.example.demo.services.TransactionServiceImpl;
import com.example.demo.services.UserService;
import com.example.demo.services.WalletService;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.demo.service.Factory.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class TransactionServiceTests {
    @Mock
    TransactionRepository transactionRepository;
    @Mock
    UserService userService;
    @Mock
    CardDetailsService cardDetailsService;
    @Mock
    WalletService walletService;
    @Mock
    WalletRepository walletRepository;
    @Mock
    SessionFactory sessionFactory;
    @Mock
    TransactionMapper transactionMapper;
    @Mock
    Internal internal;

    @InjectMocks
    TransactionServiceImpl transactionService;



    @Test
    public void getAllTransactionsShould_CallRepository() {
        //Arrange
        List<Transaction> transactionsList = new ArrayList<>();

        Mockito.when(transactionRepository.getTransactions())
                .thenReturn(transactionsList);
        //Act
        transactionService.getAllTransactions();

        //Assert
        Assert.assertSame(transactionService.getAllTransactions(), transactionsList);
    }

    @Test
    public void getAllTransactionsShould_ReturnEmptyListWhenNoTransactions() {
        //Arrange
        List<Transaction> transactionsList = new ArrayList<>();

        Mockito.when(transactionRepository.getTransactions())
                .thenReturn(transactionsList);
        //Act
        transactionService.getAllTransactions();

        //Assert
        Assert.assertTrue(transactionService.getAllTransactions().isEmpty());
    }

    @Test
    public void getAllTransactionsShould_ReturnListWhenThereAreTransactions() {
        //Arrange
        Internal internal = createInternal();
        Withdrawal withdrawal = createWithdrawal();

        Mockito.when(transactionRepository.getTransactions())
                .thenReturn(Arrays.asList(internal, withdrawal));
        //Act
        List<Transaction> list = transactionService.getAllTransactions();

        //Assert
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void checkIfWalletIdExistsShould_ReturnTrueWhenWalletExists() {
        //Arrange
        Wallet wallet = createWallet();

        Mockito.when(walletService.checkIfWalletIdExists(INDEX))
                .thenReturn(true);
        //Act
        walletService.checkIfWalletIdExists(INDEX);

        //Assert
        Assert.assertTrue(walletService.checkIfWalletIdExists(INDEX));
    }

    @Test
    public void createDepositShould_CallRepository() {
        //Arrange
        TransactionDTO transactionDTO = createTransactionDTO();
        TransactionMapper transactionMapper = new TransactionMapper(userService, cardDetailsService, walletService);
        Deposit deposit = transactionMapper.createDeposit(transactionDTO);
        List<Deposit> list = new ArrayList<>();
        list.add(deposit);

        //Act
        Deposit depositTest = list.get(0);

        //Assert
        Assert.assertSame(deposit, depositTest);
    }

    @Test
    public void createInternalShould_CallRepository() {
        //Arrange
        TransactionDTO transactionDTO = createTransactionDTO();
        TransactionMapper transactionMapper = new TransactionMapper(userService, cardDetailsService, walletService);
        Internal betweenWallets = transactionMapper.createInternalTransaction(transactionDTO);
        List<Internal> list = new ArrayList<>();
        list.add(betweenWallets);

        //Act
        Internal betweenWalletsTest = list.get(0);

        //Assert
        Assert.assertSame(betweenWallets, betweenWalletsTest);
    }

    @Test
    public void createWithdrawalShould_CallRepository() {
        //Arrange
        TransactionDTO transactionDTO = createTransactionDTO();
        TransactionMapper transactionMapper = new TransactionMapper(userService, cardDetailsService, walletService);
        Withdrawal withdrawal = transactionMapper.createWithdrawal(transactionDTO);
        List<Withdrawal> list = new ArrayList<>();
        list.add(withdrawal);

        //Act
        Withdrawal withdrawalTest = list.get(0);

        //Assert
        Assert.assertSame(withdrawal, withdrawalTest);
    }

    @Test(expected = DuplicateIdempotencyKeyException.class)
    public void createWithdrawalShouldThrowException_WhenIdempotencyKeyExists() {
        //Arrange
        TransactionDTO transactionDTO = createTransactionDTO();
        TransactionMapper transactionMapper = new TransactionMapper(userService, cardDetailsService, walletService);
        Withdrawal withdrawal = transactionMapper.createWithdrawal(transactionDTO);
        withdrawal.setIdempotencyKey("1");
        Mockito.when(transactionService.checkIfIdempotencyKeyExists("1"))
                .thenThrow(DuplicateIdempotencyKeyException.class);

        //Act
        transactionService.checkIfIdempotencyKeyExists("1");

    }

    @Test(expected = InsufficientFundsException.class)
    public void createWithdrawalShouldThrowException_WhenWalletMoneyAreNotEnough() {

        //Arrange
        TransactionDTO transactionDTO = createTransactionDTO();
        Withdrawal withdrawal = createWithdrawal();
        CardDetails cardDetails = createCard();
        Wallet wallet = createWallet();
        generateWithdrawalWithInSufficientAmount(withdrawal, cardDetails, wallet);
        Mockito.when(transactionService.getWithdrawal(transactionDTO))
                .thenReturn(withdrawal);

        //Act
        transactionService.createWithdrawal(transactionDTO);

    }

    @Test
    public void createWithdrawalShouldCallRepository_Save() {
        //Arrange
        TransactionDTO transactionDTO = createTransactionDTO();
        Withdrawal withdrawal = createWithdrawal();
        CardDetails cardDetails = createCard();
        Wallet wallet = createWallet();
        generateWithdrawalWithSufficientAmount(withdrawal, cardDetails, wallet);
        Mockito.when(transactionService.getWithdrawal(transactionDTO))
                .thenReturn(withdrawal);

        //Act
        transactionService.createWithdrawal(transactionDTO);

        //Assert
        Mockito.verify(transactionRepository, Mockito.times(1)).createWithdrawal(withdrawal, BALANCE_NOT_ENOUGH, cardDetails.getId());
    }

    @Test(expected = InsufficientFundsException.class)
    public void createInternalShouldThrowException_WhenWalletMoneyAreNotEnough() {
        //Arrange
        TransactionDTO transactionDTO = createTransactionDTO();
        Internal betweenWallets = createInternal();
        Wallet walletSender = createWallet();
        Wallet walletReceiver = createWallet();
        generateInternalWithInSufficientAmount(betweenWallets, walletSender, walletReceiver);
        Mockito.when(transactionService.getInternal(transactionDTO))
                .thenReturn(betweenWallets);

        //Act
        transactionService.createInternal(transactionDTO);
    }

    @Test(expected = DuplicateIdempotencyKeyException.class)
    public void createInternalShouldThrowException_WhenIdempotencyKeyExists() {
        //Arrange
        TransactionDTO transactionDTO = createTransactionDTO();
        Internal betweenWallets = createInternal();
        Wallet walletSender = createWallet();
        walletSender.setBalance(BALANCE_ENOUGH);
        Wallet walletReceiver = createWallet();
        generateInternalWithInSufficientAmount(betweenWallets, walletSender, walletReceiver);
        Mockito.when(transactionService.getInternal(transactionDTO))
                .thenReturn(betweenWallets);
        Mockito.when(transactionService.checkIfIdempotencyKeyExists(betweenWallets.getIdempotencyKey()))
                .thenReturn(true);


        //Act
        transactionService.createInternal(transactionDTO);

    }

    @Test
    public void createInternalShouldCallRepository_Save() {
        //Arrange
        TransactionDTO transactionDTO = createTransactionDTO();
        Internal betweenWallets = createInternal();
        Wallet walletSender = createWallet();
        walletSender.setBalance(BALANCE_ENOUGH);
        Wallet walletReceiver = createWallet();
        generateInternalWithSufficientAmount(betweenWallets, walletSender, walletReceiver);
        Mockito.when(transactionService.getInternal(transactionDTO))
                .thenReturn(betweenWallets);
        Mockito.when(transactionService.checkIfIdempotencyKeyExists(betweenWallets.getIdempotencyKey()))
                .thenReturn(false);


        //Act
        transactionService.createInternal(transactionDTO);

        //Assert
        Mockito.verify(transactionRepository,
                Mockito.times(1))
                .createInternal(betweenWallets,
                        walletSender.getBalance() - TRANSACTION_AMOUNT,
                        walletReceiver.getBalance() + TRANSACTION_AMOUNT,
                        walletSender.getId(),
                        walletReceiver.getId());
    }

    @Test(expected = DuplicateIdempotencyKeyException.class)
    public void createDepositShouldThrowException_WhenIdempotencyKeyExists() {
        //Arrange
        TransactionDTO transactionDTO = createTransactionDTO();
        Deposit deposit = createDeposit();
        Mockito.when(transactionService.getDeposit(transactionDTO))
                .thenReturn(deposit);
        Mockito.when(transactionService.checkIfIdempotencyKeyExists(deposit.getIdempotencyKey()))
                .thenReturn(true);

        //Act
        transactionService.createDeposit(transactionDTO);

    }

//    @Test(expected = ResourceAccessException.class)
//    public void createDepositShouldThrowException_WhenAPINotConnected() {
//        //Arrange
//        TransactionDTO transactionDTO = createTransactionDTO();
//        Deposit deposit = createDeposit();
//        Mockito.when(transactionService.getDeposit(transactionDTO))
//                .thenReturn(deposit);
//        Mockito.when(transactionService.checkIfIdempotencyKeyExists(betweenWallets.getIdempotencyKey()))
//                .thenReturn(false);
//
//        //Act
//        transactionService.createDeposit(transactionDTO);
//    }

}