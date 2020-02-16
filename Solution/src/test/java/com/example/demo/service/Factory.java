package com.example.demo.service;

import com.example.demo.models.card.CardDetails;
import com.example.demo.models.card.CardRegistrationDTO;
import com.example.demo.models.card.CardUpdateDTO;
import com.example.demo.models.confirmIdentity.ConfirmIdentityRegistrationDTO;
import com.example.demo.models.role.Role;
import com.example.demo.models.transaction.*;
import com.example.demo.models.user.ProfileUpdateDTO;
import com.example.demo.models.user.User;
import com.example.demo.models.user.UserRegistrationDTO;
import com.example.demo.models.verificationToken.VerificationToken;
import com.example.demo.models.wallet.Wallet;
import com.example.demo.models.wallet.WalletCreationDTO;
import com.example.demo.services.CardDetailsService;
import com.example.demo.services.UserService;
import com.example.demo.services.WalletService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Factory {
    public static final int INDEX = 1;
    public static final int TRANSACTION_AMOUNT = 100;
    public static final int TRANSACTION_AMOUNT_HIGHER_THAN_BALANCE = 1000;
    public static final int BALANCE_ENOUGH = 101;
    public static final int BALANCE_NOT_ENOUGH = 1;
    public static final int PAGE = 1;
    public static final String DIRECTION_ALL = "All";
    public static final String DIRECTION_OUTGOING = "Outgoing";
    public static final String DIRECTION_INCOMING = "Incoming";
    public static final String START_DATE_NONE = "";
    public static final String END_DATE_NONE = "";

    public static final String START_DATE = "04/02/2020";
    public static final String END_DATE = "04/02/2021";
    public static final String RECIPIENT_NONE = "";
    public static final String RECIPIENT_TRUE = "radomirr";



    public static UserService userService;
    public static CardDetailsService cardDetailsService;
    public static WalletService walletService;


    public static LocalDate parseDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(dateString, formatter);
    }

    public static Internal createInternal(){
        return new Internal();
    }

    public static Withdrawal createWithdrawal(){
        return new Withdrawal();
    }

    public static Deposit createDeposit(){
        return new Deposit();
    }

    public static CardDetails createCard(){
        return new CardDetails();
    }

    public static User createUser() { return new User(); }

    public static List<Transaction> createTransactions() {
        List<Transaction> transactionList = new ArrayList<>();
        Internal internal = createInternal();
        Withdrawal withdrawal = createWithdrawal();
        Deposit deposit = createDeposit();
        transactionList.add(internal);
        transactionList.add(withdrawal);
        transactionList.add(deposit);
        return transactionList;
    }

    public static TransactionMapper transactionMapper = new TransactionMapper(userService, cardDetailsService, walletService);


    public static Wallet createWallet(){
        Wallet wallet = new Wallet();
        wallet.setId(INDEX);
        wallet.setBalance(BALANCE_ENOUGH);
        return wallet;
    }

    public static TransactionDTO createTransactionDTO(){
        return new TransactionDTO(1, 1, "t", "d", "i", 8, "bgn", "sender", "receiver");
    }

    public static CardRegistrationDTO createCardRegistrationDTO() {
        return new CardRegistrationDTO();
    }

    public static WalletCreationDTO createWalletCreationDTO() {
        return new WalletCreationDTO();
    }

    public static CardUpdateDTO createCardUpdateDTO() {
        return new CardUpdateDTO();
    }

    public static ProfileUpdateDTO createProfileUpdateDTO() {
        return new ProfileUpdateDTO();
    }

    public static UserRegistrationDTO createUserRegistrationDTO() {
        return new UserRegistrationDTO();
    }

    public static VerificationToken createVerificationToken() {
        return new VerificationToken();
    }

    public static Role createRole() {
        return new Role();
    }

    public static ConfirmIdentityRegistrationDTO createConfirmIdentityRegistrationDTO() {
        return new ConfirmIdentityRegistrationDTO();
    }


    public static void generateWithdrawalWithSufficientAmount(Withdrawal withdrawal, CardDetails cardDetails, Wallet wallet) {
        cardDetails.setId(INDEX);
        withdrawal.setAmount(TRANSACTION_AMOUNT);
        withdrawal.setReceiver(cardDetails);
        wallet.setBalance(BALANCE_ENOUGH);
        withdrawal.setSender(wallet);
    }

    public static void generateWithdrawalWithInSufficientAmount(Withdrawal withdrawal, CardDetails cardDetails, Wallet wallet) {
        cardDetails.setId(INDEX);
        withdrawal.setAmount(TRANSACTION_AMOUNT);
        withdrawal.setReceiver(cardDetails);
        wallet.setBalance(BALANCE_NOT_ENOUGH);
        withdrawal.setSender(wallet);
    }

    public static void generateInternalWithInSufficientAmount(Internal betweenWallets, Wallet sender, Wallet receiver) {
        sender.setId(INDEX);
        sender.setBalance(BALANCE_ENOUGH);
        betweenWallets.setAmount(TRANSACTION_AMOUNT_HIGHER_THAN_BALANCE);
        betweenWallets.setSender(receiver);
        betweenWallets.setReceiver(receiver);
    }

    public static void generateInternalWithSufficientAmount(Internal betweenWallets, Wallet sender, Wallet receiver) {
        sender.setId(INDEX);
        sender.setBalance(BALANCE_ENOUGH);
        betweenWallets.setAmount(TRANSACTION_AMOUNT);
        betweenWallets.setSender(receiver);
        betweenWallets.setReceiver(receiver);
    }

    public static void generateCorrectDeposit(Deposit deposit, Wallet wallet) {
        deposit.setIdempotencyKey("1");
        deposit.setAmount(TRANSACTION_AMOUNT);
        deposit.setDescription("This is description");
        deposit.setCurrency("BGN");
        deposit.setReceiver(wallet);
        wallet.setBalance(BALANCE_ENOUGH);
    }
}
