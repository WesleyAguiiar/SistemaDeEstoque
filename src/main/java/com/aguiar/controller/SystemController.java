package com.aguiar.controller;

import com.aguiar.dto.ProductRecord;

import java.util.Scanner;

public class SystemController {

    Scanner sc = new Scanner(System.in);

    public String menu() {

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

        return sc.nextLine();
    }

    public ProductRecord registerMenu(){

        System.out.println("==== REGISTRO ====");
        System.out.println("Nome: ");
        String name = sc.nextLine();

        System.out.println("Preço: ");
        double price = sc.nextDouble();

        System.out.println("Quantidade: ");
        int amount = sc.nextInt();

        return new ProductRecord(name, price, amount);
    }

    public String menuBusca(){
        System.out.println("==== BUSCA ====");
        System.out.println("Nome: ");

        return sc.nextLine();
    }

    public String saleMenu(){
        return sc.nextLine();
    }
}
