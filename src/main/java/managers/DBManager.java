package managers;

import classes.Car;
import classes.Coordinates;
import classes.HumanBeing;
import classes.User;
import enums.Mood;
import enums.WeaponType;
import org.apache.ibatis.jdbc.ScriptRunner;
import run.Main;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DBManager {

    CollectionManager cm = Main.cm;

    private static Connection conn;
    private static ResultSet rs;
    private static ScriptRunner runner;

    public void connect() {
        try {
            this.conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/studs", "s467055", System.getenv("PASSWD"));
            this.runner = new ScriptRunner(this.conn);
            setScriptRunnerConfig();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void create() {
        try {
            FileReader fr = new FileReader("src/main/resources/sql/create.sql", StandardCharsets.UTF_8);
            runner.runScript(fr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<HumanBeing> getCollection() {
        ArrayList<HumanBeing> hb = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT " +
                        "hb.id, " +
                        "hb.name, " +
                        "coord.x, " +
                        "coord.y, " +
                        "hb.creation_date, " +
                        "hb.real_hero, " +
                        "hb.has_toothpick, " +
                        "hb.impact_speed, " +
                        "hb.soundtrack_name, " +
                        "hb.weapon_type, " +
                        "hb.mood, " +
                        "car.name_car, " +
                        "car.cool, " +
                        "col.owner_id " +
                        "FROM hb " +
                        "JOIN cars car on car.id_car = hb.car_id " +
                        "JOIN coords coord on coord.id_coord = hb.coords_id " +
                        "JOIN collection col on hb.id = col.element_id"
        );
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Integer id = rs.getInt("id");
                String name = rs.getString("name");
                Long x = rs.getLong("x");
                Long y = rs.getLong("y");
                java.util.Date date = rs.getDate("creation_date");
                Boolean realHero = rs.getBoolean("real_hero");
                Boolean hasToothpick = rs.getBoolean("has_toothpick");
                Long impactSpeed = rs.getLong("impact_speed");
                String soundtrackName = rs.getString("soundtrack_name");
                WeaponType weaponType = WeaponType.valueOf(rs.getString("weapon_type"));
                String moodString = rs.getString("mood");
                Mood mood = (moodString != null && !moodString.trim().isEmpty()) ? Mood.valueOf(moodString) : null;

                String nameCar = rs.getString("name_car");
                Boolean cool = rs.getBoolean("cool");

                Integer owner_id = rs.getInt("owner_id");

                HumanBeing being = new HumanBeing(
                        id,
                        name,
                        new Coordinates(x, y),
                        date,
                        realHero,
                        hasToothpick,
                        impactSpeed,
                        soundtrackName,
                        weaponType,
                        mood,
                        new Car(nameCar, cool),
                        owner_id
                );
                hb.add(being);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return hb;
    }

    public HashMap<Integer, User> getUsers() {
        HashMap<Integer, User> users = new HashMap<>();
        try {
            Statement s = conn.createStatement();
            rs = s.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                Integer uID = rs.getInt("user_id");
                String uName = rs.getString("login");
                String uPassword = rs.getString("password");
                User user = new User(uName, uPassword);
                users.put(uID, user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void registerUser(String username, String password) {
        try {
            conn.setAutoCommit(false);
            boolean userExists = false;
            try (PreparedStatement psCheck = conn.prepareStatement("SELECT 1 FROM users WHERE login = ?")) {
                psCheck.setString(1, username);
                try (ResultSet rsCheck = psCheck.executeQuery()) {
                    if (rsCheck.next()) {
                        userExists = true;
                    }
                }
            }

            if (userExists) {
                System.out.println("Такой пользователь уже существует: " + username);
                Main.login = false;
            } else {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO users (login, password) VALUES (?, ?)");
                ps.setString(1, username);
                ps.setString(2, hashSmth(password));
                int rows = ps.executeUpdate();

                if (rows > 0) {
                    conn.commit();
                    System.out.println("Пользователь " + username + " успешно зарегистрирован.");
                    Main.login = true;
                    Main.username = username;
                } else {
                    conn.rollback();
                    Main.login = false;
                }
            }
        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ignored) {}
            e.printStackTrace();
            Main.login = false;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean login(String username, String password) {
        boolean result = false;
        try {
            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement("SELECT password FROM users WHERE login = ?");
            ps.setString(1, username);
            rs = ps.executeQuery();
            String ourHash = hashSmth(password);
            boolean uFound = false;
            while (rs.next()) {
                uFound = true;
                String sHash = rs.getString("password");
                result = ourHash.equals(sHash);
            }

            if (!uFound || !result) {
                System.out.println("Неверный логин или пароль");
                result = false;
            } else {
                System.out.println("Авторизация успешна!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

public boolean add(HumanBeing hb, String username) {
    boolean success = false;
    int ownerId = findUserIDbyUsername(username);

    if (ownerId <= 0) {
        System.err.println("Ошибка: Пользователь '" + username + "' не найден или невозможно получить его ID.");
        return false;
    }

    hb.setOwnerId(ownerId);

    try {
        conn.setAutoCommit(false);

        int coordId = -1;
        try (PreparedStatement psCoords = conn.prepareStatement("INSERT INTO coords (x, y) VALUES (?, ?) RETURNING id_coord", Statement.RETURN_GENERATED_KEYS)) {
            psCoords.setLong(1, hb.getCoordinates().getX());
            psCoords.setLong(2, hb.getCoordinates().getY());
            int affectedRows = psCoords.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Ошибка при добавлении координат, ни одна строка не затронута.");
            }
            try (ResultSet generatedKeys = psCoords.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    coordId = generatedKeys.getInt(1);
                }
            }
        }

        int carId = -1;
        try (PreparedStatement psCars = conn.prepareStatement("INSERT INTO cars (name_car, cool) VALUES (?, ?) RETURNING id_car", Statement.RETURN_GENERATED_KEYS)) {
            psCars.setString(1, hb.getCar().getName());
            psCars.setBoolean(2, hb.getCar().isCool());
            int affectedRows = psCars.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Ошибка при добавлении машины, ни одна строка не затронута.");
            }
            try (ResultSet generatedKeys = psCars.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    carId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Ошибка при добавлении машины, не получен сгенерированный ID.");
                }
            }
        }

        int humanBeingId = -1;
        try (PreparedStatement psHB = conn.prepareStatement("INSERT INTO hb (name, coords_id, creation_date, real_hero, has_toothpick, impact_speed, soundtrack_name, weapon_type, mood, car_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id", Statement.RETURN_GENERATED_KEYS)) {
            psHB.setString(1, hb.getName());
            psHB.setInt(2, coordId);
            psHB.setDate(3, new java.sql.Date(System.currentTimeMillis()));
            psHB.setBoolean(4, hb.isRealHero());
            psHB.setBoolean(5, hb.isHasToothpick());
            psHB.setLong(6, hb.getImpactSpeed());
            psHB.setString(7, hb.getSoundtrackName());
            psHB.setString(8, hb.getWeaponType().name());
            psHB.setString(9, hb.getMood() != null ? hb.getMood().name() : null);
            psHB.setInt(10, carId);

            int affectedRows = psHB.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Ошибка при добавлении HumanBeing");
            }
            try (ResultSet generatedKeys = psHB.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    humanBeingId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Ошибка при добавлении HumanBeing, не получен сгенерированный ID.");
                }
            }
        }

        try (PreparedStatement psCollection = conn.prepareStatement(
                "INSERT INTO collection (element_id, owner_id) VALUES (?, ?)")) {
            psCollection.setInt(1, humanBeingId);
            psCollection.setInt(2, ownerId);
            psCollection.executeUpdate();
        }

        conn.commit();
        success = true;
    } catch (SQLException e) {
        try {
            if (conn != null) {
                conn.rollback();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        e.printStackTrace();
    } finally {
        try {
            if (conn != null) {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при сбросе autoCommit:");
            e.printStackTrace();
        }
    }
    return success;
}

    public boolean updateID(int id, HumanBeing hb, String username) { // Принимаем int id
        boolean success = false;
        int ownerId = findUserIDbyUsername(username); // Находим ID пользователя
        if (ownerId <= 0) {
            System.err.println("Ошибка updateID: Пользователь '" + username + "' не найден или невозможно получить его ID.");
            return false;
        }

        try {
            conn.setAutoCommit(false); // Начинаем транзакцию

            // 1. Проверяем, принадлежит ли элемент с данным id текущему пользователю,
            //    и получаем IDs связанных координат и машины.
            int existingCoordsId = -1;
            int existingCarId = -1;

            try (PreparedStatement psCheckAndGetIds = conn.prepareStatement("SELECT hb.coords_id, hb.car_id FROM hb JOIN collection ON hb.id = collection.element_id WHERE hb.id = ? AND collection.owner_id = ?")) {
                psCheckAndGetIds.setInt(1, id);
                psCheckAndGetIds.setInt(2, ownerId);
                try (ResultSet rsCheck = psCheckAndGetIds.executeQuery()) {
                    if (!rsCheck.next()) {
                        System.err.println("Ошибка updateID: Элемент с ID " + id + " не найден или не принадлежит пользователю '" + username + "'.");
                        try {
                            if (conn != null) conn.rollback();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        return false;
                    }
                    existingCoordsId = rsCheck.getInt("coords_id");
                    existingCarId = rsCheck.getInt("car_id");
                }
            }
            try (PreparedStatement psUCoord = conn.prepareStatement("UPDATE coords SET x = ?, y = ? WHERE id_coord = ?")) {
                psUCoord.setLong(1, hb.getCoordinates().getX());
                psUCoord.setLong(2, hb.getCoordinates().getY());
                psUCoord.setInt(3, existingCoordsId);
                int affectedRows = psUCoord.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Обновление координат (id_coord: " + existingCoordsId + ") не удалось");
                }
            }

            try (PreparedStatement psUCar = conn.prepareStatement("UPDATE cars SET name_car = ?, cool = ? WHERE id_car = ?")) {
                psUCar.setString(1, hb.getCar().getName());
                psUCar.setBoolean(2, hb.getCar().isCool());
                psUCar.setInt(3, existingCarId);
                int affectedRows = psUCar.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Обновление машины (id_car: " + existingCarId + ") не удалось");
                }
            }

            try (PreparedStatement psE = conn.prepareStatement(
                    "UPDATE hb SET name = ? , real_hero = ?, has_toothpick = ?, impact_speed = ?, soundtrack_name = ?, weapon_type = ?, mood = ? WHERE id = ?")) { // Убрали обновление coords_id и car_id т.к. они не меняются, меняются данные по ним
                psE.setString(1, hb.getName());
                // psE.setDate(2, new java.sql.Date(hb.getCreationDate().getTime()));
                psE.setBoolean(2, hb.isRealHero());
                psE.setBoolean(3, hb.isHasToothpick());
                psE.setLong(4, hb.getImpactSpeed());
                psE.setString(5, hb.getSoundtrackName());
                psE.setString(6, hb.getWeaponType().name());
                psE.setString(7, hb.getMood() != null ? hb.getMood().name() : null);
                psE.setInt(8, id);

                int affectedRows = psE.executeUpdate();
                if (affectedRows == 0) {
                    // Это может произойти, если элемент был удален после проверки владения, но до UPDATE hb
                    throw new SQLException("Обновление HumanBeing (id: " + id + ") не удалось");
                }
            }

            conn.commit();
            success = true;
            System.out.println("Элемент с ID " + id + " успешно обновлен.");

        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                System.err.println("Ошибка при откате транзакции updateID:");
                ex.printStackTrace();
            }
            System.err.println("Подробности ошибки updateID:");
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    public boolean removeByID(String username, int id) {
        boolean success = false;
        int ownerId = findUserIDbyUsername(username);
        if (ownerId <= 0) {
            System.err.println("Ошибка removeByID: Пользователь '" + username + "' не найден или невозможно получить его ID.");
            return false;
        }

        try {
            conn.setAutoCommit(false);

            boolean elementOwned = false;
            try (PreparedStatement psCO = conn.prepareStatement(
                    "SELECT 1 FROM collection WHERE element_id = ? AND owner_id = ?")) {
                psCO.setInt(1, id);
                psCO.setInt(2, ownerId);
                try (ResultSet rsCheck = psCO.executeQuery()) {
                    if (rsCheck.next()) {
                        elementOwned = true;
                    }
                }
            }

            if (!elementOwned) {
                System.err.println("Ошибка removeByID: Элемент с ID " + id + " не найден или не принадлежит пользователю '" + username + "'.");
                try {
                    if (conn != null) {
                        conn.rollback();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                return false;
            }

            try (PreparedStatement psDBID = conn.prepareStatement("DELETE FROM hb WHERE id = ?")) {
                psDBID.setInt(1, id);
                int affectedRows = psDBID.executeUpdate();

                if (affectedRows > 0) {
                    conn.commit();
                    success = true;
                    System.out.println("Элемент с ID " + id + " успешно удален.");
                }
            }

        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    public boolean clear(String username) {
        boolean success = false;
        int ownerId = findUserIDbyUsername(username);
        if (ownerId <= 0) {
            System.err.println("Ошибка clear: Пользователь '" + username + "' не найден или невозможно получить его ID.");
            return false;
        }

        try {
            conn.setAutoCommit(false);

            try (PreparedStatement psClearCollection = conn.prepareStatement(
                    "DELETE FROM hb WHERE id IN (SELECT element_id FROM collection WHERE owner_id = ?)")) {
                psClearCollection.setInt(1, ownerId);
                int affectedRows = psClearCollection.executeUpdate();

                conn.commit();
                success = true;
                System.out.println("Все элементы пользователя '" + username + "' успешно удалены (" + affectedRows + " элементов).");
            }

        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    public int findUserIDbyUsername(String username) {
        int id = 0;
        try {
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement("SELECT user_id FROM users WHERE login = ?");
            ps.setString(1, username);
            rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt("user_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return id;
    }

    public boolean removeFirst(String username) {
        boolean success = false;
        int ownerId = findUserIDbyUsername(username); // Находим ID пользователя
        if (ownerId <= 0) {
            System.err.println("Ошибка removeFirst: Пользователь '" + username + "' не найден или невозможно получить его ID.");
            return false; // Пользователь не найден
        }

        try {
            conn.setAutoCommit(false);

            int elementIdToRemove = -1;
            try (PreparedStatement psFindMinOwned = conn.prepareStatement("SELECT hb.id FROM hb JOIN collection ON hb.id = collection.element_id WHERE collection.owner_id = ? ORDER BY hb.id ASC LIMIT 1")) {
                psFindMinOwned.setInt(1, ownerId);

                try (ResultSet rsMinOwned = psFindMinOwned.executeQuery()) {
                    if (!rsMinOwned.next()) {
                        System.out.println("У пользователя '" + username + "' нет элементов для удаления.");
                        try {
                            if (conn != null) conn.rollback();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        return false;
                    }
                    elementIdToRemove = rsMinOwned.getInt(1);
                }
            }

            try (PreparedStatement psDeleteHB = conn.prepareStatement("DELETE FROM hb WHERE id = ?")) {
                psDeleteHB.setInt(1, elementIdToRemove);
                int affectedRows = psDeleteHB.executeUpdate();

                if (affectedRows > 0) {
                    conn.commit();
                    success = true;
//                    System.out.println("Минимальный элемент пользователя '" + username + "' (ID: " + elementIdToRemove + ") успешно удален.");
                } else {
                    System.err.println("Ошибка removeFirst: Элемент с ID " + elementIdToRemove + " не был удален (возможно, уже отсутствует?).");
                    try {
                        if (conn != null) conn.rollback();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace(); // Печатаем информацию об исходной ошибке
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    public boolean removeAnyByMood (String username, String moodString) {
        boolean success = false;
        int ownerId = findUserIDbyUsername(username);
        if (ownerId <= 0) {
            System.err.println("Ошибка removeAnyByMood: Пользователь '" + username + "' не найден или невозможно получить его ID.");
            return false;
        }

        String upperMoodString = moodString.toUpperCase();
        try {
            Mood.valueOf(upperMoodString);
        } catch (IllegalArgumentException e) {
            System.err.println("Ошибка removeAnyByMood: Неверное значение настроения: " + moodString);
            return false;
        }

        try {
            conn.setAutoCommit(false);

            int elementIdToRemove = -1;
            try(PreparedStatement psFind = conn.prepareStatement("SELECT hb.id FROM hb JOIN collection ON hb.id = collection.element_id WHERE collection.owner_id = ? AND hb.mood = ? ORDER BY hb.id ASC LIMIT 1")) {
                psFind.setInt(1, ownerId);
                psFind.setString(2, upperMoodString);
                try(ResultSet rsFind = psFind.executeQuery()) {
                    if (!rsFind.next()) {
                        System.out.println("У пользователя '" + username + "' нет элементов с настроением " + moodString + " для удаления.");
                        try {
                            if (conn != null) conn.rollback();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        return false;
                    }
                    elementIdToRemove = rsFind.getInt(1);
                }
            }

            if (elementIdToRemove != -1) {
                try(PreparedStatement psDeleteHB = conn.prepareStatement("DELETE FROM hb WHERE id = ?")) {
                    psDeleteHB.setInt(1, elementIdToRemove);
                    int affectedRows = psDeleteHB.executeUpdate();
                    if (affectedRows > 0) {
                        conn.commit();
                        success = true;
                        System.out.println("Один элемент пользователя '" + username + "' с настроением " + moodString + " (ID: " + elementIdToRemove + ") успешно удален.");
                    }
                }
            }

        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    public int findOwnerIDByElementID (int id) {
        int ownerID = 0;
        try {
            try (PreparedStatement ps = conn.prepareStatement("SELECT collection.owner_id FROM collection WHERE element_id = ?")) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        ownerID = rs.getInt("owner_id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ownerID;
    }

    public String hashSmth (String string) {
        StringBuilder hashPassword = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");

            byte[] pass = string.getBytes(StandardCharsets.UTF_8);
            byte[] digest = md.digest(pass);
            for (byte b : digest) {
                String hex = String.format("%02x", b & 0xff);
                hashPassword.append(hex);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashPassword.toString();
    }

    private void setScriptRunnerConfig() {
        runner.setStopOnError(true);
        runner.setAutoCommit(false);
        runner.setLogWriter(null);
        runner.setSendFullScript(false);
    }
}
