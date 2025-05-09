package managers;

import run.Main;

import java.util.*;

public class LoginManager {

    CollectionManager cm;
    Scanner sc;
    DBManager db;

    public LoginManager() {
        this.cm = Main.cm;
        this.sc = Main.sc;
        this.db = Main.db;
    }

    public void startAuth() {
        while (!Main.login) {
            try {
                if (!sc.hasNextLine()) {
                    break;
                }
                String line = sc.nextLine().trim();
                String[] parts = line.split("\\s+");
                String commandStr = parts[0].toLowerCase();
                if (commandStr.equals("login") && parts.length == 3) {
                    Main.login = db.login(parts[1], parts[2]);
                    if (Main.login) {
                        Main.username = parts[1];
                    }
                } else if (commandStr.equals("register") && parts.length == 3) {
                    db.registerUser(parts[1], parts[2]);
                    if (Main.login) {
                        Main.username = parts[1];
                    }
                    if (Main.toBreak) {
                        Main.toBreak = false;
                        break;
                    }
                } else if (parts.length != 3) {
                    System.out.println("Нет логина или пароля или команда неверна");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
