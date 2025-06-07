package functions;

import enums.Mood;
import enums.WeaponType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class TypeOfClassMap {
    public static Map<Class<?>, Function<String, ?>> classMap = new HashMap<>();

    static {
        classMap.put(Integer.class, Integer::parseInt);
        classMap.put(Double.class, Double::parseDouble);
        classMap.put(Float.class, Float::parseFloat);
        classMap.put(Long.class, Long::parseLong);
        classMap.put(Short.class, Short::parseShort);
        classMap.put(Byte.class, Byte::parseByte);
        classMap.put(WeaponType.class, WeaponType::valueOf);
        classMap.put(String.class, String::valueOf);
        classMap.put(Mood.class, Mood::valueOf);
        classMap.put(Boolean.class, s -> {
            if (s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false")) {
                return Boolean.parseBoolean(s);
            } else {
                throw new NumberFormatException("Неправильное значение для boolean: " + s);
            }
        });
    }
}
