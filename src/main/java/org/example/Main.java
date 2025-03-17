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
        ConsoleManager consM = new ConsoleManager();
        consM.startConsole();
    }
}