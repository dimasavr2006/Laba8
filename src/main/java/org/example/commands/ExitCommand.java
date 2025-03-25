package org.example.commands;

/**
 * @author Dimasavr
 */

public class ExitCommand extends Command {

    private String desc = "Выполняет выход из программы";
    private String name = "exit";

    public ExitCommand() {
        this.nameOfCommand = name;
        this.description = desc;
    }

    @Override
    public void bodyOfCommand(String argument) {
        System.out.println("Выполняется выход из программы....");
        cm.exit();
    }

    //    @Override
//    public void execute(String argument) {
//        int expected = 1;
//        String[] arguments = argument.split(" ");
//        if (arguments.length != expected) {
//            throw new IncorrectArgsNumber(expected);
//        }
//    }
//
//    @Override
//    public void execute() {
//        System.out.println("Выполняется выход из программы....");
//        cm.exit();
//    }
}
