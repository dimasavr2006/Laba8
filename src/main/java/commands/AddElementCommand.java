package commands;

import classes.HumanBeing;
import run.Main;
import utils.BuildersOfElement;

import java.util.Scanner;

/**
 * @author Dimasavr
 */

public class AddElementCommand extends Command {

    private String desc = "Добавление нового элемента в коллекцию";
    private String name = "add";

    private Scanner sc;

    int expected = 0;

    public AddElementCommand() {
        super();
        this.nameOfCommand = name;
        this.numberOfArgs = expected;
        this.description = desc;
    }

    @Override
    public void bodyOfCommand(String line) {
        BuildersOfElement b = new BuildersOfElement();
        HumanBeing toAdd = b.createNoAdd(true, sc, null);
        cm.add(toAdd);
        db.add(toAdd, username);
    }

}
