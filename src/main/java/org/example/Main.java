package org.example;

import org.example.collections.CollectionManager;
import org.example.commands.Command;
import org.example.exceptions.IncorrectArgsNumber;
import org.example.functions.Invoker;

import java.util.Date;
import java.util.Scanner;

/**
 * @author Dimasavr
 */

public class Main {

    public static CollectionManager cm = new CollectionManager();

    public static Invoker inv = new Invoker();

    public static Scanner sc = new Scanner(System.in);



    public static void main(String[] args) {
        System.out.println(new Date());
        while (sc.hasNext()) {
            try{
                String line = sc.nextLine().trim();
                String[] tokens = line.split(" ");
                Command command = inv.commands.get(tokens[0]);

                if (tokens.length == 2) {
                    command.execute(tokens[1]);
                } else if (tokens.length == 1) {
                    command.execute();
                }

            } catch (NullPointerException e){
                System.out.println("Команда неизвестная, введите другую");
            } catch (IncorrectArgsNumber e){
                System.out.println(e.getMessage());
                System.out.println("Попробуйте ещё раз");
            }
        }
    }
}