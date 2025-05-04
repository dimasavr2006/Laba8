package dbCommands;

/**
 * @author Dimasavr
 */

public class ExecuteScriptFromFileCommandDB extends CommandDB {

    private String desc = "Запускает скрип из файла";
    private String name = "execute_script";

    int expected = 1;

    public ExecuteScriptFromFileCommandDB() {
        this.nameOfCommand = name;
        this.description = desc;
        this.numberOfArgs = expected;
    }

    @Override
    public void bodyOfCommand(String argument) {
        cm.executeScriptFromFilename(argument);
    }

    //    @Override
//    public void execute(String argument) {
//        String[] arguments = argument.split(" ");
//        if (arguments.length != expected) {
//            throw new IncorrectArgsNumber(expected);
//        }
//        cm.executeScriptFromFilename(arguments[0]);
////        System.out.println("Загрузка команд из файла: " + arguments[0] + "была успешна");
//    }
//
//    @Override
//    public void execute() {
//        throw new IncorrectArgsNumber(expected);
//    }
}
