package rvt.products;

import java.util.List;
import java.util.Scanner;

public class ProductsCli {
    private static final DatabaseManager dbManager = new DatabaseManager();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("====== Produktu un kategoriju sistēma ======");
        
        while (true) {
            printMenu();
            System.out.print("Jūsu izvēle: ");
            String input = scanner.nextLine();
            
            switch (input) {
                case "1":
                    addCategory();
                    break;
                case "2":
                    addProduct();
                    break;
                case "3":
                    showAllCategories();
                    break;
                case "4":
                    showAllProducts();
                    break;
                case "5":
                    searchProductsByCategory();
                    break;
                case "6":
                    updateProduct();
                    break;
                case "7":
                    deleteProduct();
                    break;
                case "0":
                    System.out.println("Programma pabeigta.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Nepareiza izvēle, mēģiniet vēlreiz!");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n-------------------------------------------");
        System.out.println("1 - Pievienot kategoriju");
        System.out.println("2 - Pievienot produktu");
        System.out.println("3 - Parādīt visas kategorijas");
        System.out.println("4 - Parādīt visus produktus");
        System.out.println("5 - Meklēt produktus pēc kategorijas");
        System.out.println("6 - Rediģēt produktu (Update)");
        System.out.println("7 - Dzēst produktu (Delete)");
        System.out.println("0 - Iziet");
        System.out.println("-------------------------------------------");
    }

    private static void addCategory() {
        System.out.print("Ievadiet kategorijas nosaukumu: ");
        String name = scanner.nextLine();
        if (!name.trim().isEmpty()) {
            dbManager.addCategory(name.trim());
        } else {
            System.out.println("Kategorijas nosaukums nevar būt tukšs!");
        }
    }

    private static void addProduct() {
        System.out.print("Ievadiet produkta nosaukumu: ");
        String name = scanner.nextLine();
        if (name.trim().isEmpty()) {
            System.out.println("Nosaukums nevar būt tukšs!");
            return;
        }

        System.out.print("Ievadiet cenu: ");
        double price;
        try {
            price = Double.parseDouble(scanner.nextLine());
            if (price <= 0) {
                System.out.println("Cenai jābūt lielākai par 0!");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Nederīga cena!");
            return;
        }

        System.out.println("Esošās kategorijas:");
        showAllCategories();
        System.out.print("Ievadiet kategorijas ID: ");
        int categoryId;
        try {
            categoryId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Nederīgs ID!");
            return;
        }

        dbManager.addProduct(name.trim(), price, categoryId);
    }

    private static void showAllCategories() {
        List<Category> categories = dbManager.getAllCategories();
        if (categories.isEmpty()) {
            System.out.println("Nav nevienas kategorijas datubāzē.");
            return;
        }
        System.out.println(String.format("+-%-5s-+-%-20s-+", "-----", "--------------------"));
        System.out.println(String.format("| %-5s | %-20s |", "ID", "Kategorija"));
        System.out.println(String.format("+-%-5s-+-%-20s-+", "-----", "--------------------"));
        for (Category c : categories) {
            System.out.println(c);
        }
        System.out.println(String.format("+-%-5s-+-%-20s-+", "-----", "--------------------"));
    }

    private static void showAllProducts() {
        List<Product> products = dbManager.getAllProducts();
        printProductsTable(products);
    }

    private static void searchProductsByCategory() {
        System.out.print("Ievadiet kategorijas ID vai nosaukumu: ");
        String searchInput = scanner.nextLine();
        List<Product> products = dbManager.getProductsByCategorySearch(searchInput.trim());
        printProductsTable(products);
    }

    private static void updateProduct() {
        showAllProducts();
        System.out.print("Ievadiet produkta ID, kuru vēlaties rediģēt: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Nederīgs ID!");
            return;
        }

        System.out.print("Ievadiet jauno produkta nosaukumu: ");
        String name = scanner.nextLine();
        if (name.trim().isEmpty()) {
            System.out.println("Nosaukums nevar būt tukšs!");
            return;
        }

        System.out.print("Ievadiet jauno cenu: ");
        double price;
        try {
            price = Double.parseDouble(scanner.nextLine());
            if (price <= 0) {
                System.out.println("Cenai jābūt lielākai par 0!");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Nederīga cena!");
            return;
        }

        showAllCategories();
        System.out.print("Ievadiet jauno kategorijas ID: ");
        int categoryId;
        try {
            categoryId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Nederīgs ID!");
            return;
        }

        dbManager.updateProduct(id, name.trim(), price, categoryId);
    }

    private static void deleteProduct() {
        showAllProducts();
        System.out.print("Ievadiet produkta ID, kuru vēlaties dzēst: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Nederīgs ID!");
            return;
        }

        dbManager.deleteProduct(id);
    }

    private static void printProductsTable(List<Product> products) {
        if (products.isEmpty()) {
            System.out.println("Netika atrasts neviens produkts.");
            return;
        }
        System.out.println(String.format("+-%-5s-+-%-20s-+-%-10s-+-%-20s-+", "-----", "--------------------", "----------", "--------------------"));
        System.out.println(String.format("| %-5s | %-20s | %-10s | %-20s |", "ID", "Nosaukums", "Cena", "Kategorija"));
        System.out.println(String.format("+-%-5s-+-%-20s-+-%-10s-+-%-20s-+", "-----", "--------------------", "----------", "--------------------"));
        for (Product p : products) {
            System.out.println(p);
        }
        System.out.println(String.format("+-%-5s-+-%-20s-+-%-10s-+-%-20s-+", "-----", "--------------------", "----------", "--------------------"));
    }
}
