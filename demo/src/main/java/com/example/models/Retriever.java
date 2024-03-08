package com.example.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Retriever {
    
    public void records(){
        Scanner sc= new Scanner(System.in);
        System.out.println("Enter the bill id to see record :");
        String billing_id = sc.nextLine();
        String url = "jdbc:sqlite:mydb.db";
        try{
            Connection connection = DriverManager.getConnection(url);
            System.out.println("\n\nConnected");
            Statement statement = connection.createStatement();

            if (billing_id != null){
                try{
                String query = "SELECT * FROM "+billing_id;
            
                ResultSet rs = statement.executeQuery(query);
                System.out.println("\n\nid\tproduct_name\t\tproduct_price\t\tdiscount\t\ttotal_amount \n\n");
                while (rs.next()) {
                    System.out.println("\n\n" + rs.getInt("id") + "\t\t" + rs.getString("product_name") + "\t\t\t" + rs.getInt("product_price") + "\t\t\t\t" + rs.getInt("discount")  + "\t\t" + rs.getInt("total_amount")+ "\n\n");
                }
                }catch(Exception e) {
                System.out.println("404 NOT FOUND" +        e);
                }
            }
            else{
                System.out.println("ERROR");
            }
        } catch (Exception ex) {
            System.err.println("Error creating connection! " +        ex );

    }    
}
}