package org.example.commands;

import org.example.Main;
import org.example.collections.CollectionManager;
import org.example.interfaces.Commander;

import java.util.Scanner;

public abstract class Command implements Commander {

    protected static CollectionManager cm = Main.cm;

    Scanner sc = Main.sc;

    protected String splite = "----------";

    protected String description;
    protected String nameOfCommand;

    private CollectionManager collectionManager = Main.cm;

    @Override
    public void execute(String args) {}

    @Override
    public void execute() {}

    @Override
    public void description() {
        String ret = nameOfCommand + " - " + description;
        System.out.println(ret);
    }

    public String getDescription() {
        return description;
    }

    public String getNameOfCommand() {
        return nameOfCommand;
    }
}
