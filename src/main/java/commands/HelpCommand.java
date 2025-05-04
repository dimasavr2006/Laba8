package commands;

import functions.Invoker;
import run.Main;

import java.util.Map;

;

/**
 * @author Dimasavr
 */

public class HelpCommand extends Command{

    private String desc = "Выводит краткую справку на все команды программы";
    private String name = "help";
    private int expected = 0;

    public HelpCommand(){
        this.nameOfCommand = name;
        this.description = desc;
        this.numberOfArgs = expected;
    }

    @Override
    public void bodyOfCommand(String argument) {
        Invoker.adder();
        Map<String, Command> commands = Main.inv.commands;

        for (Command command : commands.values()) {
            command.description();
        }
    }

    //    @Override
//    public void execute(String argument) {
//        int expected = 0;
//        String[] arguments = argument.split(" ");
//        if (arguments.length != expected) {
//            throw new IncorrectArgsNumber(expected);
//        }
////        cm.clear();
////        System.out.println("Коллекция очищена");
//    }
//
//    @Override
//    public void execute() {
//        Invoker.adder();
//        Map<String, Command> commands = run.Main.inv.commands;
//
//        for (Command command : commands.values()) {
//            command.description();
//        }
//    }

}
