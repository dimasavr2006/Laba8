package commands;

import classes.HumanBeing;
import utils.BuildersOfElement;

import java.nio.file.AccessDeniedException;
import java.rmi.AccessException;
import java.util.Scanner;

/**
 * @author Dimasavr
 */

public class AddElementCommand extends Command {

    private String desc = "Добавление нового элемента в коллекцию";
    private String name = "add";

    private Scanner sc;

    int expected = 0;

    static HumanBeing toAdd = null;
    static BuildersOfElement b = new BuildersOfElement();

    private static HumanBeing objectFromGUI = null;

    public AddElementCommand() {
        super();
        this.nameOfCommand = name;
        this.numberOfArgs = expected;
        this.description = desc;
    }

    @Override
    public void bodyOfCommand(String line) {
////        BuildersOfElement b = new BuildersOfElement();
////        toAdd = b.createNoAdd(true, sc, null);
//        cm.add(toAdd);
////        db.add(toAdd, username);
        if (objectFromGUI != null) {
            cm.add(objectFromGUI);
            System.out.println("Объект из GUI добавлен в локальную коллекцию (операция с БД могла быть неуспешной).");
            objectFromGUI = null;
        }
//        else if (AddElementCommand.toAdd != null) { // Старый механизм для скриптов/консоли, если он еще используется
//            cm.add(AddElementCommand.toAdd);
//            System.out.println("Объект (через статический toAdd) добавлен в локальную коллекцию.");
//            AddElementCommand.toAdd = null;
//        }
        else {
            System.err.println("AddElementCommand (bodyOfCommand): Нет объекта для добавления.");
        }
    }

    @Override
    public Boolean bodyOfDBCommand(String argument) throws AccessException, AccessDeniedException {
////        BuildersOfElement b = new BuildersOfElement();
//        toAdd = b.createNoAdd(true, sc, null);
//        return db.add(toAdd, username);
        if (objectFromGUI == null) {
            System.err.println("AddElementCommand (bodyOfDBCommand): Объект HumanBeing не был предоставлен из GUI. Добавление отменено.");
            return false;
        }

        HumanBeing humanToAdd = objectFromGUI;
        objectFromGUI = null;

        boolean success = db.add(humanToAdd, username);

        if (success) {
            System.out.println("AddElementCommand: Объект, полученный из GUI, успешно добавлен в БД.");
        } else {
            System.err.println("AddElementCommand: Ошибка добавления объекта из GUI в БД.");
        }
        return success;
    }

    public static void setObjectFromGUI(HumanBeing hb) {
        objectFromGUI = hb;
    }
}
