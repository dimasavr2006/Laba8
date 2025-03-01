package org.example;

import org.example.classes.*;
import org.example.collections.*;
import org.example.commands.*;
import org.example.enums.*;
import org.example.exceptions.*;
import org.example.functions.*;
import org.example.utils.EnvFileReader;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static CollectionManager cm = new CollectionManager();

    public static void main(String[] args) {
        String fileName = System.getenv("FILE_NAME");
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("Переменная окружения FILE_NAME не установлена.");
        } else {
            System.out.println("Читаем данные из файла: " + fileName);
        }

        EnvFileReader reader = new EnvFileReader();
        ArrayList<HumanBeing> humanList = reader.readData();

        // Выводим данные на экран
        System.out.println("Данные из файла:");
        for (HumanBeing human : humanList) {
            System.out.println(human);
        }
    }
}