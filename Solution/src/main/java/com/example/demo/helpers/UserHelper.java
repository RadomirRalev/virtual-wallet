package com.example.demo.helpers;

import com.example.demo.exceptions.InvalidOptionalFieldParameter;
import com.example.demo.models.user.User;

import static com.example.demo.constants.ExceptionConstants.*;

public class UserHelper {
    private static final int MIN_LENGTH = 3;
    private static final String ONLY_LETTERS = "^[a-zA-Z]+$";
    private static final String EMPTY_STRING = "";


    public static void setOptionalFields(User user) {
        if (!user.getFirstName().equals(EMPTY_STRING)) {
            if (user.getFirstName().length() < MIN_LENGTH) {
                throw new InvalidOptionalFieldParameter(FIRST_NAME_LENGTH_EXCEPTION);
            }
            if (!user.getFirstName().matches(ONLY_LETTERS)) {
                throw new InvalidOptionalFieldParameter(FIRST_NAME_REGEX_EXCEPTION);
            }
        }

        if (!user.getLastName().equals(EMPTY_STRING)) {
            if (user.getLastName().length() < MIN_LENGTH) {
                throw new InvalidOptionalFieldParameter(LAST_NAME_LENGTH_EXCEPTION);
            }
            if (!user.getLastName().matches(ONLY_LETTERS)) {
                throw new InvalidOptionalFieldParameter(LAST_NAME_REGEX_EXCEPTION);
            }
        }
    }
//TODO
//    public static String currentPrincipalName(){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return authentication.getName();
//    }
}