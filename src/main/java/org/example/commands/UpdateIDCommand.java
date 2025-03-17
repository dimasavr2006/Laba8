package org.example.commands;

import org.example.exceptions.IncorrectArgsNumber;

/**
 * @author Dimasavr
 */

public class UpdateIDCommand extends Command {

    private String desc = "Обновляет объект с заданным ID";
    private String name = "update_id";

    int expected = 1;

    boolean needScannerToExecute = false;

    public UpdateIDCommand() {
        this.nameOfCommand = name;
        this.description = desc;
    }

    @Override
    public void execute(String args) {
        String[] arguments = args.split(" ");
        if (arguments.length != expected) {
            throw new IncorrectArgsNumber(expected);
        }
        try{
            int id = Integer.parseInt(arguments[0]);
            System.out.println("Начнем создание элемента коллекции для данного ID");
            AddElementCommand ad = new AddElementCommand();
            cm.updateID(Integer.parseInt(arguments[0]), ad.createNoAdd());
        } catch (NumberFormatException e) {
            System.out.println("Неверный ID");
        }
    }

}
