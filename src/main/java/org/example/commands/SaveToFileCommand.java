package org.example.commands;

/**
 * @author Dimasavr
 */

public class SaveToFileCommand extends Command {

    private String desc = "Сохраняет текущую коллекцию  в файл пормата json";
    private String name = "save";

    int expected = 1;

    public SaveToFileCommand() {
        this.nameOfCommand = name;
        this.description = desc;
        this.numberOfArgs = expected;
    }

    @Override
    public void bodyOfCommand(String argument) {
        cm.saveToFile(argument);
    }

    //    @Override
//    public void execute() {
//        ArrayList<HumanBeing> data = cm.getCollection();
//        EnvFileWriter fileWriter = new EnvFileWriter();
//        fileWriter.writeData(data);
//        System.out.println("Коллекция успешно сохранена в файл.");
//    }
//    @Override
//    public void execute(String args) {
//        int expected = 1;
//        String[] arguments = args.split(" ");
//        if (arguments.length != expected) {
//            throw new IncorrectArgsNumber(expected);
//        }
//        ArrayList<HumanBeing> data = cm.getCollection();
//        EnvFileWriter fileWriter = new EnvFileWriter();
//        fileWriter.writeData(data);
//        System.out.println("Коллекция успешно сохранена в файл.");
//    }


//    @Override
//    public void execute(String argument) {
//        String[] arguments = argument.split(" ");
//        if (arguments.length != expected) {
//            throw new IncorrectArgsNumber(expected);
//        }
////        cm.saveToFile(arguments[0]);
//
//        try {
//            cm.saveToFile(arguments[0]);
//        } catch (NullPointerException e) {
//            System.out.println("dfs");
//        }
//
//        System.out.println("Коллекция была успешно загружена в файл " + arguments[0]);
//    }
//
//    @Override
//    public void execute() {
//        throw new IncorrectArgsNumber(expected);
//    }

}

