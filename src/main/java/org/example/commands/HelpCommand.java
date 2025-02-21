package org.example.commands;

import org.example.Main;
import org.example.exceptions.IncorrectArgsNumber;
import org.example.functions.Invoker;

public class HelpCommand extends Command{

    private String description = "Выводит краткую справку на все команды программы";
    private String nameOfCommand = "help";

    @Override
    public void execute(String args) {
        int expected = 0;
        String[] arguments = args.split(" ");
        if (arguments.length != expected) {
            throw new IncorrectArgsNumber(expected);
        }
//        cm.clear();
//        System.out.println("Коллекция очищена");
    }

//    @Override
//    public void execute() {
//        for (int i = 0; i < invoker.commands.size(); i++) {
//            invoker.commands.get(i).description();
//        }
//    }

}
