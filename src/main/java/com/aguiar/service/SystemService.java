package com.aguiar.service;

import com.aguiar.controller.SystemController;
import com.aguiar.dao.ProductDao;
import com.aguiar.dto.ProductRecord;
import com.aguiar.model.Product;

import java.sql.SQLException;
import java.util.InputMismatchException;

public class SystemService {

    private final SystemController systemController = new SystemController();
    private final ProductDao productDao;

    public SystemService(ProductDao productDao) {
        this.productDao = productDao;
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
            productDao.salvar(product);
            System.out.println("[Info] Produto adicionado ao estoque.");
        }
    }

    private Product validarRegistroProduto() throws SQLException{

        ProductRecord product = systemController.registerMenu();

        if (product.amount() < 1) {
            System.out.println("[Erro] O número de produtos precisa ser maior que 0.");
        } else if (product.price() <= 0) {
            System.out.println("[Erro] O preço do produto precisa ser maior que 0.");
        } else if (product.name() == null || product.name().length() < 2) {
            System.out.println("[Erro] O nome do produto não pode ser nulo.");
        } else if (productDao.buscarProdutoPeloNome(product.name()) != null){
            System.out.println("[Erro] Produto já cadastrado no sistema.");
        } else {
            return new Product(product.name(), product.price(), product.amount());
        }
        return null;
    }

    private void listarProdutos() throws SQLException{

        System.out.println("[Info] Produtos cadastrados: \n");
        for (Product p : productDao.listarTodos()) {
            System.out.println(p);
        }
    }

    private void buscarProduto() throws SQLException{

        String nome = systemController.menuBusca();
        Product produto = productDao.buscarProdutoPeloNome(nome);

        if (!(produto == null)){
            System.out.println(produto);
        } else {
            throw new ProductNotFoundException();
        }
    }

    private void realizarVenda() throws SQLException{
        String nome = systemController.menuBusca();
        int quantidade = validarInteiro();

        Product product = productDao.buscarProdutoPeloNome(nome);

        if (product == null){
            System.out.println("[Erro] Produto não encontrado!");
        } else if(product.getAmount() < quantidade){
            System.out.println("[Erro] Não há produtos suficientes no estoque.");
        } else if (quantidade == 0){
            System.out.println("[Erro] Digite uma quantidade maior que zero.");
        } else {
            product.diminuirEstoque(quantidade);
            productDao.atualizarProduto(product);
            System.out.println("[Info] Restaram " + product.getAmount() + " unidades de " + product.getName() + " no estoque.");
        }
    }

    private void adicionarEstoque() throws SQLException{
        String nome = systemController.menuBusca();
        int quantidade = validarInteiro();
        Product product = productDao.buscarProdutoPeloNome(nome);

        if (product == null){
            System.out.println("[Erro] Produto não encontrado!");
        } else if(quantidade < 1){
            System.out.println("[Erro] Digite um número maior que 1.");
        } else {
            product.adicionarEstoque(quantidade);
            productDao.atualizarProduto(product);
            System.out.println("[Info] Adicionadas " + quantidade + " unidades de " + product.getName() + " ao estoque. Total: " + product.getAmount());
        }
    }

    private void produtoComMenorEstoque() throws SQLException{
        Product menorEstoque = productDao.listarTodos().getFirst();

        for (int i = 0; i < productDao.listarTodos().size() ; i++){
            Product atual = productDao.listarTodos().get(i);
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

        for (int i = 0; i < productDao.listarTodos().size(); i++){

            estoque += productDao.listarTodos().get(i).getAmount();
            valorTotal += productDao.listarTodos().get(i).getAmount() * productDao.listarTodos().get(i).getPrice();

        }
        System.out.println("[Info] Quantidade total de produtos cadastrados: " + productDao.listarTodos().size());
        System.out.println("[Info] Soma de todas unidades no estoque: " + estoque);
        System.out.println("[Info] Valor total do estoque: R$ " + valorTotal);
    }

    private void alterarPreco() throws SQLException{

        String nome = systemController.menuBusca();
        Product product = productDao.buscarProdutoPeloNome(nome);

        Double preco = validarDouble();

        if (product == null){
            System.out.println("[Erro] Esse produto não foi cadastrado.");
        } else if (preco <= 0){
            System.out.println("[Erro] Insira um valor maior que 0.");
        } else {
            product.setPrice(preco);
            productDao.atualizarProduto(product);
        }
    }

    // MÉTODOS DE VALIDAÇÃO PARA ENTRADA DE DADOS DO USUÁRIO
    private Integer validarMenu() {
        while (true) {
            try {
                int option = Integer.parseInt(systemController.menu());
                if (option < 0 || option > 8) {
                    System.out.println("[Erro] Escolha uma opção entre 0 e 7.");
                } else {
                    return option;
                }
            } catch (InputMismatchException e) {
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
            System.out.println("Digite o novo valor: ");
            try {
                return Double.parseDouble(systemController.saleMenu());
            } catch (InputMismatchException e) {
                System.out.println("[Erro] Digite apenas números");
            }
        }
    }
}
