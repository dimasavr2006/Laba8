//package gui.filter; // Твой пакет
//
//import classes.HumanBeing;
//import gui.HBTable;
//
//import javax.swing.*;
//import java.util.stream.Stream;
//
//public class ColumnRowFilter extends RowFilter<HBTable, Integer> {
//    private String searchText;
//    private int columnIndexToFilter; // Эта переменная теперь будет использоваться
//    private HBTable tableModel;
//
//    public ColumnRowFilter(String searchText, int columnIndexToFilter, HBTable model) {
//        this.searchText = searchText.toLowerCase().trim();
//        this.columnIndexToFilter = columnIndexToFilter;
//        this.tableModel = model;
//    }
//
//    @Override
//    public boolean include(Entry<? extends HBTable, ? extends Integer> entry) {
//        if (searchText.isEmpty()) {
//            return true;
//        }
//
//        HumanBeing human = tableModel.getHumanBeingAt(entry.getIdentifier());
//        if (human == null) {
//            return false;
//        }
//
//        Object valueFromColumn;
//
//        // Получаем значение ТОЛЬКО для указанной columnIndexToFilter
//        // Важно: Model getValueAt возвращает "сырые" данные (Date, Enum, etc.)
//        // Мы должны привести их к строке для текстового поиска.
//        try {
//            // Используем метод TableModel для получения значения, которое будет отсортировано/отфильтровано
//            // entry.getValue(columnIndexToFilter) - это значение из модели для этой колонки
//            valueFromColumn = entry.getValue(columnIndexToFilter);
//
//        } catch (IndexOutOfBoundsException e) {
//            System.err.println("Неверный индекс колонки в ColumnRowFilter: " + columnIndexToFilter);
//            return false; // Не включать, если индекс колонки неверный
//        }
//
//
//        if (valueFromColumn == null) {
//            return false;
//        }
//
//        // Используем Stream API, как требовалось
//        return Stream.of(String.valueOf(valueFromColumn).toLowerCase())
//                .anyMatch(fieldValue -> fieldValue.contains(searchText));
//    }
//}

package functions.filter;

import classes.HumanBeing;
import gui.HBTable;
import managers.LocalisationManager;

import javax.swing.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.stream.Stream;

public class ColumnRowFilter extends RowFilter<HBTable, Integer> {
    private String filterText;
    private int columnIndex;
    private String operatorKey; // Ключ оператора, например "operator.contains", "operator.greaterThan"
    private HBTable tableModel;

    public ColumnRowFilter(String filterText, int columnIndex, String operatorKey, HBTable model) {
        this.filterText = filterText.trim(); // Убираем пробелы только здесь
        this.columnIndex = columnIndex;
        this.operatorKey = operatorKey;
        this.tableModel = model;
    }

    @Override
    public boolean include(Entry<? extends HBTable, ? extends Integer> entry) {
        HumanBeing human = tableModel.getHumanBeingAt(entry.getIdentifier());
        if (human == null) return false;

        Object modelValue = entry.getValue(columnIndex); // Получаем "сырое" значение из модели

        // Если оператор не требует значения (например, для boolean "is true" / "is false")
        if (modelValue instanceof Boolean) {
            if (operatorKey.equals("operator.isTrue")) return (Boolean) modelValue;
            if (operatorKey.equals("operator.isFalse")) return !(Boolean) modelValue;
        }

        // Если текст фильтра пуст, а оператор требует значение, то строка не проходит
        if (filterText.isEmpty() && !(modelValue instanceof Boolean && (operatorKey.equals("operator.isTrue") || operatorKey.equals("operator.isFalse")))) {
            // Для некоторых операторов (IS NULL, IS NOT NULL) пустой текст мог бы быть валидным, но у нас их пока нет.
            return false; // Или true, если пустой фильтр должен все показывать (но это обычно обрабатывается выше)
        }


        if (modelValue == null) {
            // Обработка null значений в колонке - для большинства операторов сравнения это false
            // Кроме, возможно, "is null" / "is not null", которых у нас пока нет
            return operatorKey.equals("operator.notEquals") && !filterText.isEmpty(); // Пример: "не равно пустоте" может пройти
        }

        String modelValueString = String.valueOf(modelValue).toLowerCase();
        String filterTextLower = filterText.toLowerCase();

        // Используем Streams API для соответствия ТЗ, хотя для одного сравнения это избыточно
        try {
            switch (operatorKey) {
                case "operator.contains":
                    return Stream.of(modelValueString).anyMatch(s -> s.contains(filterTextLower));
                case "operator.equals":
                case "operator.equalsNum": // Для чисел и строк можно использовать одно и то же
                case "operator.equalsEnum":
                    return Stream.of(modelValueString).anyMatch(s -> s.equals(filterTextLower));
                case "operator.notEquals":
                case "operator.notEqualsNum":
                    return Stream.of(modelValueString).noneMatch(s -> s.equals(filterTextLower)); // или !anyMatch
                case "operator.startsWith":
                    return Stream.of(modelValueString).anyMatch(s -> s.startsWith(filterTextLower));
                case "operator.endsWith":
                    return Stream.of(modelValueString).anyMatch(s -> s.endsWith(filterTextLower));

                // Для чисел
                case "operator.greaterThan":
                    if (modelValue instanceof Number) {
                        return ((Number) modelValue).doubleValue() > Double.parseDouble(filterText);
                    } break;
                case "operator.lessThan":
                    if (modelValue instanceof Number) {
                        return ((Number) modelValue).doubleValue() < Double.parseDouble(filterText);
                    } break;
                case "operator.greaterOrEquals":
                    if (modelValue instanceof Number) {
                        return ((Number) modelValue).doubleValue() >= Double.parseDouble(filterText);
                    } break;
                case "operator.lessOrEquals":
                    if (modelValue instanceof Number) {
                        return ((Number) modelValue).doubleValue() <= Double.parseDouble(filterText);
                    } break;

                // Для дат (требует парсинга filterText в Date)
                case "operator.dateEquals":
                case "operator.dateBefore":
                case "operator.dateAfter":
                    if (modelValue instanceof Date) {
                        try {
                            Locale currentLocale = LocalisationManager.getLocale();
                            DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, currentLocale == null ? Locale.getDefault() : currentLocale);
                            Date filterDate = df.parse(filterText); // Пытаемся распарсить введенную дату
                            Date modelDate = (Date) modelValue;
                            if (operatorKey.equals("operator.dateEquals")) return modelDate.equals(filterDate); // Сравнение дат может быть сложным из-за времени
                            if (operatorKey.equals("operator.dateBefore")) return modelDate.before(filterDate);
                            if (operatorKey.equals("operator.dateAfter")) return modelDate.after(filterDate);
                        } catch (ParseException e) { return false; /* Некорректный формат даты в фильтре */ }
                    } break;

                // Для Boolean (уже обработано выше, но можно добавить здесь для полноты)
                case "operator.isTrue":
                    return modelValue instanceof Boolean && (Boolean)modelValue;
                case "operator.isFalse":
                    return modelValue instanceof Boolean && !(Boolean)modelValue;

                default: return false; // Неизвестный оператор
            }
        } catch (NumberFormatException nfe) {
            return false; // Ошибка парсинга числа из filterText
        }
        return false; // Если тип не совпал или оператор не обработан
    }
}