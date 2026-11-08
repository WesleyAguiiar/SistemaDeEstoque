package com.aguiar.service;

import com.aguiar.connection.ConnectionFactory;
import com.aguiar.controller.SystemController;
import com.aguiar.dao.ProductDaoImpl;
import com.aguiar.dto.ProductRecord;
import com.aguiar.model.Product;
import com.aguiar.repository.SystemRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;

public class SystemService {

    private final SystemController systemController = new SystemController();
    private final SystemRepository systemRepository = new SystemRepository();
    private final ProductDaoImpl productDaoImpl;

    public SystemService(ProductDaoImpl productDao) {
        this.productDaoImpl = productDao;
    }

    public void mainMenu() throws SQLException {
        while (true) {
            Integer option = validarMenu();
            switch (option) {
                case 1:
                    registrarProduto();
                    break;
                case 2:
                    listarProdutos();
                    break;
                case 3:
                    buscarProduto();
                    break;
                case 4:
                    realizarVenda();
                    break;
                case 5:
                    adicionarEstoque();
                    break;
                case 6:
                    produtoComMenorEstoque();
                    break;
                case 7:
                    relatorioGeral();
                    break;
                case 8:
                    alterarPreco();
                    break;
                case 0:
                    System.exit(0);
            }
        }
    }



    private void registrarProduto() throws SQLException {
        Product product = validarRegistroProduto();

        if (product == null) {
            System.out.println("[Erro] Não foi possível adicionar o produto.");
        } else {
            //systemRepository.addProduct(product);
            try (Connection connection = ConnectionFactory.getConnection()){
                productDaoImpl.salvar(product);
                System.out.println("[Info] Produto adicionado ao estoque.");
            } catch (SQLException e){
                System.out.println("[Erro] " + e.getMessage());
            }
        }
    }

    private Product validarRegistroProduto() {

        ProductRecord product = systemController.registerMenu();

        if (product.amount() < 1) {
            System.out.println("[Erro] O número de produtos precisa ser maior que 0.");
        } else if (product.price() <= 0) {
            System.out.println("[Erro] O preço do produto precisa ser maior que 0.");
        } else if (product.name() == null || product.name().length() < 2) {
            System.out.println("[Erro] O nome do produto não pode ser nulo.");
        } else if (systemRepository.buscarProduto(product.name()) != null){
            System.out.println("[Erro] Produto já cadastrado no sistema.");
        } else {
            return new Product(product.name(), product.price(), product.amount());
        }
        return null;
    }

    private void listarProdutos() throws SQLException{

        System.out.println("[Info] Produtos cadastrados: \n");
        for (Product p : productDaoImpl.listarTodos()) {
            System.out.println(p);
        }
    }

    private void buscarProduto() throws SQLException{

        String nome = systemController.menuBusca();
        Product produto = productDaoImpl.buscarProdutoPeloNome(nome);

        if (!(produto == null)){
            System.out.println(produto);
        } else {
            System.out.println("[Erro] Produto não encontrado!");
        }
    }

    private void realizarVenda() throws SQLException{
        String nome = systemController.menuBusca();
        int quantidade = validarInteiro();

        Product product = productDaoImpl.buscarProdutoPeloNome(nome);

        if (product == null){
            System.out.println("[Erro] Produto não encontrado!");
        } else if(product.getAmount() < quantidade){
            System.out.println("[Erro] Não há produtos suficientes no estoque.");
        } else {
            product.diminuirEstoque(quantidade);
            productDaoImpl.atualizarProduto(product);
            System.out.println("[Info] Restaram " + product.getAmount() + " unidades de " + product.getName() + " no estoque.");
        }
    }

    private void adicionarEstoque() throws SQLException{
        String nome = systemController.menuBusca();
        int quantidade = validarInteiro();
        Product product = productDaoImpl.buscarProdutoPeloNome(nome);

        if (product == null){
            System.out.println("[Erro] Produto não encontrado!");
        } else if(quantidade < 1){
            System.out.println("[Erro] Digite um número maior que 1.");
        } else {
            product.adicionarEstoque(quantidade);
            productDaoImpl.atualizarProduto(product);
            System.out.println("[Info] Adicionadas " + quantidade + " unidades de " + product.getName() + " ao estoque. Total: " + product.getAmount());
        }
    }

    private void produtoComMenorEstoque() throws SQLException{
        Product menorEstoque = productDaoImpl.listarTodos().getFirst();

        for (int i = 0; i < systemRepository.getProducts().size() ; i++){
            Product atual = systemRepository.getProducts().get(i);
            if (atual.getAmount() < menorEstoque.getAmount()){
                menorEstoque = atual;
            }
        }
        System.out.println("[Info] O produto com menor estoque é " + menorEstoque.getName() +
                " com " + menorEstoque.getAmount() + " unidades no estoque");
    }

    private void relatorioGeral() throws SQLException{
        int estoque = 0;
        double valorTotal = 0;

        for (int i = 0; i < productDaoImpl.listarTodos().size(); i++){

            estoque += productDaoImpl.listarTodos().get(i).getAmount();
            valorTotal += productDaoImpl.listarTodos().get(i).getAmount() * systemRepository.getProducts().get(i).getPrice();

        }
        System.out.println("[Info] Quantidade total de produtos cadastrados: " + productDaoImpl.listarTodos().size());
        System.out.println("[Info] Soma de todas unidades no estoque: " + estoque);
        System.out.println("[Info] Valor total do estoque: R$ " + valorTotal);
    }

    private void alterarPreco() throws SQLException{

        String nome = systemController.menuBusca();
        Product product = productDaoImpl.buscarProdutoPeloNome(nome);

        Double preco = validarDouble();

        if (product == null){
            System.out.println("[Erro] Esse produto não foi cadastrado.");
        } else if (preco <= 0){
            System.out.println("[Erro] Insira um valor maior que 0.");
        } else {
            product.setPrice(preco);
            productDaoImpl.atualizarProduto(product);
        }
    }


    // MÉTODOS DE VALIDAÇÃO PARA ENTRADA DE DADOS DO USUÁRIO
    private Integer validarMenu() {
        while (true) {
            try {
                int option = Integer.parseInt(systemController.menu());
                if (option < 0 || option > 7) {
                    System.out.println("[Erro] Escolha uma opção entre 0 e 7.");
                } else {
                    return option;
                }
            } catch (RuntimeException e) {
                System.out.println("[Erro] Digite apenas números.");
            }
        }
    }

    private int validarInteiro(){
        while (true){
            System.out.println("Quantidade: ");
            try {
                return Integer.parseInt(systemController.saleMenu());
            } catch (InputMismatchException e) {
                System.out.println("[Erro] Digite apenas números");
            }
        }
    }

    private double validarDouble(){
        while (true){
            System.out.println("Digite: ");
            try {
                return Double.parseDouble(systemController.saleMenu());
            } catch (InputMismatchException e) {
                System.out.println("[Erro] Digite apenas números");
            }
        }
    }
}
