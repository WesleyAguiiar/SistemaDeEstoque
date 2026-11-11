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

        try (Connection connection = ConnectionFactory.getConnection()){

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setInt(3, product.getAmount());

            return statement.executeUpdate() > 0;

        } catch (SQLException e){
            System.out.println("[ERRO] " + e.getMessage());
        }
        return false;
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

        try (Connection connection = ConnectionFactory.getConnection()){

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            while (result.next()){

                String nome = result.getString("nome");
                Double preco = result.getDouble("preco");
                Integer quantidade = result.getInt("quantidade");

                Product product = new Product(nome, preco, quantidade);
                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println("[Erro] " + e.getMessage());
        }
        return products;
    }

    // READ (PELO NOME)
    @Override
    public Product buscarProdutoPeloNome(String nome) throws SQLException {

        String sql = """
                SELECT id, nome, preco, quantidade
                FROM produtos
                WHERE nome = ?
                """;

        try (Connection connection = ConnectionFactory.getConnection()){

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nome);

            ResultSet result = statement.executeQuery();

            while (result.next()){
                Product product = new Product(result.getString("nome"),
                        result.getDouble("preco"),
                        result.getInt("quantidade"));

                return product;
            }

        } catch (SQLException e) {
            System.out.println("[Erro] " + e.getMessage());
        }
        return null;
    }

    // UPDATE
    public boolean atualizarProduto(Product product) throws SQLException {

        String sql = """
                UPDATE produtos 
                SET nome = ?, preco = ?, quantidade = ?
                WHERE nome = ?
                """;

        try (Connection connection = ConnectionFactory.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setInt(3, product.getAmount());
            statement.setString(4, product.getName());

            return statement.executeUpdate() > 0;
        }
    }
}
