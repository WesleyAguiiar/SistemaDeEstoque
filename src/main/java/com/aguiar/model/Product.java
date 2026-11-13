package com.aguiar.model;

import com.aguiar.exception.InsufficientStock;
import com.aguiar.exception.InvalidAmountException;
import com.aguiar.exception.InvalidPriceException;

import java.util.Objects;

public class Product {

    private String name;
    private double price;
    private int amount;

    public Product(String name, double price, int amount) {
        if (name == null || name.length() < 2){
            System.out.println("[Erro] O nome não pode ter menos de 2 caracteres.");
        }
        else if (price <= 0){
            System.out.println("[Erro] O preço não pode ser menor ou igual a 0.");
        } else if (amount < 0) {
            System.out.println("[Erro] A quantidade não pode ser menor que 0.");
        } else {
            this.name = name;
            this.price = price;
            this.amount = amount;
        }
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

    public boolean setPrice(double price) {
        if (price <= 0){
            throw new InvalidPriceException();
        } else {
            this.price = price;
            return true;
        }
    }

    public int getAmount() {
        return amount;
    }

    public boolean adicionarEstoque(int quantidade){
        if (quantidade <= 0){
            throw new InvalidAmountException();
        } else {
            this.amount += quantidade;
            return true;
        }
    }

    public boolean diminuirEstoque(int quantidade){
        if (this.getAmount() < quantidade){
            throw new InsufficientStock();
        } else if (quantidade <= 0) {
            throw new InvalidAmountException();
        } else {
            this.amount -= quantidade;
            return true;
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
