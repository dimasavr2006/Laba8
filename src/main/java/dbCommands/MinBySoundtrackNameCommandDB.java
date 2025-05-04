package dbCommands;

/**
 * @author Dimasavr
 */

public class MinBySoundtrackNameCommandDB extends CommandDB {

    private String desc = "Выводит название элемента коллекции с минимальным значением поля soundtrackName";
    private String name = "min_by_soundtrack_name";
    private int expected = 1;

    public MinBySoundtrackNameCommandDB() {
        this.nameOfCommand = name;
        this.description = desc;
        this.numberOfArgs = expected;
    }

    @Override
    public void bodyOfCommand(String argument) {
        cm.minBySoundtrackName();
    }

    //    @Override
//    public void execute(String argument) {
//        int expected = 1;
//        String[] arguments = argument.split(" ");
//        if (arguments.length != expected) {
//            throw new IncorrectArgsNumber(expected);
//        }
//        cm.minBySoundtrackName();
//    }
}
