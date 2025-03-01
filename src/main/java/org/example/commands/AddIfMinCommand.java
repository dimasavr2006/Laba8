package org.example.commands;

import org.example.classes.*;
import org.example.exceptions.*;

public class AddIfMinCommand extends Command {

    private String desc = "Добавление нового элемента в коллекцию при условии того, что он является минимальным";
    private String name = "add_if_min";

    public AddIfMinCommand() {
        this.nameOfCommand = name;
        this.description = desc;
    }

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
        boolean b = true;
        HumanBeing being = new HumanBeing(b);
        HumanBeing min = cm.findMin();
        if (being.compareTo(min) < 0) {
            cm.updateID(min.getId(), being);
            System.out.println("Элемент добавлен в коллекцию");
        } else {
            System.out.println("Добавление не произошло так как элемент не является наименьшим");
        }
    }
}
