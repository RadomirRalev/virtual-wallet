package com.example.demo.constants;

public class SQLQueryConstants {
    public static final String ADD_USER_ROLE = "insert into user_role " +
            "value ('%d','%d');";
    public static final String ADD_USER_CARD = "insert into user_cards " +
            "value ('%d','%d');";

    //Status
    public static final int ENABLE = 1;
    public static final int DISABLE = 0;

    //Roles
    public static final String ROLE_USER = "ROLE_USER";

    //Pagination
    public static final int RESULTS_PER_PAGE = 5;
    public static final int PAGES_TO_SHOW = 1;
    public static final int MAX_NAVIGATION_RESULT = 10;


}
