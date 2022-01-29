package com.mycompany.supermarket;

import de.vandermeer.asciitable.AsciiTable;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DatabaseOperations {
    private static Statement stmt;
    private static Connection c = null;
    private static Scanner scanner;

    DatabaseOperations() {
        Connection c = null;

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            c = DriverManager.getConnection("jdbc:sqlite:supermarket.db");
            stmt = c.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String createItemsTableQuery = "CREATE TABLE IF NOT EXISTS items (\n" +
                "    sr_no VARCHAR,\n" +
                "    name TEXT,\n" +
                "    price REAL   \n" +
                ");";
        String createUsersTableQuery = "CREATE TABLE IF NOT EXISTS users (\n" +
                "    username VARCHAR,\n" +
                "    password VARCHAR\n" +
                ");";
        try {
            stmt.executeUpdate(createItemsTableQuery);
            stmt.executeUpdate(createUsersTableQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Opened database successfully");
    }

    public static int insertItem(Item item) {
        String insertQuery = "INSERT INTO items VALUES (\"" + item.getSrno() + "\", \"" + item.getName() + "\", " + item.getPrice() + ");";
        try {
            stmt.executeUpdate(insertQuery);
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int insertUser(User user) {
        String insertQuery = "INSERT INTO users VALUES (\"" + user.getUsername() + "\", \"" + user.getPassword() + "\");";
        try {
            stmt.executeUpdate(insertQuery);
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getPassword(String username) {
        String selectQuery = "SELECT * FROM users WHERE username=\"" + username + "\"" + ";";
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(selectQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String password = null;
        try {
            while (rs.next()) {
                password = rs.getString("password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return password;
    }



    public static int deleteItem(Item item) {
        String deleteQuery = "DELETE FROM items WHERE sr_no=\"" + item.getSrno() + "\";";
        try {
            stmt.executeUpdate(deleteQuery);
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static ArrayList<Item> getAllItems() {
        ArrayList<Item> items = new ArrayList<>();
        String selectQuery = "SELECT * FROM items";
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(selectQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            while (rs.next()) {
                String srno = rs.getString("sr_no");
                String name = rs.getString("name");
                float price = rs.getInt("price");
                Item item = new Item(srno, name, price);
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    static void addItem() {
        scanner.nextLine();
        System.out.print("Enter Serial Number of item: ");
        String srno = scanner.nextLine();
        System.out.print("Enter name of item: ");
        String name = scanner.nextLine();
        System.out.print("Enter price of item: ");
        float price = scanner.nextFloat();
        Item item = new Item(srno, name, price);
        if (insertItem(item) == 1) {
            System.out.println("Added Item successfully");
        } else {
            System.out.println("Failed to add item");
        }
    }

    private static void showAllItems() {
        ArrayList<Item> items = getAllItems();
        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("Serial Number", "Item name", "Price");
        at.addRule();
        for (Item item : items) {
            at.addRow(item.getSrno(), item.getName(), item.getPrice());
        }
        at.addRule();
        String table = at.render();
        System.out.println(table);
    }

    public static void addUser() {
        scanner.nextLine();
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        User user = new User(username, password);
        insertUser(user);
    }

    public static void main(String[] args) {
        new DatabaseOperations();
        scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("Enter choice");
            System.out.println("1. Add Item");
            System.out.println("2. Show items");
            System.out.println("3. Delete item");
            System.out.println("4. Add User");
            System.out.println("5. Exit");
            System.out.println();
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    addItem();
                    break;
                case 2:
                    showAllItems();
                    break;
                case 3:
                    scanner.nextLine();
                    System.out.print("Enter Serial Number: ");
                    String srno = scanner.nextLine();
                    Item item = new Item(srno, null, 0);
                    deleteItem(item);
                    break;
                case 4:
                    addUser();
            }
        } while (choice != 5);
    }
}
