package com.aguiar.repository;

import com.aguiar.model.Product;

import java.util.ArrayList;

public class SystemRepository {

    ArrayList<Product> products = new ArrayList<>();

    public void addProduct(Product product){
        products.add(product);
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public Product buscarProduto(String nome){
        for (Product p : getProducts()) {
            if (p.getName().equals(nome)) {
                return p;
            }
        }
        return null;
    }
}
