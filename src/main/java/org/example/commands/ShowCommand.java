package org.example.commands;

import org.example.classes.HumanBeing;

/**
 * @author Dimasavr
 */

public class ShowCommand extends Command {

    private String desc = "Выводит все элементы колллекции";
    private String name = "show";
    private int expected = 0;

    public ShowCommand() {
        this.nameOfCommand = name;
        this.description = desc;
        this.numberOfArgs = expected;
    }

    @Override
    public void bodyOfCommand(String argument) {
        for (HumanBeing hb : cm.collection){
            System.out.println(splite);
            System.out.println(hb);
        }
        System.out.println(splite);
    }

    //    @Override
//    public void execute(String argument) {
//        int expected = 0;
//        String[] arguments = argument.split(" ");
//        if (arguments.length != expected) {
//            throw new IncorrectArgsNumber(expected);
//        }
//    }
//
//    @Override
//    public void execute() {
//        for (HumanBeing hb : cm.collection){
//            System.out.println(splite);
//            System.out.println(hb);
//        }
//        System.out.println(splite);
//    }
}
