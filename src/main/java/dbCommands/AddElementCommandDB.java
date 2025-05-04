package dbCommands;

import classes.HumanBeing;
import utils.BuildersOfElement;

import java.util.Scanner;

/**
 * @author Dimasavr
 */

public class AddElementCommandDB extends CommandDB {

    private String desc = "Добавление нового элемента в коллекцию";
    private String name = "add";

    private Scanner sc;

    int expected = 0;

    public AddElementCommandDB() {
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
    }

}
