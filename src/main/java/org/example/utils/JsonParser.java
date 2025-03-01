package org.example.utils;

import com.fasterxml.jackson.datatype.jsr310.*;
import com.fasterxml.jackson.databind.*;
import org.example.classes.*;

import java.io.*;
import java.text.*;
import java.util.*;

public class JsonParser {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat("dd.MM.yyyy"));
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try {
            List<HumanBeing> hb = mapper.readValue(
                    new File("src/main/jsonF/input.json"),
                    mapper.getTypeFactory().constructCollectionType(List.class, HumanBeing.class)
            );
            hb.forEach(System.out::println);

//            mapper.writeValue(new File("src/main/java/output.json"), hb);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}