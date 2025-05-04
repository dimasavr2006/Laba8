package dbCommands;

/**
 * @author Dimasavr
 */

public class RemoveFirstCommandDB extends CommandDB {
    private String desc = "Удаляет первый элемент коллекции";
    private String name = "remove_first";
    private int expected = 0;

    public RemoveFirstCommandDB() {
        this.nameOfCommand = name;
        this.description = desc;
    }

    @Override
    public void bodyOfCommand(String argument) {
        cm.removeFirst();
    }

    //    @Override
//    public void execute(String argument) {
//        int expected = 0;
//        String[] arguments = argument.split(" ");
//        if (arguments.length != expected) {
//            throw new IncorrectArgsNumber(expected);
//        }
//        cm.removeFirst();
//    }
}
