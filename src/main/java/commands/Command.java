package commands;

import exceptions.IncorrectArgsNumber;
import managers.CollectionManager;
import managers.DBManager;
import run.Main;

import java.nio.file.AccessDeniedException;
import java.rmi.AccessException;
import java.util.Scanner;

;

/**
 * @author Dimasavr
 */

public abstract class Command {

    protected static CollectionManager cm = Main.cm;

    protected static DBManager db = Main.db;

    protected static String username;

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

    public void execute(String argument) throws AccessDeniedException, AccessException {
        if ((argument.trim().isEmpty() && numberOfArgs == 0) || (!argument.trim().isEmpty() && numberOfArgs == 1) || (!argument.trim().isEmpty() && numberOfArgs == 3)) {
            setUsernameAgain();
            Boolean b = bodyOfDBCommand(argument);
            if (b == null) {
                bodyOfCommand(argument);
            } else if (b == true) {
//                bodyOfCommand(argument);
                CollectionManager.collection = db.getCollection();
            } else if (b == false) {
                CollectionManager.collection = db.getCollection();
            }
            CollectionManager.collection = db.getCollection();
        } else {
            throw new IncorrectArgsNumber(numberOfArgs);
        }
    }
    public void bodyOfCommand(String argument) throws AccessException, AccessDeniedException {}

    public Boolean bodyOfDBCommand(String argument) throws AccessException, AccessDeniedException {
        return null;
    }

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

    public static void setUsernameAgain() {
        username = Main.username;
    }
}
