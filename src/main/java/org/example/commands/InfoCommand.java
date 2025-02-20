package org.example.commands;

import org.example.collections.CollectionManager;
import org.example.exceptions.IncorrectArgsNumber;


public class InfoCommand extends Command {

    private String description = "info command test";
    private String nameOfCommand;

    @Override
    public void execute(String args) {
        try{
            int expected = 1;
            String[] arguments = args.split(" ");
            if (arguments.length != expected) {
                throw new IncorrectArgsNumber(expected);
            }
        } catch (IncorrectArgsNumber e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Тип коллекции: " + cm.collection.getClass() + ", дата создания: " + cm.getInitialazed() + "Количество элементов: " + cm.collection.size());
    }
}
