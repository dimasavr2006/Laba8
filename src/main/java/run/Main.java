package run;

import functions.Invoker;
import gui.MainWindow;
import gui.dialogs.LoginDialog;
import managers.CollectionManager;
import managers.DBManager;
import utils.JSCh;

import javax.swing.*;
import java.util.Scanner;

/**
 * @author Dimasavr
 */

public class Main {

    public static DBManager db = new DBManager();

    public static CollectionManager cm = new CollectionManager(db);

    public static Invoker inv = new Invoker();

    public static Scanner sc = new Scanner(System.in);

//    public static DBManager db = new DBManager();

    public static boolean login = false;
    public static boolean toBreak = false;

    public static String username = "";

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSCh jsc = new JSCh();
        try {
            System.out.println("Попытка подключения к SSH...");
            jsc.connectSSH();
            System.out.println("SSH подключен. Попытка подключения к БД...");
            db.connect();
            System.out.println("БД подключена.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Критическая ошибка подключения к БД или SSH:\n" + e.getMessage() +
                            "\nПриложение будет закрыто.",
                    "Ошибка подключения", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
            return;
        }

        LoginDialog loginDialog = new LoginDialog(null, db); // Передаем db
        loginDialog.setVisible(true);

        if (loginDialog.isSucceeded() && Main.login) {
            try {
                cm.startCM();
                System.out.println("Коллекция загружена. Пользователь: " + Main.username);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Ошибка загрузки данных коллекции:\n" + e.getMessage(),
                        "Ошибка данных", JOptionPane.ERROR_MESSAGE);
            }

            SwingUtilities.invokeLater(() -> {
                MainWindow mainWindow = new MainWindow();
                mainWindow.setVisible(true);
            });
        } else {
            System.out.println("Авторизация не удалась. Приложение будет закрыто.");
            try {
                jsc.disconnectSSH();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.exit(0);
        }


//        JSCh jsc = new JSCh();
//        try {
//            jsc.connectSSH();
//            db.connect();
//            cm.startCM();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("Пожалуйста авторизуйтесь : введите или register <имя> <пароль> или login <имя> <пароль>");
//        LoginManager lm = new LoginManager();
//        lm.startAuth();
//
//        System.out.println("Приветствую в консольной части моей программы!");
//        System.out.println("Напоминаю, что для справки напишите help");
//        System.out.println("А для выхода из программы советую использовать сочетание клавиш Ctrl+C/D (в зависимости от вашей системы)");
//        System.out.println("Желаю удачи!");
//
//        ConsoleManager consM = new ConsoleManager();
//        consM.startConsole();
//
//        System.out.println("Выход из программы...");
//        try {
//            jsc.disconnectSSH();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}