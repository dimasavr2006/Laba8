package org.example.commands;

import org.example.Main;
import org.example.exceptions.IncorrectArgsNumber;
import org.example.functions.Invoker;

import java.util.Map;

/**
 * @author Dimasavr
 */

public class HelpCommand extends Command{

    private String desc = "Выводит краткую справку на все команды программы";
    private String name = "help";

    boolean needScannerToExecute = false;

    public HelpCommand(){
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
//        cm.clear();
//        System.out.println("Коллекция очищена");
    }

    @Override
    public void execute() {
        Invoker.adder();
        Map<String, Command> commands = Main.inv.commands;

        for (Command command : commands.values()) {
            command.description();
        }
    }

}
