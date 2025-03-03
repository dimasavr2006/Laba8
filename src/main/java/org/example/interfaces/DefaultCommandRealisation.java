package org.example.interfaces;

import org.example.Main;
import org.example.commands.Command;
import org.example.functions.Invoker;

import java.util.Scanner;

public interface DefaultCommandRealisation {

    default void def(int numberOfArgs){
        Invoker invoker = new Invoker();
        Scanner sc = Main.sc;
        while (sc.hasNext()) {
            String line = sc.nextLine();
            String[] tokens = line.split(" ");
            Command command = invoker.commands.get(tokens[0]);
            if (tokens.length > 1) {
                command.execute(tokens[1]);
            } else {
                command.execute();
            }
        }
    }

}
