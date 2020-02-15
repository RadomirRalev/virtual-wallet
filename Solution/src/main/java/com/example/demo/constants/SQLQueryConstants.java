package com.example.demo.constants;

public class SQLQueryConstants {

    //Status
    public static final boolean ENABLE = true;
    public static final boolean DISABLE = false;

    //Roles
    public static final String ROLE_USER = "ROLE_USER";

    //Pagination
    public static final int RESULTS_PER_PAGE = 5;
    public static final int PAGES_TO_SHOW = 1;
    public static final int MAX_NAVIGATION_RESULT = 10;

    //Currency
    public static final String USD = "USD";

    //User
    public static final String GET_USERS_FOR_CONFIRM = "select * " +
            "from users join confirm_identy " +
            "on users.user_id = confirm_identy.user_id " +
            "where have_request = true";

    //Transactions
    public static final String FROM_INTERNAL_DATE_SENDERID = "from Internal " +
            " where date between :startDate and :endDate and sender.user.id = :userId";
    public static final String FROM_INTERNAL_DATE_RECEIVERID = "from Internal " +
            "where date between :startDate and :endDate and receiver.user.id = :userId";
    public static final String FROM_DEPOSIT_DATE_RECEIVERID = "from Deposit " +
            "where date between :startDate and :endDate and receiver.user.id = :userId";
    public static final String FROM_WITHDRAWAL_DATE_SENDERID = "from Withdrawal " +
            "where date between :startDate and :endDate and sender.user.id = :userId";
    public static final String FROM_INTERNAL_RECEIVERNAME_SENDERID = "from Internal " +
            " where receiverName like :recipientSearchString and sender.user.id = :userId";
    public static final String FROM_INTERNAL_RECEIVERNAME = "from Internal " +
            " where receiverName like :recipientSearchString";
    public static final String FROM_DEPOSIT_RECEIVERID = "from Deposit " +
            "where receiver.user.id = :userId";
    public static final String FROM_WITHDRAWAL_RECEIVERNAME_SENDERID = "from Withdrawal " +
            "where receiverName like :recipientSearchString and sender.user.id = :userId";
    public static final String FROM_INTERNAL_DATE_RECEIVERNAME_SENDERID = "from Internal " +
            " where date between :startDate and :endDate and receiverName like :recipientSearchString and sender.user.id = :userId";
    public static final String FROM_INTERNAL_DATE_RECEIVERNAME = "from Internal " +
            " where date between :startDate and :endDate and receiverName like :recipientSearchString";
    public static final String FROM_WITHDRAWAL_DATE_RECEIVERNAME_SENDERID = "from Withdrawal " +
            "where date between :startDate and :endDate and receiverName like :recipientSearchString and sender.user.id = :userId";
    public static final String FROM_INTERNAL = "from Internal";
    public static final String FROM_DEPOSIT = "from Deposit";
    public static final String FROM_WITHDRAWAL = "from Withdrawal";
    public static final String FROM_INTERNAL_SENDERID_WALLET = "from Internal where sender.id = :walletId";
    public static final String FROM_INTERNAL_RECEIVERID_WALLET = "from Internal where receiver.id = :walletId";
    public static final String FROM_WITHDRAWAL_SENDERID_WALLET = "from Withdrawal where sender.id = :walletId";
    public static final String FROM_WITHDRAWAL_DEPOSIT_WALLET = "from Deposit where sender.id = :walletId";
    public static final String FROM_INTERNAL_KEY = "from Internal where idempotencyKey = :idempotencyKey ";
    public static final String FROM_DEPOSIT_KEY = "from Deposit where idempotencyKey = :idempotencyKey ";
    public static final String FROM_WITHDRAWAL_KEY = "from Withdrawal where idempotencyKey = :idempotencyKey ";
    public static final String SELECT_SENDER_INTERNAL_SENDERID = "from Internal where sender.user.id = :userId";
    public static final String SELECT_RECEIVER_INTERNAL_RECEIVERID = "from Internal where receiver.user.id = :userId";
    public static final String SELECT_RECEIVER_DEPOSIT_RECEIVERID = "from Deposit where receiver.user.id = :userId";
    public static final String SELECT_SENDER_WITHDRAWAL_SENDERID = "from Withdrawal where sender.user.id = :userId";









}
