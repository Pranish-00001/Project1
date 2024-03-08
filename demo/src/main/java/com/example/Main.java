package com.example;

import java.util.Scanner;

import com.example.models.Bill;
import com.example.models.Items;
import com.example.models.Retriever;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Items item1 = new Items();
        Bill item2 = new Bill();
        Retriever item3 = new Retriever();
        System.out.println("1) Add your shop items \n2) Make a bill\n3) Retrieve previous data");
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                item1.input_taker();
                break;
            
            case 2:
                item2.final_bill();
                break;
            case 3:
                item3.records();  
                break;  
            default:
                System.out.println("Invalid INPUT");
                break;
        }
    
        
    }

}