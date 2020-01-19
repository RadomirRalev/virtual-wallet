package com.example.demo.constants;

public class SQLQueryConstants {
    public static final String ADD_USER_ROLE = "insert into user_role " +
            "value ('%d','%d');";

    //Status
    public static final int ENABLE = 1;
    public static final int DISABLE = 0;

    //Roles
    public static final String ROLE_USER = "ROLE_USER";
}
