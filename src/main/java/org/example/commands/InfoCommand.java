package org.example.commands;

import org.example.exceptions.IncorrectArgsNumber;

/**
 * @author Dimasavr
 */

public class InfoCommand extends Command {

    private String desc = "Выводит краткую справку про коллекцию";
    private String name = "info";

    boolean needScannerToExecute = false;

    public InfoCommand() {
        this.nameOfCommand = name;
        this.description = desc;
    }

    @Override
    public void execute(String args) {

        String in = "Тип коллекции: " + cm.collection.getClass() + ", дата создания: " + cm.getInitialazed() + " Количество элементов: " + cm.collection.size();

        try{
            int expected = 0;
            String[] arguments = args.split(" ");
            if (arguments.length != expected) {
                throw new IncorrectArgsNumber(expected);
            }
            System.out.println(in);
        } catch (IncorrectArgsNumber e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void execute() {
        String in = "Тип коллекции: " + cm.collection.getClass() + ", дата создания: " + cm.getInitialazed() + " Количество элементов: " + cm.collection.size();
        System.out.println(in);
    }
}
