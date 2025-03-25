package org.example.commands;

/**
 * @author Dimasavr
 */

public class UpdateIDCommand extends Command {

    private String desc = "Обновляет объект с заданным ID";
    private String name = "update_id";
    private int expected = 1;

    public UpdateIDCommand() {
        this.nameOfCommand = name;
        this.description = desc;
        this.numberOfArgs = expected;
    }

    @Override
    public void bodyOfCommand(String argument) {
        try {
            int id = Integer.parseInt(argument);
            System.out.println("Начнем создание элемента коллекции для данного ID");
            AddElementCommand ad = new AddElementCommand();
            cm.updateID(id, ad.createNoAddNew(sc, false));
        } catch (NumberFormatException e) {
            System.out.println("Неверный ID");
        }
    }

    //    @Override
//    public void execute(String argument) {
//        String[] arguments = argument.split(" ");
//        if (arguments.length != expected) {
//            throw new IncorrectArgsNumber(expected);
//        }
//        try{
//            int id = Integer.parseInt(arguments[0]);
//            System.out.println("Начнем создание элемента коллекции для данного ID");
//            AddElementCommand ad = new AddElementCommand();
//            cm.updateID(Integer.parseInt(arguments[0]), ad.createNoAdd());
//        } catch (NumberFormatException e) {
//            System.out.println("Неверный ID");
//        }
//    }

}
