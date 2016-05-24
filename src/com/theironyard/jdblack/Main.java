package com.theironyard.jdblack;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void createItem(Scanner scanner, ArrayList<ToDoItem> items) {
        System.out.println("Enter your to do item");
        String text = scanner.nextLine();
        ToDoItem item = new ToDoItem(text, false);
        items.add(item); // adds it to the array list

    }
    public static void toggleItem(Scanner scanner, ArrayList<ToDoItem> items) {

        System.out.println("Enter the # of the item you wish to toggle:");
        String numStr = scanner.nextLine();
        try {
            int num = Integer.valueOf(numStr);
            ToDoItem tempItem = items.get(num - 1);
            tempItem.isDone = !tempItem.isDone; //nice way of flipping current value
        }
        catch (NumberFormatException e){
            System.out.println("Error: You Did Not Type A Number!"); // nice way to handle exceptions without crashing the program
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error: Number Is Not Valid");
        }

    }
    public static void listItems(ArrayList<ToDoItem> items) {
        int i = 1;
        for (ToDoItem toDoItem : items) {
            String checkbox = "[ ]";
            if (toDoItem.isDone) {
                checkbox = "[x]";
            }
            //System.out.println(checkbox + " " + i + ". " + toDoItem.text);
            System.out.printf("%s (%s.) %s\n", checkbox, i, toDoItem.text);
            i++; //incrementing the numbers beside the items
        }
    }

    public static void main(String[] args) {
        ArrayList<ToDoItem> items = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Create new Item");
            System.out.println("2. Toggle Item");
            System.out.println("3. List Items");

            String option = scanner.nextLine();

            switch (option){
                case "1":
                    //create new item
                   createItem(scanner, items);
                    break;

                case "2":
                  //toggle item
                    toggleItem(scanner, items);
                    break;

                case "3":
                    listItems(items);
                    break;

                default: //this is the "else" portion of the switch loop
                    System.out.println("Invalid option.");
            }
        }
    }
}
