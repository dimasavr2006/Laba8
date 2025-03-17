package org.example.commands;

import org.example.exceptions.IncorrectArgsNumber;

/**
 * @author Dimasavr
 */

public class ExecuteScriptFromFileCommand extends Command {

    private String desc = "Запускает скрип из файла";
    private String name = "execute_script";

    boolean needScannerToExecute = false;

    int expected = 1;

    public ExecuteScriptFromFileCommand() {
        this.nameOfCommand = name;
        this.description = desc;
    }

    @Override
    public void execute(String args) {
        String[] arguments = args.split(" ");
        if (arguments.length != expected) {
            throw new IncorrectArgsNumber(expected);
        }
        cm.executeScriptFromFilename(arguments[0]);
//        System.out.println("Загрузка команд из файла: " + arguments[0] + "была успешна");
    }

    @Override
    public void execute() {
        throw new IncorrectArgsNumber(expected);
    }
}
