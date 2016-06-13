package com.theironyard.jdblack;

/**
 * Created by jonathandavidblack on 5/23/16.
 */
public class ToDoItem {
    int id;
    String text;
    boolean isDone;

    public ToDoItem(String text, boolean isDone) {
        this.text = text;
        this.isDone = isDone;
    }

    public ToDoItem(int id, String text, boolean isDone) {
        this.id = id;
        this.text = text;
        this.isDone = isDone;
    }
}
