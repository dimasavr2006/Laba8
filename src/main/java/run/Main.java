package run;

import functions.*;
import managers.*;
import utils.*;

import java.util.Scanner;

/**
 * @author Dimasavr
 */

public class Main {

    public static CollectionManager cm = new CollectionManager();

    public static Invoker inv = new Invoker();

    public static Scanner sc = new Scanner(System.in);

    public static DBManager db = new DBManager();

    public static boolean login = false;
    public static boolean toBreak = false;

    public static void main(String[] args) {

        JSCh jsc = new JSCh();
        try {
            jsc.connectSSH();
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Пожалуйста авторизуйтесь : введите или register <имя> <пароль> или login <имя> <пароль>");
        LoginManager lm = new LoginManager();
        lm.startAuth();

        System.out.println("Приветствую в консольной части моей программы!");
        System.out.println("Напоминаю, что для справки напишите help");
        System.out.println("А для выхода из программы советую использовать сочетание клавиш Ctrl+C/D (в зависимости от вашей системы)");
        System.out.println("Желаю удачи!");

        boolean login = false;

        ConsoleManager consM = new ConsoleManager();
        consM.startConsole();

        System.out.println("Выход из программы...");
        try {
            jsc.disconnectSSH();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}