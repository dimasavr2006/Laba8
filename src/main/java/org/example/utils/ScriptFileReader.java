package org.example.utils;

import org.example.Main;
import org.example.commands.Command;
import org.example.exceptions.IllegalScriptLine;
import org.example.exceptions.IncorrectArgsNumber;
import org.example.functions.Invoker;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ScriptFileReader {

    public void readFile(String fileName) {

        Invoker inv = Main.inv;
        int numberOfLine = 1;

//        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] tokens = line.split(" ");
//                Command command = null;
//                try {
//                    command = inv.commands.get(tokens[0]);
//                } catch (NullPointerException e) {
//                    System.out.println("Строка " + numberOfLine + "Пропущена так как такой команды не существует");
//                }
//                if (tokens.length == 2) {
//                    command.execute(tokens[1]);
//                } else if (tokens.length == 1) {
//                    command.execute();
//                }
//            }
//        }

        try (Scanner scanner = new Scanner(new File(fileName))) {
            System.out.println("Начато выполнение команд из файла");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] tokens = line.split(" ");
                Command command = null;
                try {
                    command = inv.commands.get(tokens[0]);
                } catch (NullPointerException e) {
                    System.out.println("Строка " + numberOfLine + "Пропущена так как такой команды не существует");
                }

                if (tokens[0].equals("execute_script") && tokens[1].equals(fileName)) {
                    throw new IllegalScriptLine("Рекурсивный файл поменяйте строку " + numberOfLine);
                }
                try {
                    if (tokens[0].equals("add") && tokens.length == 1) {
                        command.execute(scanner);
                    } else {
                        if (tokens.length == 2) {
                            command.execute(tokens[1]);
                        } else if (tokens.length == 1){
                            command.execute();
                        }
                    }
                } catch (NullPointerException e) {
                    System.out.println("Ошибка в строке " + numberOfLine + " Выполняется прерывание выполнения программы...");
                    break;
                }  catch (IllegalScriptLine e) {
                    if (e.getMessage().contains("Рекурсивный файл поменяйте строку")) {
                        scanner.nextLine();
                        break;
                    }
                    System.out.println(e.getMessage());
                }

                numberOfLine++;
            }
            System.out.println("Выполнение команд из файла завершено");
        } catch (IOException e) {
            System.out.println("Файл не найден, попробуйте исправить ошибку");
        } catch (IllegalScriptLine e) {
            System.out.println(e.getMessage());
        } catch (IncorrectArgsNumber e) {

        }


    }
}
