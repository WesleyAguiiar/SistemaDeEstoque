package com.aguiar.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static String URL = "jdbc:postgresql://localhost:5432/sistema_produtos";
    private static String USER = "postgres";
    private static String SENHA = "1234";

    // Método de conexão ao banco de dados
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, SENHA);
    }
}
