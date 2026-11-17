package com.aguiar.model;

import com.aguiar.exception.InsufficientStock;
import com.aguiar.exception.InvalidAmountException;
import com.aguiar.exception.InvalidNameException;
import com.aguiar.exception.InvalidPriceException;

import java.util.Objects;

import static java.lang.Double.isInfinite;
import static java.lang.Double.isNaN;

public class Product {

    // FALTA CORRIGIR O USO DO ID PARA AS OUTRAS CLASSES

    private int id;
    private String name;
    private double price;
    private int amount;

    public Product(int id, String name, Double price, int amount) {
        if (name == null || name.isBlank()){
            throw new InvalidNameException();
        } else if (validarPreco(price)){
            throw new InvalidPriceException();
        } else if (amount < 0) {
            throw new InvalidAmountException();
        } else {
            this.id = id;
            this.name = name.trim();
            this.price = price;
            this.amount = amount;
        }
    }

    public Product(String name, double price, int amount) {
        if (name == null || name.trim().isBlank()){
            throw new InvalidNameException();
        } else if (validarPreco(price)){
            throw new InvalidPriceException();
        } else if (amount < 0) {
            throw new InvalidAmountException();
        } else {
            this.name = name;
            this.price = price;
            this.amount = amount;
        }
    }

    @Override
    public String toString() {
        return "ID: " + id +
                ", Nome: " + name +
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
        if (validarPreco(price)){
            throw new InvalidPriceException();
        } else {
            this.price = price;
        }
    }

    public int getAmount() {
        return amount;
    }

    public int getId() {
        return id;
    }

    public void adicionarEstoque(int quantidade){
        if (quantidade <= 0){
            throw new InvalidAmountException();
        } else {
            this.amount += quantidade;
        }
    }

    public void diminuirEstoque(int quantidade){
        if (this.getAmount() < quantidade){
            throw new InsufficientStock();
        } else if (quantidade <= 0) {
            throw new InvalidAmountException();
        } else {
            this.amount -= quantidade;
        }
    }

    boolean validarPreco(double preco){
        return isInfinite(preco) || isNaN(preco) || preco <= 0;
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
