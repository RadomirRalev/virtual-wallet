package com.example.demo.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.demo.constants.ExceptionConstants.*;
import static java.lang.Integer.parseInt;

public class CardHelper {
    private static final double MAGIC = 0.5;
    private static final int GET_YEAR = 3;
    private static final int BEGIN_INDEX = 0;
    private static final int GET_MONTH = 2;
    private static final int LAST_TWO_DIGITS = 100;
    private static final int DECEMBER = 12;

    public static boolean isValidExpirationDate(String expirationDate) {
        Date date = new Date();
        SimpleDateFormat formatterYear = new SimpleDateFormat("yyyy");
        SimpleDateFormat formatterMonth = new SimpleDateFormat("MM");
        int currentYear = parseInt(formatterYear.format(date))% LAST_TWO_DIGITS;
        int currentMonth = parseInt(formatterMonth.format(date));
        int expirationMonth = parseInt(expirationDate.substring(BEGIN_INDEX, GET_MONTH));
        int expirationYear = parseInt(expirationDate.substring(GET_YEAR));

        if(expirationMonth == 0 || expirationMonth> DECEMBER){
            return false;
        }

        if (currentMonth > expirationMonth) {
            expirationYear--;
        }
        return (expirationYear >= currentYear);
    }

    public static String cardType() {
        if (Math.random() > MAGIC) {
            return DEBIT;
        } else {
            return CREDIT;
        }
    }
}
