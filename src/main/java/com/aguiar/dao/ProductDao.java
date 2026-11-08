package com.aguiar.dao;

import com.aguiar.model.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {

    // CADASTRAR PRODUTO
    boolean salvar(Product product) throws SQLException;

    // BUSCAR TODOS OS PRODUTOS CADASTRADOS
    List<Product> listarTodos() throws SQLException;

    // BUSCAR PRODUTO PELO NOME
    Product buscarProdutoPeloNome(String nome) throws SQLException;

    // ATUALIZAR INFORMAÇÕES DO PRODUTO
    boolean atualizarProduto(Product product) throws SQLException;

}
