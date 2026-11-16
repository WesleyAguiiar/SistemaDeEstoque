package com.aguiar.service;

import com.aguiar.dao.ProductDao;
import com.aguiar.dto.ProductRecord;
import com.aguiar.dto.RelatorioRecord;
import com.aguiar.exception.*;
import com.aguiar.model.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SystemService {

    private final ProductDao productDao;

    public SystemService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public boolean registerProduct(ProductRecord product) throws SQLException {

        if (product.name() == null || product.name().length() < 2) {
            throw new InvalidNameException();
        } else if (product.price() <= 0) {
            throw new InvalidPriceException();
        } else if (product.amount() < 1) {
            throw new InvalidAmountException();
        } else {
            Product p = new Product(product.name(), product.price(), product.amount());
            return productDao.salvar(p);
        }
    }

    public List<Product> listarProdutos() throws SQLException{
        List<Product> products = new ArrayList<>();
        products.addAll(productDao.listarTodos());
        return products;
    }

    public Product buscarProduto(int id) throws SQLException{
        Product produto = productDao.buscarProdutoPeloId(id);
        return produto;
    }

    public boolean realizarVenda(int id, int quantidade) throws SQLException, InvalidAmountException, InsufficientStock{

        Product product = productDao.buscarProdutoPeloId(id);

        if (product == null){
            throw new ProductNotFoundException();
        } else {
            product.diminuirEstoque(quantidade);
            return productDao.atualizarProduto(product);
        }
    }

    public boolean adicionarEstoque(int id, int quantidade) throws SQLException, InvalidAmountException{
        Product product = productDao.buscarProdutoPeloId(id);

        if (product == null){
            throw new ProductNotFoundException();
        } else{
            product.adicionarEstoque(quantidade);
            return productDao.atualizarProduto(product);
        }
    }

    public Product produtoComMenorEstoque() throws SQLException{
        List<Product> products = new ArrayList<>(productDao.listarTodos());

        if (products.isEmpty()){
            throw new ProductNotFoundException();
        } else {
            Product menorEstoque = products.getFirst();
            for (Product p : products){
                if (p.getAmount() < menorEstoque.getAmount()){
                    menorEstoque = p;
                }
            }
            return menorEstoque;
        }
    }

    public RelatorioRecord relatorioGeral() throws SQLException{
        int estoque = 0;
        double valorTotal = 0;

        List<Product> products = new ArrayList<>(productDao.listarTodos());

        for (Product p : products){
            estoque += p.getAmount();
            valorTotal += p.getPrice() * p.getAmount();
        }
        return new RelatorioRecord(products.size(), estoque, valorTotal);
    }

    public boolean alterarPreco(int id, double preco) throws SQLException, InvalidPriceException{
        Product product = productDao.buscarProdutoPeloId(id);

        if (product == null){
            throw new ProductNotFoundException();
        } else {
            product.setPrice(preco);
            return productDao.atualizarProduto(product);
        }
    }

    public boolean deletarProduto(int id) throws SQLException{
            return productDao.deletarProduto(id);
    }
}
