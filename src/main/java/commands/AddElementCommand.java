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

    public AddElementCommand() {
        super();
        this.nameOfCommand = name;
        this.numberOfArgs = expected;
        this.description = desc;
    }

    @Override
    public void bodyOfCommand(String line) {
//        BuildersOfElement b = new BuildersOfElement();
//        toAdd = b.createNoAdd(true, sc, null);
        cm.add(toAdd);
//        db.add(toAdd, username);
    }

    @Override
    public Boolean bodyOfDBCommand(String argument) throws AccessException, AccessDeniedException {
//        BuildersOfElement b = new BuildersOfElement();
        toAdd = b.createNoAdd(true, sc, null);
        return db.add(toAdd, username);
    }
}
