package org.example.commands;

import org.example.exceptions.*;

public class RemoveByIDCommand extends Command {
    private String desc = "Удяляет элемент коллекции с заданным ID";
    private String name = "remove_by_id";

    public RemoveByIDCommand() {
        this.nameOfCommand = name;
        this.description = desc;
    }

    @Override
    public void execute(String args) {
        try {
            int expected = 1;
            String[] arguments = args.split(" ");
            if (arguments.length != expected) {
                throw new IncorrectArgsNumber(expected);
            }
            cm.removeById(Integer.parseInt(arguments[0]));
        } catch (IndexOutOfBoundsException e){
            System.out.println("Удаление не удалось, данного элемента нет");
        }
    }
}
