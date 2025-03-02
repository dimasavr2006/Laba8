package org.example.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.classes.HumanBeing;

import java.io.IOException;
import java.util.ArrayList;

public class JsonParser {
    private final ObjectMapper objectMapper;

    public JsonParser() {
        this.objectMapper = new ObjectMapper();
    }

    // Парсинг JSON в ArrayList<HumanBeing>
    public ArrayList<HumanBeing> parseJson(String jsonContent) {
        try {
            return objectMapper.readValue(jsonContent, new TypeReference<ArrayList<HumanBeing>>() {});
        } catch (IOException e) {
            System.err.println("Ошибка при парсинге JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Преобразование ArrayList<HumanBeing> в JSON
    public String toJson(ArrayList<HumanBeing> data) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        } catch (IOException e) {
            System.err.println("Ошибка при преобразовании в JSON: " + e.getMessage());
            return "[]";
        }
    }
}
