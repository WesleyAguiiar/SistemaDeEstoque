package com.aguiar.service;

import com.aguiar.controller.SystemController;
import com.aguiar.dto.ProductRecord;
import com.aguiar.model.Product;
import com.aguiar.repository.SystemRepository;

import java.util.InputMismatchException;

public class SystemService {

    private final SystemController systemController = new SystemController();
    private final SystemRepository systemRepository = new SystemRepository();

    public void mainMenu() {
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
                    validarBusca();
                    break;
                case 4:
                    realizarVenda();
                    break;
                case 5:
                    adicionarEstoque();
                    break;
                case 6:
                    menorEstoque();
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

    private void registrarProduto() {
        Product product = validarRegistroProduto();

        if (product == null) {
            System.out.println("[Erro] Não foi possível adicionar o produto.");
        } else {
            systemRepository.addProduct(product);
            System.out.println("[Info] Produto adicionado ao estoque.");
        }
    }

    private Product validarRegistroProduto() {
        ProductRecord product = systemController.registerMenu();

        systemRepository.addProduct(new Product("Bolacha", 5, 10));
        systemRepository.addProduct(new Product("Sapato", 50, 8));
        systemRepository.addProduct(new Product("Bola", 10, 10));

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

    private void listarProdutos() {
        for (Product p : systemRepository.getProducts()) {
            System.out.println(p);
        }
    }

    private void validarBusca(){
        String nome = systemController.menuBusca();
        Product produto = systemRepository.buscarProduto(nome);

        if (produto == null){
            System.out.println("[Erro] Produto não encontrado!");
        } else {
            System.out.println(produto);
        }
    }

    private void realizarVenda(){
        String nome = systemController.menuBusca();
        int quantidade = validarInteiro();
        Product p = systemRepository.buscarProduto(nome);

        if (p == null){
            System.out.println("[Erro] Produto não encontrado!");
        } else if(p.getAmount() < quantidade){
            System.out.println("[Erro] Não há produtos suficientes no estoque.");
        } else {
            p.diminuirEstoque(quantidade);
            System.out.println("[Info] Restaram " + p.getAmount() + " unidades de " + p.getName() + " no estoque.");
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

    private void adicionarEstoque(){
        String nome = systemController.menuBusca();
        int quantidade = validarInteiro();
        Product p = systemRepository.buscarProduto(nome);

        if (p == null){
            System.out.println("[Erro] Produto não encontrado!");
        } else if(quantidade < 1){
            System.out.println("[Erro] Digite um número maior que 1.");
        } else {
            p.adicionarEstoque(quantidade);
            System.out.println("[Info] Restaram " + p.getAmount() + " unidades de " + p.getName() + " no estoque.");
        }
    }

    private void menorEstoque(){
        Product menor = systemRepository.getProducts().getFirst();

        for (int i = 0; i < systemRepository.getProducts().size() ; i++){
            Product atual = systemRepository.getProducts().get(i);
            if (atual.getAmount() < menor.getAmount()){
                menor = atual;
            }
        }
        System.out.println("[Info] O produto com menor estoque é " + menor.getName() +
                " com " + menor.getAmount() + " unidades no estoque");
    }

    private void relatorioGeral(){
        int estoque = 0;
        double valorTotal = 0;

        for (int i = 0; i < systemRepository.getProducts().size(); i++){
            estoque += systemRepository.getProducts().get(i).getAmount();
            valorTotal += systemRepository.getProducts().get(i).getAmount() * systemRepository.getProducts().get(i).getPrice();
        }
        System.out.println("[Info] Quantidade total de produtos cadastrados: " + systemRepository.getProducts().size());
        System.out.println("[Info] Soma de todas unidades no estoque: " + estoque);
        System.out.println("[Info] Valor total do estoque: R$ " + valorTotal);
    }

    private void alterarPreco(){
        String nome = systemController.menuBusca();
        Product product = systemRepository.buscarProduto(nome);
        Double preco = validarDouble();

        if (product == null){
            System.out.println("[Erro] Esse produto não foi cadastrado.");
        } else if (preco <= 0){
            System.out.println("[Erro] Insira um valor maior que 0.");
        } else {
            product.setPrice(preco);
        }
    }
}
