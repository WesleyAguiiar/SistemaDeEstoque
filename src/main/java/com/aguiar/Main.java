package com.aguiar;

import com.aguiar.controller.SystemController;
import com.aguiar.dao.ProductDaoImpl;
import com.aguiar.service.SystemService;

public class Main {
    static void main() {

        // INJEÇÃO DE DEPENDÊNCIAS
        ProductDaoImpl productDao = new ProductDaoImpl();

        SystemService systemService = new SystemService(productDao);

        SystemController systemController = new SystemController(systemService);

        systemController.mainMenu();

    }
}
