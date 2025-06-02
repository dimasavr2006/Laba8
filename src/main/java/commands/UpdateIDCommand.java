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

    private static HumanBeing objectFromGUI = null;

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
//        toAdd = b.createNoAdd(true, sc, null);
//        return db.updateID(Integer.parseInt(argument), toAdd, username);

        if (objectFromGUI == null) {
            return false; // Не можем обновить без новых данных
        }

        int idToUpdate;
        try {
            idToUpdate = Integer.parseInt(argument.trim());
        } catch (NumberFormatException e) {
            System.err.println("UpdateIDCommand: Некорректный ID для обновления: " + argument);
            objectFromGUI = null;
            return false;
        }

        HumanBeing updatedHuman = objectFromGUI;
        objectFromGUI = null;

        System.out.println("UpdateIDCommand: Получен объект из GUI для обновления ID=" + idToUpdate);
        boolean success = db.updateID(idToUpdate, updatedHuman, username);
        if (!success) {
            System.err.println("UpdateIDCommand: Ошибка обновления объекта ID=" + idToUpdate + " в БД.");
        }
        return success;

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

    public static void setObjectFromGUI(HumanBeing hb) {
        objectFromGUI = hb;
    }

}
