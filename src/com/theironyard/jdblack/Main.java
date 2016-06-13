package com.theironyard.jdblack;

import org.h2.tools.Server;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void insertTodo(Connection conn, String text) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO todos VALUES (NULL, ?, FALSE)");
        stmt.setString(1, text);
        stmt.execute();
    }
    public static ArrayList<ToDoItem> selectTodos(Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM todos");
        ResultSet results = stmt.executeQuery();
        ArrayList<ToDoItem> items = new ArrayList<>();
        while (results.next()) {
            int id = results.getInt("id");
            String text = results.getString("text");
            boolean isDone = results.getBoolean("is_done");
            ToDoItem item = new ToDoItem(id, text, isDone);
            items.add(item);
        }
        return items;
    }

    public static void toggleTodo(Connection conn, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE todos SET is_done = NOT is_done WHERE id = ?");
        stmt.setInt(1, id);
        stmt.execute();

    }

    public static void createItem(Scanner scanner, Connection conn) throws SQLException {
        System.out.println("Enter your to do item");
        String text = scanner.nextLine();
        //ToDoItem item = new ToDoItem(text, false);
        //items.add(item); // adds it to the array list
        insertTodo(conn, text);

    }
    public static void toggleItem(Scanner scanner, Connection conn) throws SQLException {

        System.out.println("Enter the # of the item you wish to toggle:");
        String numStr = scanner.nextLine();
        try {
            int num = Integer.valueOf(numStr);
            //ToDoItem tempItem = items.get(num - 1);
            //tempItem.isDone = !tempItem.isDone; //nice way of flipping current value
            toggleTodo(conn, num);
        }
        catch (NumberFormatException e){
            System.out.println("Error: You Did Not Type A Number!"); // nice way to handle exceptions without crashing the program
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error: Number Is Not Valid");
        }

    }
    public static void listItems(Connection conn) throws SQLException {
        //int i = 1;
        ArrayList<ToDoItem> items = selectTodos(conn);
        for (ToDoItem toDoItem : items) {
            String checkbox = "[ ]";
            if (toDoItem.isDone) {
                checkbox = "[x]";
            }
            //System.out.println(checkbox + " " + i + ". " + toDoItem.text);
            System.out.printf("%s (%s.) %s\n", checkbox, toDoItem.id, toDoItem.text);
            //i++; //incrementing the numbers beside the items
        }
    }

    public static void main(String[] args) throws SQLException {
        Server.createWebServer().start();
        Connection conn = DriverManager.getConnection("jdbc:h2:./main");

        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS todos (id IDENTITY, text VARCHAR, is_done BOOLEAN)");


        //ArrayList<ToDoItem> items = new ArrayList<>(); deleted so we can store info in database, no need for global ArrayList
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Create new Item");
            System.out.println("2. Toggle Item");
            System.out.println("3. List Items");

            String option = scanner.nextLine();

            switch (option){
                case "1":
                    //create new item
                   createItem(scanner, conn); // down here change item to database connection
                    break;

                case "2":
                  //toggle item
                    toggleItem(scanner, conn); // down here change item to database connection
                    break;

                case "3":
                    listItems(conn); // down here change item to database connection
                    break;

                default: //this is the "else" portion of the switch loop
                    System.out.println("Invalid option.");
            }
        }
    }
}
