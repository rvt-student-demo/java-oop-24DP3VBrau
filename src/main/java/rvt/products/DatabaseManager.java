package rvt.products;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:data/products.db";

    public DatabaseManager() {
        createTables();
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    private void createTables() {
        String categoriesTable = "CREATE TABLE IF NOT EXISTS categories (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL" +
                ");";

        String productsTable = "CREATE TABLE IF NOT EXISTS products (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "price REAL NOT NULL," +
                "category_id INTEGER," +
                "FOREIGN KEY (category_id) REFERENCES categories (id)" +
                ");";

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(categoriesTable);
            stmt.execute(productsTable);
        } catch (SQLException e) {
            System.err.println("Error creating tables: " + e.getMessage());
        }
    }

    public void addCategory(String name) {
        String sql = "INSERT INTO categories (name) VALUES (?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
            System.out.println("Kategorija '" + name + "' pievienota!");
        } catch (SQLException e) {
            System.err.println("Kļūda pievienojot kategoriju: " + e.getMessage());
        }
    }

    public void addProduct(String name, double price, int categoryId) {
        String sql = "INSERT INTO products (name, price, category_id) VALUES (?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setDouble(2, price);
            pstmt.setInt(3, categoryId);
            pstmt.executeUpdate();
            System.out.println("Produkts '" + name + "' pievienots!");
        } catch (SQLException e) {
            System.err.println("Kļūda pievienojot produktu: " + e.getMessage());
        }
    }

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                categories.add(new Category(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            System.err.println("Kļūda: " + e.getMessage());
        }
        return categories;
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.id, p.name, p.price, p.category_id, c.name as category_name " +
                     "FROM products p LEFT JOIN categories c ON p.category_id = c.id";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("category_id"),
                        rs.getString("category_name")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Kļūda: " + e.getMessage());
        }
        return products;
    }

    public List<Product> getProductsByCategorySearch(String searchInput) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.id, p.name, p.price, p.category_id, c.name as category_name " +
                     "FROM products p LEFT JOIN categories c ON p.category_id = c.id " +
                     "WHERE c.id = ? OR c.name LIKE ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Try parsing to ID, else pass -1
            int searchId = -1;
            try {
                searchId = Integer.parseInt(searchInput);
            } catch (NumberFormatException ignored) {}

            pstmt.setInt(1, searchId);
            pstmt.setString(2, "%" + searchInput + "%"); // LIKE matching

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("category_id"),
                        rs.getString("category_name")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Kļūda: " + e.getMessage());
        }
        return products;
    }

    public void deleteProduct(int id) {
        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Produkts veiksmīgi izdzēsts.");
            } else {
                System.out.println("Produkts ar norādīto ID netika atrasts.");
            }
        } catch (SQLException e) {
            System.err.println("Kļūda dzēšot produktu: " + e.getMessage());
        }
    }

    public void updateProduct(int id, String newName, double newPrice, int newCategoryId) {
        String sql = "UPDATE products SET name = ?, price = ?, category_id = ? WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newName);
            pstmt.setDouble(2, newPrice);
            pstmt.setInt(3, newCategoryId);
            pstmt.setInt(4, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Produkts veiksmīgi atjaunināts.");
            } else {
                System.out.println("Produkts ar norādīto ID netika atrasts.");
            }
        } catch (SQLException e) {
            System.err.println("Kļūda atjauninot produktu: " + e.getMessage());
        }
    }
}
