package org.example.functions;

import org.example.Main;
import org.example.collections.CollectionManager;
import org.example.commands.Command;
import org.example.exceptions.IncorrectArgsNumber;

import java.util.Scanner;

public class ConsoleManager {

    CollectionManager cm;
    Invoker inv;
    Scanner sc;

    public ConsoleManager() {
        this.cm = Main.cm;
        this.inv = Main.inv;
        this.sc = Main.sc;
    }

    public void startConsole() {
//        while (sc.hasNext()) {
//            try{
//                String line = sc.nextLine().trim();
//                String[] tokens = line.split(" ");
//                Command command = inv.commands.get(tokens[0]);
//
//                if (tokens.length == 2) {
//                    command.execute(tokens[1]);
//                } else if (tokens.length == 1) {
//                    command.execute();
//                }
//
//            } catch (NullPointerException e){
//                System.out.println("Команда неизвестная, введите другую");
//            } catch (IncorrectArgsNumber e){
//                System.out.println(e.getMessage());
//                System.out.println("Попробуйте ещё раз");
//            }
//        }
//        System.out.println("Выход из программы...");

        sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            try {
                String line = sc.nextLine().trim();
//                String[] tokens = line.split(" ");
                String[] tokens = line.split("\\s+", 2);

                String commandString = tokens[0].toLowerCase();
                Command command = inv.commands.get(commandString);

                String args = "";
                if (tokens.length == 2) {
                    args = tokens[1];
                } else if (tokens.length > 2) {
                    throw new IncorrectArgsNumber(2);
                }
                command.execute(args);
            } catch (NullPointerException e){
                System.out.println("Команда неизвестная, введите другую");
            } catch (IncorrectArgsNumber e){
                System.out.println(e.getMessage());
                System.out.println("Попробуйте ещё раз");
            }
        }

    }

}
