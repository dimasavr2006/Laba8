package org.example.commands;

import org.example.Main;
import org.example.collections.CollectionManager;
import org.example.exceptions.IncorrectArgsNumber;

import java.util.Scanner;

/**
 * @author Dimasavr
 */

public abstract class Command {

    protected static CollectionManager cm = Main.cm;

    Scanner sc = Main.sc;

    protected String splite = "----------";

    protected String description;
    protected String nameOfCommand;

    protected int numberOfArgs;

    public Command() {
        this.description = description;
        this.nameOfCommand = nameOfCommand;
        this.numberOfArgs = numberOfArgs;
    }

    ////    @Override
//    public void execute(String args) {}
//
////    @Override
//    public void execute() {}
//
////    @Override
//    public void execute(String args, Scanner sc) {}
//
////    @Override
//    public void execute(Scanner sc) {}

    public void execute(String argument){
        if (argument.trim().isEmpty() && numberOfArgs == 0) {
            bodyOfCommand(argument);
        } else if (argument.trim().isEmpty() && numberOfArgs != 0) {
            throw new IncorrectArgsNumber(numberOfArgs);
        } else if (!argument.trim().isEmpty() && numberOfArgs == 0) {
            throw new IncorrectArgsNumber(numberOfArgs);
        } else if (!argument.trim().isEmpty() && numberOfArgs == 1) {
            bodyOfCommand(argument);
        }
    }
    public void bodyOfCommand(String argument){}

//    @Override
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
