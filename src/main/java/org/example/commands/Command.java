package org.example.commands;

import org.example.Main;
import org.example.collections.CollectionManager;

import java.util.Scanner;

/**
 * @author Dimasavr
 */

public abstract class Command {

    protected boolean needScannerToExecute;

    protected static CollectionManager cm = Main.cm;

    Scanner sc = Main.sc;

    protected String splite = "----------";

    protected String description;
    protected String nameOfCommand;

//    @Override
    public void execute(String args) {}

//    @Override
    public void execute() {}

//    @Override
    public void execute(String args, Scanner sc) {}

//    @Override
    public void execute(Scanner sc) {}

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

    public boolean isNeedScannerToExecute() {
        return needScannerToExecute;
    }
}
