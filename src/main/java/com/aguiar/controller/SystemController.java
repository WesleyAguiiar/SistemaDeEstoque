package com.aguiar.controller;

import com.aguiar.dto.ProductRecord;
import com.aguiar.dto.RelatorioRecord;
import com.aguiar.exception.*;
import com.aguiar.model.Product;
import com.aguiar.service.SystemService;

import java.sql.SQLException;
import java.util.Locale;
import java.util.Scanner;

public class SystemController {

    private final Scanner sc = new Scanner(System.in).useLocale(Locale.ENGLISH);
    SystemService systemService;

    public SystemController(SystemService systemService) {
        this.systemService = systemService;
    }

    public void mainMenu(){
        while (true) {
            Integer option = menu();
            switch (option) {
                case 1:
                    try {
                        if (systemService.registerProduct(menuRegistro())){
                            System.out.println("[Info] Produto adicionado ao sistema.");
                        } else {
                            System.out.println("[Erro] Não foi possível cadastrar o produto.");
                        }
                    } catch (BusinessException | SQLException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        System.out.println("[Info] Produtos cadastrados: \n");
                        for (Product p : systemService.listarProdutos()) {
                            System.out.println(p);
                        }
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        Product product = systemService.buscarProduto(validarBusca());
                        if (product != null){
                            System.out.println(product);
                        } else {
                            System.out.println("[Erro] Produto não encontrado!");
                        }
                    } catch (SQLException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    try {
                        if (systemService.realizarVenda(validarBusca(), validarInteiro())){
                            System.out.println("[Info] Transação concluida com sucesso!");
                        }
                    } catch (BusinessException | SQLException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5:
                    try {
                        if (systemService.adicionarEstoque(validarBusca(), validarInteiro())){
                            System.out.println("[Info] A quantidade de produtos no estoque foi atualizada.");
                        } else {
                            System.out.println("[Erro] A quantidade inserida precisa ser maior que zero.");
                        }
                    } catch (BusinessException | SQLException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 6:
                    try {
                        Product menorEstoque = systemService.produtoComMenorEstoque();
                        System.out.println("[Info] O produto com menor estoque é " + menorEstoque.getName() +
                                " com " + menorEstoque.getAmount() + " unidades no estoque");
                    } catch (BusinessException | SQLException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 7:
                    try {
                        RelatorioRecord relatorioRecord = systemService.relatorioGeral();
                        System.out.println("[Info] Quantidade total de produtos cadastrados: " + relatorioRecord.produtosCadastrados());
                        System.out.println("[Info] Soma de todas unidades no estoque: " + relatorioRecord.estoque());
                        System.out.println("[Info] Valor total do estoque: R$ " + relatorioRecord.valorTotal());

                    } catch (SQLException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 8:
                    try {
                        if (systemService.alterarPreco(validarBusca(), validarDouble())){
                            System.out.println("[Info] Os dados do produto foram alterados com sucesso!");
                        }
                    } catch (BusinessException | SQLException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 9:
                    try {
                        if (systemService.deletarProduto(validarBusca())){
                            System.out.println("[Info] Produto deletado do banco de dados.");
                        } else {
                            System.out.println("[Erro] Produto não encontrado!");
                        }
                    } catch (SQLException e){
                        System.out.println("[Erro] " + e.getMessage());
                    }
                    break;
                case 0:
                    System.exit(0);
            }
        }
    }

    public Integer menu() {

        System.out.println("==== ESTOQUE ====\n");
        System.out.println("1 - Registrar produto");
        System.out.println("2 - Listar produtos");
        System.out.println("3 - Buscar produto");
        System.out.println("4 - Realizar venda");
        System.out.println("5 - Repor estoque");
        System.out.println("6 - Produto com menor estoque");
        System.out.println("7 - Relátorio geral");
        System.out.println("8 - Alterar preço de produto");
        System.out.println("9 - Deletar produto");
        System.out.println("0 - Sair");

        return validarMenu();
    }

    public ProductRecord menuRegistro(){

        System.out.println("==== REGISTRO ====");
        System.out.println("Nome: ");
        String name = sc.nextLine();

        double price = validarDouble();
        int amount = validarInteiro();

        return new ProductRecord(name, price, amount);
    }

    // MÉTODOS DE VALIDAÇÃO PARA ENTRADA DE DADOS DO USUÁRIO

    public Integer validarMenu() {
        while (true) {
            String valor = sc.nextLine();
            try {
                int option = Integer.parseInt(valor);
                if (option < 0 || option > 9) {
                    System.out.println("[Erro] Escolha uma opção entre 0 e 9.");
                } else {
                    return option;
                }
            } catch (NumberFormatException e) {
                System.out.println("[Erro] Digite apenas números.");
            }
        }
    }

    public int validarInteiro(){
        while (true){
            System.out.println("Digite a Quantidade: ");
            String valor = sc.nextLine();
            try {
                return Integer.parseInt(valor);
            } catch (NumberFormatException e) {
                System.out.println("[Erro] Digite apenas números");
            }
        }
    }

    public int validarBusca(){
        while (true){
            System.out.println("Digite o ID do produto: ");
            String valor = sc.nextLine();
            try {
                return Integer.parseInt(valor);
            } catch (NumberFormatException e) {
                System.out.println("[Erro] Digite apenas números");
            }
        }
    }

    public double validarDouble(){
        while (true){
            System.out.println("Digite o valor: ");
            String valor = sc.nextLine();
            try {
                Double resultado = Double.parseDouble(valor.replace(',', '.'));
                if (resultado.isInfinite() || resultado.isNaN()){
                    System.out.println("[Erro] O valor inserido é inválido.");
                } else {
                    return resultado;
                }
            } catch (NumberFormatException e) {
                System.out.println("[Erro] Digite apenas números");
            }
        }
    }
}
