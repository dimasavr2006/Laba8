package commands;

/**
 * @author Dimasavr
 */

public class RemoveFirstCommand extends Command {
    private String desc = "Удаляет первый элемент коллекции";
    private String name = "remove_first";
    private int expected = 0;

    public RemoveFirstCommand() {
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
