package org.example.commands;

import org.example.Main;
import org.example.collections.CollectionManager;
import org.example.interfaces.Commander;
import org.example.exceptions.*;

public abstract class Command implements Commander {

    protected static CollectionManager cm = Main.cm;

    protected String description;
    protected String keyword;


    @Override
    public void execute(String args) {}

    @Override
    public void execute() {}

    @Override
    public String description() {
        return "";
    }

}
