package com.example.demo.service;

import com.example.demo.exceptions.DuplicateIdempotencyKeyException;
import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.exceptions.InsufficientFundsException;
import com.example.demo.models.card.CardDetails;
import com.example.demo.models.transaction.*;
import com.example.demo.models.user.User;
import com.example.demo.models.wallet.Wallet;
import com.example.demo.repositories.CardDetailsRepository;
import com.example.demo.repositories.TransactionRepository;
import com.example.demo.repositories.UserRepository;
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
import static org.mockito.ArgumentMatchers.anyInt;

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
    UserRepository userRepository;
    @Mock
    WalletRepository walletRepository;
    @Mock
    CardDetailsRepository cardDetailsRepository;
    @Mock
    SessionFactory sessionFactory;
    @Mock
    TransactionMapper transactionMapper;
    @Mock
    Internal internal;
    @Mock
    Wallet wallet;

    @InjectMocks
    TransactionServiceImpl transactionService;



    @Test
    public void getAllTransactionsShould_CallRepository() {
        //Arrange
        List<Transaction> transactionsList = new ArrayList<>();

        Mockito.when(transactionRepository.getTransactions(PAGE))
                .thenReturn(transactionsList);
        //Act
        transactionService.getAllTransactions(PAGE);

        //Assert
        Assert.assertSame(transactionService.getAllTransactions(PAGE), transactionsList);
    }

    @Test
    public void getAllTransactionsShould_ReturnEmptyListWhenNoTransactions() {
        //Arrange
        List<Transaction> transactionsList = new ArrayList<>();

        Mockito.when(transactionRepository.getTransactions(PAGE))
                .thenReturn(transactionsList);
        //Act
        transactionService.getAllTransactions(PAGE);

        //Assert
        Assert.assertTrue(transactionService.getAllTransactions(PAGE).isEmpty());
    }

    @Test
    public void getAllTransactionsShould_ReturnListWhenThereAreTransactions() {
        //Arrange
        Internal internal = createInternal();
        Withdrawal withdrawal = createWithdrawal();

        Mockito.when(transactionRepository.getTransactions(PAGE))
                .thenReturn(Arrays.asList(internal, withdrawal));
        //Act
        List<Transaction> list = transactionService.getAllTransactions(PAGE);

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

        //Act
        Deposit deposit = createDeposit();
        List<Deposit> list = new ArrayList<>();
        list.add(deposit);
        Deposit depositTest = list.get(0);

        //Assert
        Assert.assertSame(deposit, depositTest);
    }

    @Test
    public void createInternalShould_CallRepository() {
        //Arrange
        TransactionDTO transactionDTO = createTransactionDTO();
        TransactionMapper transactionMapper = new TransactionMapper(userService, cardDetailsService, walletService);
        Internal betweenWallets = createInternal();
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
        Withdrawal withdrawal = createWithdrawal();
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
        Withdrawal withdrawal = createWithdrawal();
        withdrawal.setIdempotencyKey("1");
        Mockito.when(transactionService.checkIfIdempotencyKeyExists("1"))
                .thenThrow(DuplicateIdempotencyKeyException.class);

        //Act
        transactionService.checkIfIdempotencyKeyExists("1");

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
        Mockito.when(transactionService.checkIfWalletIdExists(wallet.getId()))
                .thenReturn(true);
        Mockito.when(transactionService.checkIfCardIdExists(cardDetails.getId()))
                .thenReturn(true);

        //Act
        transactionService.createWithdrawal(transactionDTO, "sender");

        //Assert
        Mockito.verify(transactionRepository, Mockito.times(1)).createWithdrawal(withdrawal, BALANCE_NOT_ENOUGH, cardDetails.getId());
    }

    @Test(expected = EntityNotFoundException.class)
    public void createWithdrawalShouldThrowException_WhenSenderIdDoesNotExist() {
        //Arrange
        TransactionDTO transactionDTO = createTransactionDTO();
        Wallet walletSender = createWallet();
        CardDetails cardReceiver = createCard();
        Withdrawal withdrawal = createWithdrawal();
        generateWithdrawalWithSufficientAmount(withdrawal, cardReceiver, walletSender);

        Mockito.when(transactionService.getWithdrawal(transactionDTO))
                .thenReturn(withdrawal);
        Mockito.when(transactionService.checkIfIdempotencyKeyExists(withdrawal.getIdempotencyKey()))
                .thenReturn(false);
        Mockito.when(transactionService.checkIfWalletIdExists(anyInt()))
                .thenReturn(false);

        //Act
        transactionService.createWithdrawal(transactionDTO, "sender");
    }

    @Test(expected = EntityNotFoundException.class)
    public void createWithdrawalShouldThrowException_WhenReceiverIdDoesNotExist() {
        //Arrange
        TransactionDTO transactionDTO = createTransactionDTO();
        Wallet walletSender = createWallet();
        CardDetails cardReceiver = createCard();
        Withdrawal withdrawal = createWithdrawal();
        generateWithdrawalWithSufficientAmount(withdrawal, cardReceiver, walletSender);

        Mockito.when(transactionService.getWithdrawal(transactionDTO))
                .thenReturn(withdrawal);
        Mockito.when(transactionService.checkIfIdempotencyKeyExists(withdrawal.getIdempotencyKey()))
                .thenReturn(false);
        Mockito.when(transactionService.checkIfWalletIdExists(anyInt()))
                .thenReturn(true);
        Mockito.when(transactionService.checkIfCardIdExists(anyInt()))
                .thenReturn(false);

        //Act
        transactionService.createWithdrawal(transactionDTO, "sender");
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
        transactionService.createInternal(transactionDTO, "sender");
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
        transactionService.createInternal(transactionDTO, "sender");

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
        Mockito.when(transactionService.checkIfWalletIdExists(walletSender.getId()))
                .thenReturn(true);
        Mockito.when(transactionService.checkIfCardIdExists(anyInt()))
                .thenReturn(true);


        //Act
        transactionService.createInternal(transactionDTO, "sender");

        //Assert
        Mockito.verify(transactionRepository,
                Mockito.times(1))
                .createInternal(betweenWallets,
                        walletSender.getBalance() - TRANSACTION_AMOUNT,
                        walletReceiver.getBalance() + TRANSACTION_AMOUNT,
                        walletSender.getId(),
                        walletReceiver.getId());
    }

    @Test(expected = EntityNotFoundException.class)
    public void createInternalShouldThrowException_WhenReceiverIdDoesNotExist() {
        //Arrange
        TransactionDTO transactionDTO = createTransactionDTO();
        Wallet walletSender = createWallet();
        Wallet walletReceiver = createWallet();
        Internal internal = createInternal();
        generateInternalWithSufficientAmount(internal, walletSender, walletReceiver);

        Mockito.when(transactionService.getInternal(transactionDTO))
                .thenReturn(internal);
        Mockito.when(transactionService.checkIfIdempotencyKeyExists(internal.getIdempotencyKey()))
                .thenReturn(false);
        Mockito.when(transactionService.checkIfWalletIdExists(anyInt()))
                .thenReturn(false);

        //Act
        transactionService.createInternal(transactionDTO, "sender");
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
        transactionService.createDeposit(transactionDTO, "sender");
    }

    @Test
    public void createDepositShouldCallRepository_Save() {
        //Arrange
        TransactionDTO transactionDTO = createTransactionDTO();
        Wallet wallet = createWallet();
        Deposit deposit = createDeposit();
        generateCorrectDeposit(deposit, wallet);
        Mockito.when(transactionService.getDeposit(transactionDTO))
                .thenReturn(deposit);
        Mockito.when(transactionService.checkIfIdempotencyKeyExists(deposit.getIdempotencyKey()))
                .thenReturn(false);
        Mockito.when(transactionService.checkIfWalletIdExists(anyInt()))
                .thenReturn(true);
        Mockito.when(walletRepository.getWalletById(anyInt()))
                .thenReturn(wallet);

        //Act
        transactionService.createDeposit(transactionDTO, "sender");

        //Assert
        Mockito.verify(transactionRepository, Mockito.times(1))
                .createDeposit(deposit, BALANCE_ENOUGH + deposit.getAmount(), wallet.getId());
    }

    @Test(expected = EntityNotFoundException.class)
    public void createDepositShouldThrowException_WhenReceiverIdExists() {
        //Arrange
        TransactionDTO transactionDTO = createTransactionDTO();
        Wallet wallet = createWallet();
        Deposit deposit = createDeposit();
        deposit.setIdempotencyKey("1");
        deposit.setAmount(100);
        deposit.setDescription("This is description");
        deposit.setCurrency("BGN");
        deposit.setReceiver(wallet);
        Mockito.when(transactionService.getDeposit(transactionDTO))
                .thenReturn(deposit);
        Mockito.when(transactionService.checkIfIdempotencyKeyExists(deposit.getIdempotencyKey()))
                .thenReturn(false);
        Mockito.when(transactionService.checkIfWalletIdExists(anyInt()))
                .thenReturn(false);

        //Act
        transactionService.createDeposit(transactionDTO, "sender");
    }

    @Test
    public void getTransactionsbyUserIdShould_CallRepository() {
        //Arrange
        List<Transaction> transactionsList = new ArrayList<>();
        User user = createUser();

        Mockito.when(transactionRepository.getTransactionsByUserId(user.getId(), PAGE))
                .thenReturn(transactionsList);
        Mockito.when(transactionService.checkIfUserIdExists(user.getId()))
                .thenReturn(true);
        //Act
        transactionService.getTransactionsByUserId(user.getId(), PAGE);

        //Assert
        Assert.assertSame(transactionService.getTransactionsByUserId(user.getId(), PAGE), transactionsList);
    }

    @Test
    public void getTransactionsbyUserIdShould_ReturnEmptyListWhenNoTransactions() {
        //Arrange
        List<Transaction> transactionsList = new ArrayList<>();
        User user = createUser();

        Mockito.when(transactionRepository.getTransactionsByUserId(user.getId(), PAGE))
                .thenReturn(transactionsList);
        Mockito.when(transactionService.checkIfUserIdExists(user.getId()))
                .thenReturn(true);

        //Act
        transactionService.getTransactionsByUserId(user.getId(), PAGE);

        //Assert
        Assert.assertTrue(transactionService.getAllTransactions(PAGE).isEmpty());
    }

    @Test
    public void getTransactionsbyUserIdShould_ReturnListWhenThereAreTransactions() {
        //Arrange
        Internal internal = createInternal();
        Withdrawal withdrawal = createWithdrawal();
        User user = createUser();

        Mockito.when(transactionRepository.getTransactionsByUserId(user.getId(), PAGE))
                .thenReturn(Arrays.asList(internal, withdrawal));
        Mockito.when(transactionService.checkIfUserIdExists(user.getId()))
                .thenReturn(true);

        //Act
        List<Transaction> list = transactionService.getTransactionsByUserId(user.getId(), PAGE);


        //Assert
        Assert.assertEquals(2, list.size());
    }

    @Test(expected = EntityNotFoundException.class)
    public void checkIfUserIdExistsShould_ThrowExceptionIfUserDoesNotExist() {

        Mockito.when(transactionService.checkIfUserIdExists(anyInt()))
                .thenReturn(false);

        //Act
        List<Transaction> list = transactionService.getTransactionsByUserId(anyInt(), PAGE);

    }

    @Test
    public void getTransactionsbyWalletIdShould_CallRepository() {
        //Arrange
        List<Transaction> transactionsList = new ArrayList<>();
        Wallet wallet = createWallet();

        Mockito.when(transactionRepository.getTransactionsbyWalletId(wallet.getId(), PAGE))
                .thenReturn(transactionsList);
        Mockito.when(transactionService.checkIfWalletIdExists(wallet.getId()))
                .thenReturn(true);
        //Act
        transactionService.getTransactionsbyWalletId(wallet.getId(), PAGE);

        //Assert
        Assert.assertSame(transactionService.getTransactionsbyWalletId(wallet.getId(), PAGE), transactionsList);
    }

    @Test
    public void getTransactionsbyWalletIdShould_ReturnListWhenThereAreTransactions() {
        //Arrange
        Internal internal = createInternal();
        Withdrawal withdrawal = createWithdrawal();
        Wallet wallet = createWallet();

        Mockito.when(transactionRepository.getTransactionsbyWalletId(wallet.getId(), PAGE))
                .thenReturn(Arrays.asList(internal, withdrawal));
        Mockito.when(transactionService.checkIfWalletIdExists(wallet.getId()))
                .thenReturn(true);

        //Act
        List<Transaction> list = transactionService.getTransactionsbyWalletId(wallet.getId(), PAGE);


        //Assert
        Assert.assertEquals(2, list.size());
    }

    @Test(expected = EntityNotFoundException.class)
    public void checkIfWalletIdExistsShould_ThrowExceptionIfUserDoesNotExist() {

        Mockito.when(transactionService.checkIfWalletIdExists(anyInt()))
                .thenReturn(false);

        //Act
        List<Transaction> list = transactionService.getTransactionsbyWalletId(anyInt(), PAGE);
    }

}