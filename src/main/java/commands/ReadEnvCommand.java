package commands;

/**
 * @author Dimasavr
 */

public class ReadEnvCommand extends Command {
    private String desc = "Чтение данных из переменной среды";
    private String name = "readEnv";
    private int expected = 0;

    public ReadEnvCommand() {
        this.nameOfCommand = name;
        this.description = desc;
        this.numberOfArgs = expected;
    }

    @Override
    public void bodyOfCommand(String argument) {
        cm.readEnv();
    }

    //    @Override
//    public void execute(String argument) {
//        int expected = 0;
//        String[] arguments = argument.split(" ");
//        if (arguments.length != expected) {
//            throw new IncorrectArgsNumber(expected);
//        }
//    }
//
//    @Override
//    public void execute() {
//        cm.readEnv();
//    }
}
