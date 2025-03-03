package org.example.utils;

import com.fasterxml.jackson.core.type.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.*;
import org.example.classes.*;

import java.io.*;
import java.text.*;
import java.util.*;

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

    public static ArrayList<HumanBeing> jsonToCollection(String nameOfFile) {
        try {

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.setDateFormat(new SimpleDateFormat("dd.MM.yyyy"));
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            CollectionType hbFile = mapper.getTypeFactory().constructCollectionType(ArrayList.class, HumanBeing.class);

            File file = new File(nameOfFile);

            ArrayList<HumanBeing> hbOut = mapper.readValue(file, hbFile);

            return hbOut;
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
            System.out.println("Попробуйте ещё раз");
            return null;
        }
    }

}
