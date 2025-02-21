package org.example.commands;

import org.example.classes.HumanBeing;
import org.example.exceptions.IncorrectArgsNumber;

public class UpdateIDCommand extends Command {
    private String description = "";
    public UpdateIDCommand() {}

    @Override
    public void execute(String args) {
        int expected = 1;
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
