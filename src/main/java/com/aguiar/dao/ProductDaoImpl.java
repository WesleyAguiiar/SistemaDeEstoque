package com.aguiar.dao;

import com.aguiar.connection.ConnectionFactory;
import com.aguiar.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao{

    // CREATE
    @Override
    public boolean salvar(Product product) throws SQLException {

        String sql = """
                INSERT INTO produtos (nome, preco, quantidade)
                VALUES (?, ?, ?)
                """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setInt(3, product.getAmount());

            return statement.executeUpdate() > 0;
        }
    }

    // READ
    @Override
    public List<Product> listarTodos() throws SQLException {

        String sql = """
                SELECT id, nome, preco, quantidade
                FROM produtos
                ORDER BY id
                """;

        List<Product> products = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){

            try (ResultSet result = statement.executeQuery()){
                while (result.next()){

                    int id = result.getInt("id");
                    String nome = result.getString("nome");
                    double preco = result.getDouble("preco");
                    int quantidade = result.getInt("quantidade");

                    Product product = new Product(id, nome, preco, quantidade);
                    products.add(product);
                }
            }
        }
        return products;
    }

    // READ (PELO NOME)
    @Override
    public Product buscarProdutoPeloId(int id) throws SQLException {

        String sql = """
                SELECT id, nome, preco, quantidade
                FROM produtos
                WHERE id = ?
                """;

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setInt(1, id);

            try (ResultSet result = statement.executeQuery()){
                while (result.next()){
                    Product product = new Product(
                            result.getInt("id"),
                            result.getString("nome"),
                            result.getDouble("preco"),
                            result.getInt("quantidade"));

                    return product;
                }
            }
        }
        return null;
    }

    // UPDATE
    @Override
    public boolean atualizarProduto(Product product) throws SQLException {

        String sql = """
                UPDATE produtos 
                SET nome = ?, preco = ?, quantidade = ?
                WHERE id = ?
                """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setInt(3, product.getAmount());
            statement.setInt(4, product.getId());

            return statement.executeUpdate() > 0;
        }
    }

    // DELETE
    @Override
    public boolean deletarProduto(int id) throws SQLException {
        String sql = """
                DELETE FROM produtos
                WHERE id = ?
                """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;
        }
    }
}
