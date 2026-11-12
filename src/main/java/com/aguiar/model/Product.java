package com.aguiar.model;

import java.util.Objects;

public class Product {

    private String name;
    private double price;
    private int amount;

    public Product(String name, double price, int amount) {
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Nome: " + name +
                ", Preço: R$ " + price +
                ", Quantidade: " + amount;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price <= 0){
            System.out.println("[Erro] O preço não pode ser menor ou igual a zero.");
        } else {
            this.price = price;
        }
    }

    public int getAmount() {
        return amount;
    }

    public void adicionarEstoque(int amount){
        if (amount <= 0){
            System.out.println("[Erro] Digite um número maior que zero.");
        } else {
            this.amount += amount;
        }
    }

    public void diminuirEstoque(int quantidade){
        if (this.getAmount() < quantidade){
            System.out.println("[Erro] Não há produtos suficientes no estoque.");
        } else {
            this.amount -= quantidade;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
