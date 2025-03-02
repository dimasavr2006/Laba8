package org.example.collections;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.classes.HumanBeing;

import java.io.File;
import java.io.IOException;

public class JsonParser {
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            HumanBeing human = objectMapper.readValue(new File("data.json"), HumanBeing.class);

            System.out.println("Результат парсинга:");
            System.out.println("ID: " + human.getId());
            System.out.println("Name: " + human.getName());
            System.out.println("Coordinates: x = " + human.getCoordinates().getX() + ", y = " + human.getCoordinates().getY());
            System.out.println("Creation Date: " + human.getCreationDate());
            System.out.println("Real Hero: " + human.isRealHero());
            System.out.println("Has Toothpick: " + human.isHasToothpick());
            System.out.println("Impact Speed: " + human.getImpactSpeed());
            System.out.println("Soundtrack Name: " + human.getSoundtrackName());
            System.out.println("Weapon Type: " + human.getWeaponType());
            System.out.println("Mood: " + human.getMood());
            System.out.println("Car: " + human.getCar().getName() + ", Cool: " + human.getCar().isCool());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
