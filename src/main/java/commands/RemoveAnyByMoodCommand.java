package commands;

import enums.Mood;

/**
 * @author Dimasavr
 */

public class RemoveAnyByMoodCommand extends Command {
    private String desc = "Удаляет случайный элемент коллекции, настроение которого равно заданному";
    private String name = "remove_any_by_mood";
    private int expected = 1;

    public RemoveAnyByMoodCommand() {
        this.nameOfCommand = name;
        this.description = desc;
        this.numberOfArgs = expected;
    }

    @Override
    public void bodyOfCommand(String argument) {
        cm.removeAnyByMood(Mood.valueOf(argument));
    }

    //    @Override
//    public void execute(String argument) {
//        int expected = 1;
//        String[] arguments = argument.split(" ");
//        if (arguments.length != expected) {
//            throw new IncorrectArgsNumber(expected);
//        }
//        cm.removeAnyByMood(Mood.valueOf(arguments[1]));
//    }
}
