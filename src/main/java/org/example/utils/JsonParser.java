package org.example.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.classes.HumanBeing;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * @author Dimasavr
 */

public class JsonParser {
    private ObjectMapper mapper;

    /**
     * Чтение коллекции из файла JSON
     * @param nameOfFile
     * @return ArrayList
     */
    public static ArrayList<HumanBeing> jsonToCollection(String nameOfFile) {
        try {

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
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

    /**
     * Запись коллекции в файл JSON
     * @param way
     * @param data
     */
    public static void collectionToJson(String way, ArrayList<HumanBeing> data) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            mapper.writeValue(new File(way), data);
        } catch (IOException e) {
            System.out.println("Ошибка сохранения коллекции в файл: " + e.getMessage());
        }
    }

}
