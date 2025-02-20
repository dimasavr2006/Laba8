package org.example;

import org.example.classes.HumanBeing;
import org.example.collections.CollectionManager;
import org.example.commands.Command;
import org.example.enums.WeaponType;
import org.example.functions.Invoker;

import java.util.Scanner;

public class Main {

    public static CollectionManager cm = new CollectionManager();



    public static void main(String[] args) {
        Invoker invoker = new Invoker();
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            HumanBeing humanBeing = new HumanBeing();
            String line = sc.nextLine();
            String[] tokens = line.split(" ");
            Command command = invoker.commands.get(tokens[0]);
            if (tokens.length > 1) {
                command.execute(tokens[1]);
            }
            command.execute();
        }
    }
}