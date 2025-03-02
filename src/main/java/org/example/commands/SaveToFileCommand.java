package org.example.commands;

import org.example.exceptions.IncorrectArgsNumber;

import org.example.utils.EnvFileWriter;
import org.example.classes.HumanBeing;
import org.example.collections.CollectionManager;

import java.util.ArrayList;

public class SaveToFileCommand extends Command {

    private String desc = "Сохраняет текущую коллекцию  в файл пормата json";
    private String name = "save";

    public SaveToFileCommand() {
        this.nameOfCommand = name;
        this.description = desc;
    }
    private final String description = "Сохраняет текущую коллекцию в файл формата JSON";
    private final String nameOfCommand = "save";
    private final CollectionManager collectionManager;

    public SaveToFileCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute() {
        ArrayList<HumanBeing> data = collectionManager.getCollection();
        EnvFileWriter fileWriter = new EnvFileWriter();
        fileWriter.writeData(data);
        System.out.println("Коллекция успешно сохранена в файл.");
    }
    @Override
    public void execute(String args) {
        int expected = 1;
        String[] arguments = args.split(" ");
        if (arguments.length != expected) {
            throw new IncorrectArgsNumber(expected);
        }

    }
}

    @Override
    public String getDescription() {
        return description;
    }
}