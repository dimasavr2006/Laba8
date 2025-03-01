package org.example.utils;

import org.example.classes.HumanBeing;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class EnvFileReader {
    private final String fileName;

    public EnvFileReader() {
        // Получаем имя файла из переменной окружения FILE_NAME
        this.fileName = System.getenv("FILE_NAME");
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("Переменная окружения FILE_NAME не установлена.");
        }
    }

    public ArrayList<HumanBeing> readData() {
        StringBuilder jsonContent = new StringBuilder();

        // Читаем данные из файла с использованием BufferedReader
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                jsonContent.append(line);
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }

        // Парсим JSON в ArrayList<HumanBeing>
        JsonParser parser = new JsonParser();
        return parser.parseJson(jsonContent.toString());
    }
}
