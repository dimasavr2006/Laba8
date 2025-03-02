package org.example.utils;

import org.example.classes.HumanBeing;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class EnvFileWriter {
    private final String fileName;

    public EnvFileWriter() {
        // Получаем имя файла из переменной окружения FILE_NAME
        this.fileName = System.getenv("FILE_NAME");
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("Переменная окружения FILE_NAME не установлена.");
        }
    }

    public void writeData(ArrayList<HumanBeing> data) {
        JsonParser parser = new JsonParser();
        String jsonContent = parser.toJson(data);

        // Запись данных в файл с использованием FileWriter
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(jsonContent);
        } catch (IOException e) {
            System.err.println("Ошибка при записи файла: " + e.getMessage());
        }
    }
}
