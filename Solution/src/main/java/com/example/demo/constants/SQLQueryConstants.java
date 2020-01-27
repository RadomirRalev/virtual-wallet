package com.example.demo.constants;

public class SQLQueryConstants {
    public static final String ADD_USER_ROLE = "insert into user_role " +
            "value ('%d','%d');";


    //Status
    public static final boolean ENABLE = true;
    public static final boolean DISABLE = false;

    //Roles
    public static final String ROLE_USER = "ROLE_USER";

    //Pagination
    public static final int RESULTS_PER_PAGE = 5;
    public static final int PAGES_TO_SHOW = 1;
    public static final int MAX_NAVIGATION_RESULT = 10;


}
