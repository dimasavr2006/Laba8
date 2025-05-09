package commands;

/**
 * @author Dimasavr
 */

public class ClearCommand extends Command {

    private String desc = "Очистка коллекции";
    private String name = "clear";
    private int expected = 0;

    public ClearCommand() {
        this.nameOfCommand = name;
        this.description = desc;
        this.numberOfArgs = expected;
    }

    @Override
    public void bodyOfCommand(String line) {
        cm.clear();
        db.clear(username);
        System.out.println("Коллекция очищена");
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
//        cm.clear();
//        System.out.println("Коллекция очищена");
//    }


}
