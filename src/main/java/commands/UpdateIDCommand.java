package commands;

import classes.HumanBeing;
import utils.BuildersOfElement;

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
            BuildersOfElement b = new BuildersOfElement();
            HumanBeing h = b.createNoAdd(true, sc, null);
            cm.updateID(id, h);
            db.updateID(id, h, username);
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
