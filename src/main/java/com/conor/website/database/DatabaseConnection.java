package com.conor.website.database;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DatabaseConnection {


    private static String JDBC_URL;
    private static String USER;
    private static String PASS;

    @Value("${DATABASE_URL}")
    private String jdbcUrl;

    @Value("${DATABASE_USERNAME}")
    private String user;

    @Value("${DATABASE_PASSWORD}")
    private String pass;

    /*
    From Documentation: The PostConstruct annotation is used on a method that needs to be executed after
    dependency injection is done to perform any initialization.
    I am doing this so that I don't need to create an object of DatabaseConnection in my service class for now.
    This is because the @Value annotation works with instance fields,
    and it doesn't support injecting values into static fields.
    But I want to keep this as a static connection for now just because I wanted to see if it was possible since
    I currently only have one database and no reason to change the connection data.
    */
    @PostConstruct
    private void init() {
        JDBC_URL = jdbcUrl;
        USER = user;
        PASS = pass;
    }

    /*
    This may need to be updated later to non-static if the state of the instance determines the database connection
    otherwise, for now it can remain static.
    If it becomes non-static then this call will have to be made on a new object of DatabaseConnection rather than
    calling it on the class itself inside the RockPaperScissors class for example.
    */
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USER, PASS);
    }
}
