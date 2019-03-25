package com.company;
import java.sql.*;

public class CreateDB {
    public CreateDB(){
        try{
            final String DB_URL = "jdbc:derby:DoorknobDB;create=true";

            Connection conn = DriverManager.getConnection(DB_URL);

            // If the DB already exists, drop the tables.
            dropTables(conn);

            // Build the Coffee table.
            buildProductTable(conn);

            // Build the Customer table.
            buildCartTable(conn);

             //Close the connection.
            conn.close();


        }
        catch(Exception e){

        }






    }
    public static void dropTables(Connection conn)
    {
        System.out.println("Checking for existing tables.");

        try
        {
            // Get a Statement object.
            Statement stmt = conn.createStatement();

            try
            {
                // Drop the Customer table.
                stmt.execute("DROP TABLE Product");
                System.out.println("Product table dropped.");
            } catch (SQLException ex)
            {
                // No need to report an error.
                // The table simply did not exist.
            }

            try
            {
                // Drop the Coffee table.
                stmt.execute("DROP TABLE Cart");
                System.out.println("Cart table dropped.");
            } catch (SQLException ex)
            {
                // No need to report an error.
                // The table simply did not exist.
            }
        } catch (SQLException ex)
        {
            System.out.println("ERROR: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void buildProductTable(Connection conn)
    {
        try
        {
            // Get a Statement object.
            Statement stmt = conn.createStatement();

            // Create the table.
            stmt.execute("CREATE TABLE Product (" +
                    "ProdNum INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "+
                    "Name VARCHAR(25)," +
                    "ShortDesc VARCHAR(40)," +
                    "LongDesc CLOB," +
                    "Price DOUBLE " +
                    ")");

            stmt.executeUpdate("INSERT INTO Product VALUES" +
                    "(default, 'Brass Knob', 'A knob made of premium brass'," +
                    " 'A classic style knob made of the finest swiss brass', 5.99 )");
            stmt.executeUpdate("INSERT INTO Product VALUES" +
                    "(default, 'Glass Knob', 'A luxurious glass knob'," +
                    " 'A knob crafted by the finest glassblower in all of Mozambique', 8.99 )");
            stmt.executeUpdate("INSERT INTO Product VALUES" +
                    "(default, 'Stainless Steel Knob', 'A sleek modern knob'," +
                    " 'A fresh new style of knob made of 100% recycled railroad steel', 7.99 )");
            System.out.println("Product table created.");
        } catch (SQLException ex)
        {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    /**
     * The buildCustomerTable method creates the
     * Customer table and adds some rows to it.
     */
    public static void buildCartTable(Connection conn)
    {
        try
        {
            // Get a Statement object.
            Statement stmt = conn.createStatement();

            // Create the table.
            stmt.execute("CREATE TABLE Cart" +
                    "( CartItemNum INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                    "  ProdNum INT NOT NULL)");

            System.out.println("Cart table created.");
        } catch (SQLException ex)
        {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

}
