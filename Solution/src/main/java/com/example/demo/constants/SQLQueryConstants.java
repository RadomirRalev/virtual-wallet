package com.example.demo.constants;

public class SQLQueryConstants {
    public static final String INSERT_USER_ROLE_SQL = "insert into authorities " +
            "value ('%s','ROLE_USER')";

    //Status
    public static final int ENABLE = 1;
    public static final int DISABLE = 0;
}
