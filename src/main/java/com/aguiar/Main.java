package com.aguiar;

import com.aguiar.connection.ConnectionFactory;
import com.aguiar.dao.ProductDaoImpl;
import com.aguiar.service.SystemService;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    static void main() {

        ProductDaoImpl productDao = new ProductDaoImpl();
        SystemService systemService = new SystemService(productDao);

        try (Connection connection = ConnectionFactory.getConnection()){
            System.out.println("[INFO] Conexão ao banco de dados bem sucedida!");

            systemService.mainMenu();

        } catch (SQLException e) {
            System.out.println("[ERRO] Não foi possível conectar ao banco de dados." + e.getMessage());
        }
    }
}
