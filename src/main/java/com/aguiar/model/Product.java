package com.aguiar.model;

import java.util.Objects;

public class Product {

    String name;
    double price;
    int amount;

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
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void adicionarEstoque(int amount){
        this.amount += amount;
    }

    public void diminuirEstoque(int amount){
        this.amount -= amount;
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
