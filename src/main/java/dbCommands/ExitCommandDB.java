package dbCommands;

/**
 * @author Dimasavr
 */

public class ExitCommandDB extends CommandDB {

    private String desc = "Выполняет выход из программы";
    private String name = "exit";

    public ExitCommandDB() {
        this.nameOfCommand = name;
        this.description = desc;
    }

    @Override
    public void bodyOfCommand(String argument) {
        System.out.println("Выполняется выход из программы....");
        cm.exit();
    }

    //    @Override
//    public void execute(String argument) {
//        int expected = 1;
//        String[] arguments = argument.split(" ");
//        if (arguments.length != expected) {
//            throw new IncorrectArgsNumber(expected);
//        }
//    }
//
//    @Override
//    public void execute() {
//        System.out.println("Выполняется выход из программы....");
//        cm.exit();
//    }
}
