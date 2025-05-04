package commands;

import enums.Mood;

/**
 * @author Dimasavr
 */

public class CountGreaterThanMoodCommand extends Command {

    private String desc = "Выводит количество элементов коллекции настроение которых хуже, чем заданное";
    private String name = "count_greater_than_mood";
    private int expected = 1;

    public CountGreaterThanMoodCommand() {
        this.nameOfCommand = name;
        this.description = desc;
        this.numberOfArgs = expected;
    }

    @Override
    public void bodyOfCommand(String argument) {
        System.out.println("Вот список возможных настроений: " + Mood.getV());
        cm.countGreaterThanMood(Mood.valueOf(argument.toUpperCase()));
    }

//    @Override
//    public void execute(String argument) {
//        String[] arguments = argument.split(" ");
//        if (arguments.length != expected) {
//            throw new IncorrectArgsNumber(expected);
//        }
//        cm.countGreaterThanMood(Mood.valueOf(arguments[0]));
//    }
}
