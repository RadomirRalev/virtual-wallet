package com.example.demo.constants;

public class ExceptionConstants {
    public static final String USER_USERNAME_NOT_FOUND = "User with username %s not found in the database.";
    public static final String USER_ID_NOT_FOUND = "User with username %d not found in the database.";
    public static final String USER_PHONE_NUMBER_NOT_FOUND = "User with phone number %s not found in the database.";
    public static final String USER_EMAIL_NOT_FOUND = "User with email %s not found in the database.";
    public static final String USER_USERNAME_EXISTS = "User with username %s already exists.";
    public static final String USER_EMAIL_EXISTS = "User with email %s already exists.";
    public static final String USER_PHONE_EXISTS = "User with phone %s already exists.";
    public static final String CARD_WITH_NUMBER_EXISTS = "Card with number %s already registered.";
    public static final String CARD_WITH_NUMBER_NOT_EXISTS = "Card with number %s does not exist.";
    public static final String CARD_WITH_ID_NOT_EXISTS = "Card with id %d does not exist.";
    public static final String WALLET_WITH_ID_NOT_EXISTS = "Wallet with id %d does not exist.";
    public static final String USER_CAN_NOT_MODIFY = "User with username %s can not modify beer with name %s.";
    public static final String PASSWORD_DO_NOT_MATCH = "Password doesn't match!";
    public static final String EMAIL_ALREADY_REGISTERED = "Email %s already registered.";
    public static final String PHONE_NUMBER_ALREADY_REGISTERED = "Phone number %s already registered.";
    public static final String INVALID_OLD_PASSWORD = "The old password you have entered is incorrect.";
    public static final String FIRST_NAME_LENGTH_EXCEPTION = "First name should contain at least 3 symbols.";
    public static final String FIRST_NAME_REGEX_EXCEPTION = "First name field may contain only letters.";
    public static final String LAST_NAME_LENGTH_EXCEPTION = "Last name should contain at least 3 symbols.";
    public static final String LAST_NAME_REGEX_EXCEPTION = "Last name field may contain only letters.";
    public static final String THE_NAMES_DO_NOT_MATCH = "Yours first and last name are not same as card holder field.";
    public static final String EXPIRATION_DATE_IS_INVALID = "Expiration date is invalid.";
    public static final String SENDER_FUNDS_ARE_NOT_SUFFICIENT = "Not sufficient funds.";
}
