package dbCommands;

/**
 * @author Dimasavr
 */

public class RemoveByIDCommandDB extends CommandDB {
    private String desc = "Удяляет элемент коллекции с заданным ID";
    private String name = "remove_by_id";
    private int expected = 1;

    public RemoveByIDCommandDB() {
        this.nameOfCommand = name;
        this.description = desc;
        this.numberOfArgs = expected;
    }

    @Override
    public void bodyOfCommand(String argument) {
        try {
            cm.removeById(Integer.parseInt(argument));
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Удаление не удалось, данного элемента нет");
        }
    }

    //    @Override
//    public void execute(String argument) {
//        try {
//            int expected = 1;
//            String[] arguments = argument.split(" ");
//            if (arguments.length != expected) {
//                throw new IncorrectArgsNumber(expected);
//            }
//            cm.removeById(Integer.parseInt(arguments[0]));
//        } catch (IndexOutOfBoundsException e){
//            System.out.println("Удаление не удалось, данного элемента нет");
//        }
//    }
}
