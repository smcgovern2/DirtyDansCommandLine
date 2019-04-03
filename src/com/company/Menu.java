package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;

public class Menu {
    final static String DB_URL = "jdbc:derby:DoorknobDB;create=true";
    InputStreamReader isr = new InputStreamReader(System.in);
    BufferedReader br = new BufferedReader(isr);
    String input = "";

    public Menu() {
    }


    public void printMenu() {
        boolean isExiting = false;
        while(!isExiting) {
            System.out.println("Welcome to Dirty Dan's Discount Doorknobs, What would you like to do?");
            System.out.println("==========================");
            System.out.println("1 - View Inventory");
            System.out.println("2 - View Cart");
            System.out.println("3 - Exit");
            try {
                input = br.readLine();
                switch (input) {
                    case "1":
                        ViewInventory();
                        break;
                    case "2":
                        ViewCart();
                        break;
                    case "3":
                        isExiting = true;
                        break;
                    default:
                        System.out.println("Unrecognized input");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }





    }

    private void ViewCart() {
        System.out.println("Cart");
        System.out.println("==========================");
        Statement stmt = null;
        Connection conn = null;
        try {

            conn = DriverManager.getConnection(DB_URL);
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT Name, ProdNum, ShortDesc, Price, CartItemNum FROM Cart JOIN Product WHERE Cart.";
            ResultSet rs = stmt.executeQuery(sql);
            ArrayList<String> ids = new ArrayList<>();

            while (rs.next()) {
                //Retrieve by column name
                String cartItemNum = rs.getString("CartItemNum");
                String name = rs.getString("Name");
                String id = rs.getString("ProdNum");
                String description = rs.getString("ShortDesc");
                double price = rs.getDouble("Price");

                ids.add(name);

                System.out.print(cartItemNum.trim());
                System.out.print(" " + name);
                System.out.print(" - $" + price);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        private void ViewInventory() {
        System.out.println("Inventory");
        System.out.println("==========================");
        Statement stmt = null;
        Connection conn = null;
        try{

            conn = DriverManager.getConnection(DB_URL);
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT Name, ProdNum, ShortDesc, Price FROM Product";
            ResultSet rs = stmt.executeQuery(sql);
            ArrayList<String> ids = new ArrayList<>();

            while(rs.next()){
                //Retrieve by column name
                String name = rs.getString("Name");
                String id  = rs.getString("ProdNum");
                String description = rs.getString("ShortDesc");
                double price = rs.getDouble("Price");

                ids.add(name);

                System.out.print("ID: " + id.trim());
                System.out.print(" " + name);
                System.out.print(" - $" + price);
                System.out.println(" Description: " + description);
            }
            System.out.println("Enter the Product ID of the Item you wish to add to the cart, or (B) to return to the main menu");
            input = br.readLine();
            if(input.equals("B")||input.equals("b")){
                //do nothing
            } else {
                if(ids.contains(input)){
                    try{
                        sql = "INSERT INTO Cart  " +
                                "VALUES (DEFAULT, " + Integer.parseInt(input) + ")";
                        stmt.executeUpdate(sql);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    System.out.println("Invalid entry");
                }
            }

        rs.close();
        stmt.close();
        conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try

    }
}
