package gui.filter; // Твой пакет

import classes.HumanBeing;
import gui.HBTable;

import javax.swing.*;
import java.util.stream.Stream;

public class ColumnRowFilter extends RowFilter<HBTable, Integer> {
    private String searchText;
    private int columnIndexToFilter; // Эта переменная теперь будет использоваться
    private HBTable tableModel;

    public ColumnRowFilter(String searchText, int columnIndexToFilter, HBTable model) {
        this.searchText = searchText.toLowerCase().trim();
        this.columnIndexToFilter = columnIndexToFilter;
        this.tableModel = model;
    }

    @Override
    public boolean include(Entry<? extends HBTable, ? extends Integer> entry) {
        if (searchText.isEmpty()) {
            return true;
        }

        HumanBeing human = tableModel.getHumanBeingAt(entry.getIdentifier());
        if (human == null) {
            return false;
        }

        Object valueFromColumn;

        // Получаем значение ТОЛЬКО для указанной columnIndexToFilter
        // Важно: Model getValueAt возвращает "сырые" данные (Date, Enum, etc.)
        // Мы должны привести их к строке для текстового поиска.
        try {
            // Используем метод TableModel для получения значения, которое будет отсортировано/отфильтровано
            // entry.getValue(columnIndexToFilter) - это значение из модели для этой колонки
            valueFromColumn = entry.getValue(columnIndexToFilter);

        } catch (IndexOutOfBoundsException e) {
            System.err.println("Неверный индекс колонки в ColumnRowFilter: " + columnIndexToFilter);
            return false; // Не включать, если индекс колонки неверный
        }


        if (valueFromColumn == null) {
            return false;
        }

        // Используем Stream API, как требовалось
        return Stream.of(String.valueOf(valueFromColumn).toLowerCase())
                .anyMatch(fieldValue -> fieldValue.contains(searchText));
    }
}