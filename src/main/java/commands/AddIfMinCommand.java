package commands;

import classes.HumanBeing;
import utils.BuildersOfElement;

import java.nio.file.AccessDeniedException;
import java.rmi.AccessException;

/**
 * @author Dimasavr
 */

public class AddIfMinCommand extends Command {

    private String desc = "Добавление нового элемента в коллекцию при условии того, что он является минимальным";
    private String name = "add_if_min";

    int expected = 0;

    static HumanBeing toAdd = null;
    private static BuildersOfElement b = new BuildersOfElement();

    public AddIfMinCommand() {
        this.nameOfCommand = name;
        this.description = desc;
        this.numberOfArgs = expected;
    }

    @Override
    public void bodyOfCommand(String line) {
//        BuildersOfElement b = new BuildersOfElement();
//        HumanBeing h = b.createNoAdd(true, sc, null);
//        System.out.println("Начато сравнение двух элементов");
//        HumanBeing min = cm.findMin();
//        if (h.compareTo(min) < 0) {
//            cm.updateID(min.getId(), h);
//            db.updateID(min.getId(), h, username);
//            System.out.println("Элемент добавлен в коллекцию");
//        } else {
//            System.out.println("Добавление не произошло так как элемент не является наименьшим");
//        }
        cm.add(toAdd);
    }

    @Override
    public Boolean bodyOfDBCommand(String argument) throws AccessException, AccessDeniedException {
        toAdd = b.createNoAdd(true, sc, null);
        System.out.println("Начато сравнение двух элементов");
        HumanBeing min = cm.findMin();
        if (toAdd.compareTo(min) < 0) {
            Boolean b = db.add(toAdd, username);
            System.out.println("Элемент добавлен в коллекцию");
            return b;
        } else {
            System.out.println("Добавление не произошло так как элемент не является наименьшим");
            return false;
        }
    }
    //    @Override
//    public void execute(String argument) {
//        String[] arguments = argument.split(" ");
//        if (arguments.length != expected) {
//            throw new IncorrectArgsNumber(expected);
//        }
//    }
//
//    @Override
//    public void execute() {
//        boolean b = true;
//        HumanBeing being = new HumanBeing(b);
//        HumanBeing min = cm.findMin();
//        if (being.compareTo(min) < 0) {
//            cm.updateID(min.getId(), being);
//            System.out.println("Элемент добавлен в коллекцию");
//        } else {
//            System.out.println("Добавление не произошло так как элемент не является наименьшим");
//        }
//    }

}
