package org.example.commands;

import org.example.classes.*;
import org.example.exceptions.*;

public class AddIfMinCommand extends Command {

    private String description = "";
    private String nameOfCommand = "add_if_min";

    @Override
    public void execute(String args) {
        int expected = 0;
        String[] arguments = args.split(" ");
        if (arguments.length != expected) {
            throw new IncorrectArgsNumber(expected);
        }
    }

    @Override
    public void execute() {
        HumanBeing being = new HumanBeing();
        HumanBeing min = cm.findMin();
        if (being.compareTo(min) < 0) {
            cm.updateID(min.getId(), being);
            System.out.println("Элемент добавлен в коллекцию");
        } else {
            System.out.println("Добавление не произошло так как элемент не является наименьшим");
        }
    }
}
