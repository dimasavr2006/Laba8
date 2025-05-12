package commands;

import classes.HumanBeing;
import utils.BuildersOfElement;

import java.nio.file.AccessDeniedException;
import java.rmi.AccessException;

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

    static HumanBeing toAdd = null;
    static BuildersOfElement b = new BuildersOfElement();

    @Override
    public void bodyOfCommand(String argument) {
//        try {
//            int id = Integer.parseInt(argument);
//            System.out.println("Начнем создание элемента коллекции для данного ID");
//            BuildersOfElement b = new BuildersOfElement();
//            HumanBeing h = b.createNoAdd(true, sc, null);
//            cm.updateID(id, h);
//            db.updateID(id, h, username);
//        } catch (NumberFormatException e) {
//            System.out.println("Неверный ID");
//        }
    }

    @Override
    public Boolean bodyOfDBCommand(String argument) throws AccessException, AccessDeniedException {
        toAdd = b.createNoAdd(true, sc, null);
        return db.updateID(Integer.parseInt(argument), toAdd, username);
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
