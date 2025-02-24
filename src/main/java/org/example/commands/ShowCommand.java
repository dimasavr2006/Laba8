package org.example.commands;

import org.example.classes.HumanBeing;
import org.example.exceptions.IncorrectArgsNumber;

public class ShowCommand extends Command {

    private String desc = "Выводит все элементы колллекции";
    private String name = "show";

    public ShowCommand() {
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
        for (HumanBeing hb : cm.collection){
            System.out.println(splite);
            System.out.println(hb);
        }
        System.out.println(splite);
    }
}
