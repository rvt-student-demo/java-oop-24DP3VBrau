package rvt.products;

public class Product {
    private int id;
    private String name;
    private double price;
    private int categoryId;
    private String categoryName; // Used for JOIN queries

    public Product(int id, String name, double price, int categoryId, String categoryName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    @Override
    public String toString() {
        return String.format("| %-5d | %-20s | %-10.2f | %-20s |", id, name, price, categoryName != null ? categoryName : "N/A");
    }
}
