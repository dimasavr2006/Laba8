package org.example.utils;

import org.example.classes.Car;
import org.example.classes.Coordinates;
import org.example.classes.HumanBeing;
import org.example.enums.Mood;
import org.example.enums.WeaponType;
import org.example.exceptions.NullStringException;

import java.util.Scanner;

public class DocumentFieldBuilder {

    /**
     * Ожидает ввод формата в строчку через пробел
     * name
     * coordinates.x
     * coordinates.y
     * realHero
     * hasToothpick
     * impactSpeed
     * soundtrackName
     * weaponType
     * mood
     * car.name
     * car.cool
     * @param sc
     * @return HumanBeing
     */

    public HumanBeing build(Scanner sc, String inputLine) {
        String[] tokens = inputLine.trim().split("\\s+");
        if (tokens.length != 12) {
            throw new IllegalArgumentException("Ожидается 11 полей, получено " + tokens.length);
        }

        String[] tokens2 = new String[tokens.length - 1];
        System.arraycopy(tokens, 1, tokens2, 0, tokens2.length);

        HumanBeing human = new HumanBeing();

        if (tokens2[0].trim().isEmpty()) {
            throw new NullStringException();
        }
        human.setName(tokens2[0]);

        Coordinates coordinates = new Coordinates();
        if (tokens2[1].trim().isEmpty()) {
            throw new NullStringException();
        }
        try {
            long x = Long.parseLong(tokens[1]);
            coordinates.setX(x);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Неверный формат для coordinates.x");
        }
        if (tokens2[2].trim().isEmpty()) {
            throw new NullStringException();
        }
        try {
            long y = Long.parseLong(tokens2[2]);
            coordinates.setY(y);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Неверный формат для coordinates.y");
        }
        human.setCoordinates(coordinates);

        if (tokens2[3].trim().isEmpty()) {
            throw new NullStringException();
        }
        if (!tokens2[3].equalsIgnoreCase("true") && !tokens2[3].equalsIgnoreCase("false")) {
            throw new IllegalArgumentException("Неверный формат для realHero");
        }
        human.setRealHero(Boolean.parseBoolean(tokens2[3]));

        if (tokens2[4].trim().isEmpty()) {
            throw new NullStringException();
        }
        if (!tokens2[4].equalsIgnoreCase("true") && !tokens2[4].equalsIgnoreCase("false")) {
            throw new IllegalArgumentException("Неверный формат для hasToothpick");
        }
        human.setHasToothpick(Boolean.parseBoolean(tokens2[4]));

        if (tokens2[5].trim().isEmpty() || tokens2[5].equalsIgnoreCase("null")) {
            human.setImpactSpeed(null);
        } else {
            try {
                Long impactSpeed = Long.parseLong(tokens2[5]);
                human.setImpactSpeed(impactSpeed);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Неверный формат для impactSpeed");
            }
        }

        if (tokens2[6].trim().isEmpty()) {
            throw new NullStringException();
        }
        human.setSoundtrackName(tokens2[6]);

        if (tokens2[7].trim().isEmpty()) {
            throw new NullStringException();
        }
        human.setWeaponType(WeaponType.valueOf(tokens2[7]));

        if (tokens2[8].trim().isEmpty() || tokens2[8].equalsIgnoreCase("null")) {
            human.setMood(null);
        } else {
            human.setMood(Mood.valueOf(tokens2[8]));
        }

        Car car = new Car();
        if (tokens2[9].trim().isEmpty()) {
            throw new NullStringException();
        }
        car.setName(tokens2[9]);
        if (tokens2[10].trim().isEmpty()) {
            throw new NullStringException();
        }
        if (!tokens2[10].equalsIgnoreCase("true") && !tokens2[10].equalsIgnoreCase("false")) {
            throw new IllegalArgumentException("Неверный формат для car.cool");
        }
        car.setCool(Boolean.parseBoolean(tokens2[10]));
        human.setCar(car);

        return human;
    }

}
