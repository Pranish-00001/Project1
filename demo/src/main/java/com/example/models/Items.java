package com.example.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Scanner;

public class Items {
    private String[] products;
    private int[] products_prices;

    public void input_taker() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Shop name: ");
        String shopname = sc.nextLine();

        System.out.println("How many items in the shop? ");
        int num = sc.nextInt();
        sc.nextLine();

        products = new String[num];
        products_prices = new int[num];

        for (int i = 0; i < num; i++) {
            System.out.println("Enter the product name and price for item " + (i + 1) + ": ");
            products[i] = sc.nextLine();
            products_prices[i] = sc.nextInt();
            sc.nextLine();
        }

        String url = "jdbc:sqlite:mydb.db";

        System.out.println("\n\n\n\n---------ITEMS IN " + shopname + "----------\n\n");

        for (int i = 0; i < num; i++) {
            System.out.println((i + 1) + ")\t" + products[i] + "\t\t" + products_prices[i] + "\n");
        }

        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {

            System.out.println("Connected");

            String createTableSQL = "CREATE TABLE IF NOT EXISTS " + shopname + "(id integer PRIMARY KEY NOT NULL," +
                    "item text," +
                    "price integer)";

            statement.execute(createTableSQL);
            System.out.println("Database Created");

            String add_query = "INSERT INTO " + shopname + "(item, price) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(add_query)) {
                for (int i = 0; i < products.length; i++) {
                    preparedStatement.setString(1, products[i]);
                    preparedStatement.setInt(2, products_prices[i]);
                    preparedStatement.executeUpdate();
                }
                System.out.println("Data Inserted");
            }

        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }
}
