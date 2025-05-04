package commands;

import classes.HumanBeing;
import utils.BuildersOfElement;

/**
 * @author Dimasavr
 */

public class AddIfMinCommand extends Command {

    private String desc = "Добавление нового элемента в коллекцию при условии того, что он является минимальным";
    private String name = "add_if_min";

    int expected = 0;

    public AddIfMinCommand() {
        this.nameOfCommand = name;
        this.description = desc;
        this.numberOfArgs = expected;
    }

    @Override
    public void bodyOfCommand(String line) {
        BuildersOfElement b = new BuildersOfElement();
        HumanBeing h = b.createNoAdd(true, sc, null);
        System.out.println("Начато сравнение двух элементов");
        HumanBeing min = cm.findMin();
        if (h.compareTo(min) < 0) {
            cm.updateID(min.getId(), h);
            System.out.println("Элемент добавлен в коллекцию");
        } else {
            System.out.println("Добавление не произошло так как элемент не является наименьшим");
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
