package com.example.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Bill {
    public void final_bill() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Shop name");
        String shopname = sc.nextLine();

        String url = "jdbc:sqlite:mydb.db";
        int rowCount = 0;

        try {
            Connection connection = DriverManager.getConnection(url);
            System.out.println("\n\nConnected");

            String showtableSQL = "SELECT * FROM " + shopname;

            Statement statement = connection.createStatement();
            statement.execute(showtableSQL);

            String countRowsSQL = "SELECT COUNT(*) FROM " + shopname;
            ResultSet countRs = statement.executeQuery(countRowsSQL);
            countRs.next();
            rowCount = countRs.getInt(1);

            ResultSet rs = statement.executeQuery(showtableSQL);
            while (rs.next()) {
                System.out.println("\n\n" + rs.getInt("id") + "\t" + rs.getString("item") + "\t\t" + rs.getInt("price")
                        + "\n\n");
            }

            System.out.println("How many items are there in your bill? ");
            int num_items = sc.nextInt();
            sc.nextLine(); // Consume newline character

            int[] itemIds = new int[num_items];
            String[] products = new String[num_items];
            int[] product_prices = new int[num_items];

            for (int i = 0; i < num_items; i++) {
                System.out.println("Enter the corresponding ID of item " + (i + 1) + ": ");
                itemIds[i] = sc.nextInt();
                sc.nextLine(); 

                String selected_item_price = "SELECT item, price FROM " + shopname + " WHERE id =" + itemIds[i];
                ResultSet rs1 = statement.executeQuery(selected_item_price);

                if (rs1.next()) {
                    products[i] = rs1.getString("item");
                    product_prices[i] = rs1.getInt("price");
                    System.out.println("Item added: " + products[i] + " - Price: " + product_prices[i]);
                } else {
                    System.out.println("Item not available for ID: " + itemIds[i]);
                }
            }

            int total = 0;

            for (int i = 0; i < num_items; i++) {
                System.out.println(products[i] + "\t\t" + product_prices[i]);
                total += product_prices[i];
            }

            System.out.println("Give Discount? (1-yes) (0-no)");
            int discount = sc.nextInt();

            int dis_amount = total;
            float discount2 = 0; 

            if (discount == 1) {
                System.out.println("Enter discount percent :");
                int perc_discount = sc.nextInt();
                sc.nextLine();
                discount2 = 1.0f - (perc_discount / 100.0f);
                dis_amount = (int) (total * discount2);
            }
            System.out.println(" GIVE BILLING ID ");
            String identify = sc.nextLine();
            

            System.out.println("\n----------BILL("+ identify +")----------\n");

            for (int i = 0; i < num_items; i++) {
                System.out.println(products[i] + "\t\t" + product_prices[i]);   
            }

            System.out.println("DISCOUNT :\t" + discount2);
            System.out.println("Total amount is : " + dis_amount+"\n");

            Statement statement2 = connection.createStatement();
            String createBillHistoryTable = "CREATE TABLE IF NOT EXISTS " + identify + " (id INTEGER PRIMARY KEY NOT NULL, " +
            "product_name TEXT, " +
            "product_price INTEGER, " +
            "discount INTEGER, " +
            "total_amount INTEGER)";

            
            statement2.execute(createBillHistoryTable);
            System.out.println("Added!");
            String insert_to_BillHistory = "INSERT INTO "+identify+ " (product_name, product_price, discount, total_amount) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insert_to_BillHistory);

            for (int i = 0; i < num_items; i++) {
                preparedStatement.setString(1, products[i]);
                preparedStatement.setInt(2, product_prices[i]);
                preparedStatement.setInt(3, discount);
                preparedStatement.setInt(4, dis_amount);
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("Error" +e);
           

        }
    }
}
