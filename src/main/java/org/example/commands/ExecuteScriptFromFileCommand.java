package org.example.commands;

public class ExecuteScriptFromFileCommand extends Command {

    private String desc = "Запускает скрип из файла";
    private String name = "execute_script";

    public ExecuteScriptFromFileCommand() {
        this.nameOfCommand = name;
        this.description = desc;
    }
}
