package org.example;

import org.example.classes.*;
import org.example.collections.*;
import org.example.commands.*;
import org.example.enums.*;
import org.example.exceptions.*;
import org.example.functions.*;

import java.util.Scanner;

public class Main {

    public static CollectionManager cm = new CollectionManager();

    public static void main(String[] args) {
        Invoker invoker = new Invoker();
        Scanner sc = new Scanner(System.in);
        while (true) {
            try{
                String line = sc.nextLine();
                String[] tokens = line.split(" ");
                Command command = invoker.commands.get(tokens[0]);
                if (tokens.length == 2) {
                    command.execute(tokens[1]);
                } else if (tokens.length == 1){
                    command.execute();
                }
            } catch (NullPointerException e){
                System.out.println("Команда неизвестная, введите другую");
            } catch (IncorrectArgsNumber e){
                System.out.println(e.getMessage());
            }
        }
    }
}