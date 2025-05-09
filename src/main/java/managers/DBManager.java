package managers;

import classes.*;
import enums.Mood;
import enums.WeaponType;
import exceptions.*;
import org.apache.ibatis.jdbc.ScriptRunner;
import run.Main;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
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

//    public Pair<HashMap<Integer, User>, HashMap<String, HumanBeing>> getCollection() {
//        HashMap<Integer, User> users = new HashMap<>();
//        HashMap<String, HumanBeing> hb = new HashMap<>();
//
//        try {
//            Statement s = conn.createStatement();
//            rs = s.executeQuery("SELECT * FROM collection JOIN s467055.users users on users.user_id = collection.owner_id JOIN s467055.hb humanB on humanB.id = collection.element_id JOIN s467055.cars car on humanB.car_id = car.id_car JOIN s467055.coords coord on coord.id_coord = humanB.coords_id");
//            while (rs.next()) {
//                Integer uID = rs.getInt("user_id");
//                String uName = rs.getString("login");
//                String uPassword = rs.getString("password");
//                User user = new User(uName, uPassword);
//                users.put(uID, user);
//
//                Integer id = rs.getInt("id");
//                String name = rs.getString("name");
//                Long x = rs.getLong("x");
//                Long y = rs.getLong("y");
//                java.util.Date date = rs.getDate("creation_date");
//                Boolean realHero = rs.getBoolean("real_hero");
//                Boolean hasToothpick = rs.getBoolean("has_toothpick");
//                Long impactSpeed = rs.getLong("impact_speed");
//                String soundtrackName = rs.getString("soundtrack_name");
//                WeaponType weaponType = WeaponType.valueOf(rs.getString("weapon_type"));
//                Mood mood = Mood.valueOf(rs.getString("mood"));
//                String nameCar = rs.getString("name_car");
//                Boolean cool = rs.getBoolean("cool_down");
//
//                String key = rs.getString("key");
//
//                HumanBeing being = new HumanBeing(id,
//                        name,
//                        new Coordinates(x, y),
//                        date,
//                        realHero,
//                        hasToothpick,
//                        impactSpeed,
//                        soundtrackName,
//                        weaponType,
//                        mood,
//                        new Car(nameCar, cool)
//                );
//                hb.put(key, being);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return new Pair<>(users, hb);
//    }

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

            boolean flag = false;
            for (User user : getUsers().values()) {
                if (username.equals(user.getLogin())) {
                    flag = true;
                    System.out.println("Такой пользователь уже существует");
                }
            }
            if (!flag) {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO users (login, password) VALUES (?, ?)");
                ps.setString(1, username);
                ps.setString(2, hashSmth(password));
                int rows = ps.executeUpdate();

                if (rows > 0) {
                    conn.commit();
                    System.out.println("Пользователь " + username + " успешно зарегистрирован.");
                }
                Main.login = true;
                Main.toBreak = false;

//            PreparedStatement ps = conn.prepareStatement("INSERT INTO users (login, password) VALUES (?, ?)");
//            ps.setString(1, username);
//            ps.setString(2, hashSmth(password));
//            ps.executeQuery();
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

    public boolean add (HumanBeing hb, String username) {
        boolean success = false;
        try {
            hb.setOwnerId(findUserIDbyUsername(username));
            conn.setAutoCommit(false);

            PreparedStatement psCommand = conn.prepareStatement("INSERT INTO coords (x, y) VALUES (?, ?)");
            psCommand.setLong(1, hb.getCoordinates().getX());
            psCommand.setLong(2, hb.getCoordinates().getY());
            psCommand.execute();
            Statement sCoord = conn.createStatement();
            rs = sCoord.executeQuery("SELECT id_coord FROM coords ORDER BY id_coord DESC LIMIT 1");

            int maxIDCoord = 1;
            while (rs.next()) {
                maxIDCoord = rs.getInt("id_coord");
            }

            PreparedStatement psCar = conn.prepareStatement("INSERT INTO cars (name_car, cool) VALUES (?, ?)");
            psCar.setString(1, hb.getCar().getName());
            psCar.setBoolean(2, hb.getCar().isCool());
            psCar.execute();
            Statement sCar = conn.createStatement();
            rs = sCar.executeQuery("SELECT id_car FROM cars ORDER BY id_car DESC LIMIT 1");

            int maxIDCar = 1;
            while (rs.next()) {
                maxIDCar = rs.getInt("id_car");
            }

            PreparedStatement psE = conn.prepareStatement("INSERT INTO hb (name, coords_id, creation_date, real_hero, has_toothpick, impact_speed, soundtrack_name, weapon_type, mood, car_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            psE.setString(1, hb.getName());
            psE.setInt(2, maxIDCoord);
            psE.setDate(3, (Date) hb.getCreationDate());
            psE.setBoolean(4, hb.isRealHero());
            psE.setBoolean(5, hb.isHasToothpick());
            psE.setLong(6, hb.getImpactSpeed());
            psE.setString(7, hb.getSoundtrackName());
            psE.setString(8, hb.getWeaponType().name());
            psE.setString(9, hb.getMood().name());
            psE.setInt(10, maxIDCar);

            psE.execute();

            Statement s = conn.createStatement();
            rs = s.executeQuery("SELECT hb.id FROM hb ORDER BY id DESC LIMIT 1");
            int maxID = 1;
            while (rs.next()) {
                maxID = rs.getInt("id");
            }

            PreparedStatement psCol = conn.prepareStatement("INSERT INTO collection (element_id, owner_id) VALUES (?, ?)");
            psCol.setInt(1, maxID);
            psCol.setInt(2, hb.getOwnerId());
            psCol.execute();

            conn.commit();
            success = true;

        } catch (SQLException e) {
            try {
                e.printStackTrace();
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    public boolean updateID (int id, HumanBeing hb, String username) {
        boolean success = false;

        try {
            conn.setAutoCommit(false);

            checkAccessToElement(hb, username);

            PreparedStatement psCoord = conn.prepareStatement("SELECT coords_id FROM hb WHERE coords_id = ?");
            psCoord.setInt(1, id);
            rs = psCoord.executeQuery();
            Integer coordsID = null;
            while (rs.next()) {
                coordsID = rs.getInt("coords_id");
            }

            PreparedStatement psCar = conn.prepareStatement("SELECT car_id FROM hb WHERE car_id = ?");
            psCoord.setInt(1, id);
            rs = psCoord.executeQuery();
            Integer carID = null;
            while (rs.next()) {
                carID = rs.getInt("car_id");
            }

            PreparedStatement psUCoord = conn.prepareStatement("UPDATE coords SET x = ?, y = ? WHERE id_coord = ?");
            psUCoord.setLong(1, hb.getCoordinates().getX());
            psUCoord.setLong(2, hb.getCoordinates().getY());
            psUCoord.setInt(3, coordsID);
            psUCoord.execute();

            PreparedStatement psUCar = conn.prepareStatement("UPDATE cars SET name_car = ?, cool = ? WHERE id_car = ?");
            psUCar.setLong(1, hb.getCoordinates().getX());
            psUCar.setLong(2, hb.getCoordinates().getY());
            psUCar.setInt(3, carID);
            psUCar.execute();

            PreparedStatement psE = conn.prepareStatement("UPDATE hb SET name = ? , creation_date = ?, coords_id = ?, real_hero = ?, has_toothpick = ?, impact_speed = ?, soundtrack_name = ?, weapon_type = ?, mood = ?, car_id = ? WHERE id = ? AND ? = ?");
            psE.setString(1, hb.getName());
            psE.setDate(2, (Date) hb.getCreationDate());
            psE.setInt(3, coordsID);
            psE.setBoolean(4, hb.isRealHero());
            psE.setBoolean(5, hb.isHasToothpick());
            psE.setLong(6, hb.getImpactSpeed());
            psE.setString(7, hb.getSoundtrackName());
            psE.setString(8, hb.getWeaponType().name());
            psE.setString(9, hb.getMood().name());
            psE.setInt(10, carID);

            psE.execute();
            conn.commit();
            success = true;

        } catch (SQLException e) {
            try {
                e.printStackTrace();
                conn.rollback();
            } catch (SQLException ex) {
                e.printStackTrace();
            }
        }
        finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    public boolean removeByID (String username, String id) {
        boolean success = false;
        try {
            boolean flag = false;
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement("SELECT collection.element_id FROM collection JOIN users ON collection.owner_id = users.user_id JOIN hb ON collection.element_id = hb.id JOIN coords ON hb.coords_id = coords.id_coord WHERE users.user_id = ?");
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                flag = true;
            }

            PreparedStatement psD = conn.prepareStatement("DELETE FROM coords WHERE id_coord = (SELECT hb.coords_id FROM hb WHERE coords_id = (SELECT element_id FROM collection WHERE owner_id = ?))");
            psD.setString(1, id);
            psD.execute();

            conn.commit();
            success = true;

            if (flag) {
                success = false;
            }

        } catch (SQLException e) {
            try {
                e.printStackTrace();
                conn.rollback();
            } catch (SQLException ex) {
                e.printStackTrace();
            }
        }
        finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    public boolean clear(String username) {
        boolean success = false;
        try {
            conn.setAutoCommit(false);

            PreparedStatement psD = conn.prepareStatement("DELETE FROM coords WHERE id_coord = (SELECT hb.coords_id FROM hb WHERE id_coord = (SELECT collection.element_id FROM collection WHERE owner_id = (SELECT owner_id FROM users WHERE login = ?)))");
            psD.setString(1, username);
            psD.execute();
            conn.commit();
            success = true;
        } catch (SQLException e) {
            try {
                e.printStackTrace();
                conn.rollback();
            } catch (SQLException ex) {
                e.printStackTrace();
            }
        } finally {
            try {
                conn.setAutoCommit(true);
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

    public boolean removeFirst (String username) {
        boolean success = false;
        try {
            HumanBeing min = cm.findMin();
            checkAccessToElement(min, username);
            int idElement = findIDElementByElement(min);

            PreparedStatement ps = conn.prepareStatement("DELETE FROM collection WHERE element_id = ? AND owner_id = (SELECT user_id FROM users WHERE login = ?)");
            ps.setInt(1, idElement);
            ps.setString(2, username);
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoAccessToElement e) {
            e.printStackTrace();
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    private boolean checkAccessToElement (String toFindUN, String yourUN) {
        boolean access = false;
        if (toFindUN.equals(yourUN)) {
            access = true;
        } else {
            access = false;
            throw new NoAccessToElement(yourUN);
        }
        return access;
    }
    private boolean checkAccessToElement (HumanBeing hb, String yourUN) {
        boolean access = false;
        if (findIDElementByElement(hb) == findUserIDbyUsername(yourUN)) {
//            access = true;
            try {
                conn.setAutoCommit(false);

                Integer idOfUser = findUserIDbyUsername(yourUN);
                Integer ifOfElement = findIDElementByElement(hb);

                PreparedStatement ps = conn.prepareStatement("SELECT collection.element_id FROM collection WHERE element_id = ? AND owner_id = ?");
                ps.setInt(1, ifOfElement);
                ps.setInt(2, idOfUser);
                int affectedRows = ps.executeUpdate();
                if (affectedRows == 0) {
                    throw new NoAccessToElement(yourUN);
                } else {
                    access = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            access = false;
            throw new NoAccessToElement(yourUN);
        }
        return access;
    }

    public int findIDElementByElement (HumanBeing hb) {
        int id = 0;

        Integer coordsID = null;
        Integer carID = null;

        String moodString = hb.getMood().toString();
        String weaponTypeString = hb.getWeaponType().toString();

        try {
            conn.setAutoCommit(false);

            PreparedStatement psCoord = conn.prepareStatement("SELECT coords.id_coord FROM coords WHERE x = ? AND y = ?");
            psCoord.setLong(1, hb.getCoordinates().getX());
            psCoord.setLong(2, hb.getCoordinates().getY());
            rs = psCoord.executeQuery();
            while (rs.next()) {
                coordsID = rs.getInt("id_coord");
            }

            PreparedStatement psCar = conn.prepareStatement("SELECT id_car FROM cars WHERE name_car = ? AND cool = ?");
            psCar.setString(1, hb.getCar().getName());
            psCar.setBoolean(2, hb.getCar().isCool());
            rs = psCar.executeQuery();
            while (rs.next()) {
                carID = rs.getInt("id_car");
            }

            String request = "SELECT id FROM hb WHERE (" +
                    "name = ? " +
                    "AND coords_id = " + coordsID +
                    "AND creation_date = ?" +
                    "AND real_hero = ? " +
                    "AND has_toothpick = ? " +
                    "AND impact_speed = ? " +
                    "AND soundtrack_name = ?" +
                    "AND weapon_type = " + hb.getWeaponType().toString() +
                    "AND mood = " + hb.getMood().toString() +
                    "AND car_id = " + carID + ")";

            PreparedStatement psAll = conn.prepareStatement(request);
            psAll.setString(1, hb.getName());
            psAll.setDate(2, (Date) hb.getCreationDate());
            psAll.setBoolean(3, hb.isRealHero());
            psAll.setBoolean(4, hb.isHasToothpick());
            psAll.setLong(5, hb.getImpactSpeed());
            psAll.setString(6, hb.getSoundtrackName());
            rs = psAll.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");
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

    public boolean removeAnyByMood (String mood) {
        boolean success = false;
        try {
            conn.setAutoCommit(false);
            PreparedStatement psM = conn.prepareStatement("DELETE FROM hb WHERE id IN (SELECT id FROM hb WHERE mood = ? ORDER BY id ASC LIMIT 1)");
            psM.setString(1, mood);
            psM.execute();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    public int findOwnerIDByElement (HumanBeing hb) {
        int id = 0;
        int idEl = findIDElementByElement(hb);
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT collection.owner_id FROM collection WHERE element_id = ?");
            ps.setInt(1, idEl);
            rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt("owner_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public int findOwnerIDByElementID (int id) {
        int ownerID = 0;
        try {
            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement("SELECT collection.owner_id FROM collection WHERE element_id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                ownerID = rs.getInt("owner_id");
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
