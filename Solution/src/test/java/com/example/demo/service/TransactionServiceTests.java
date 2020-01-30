package com.example.demo.service;

import com.example.demo.models.transaction.*;
import com.example.demo.models.wallet.Wallet;
import com.example.demo.repositories.TransactionRepository;
import com.example.demo.repositories.WalletRepository;
import com.example.demo.repositories.WalletRepositoryImpl;
import com.example.demo.services.*;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.demo.service.Factory.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doThrow;

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
    SessionFactory sessionFactory;

    @InjectMocks
    TransactionServiceImpl transactionService;
    @InjectMocks
    TransactionMapper transactionMapper;


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
        Internal internal = transactionMapper.createInternalTransaction(transactionDTO);
        List<Internal> list = new ArrayList<>();
        list.add(internal);

        //Act
        Internal internalTest = list.get(0);

        //Assert
        Assert.assertSame(internal, internalTest);
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
}