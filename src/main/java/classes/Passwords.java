package classes;

import java.util.HashMap;

public class Passwords {
    public HashMap<String, String> passwords = new HashMap<>();

    public boolean putUser (String username, String password) {
        boolean result = false;
        if (!passwords.containsKey(username)) {
            passwords.put(username, password);
        }
        return result;
    }
}
