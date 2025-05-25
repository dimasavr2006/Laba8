package classes;

import java.util.HashMap;
import java.util.Map;

@Deprecated(forRemoval = true)
public class Passwords {
    public Map<String, String> passwords = new HashMap<>();

    public Passwords() {
        this.passwords = new HashMap<>();
    }

    public boolean putUser (String username, String hashedPassword) {
        boolean result = false;
        if (!passwords.containsKey(username)) {
            passwords.put(username, hashedPassword);
        }
        return result;
    }
}
