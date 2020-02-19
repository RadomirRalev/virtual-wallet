package com.example.demo.constants;

public class ExceptionConstants {
    public static final String USER_USERNAME_NOT_FOUND = "User with username %s not found in the database.";
    public static final String USER_ID_NOT_FOUND = "User with id %d not found in the database.";
    public static final String USER_CONFIRM_IDENTITY_NOT_FOUND = "Pictures confirm identity for user" +
            " with id %d not found in the database.";
    public static final String USER_PHONE_NUMBER_NOT_FOUND = "User with phone number %s not found in the database.";
    public static final String USER_EMAIL_NOT_FOUND = "User with email %s not found in the database.";
    public static final String TOKEN_NOT_FOUND = "Token %s not found in the database.";
    public static final String USER_ALREADY_CONFIRMED_HIS_IDENTITY = "User %s has already confirmed his identity.";
    public static final String USER_ALREADY_HAVE_CONFIRM_IDENTITY = "User %s already have confirm identity.";
    public static final String CONFIRM_YOUR_IDENTITY_TO_REGISTER_CARD = "Confirm your identity to register a card!";
    public static final String SENDER_IS_BLOCKED = "%s you are blocked and can`t make transactions!";
    public static final String RECEIVER_IS_BLOCKED = "User with username %s is blocked and you can`t send him money!";
    //TODO ???
    public static final String USER_TOKEN_NOT_FOUND = "User with id %d haven`t token.";
    public static final String USER_USERNAME_EXISTS = "User with username %s already exists.";
    public static final String USER_EMAIL_EXISTS = "User with email %s already exists.";
    public static final String USER_PHONE_EXISTS = "User with phone %s already exists.";
    public static final String CARD_WITH_NUMBER_EXISTS = "Card with number %s already registered.";
    public static final String CARD_WITH_NUMBER_NOT_EXISTS = "Card with number %s does not exist.";
    public static final String CARD_WITH_ID_NOT_EXISTS = "Card with id %d does not exist.";
    public static final String WALLET_WITH_ID_NOT_EXISTS = "Wallet with id %d does not exist.";
    public static final String USER_HAVE_NOT_ADMIN_PERMISSION = "User with username %s have not admin permission !";
    public static final String USER_HAVE_NOT_PERMISSION_TO_UPDATE_CARD = "User with username %s have not permission to update this card!";
    public static final String PASSWORD_DO_NOT_MATCH = "Password doesn't match!";
    public static final String EMAIL_ALREADY_REGISTERED = "Email %s already registered.";
    public static final String PHONE_NUMBER_ALREADY_REGISTERED = "Phone number %s already registered.";
    public static final String INVALID_CURRENT_PASSWORD = "The current password you have entered is incorrect.";
    public static final String FIRST_NAME_LENGTH_EXCEPTION = "First name should contain at least 3 symbols.";
    public static final String FIRST_NAME_REGEX_EXCEPTION = "First name field may contain only letters.";
    public static final String LAST_NAME_LENGTH_EXCEPTION = "Last name should contain at least 3 symbols.";
    public static final String LAST_NAME_REGEX_EXCEPTION = "Last name field may contain only letters.";
    public static final String THE_NAMES_DO_NOT_MATCH = "Yours first and last name are not same as card holder field.";
    public static final String EXPIRATION_DATE_IS_INVALID = "Expiration date is invalid.";
    public static final String SENDER_FUNDS_ARE_NOT_SUFFICIENT = "Not sufficient funds.";
    public static final String YOU_CANNOT_MAKE_THE_SAME_TRANSACTION_TWICE = "You cannot make the same transaction twice.";
    public static final String INVALID_TOKEN = "Invalid Token ! Please contact support.";
    public static final String ALLOW_PICTURE_FORMAT = "Allow picture format is jpg.";
    public static final String IDENTITY_CONFIRM_REQUEST_PROCESSED = "Your identity confirm request is being processed.";
    public static final String IDENTITY_CONFIRM_SUCCESS = "Your identity confirm request was successful.";
    public static final String CANNOT_DELETE_DEFAULT_WALLET = "A default wallet cannot be deleted.";
    public static final String CANNOT_DELETE_WALLET_WITH_POSITIVE_BALANCE = "A wallet with positive balance cannot be deleted.";
    public static final String WALLET_ALREADY_DEFAULT = "The wallet has already been set as default.";

}
