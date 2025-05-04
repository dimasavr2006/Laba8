package commands;

/**
 * @author Dimasavr
 */

public class InfoCommand extends Command {

    private String desc = "Выводит краткую справку про коллекцию";
    private String name = "info";
    private int expected = 0;

    public InfoCommand() {
        this.nameOfCommand = name;
        this.description = desc;
        this.numberOfArgs = expected;
    }

    @Override
    public void bodyOfCommand(String argument) {
        String in = "Тип коллекции: " + cm.collection.getClass() + ", дата создания: " + cm.getInitialazed() + " Количество элементов: " + cm.collection.size();
        System.out.println(in);
    }

    //    @Override
//    public void execute(String argument) {
//
//        String in = "Тип коллекции: " + cm.collection.getClass() + ", дата создания: " + cm.getInitialazed() + " Количество элементов: " + cm.collection.size();
//
//        try{
//            int expected = 0;
//            String[] arguments = argument.split(" ");
//            if (arguments.length != expected) {
//                throw new IncorrectArgsNumber(expected);
//            }
//            System.out.println(in);
//        } catch (IncorrectArgsNumber e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    @Override
//    public void execute() {
//        String in = "Тип коллекции: " + cm.collection.getClass() + ", дата создания: " + cm.getInitialazed() + " Количество элементов: " + cm.collection.size();
//        System.out.println(in);
//    }
}
