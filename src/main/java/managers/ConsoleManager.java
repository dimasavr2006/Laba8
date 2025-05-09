package managers;

import commands.Command;
import exceptions.IncorrectArgsNumber;
import functions.Invoker;
import run.Main;

import java.nio.file.AccessDeniedException;
import java.rmi.AccessException;
import java.util.NoSuchElementException;
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

    /**
     * Запускает ввод с консоли
     */

    public void startConsole() {

//        sc = new Scanner(System.in);

        while (true) {
            try {

                if (!sc.hasNextLine()) {
                    break;
                }

                String line = sc.nextLine().trim();
//                String[] tokens = line.split(" ");
                String[] tokens = line.split("\\s+");

                String commandString = tokens[0].toLowerCase();

//                boolean login = Main.login;
//                boolean isLoginOrRegCommand = commandString.equals("login") || commandString.equals("reg");
//
//                if (!login && !isLoginOrRegCommand) {
//                    System.out.println("Пожалуйста, сначала выполните вход или зарегистрируйтесь (команды: login, reg)");
//                    continue;
//                }

                Command command = inv.commands.get(commandString);

                String args = "";
                if (tokens.length == 2) {
                    args = tokens[1];
                } else if (tokens.length > 2) {
                    args = tokens[2];
                }
                command.execute(args);
            } catch (NullPointerException e){
                System.out.println("Команда неизвестная, введите другую");
            } catch (IncorrectArgsNumber e){
                System.out.println(e.getMessage());
                System.out.println("Попробуйте ещё раз");
            } catch (NoSuchElementException e) {

            } catch (AccessDeniedException e) {
                throw new RuntimeException(e);
            } catch (AccessException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
