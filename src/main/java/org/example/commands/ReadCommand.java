package org.example.commands;

/**
 * @author Dimasavr
 */

public class ReadCommand extends Command {
    private String desc = "Чтение данных из заданного json файла";
    private String name = "read";
    private int expected = 1;

    public ReadCommand() {
        this.nameOfCommand = name;
        this.description = desc;
        this.numberOfArgs = expected;
    }

    @Override
    public void bodyOfCommand(String argument) {
        cm.readJson(argument);
        System.out.println("Коллекция из файла была успешно добавлена");
    }

    //    @Override
//    public void execute(String argument) {
//        String[] arguments = argument.split(" ");
//        if (arguments.length != expected) {
//            throw new IncorrectArgsNumber(expected);
//        }
//        cm.readJson(arguments[0]);
//        System.out.println("Коллекция из файла была успешно добавлена");
//    }
//
//    @Override
//    public void execute() {
//        throw new IncorrectArgsNumber(expected);
//    }
}
