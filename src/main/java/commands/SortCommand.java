package commands;

/**
 * @author Dimasavr
 */

public class SortCommand extends Command {

    private String desc = "Выполняет стандартную сортировку коллекции";
    private String name = "sort";
    private int expected = 0;

    public SortCommand() {
        this.nameOfCommand = name;
        this.description = desc;
        this.numberOfArgs = expected;
    }

    @Override
    public void bodyOfCommand(String argument) {
        cm.sort();
        System.out.println("Сортировка выполнена");
    }

    //    @Override
//    public void execute(String argument) {
//        int expected = 0;
//        String[] arguments = argument.split(" ");
//        if (arguments.length != expected) {
//            throw new IncorrectArgsNumber(expected);
//        }
//        cm.sort();
//        System.out.println("Сортировка выполнена!");
//    }
}
