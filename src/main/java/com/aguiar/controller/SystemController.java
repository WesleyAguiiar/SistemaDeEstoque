package com.aguiar.controller;

import com.aguiar.dto.ProductRecord;

import java.util.Locale;
import java.util.Scanner;

public class SystemController {

    Scanner sc = new Scanner(System.in).useLocale(Locale.ENGLISH);

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

    public String menuBusca(){
        System.out.println("==== BUSCA ====");
        System.out.println("Nome: ");

        return sc.nextLine();
    }

    // MÉTODOS DE VALIDAÇÃO PARA ENTRADA DE DADOS DO USUÁRIO

    public Integer validarMenu() {
        while (true) {
            String valor = sc.nextLine();
            try {
                int option = Integer.parseInt(valor);
                if (option < 0 || option > 8) {
                    System.out.println("[Erro] Escolha uma opção entre 0 e 8.");
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

    public double validarDouble(){
        while (true){
            System.out.println("Digite o valor: ");
            String valor = sc.nextLine();
            try {
                return Double.parseDouble(valor.replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("[Erro] Digite apenas números");
            }
        }
    }
}
