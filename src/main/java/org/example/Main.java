package org.example;

import org.example.collections.CollectionManager;
import org.example.functions.ConsoleManager;
import org.example.functions.Invoker;

import java.util.Scanner;

/**
 * @author Dimasavr
 */

public class Main {

    public static CollectionManager cm = new CollectionManager();

    public static Invoker inv = new Invoker();

    public static Scanner sc = new Scanner(System.in);



    public static void main(String[] args) {
        System.out.println("Приветствую в консольной части моей программы!");
        System.out.println("Напоминаю, что для справки напишите help");
        System.out.println("А для выхода из программы советую использовать сочетание клавиш Ctrl+C/D (в зависимости от вашей системы)");
        System.out.println("Желаю удачи!");
        ConsoleManager consM = new ConsoleManager();
        consM.startConsole();
        System.out.println("Выход из программы...");
    }
}