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
    private String operatorKey;
    private HBTable tableModel;

    public ColumnRowFilter(String filterText, int columnIndex, String operatorKey, HBTable model) {
        this.filterText = filterText.trim();
        this.columnIndex = columnIndex;
        this.operatorKey = operatorKey;
        this.tableModel = model;
    }

    @Override
    public boolean include(Entry<? extends HBTable, ? extends Integer> entry) {
        HumanBeing human = tableModel.getHumanBeingAt(entry.getIdentifier());
        if (human == null) return false;

        Object modelValue = entry.getValue(columnIndex);

        if (modelValue instanceof Boolean) {
            if (operatorKey.equals("operator.isTrue")) return (Boolean) modelValue;
            if (operatorKey.equals("operator.isFalse")) return !(Boolean) modelValue;
        }

        if (filterText.isEmpty() && !(modelValue instanceof Boolean && (operatorKey.equals("operator.isTrue") || operatorKey.equals("operator.isFalse")))) {
            return false;
        }

        if (modelValue == null) {
            return operatorKey.equals("operator.notEquals") && !filterText.isEmpty();
        }

        String modelValueString = String.valueOf(modelValue).toLowerCase();
        String filterTextLower = filterText.toLowerCase();

        try {
            switch (operatorKey) {
                case "operator.contains":
                    return Stream.of(modelValueString).anyMatch(s -> s.contains(filterTextLower));
                case "operator.equals":
                case "operator.equalsNum":
                case "operator.equalsEnum":
                    return Stream.of(modelValueString).anyMatch(s -> s.equals(filterTextLower));
                case "operator.notEquals":
                case "operator.notEqualsNum":
                    return Stream.of(modelValueString).noneMatch(s -> s.equals(filterTextLower)); // или !anyMatch
                case "operator.startsWith":
                    return Stream.of(modelValueString).anyMatch(s -> s.startsWith(filterTextLower));
                case "operator.endsWith":
                    return Stream.of(modelValueString).anyMatch(s -> s.endsWith(filterTextLower));

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

                case "operator.dateEquals":
                case "operator.dateBefore":
                case "operator.dateAfter":
                    if (modelValue instanceof Date) {
                        try {
                            Locale currentLocale = LocalisationManager.getLocale();
                            DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, currentLocale == null ? Locale.getDefault() : currentLocale);
                            Date filterDate = df.parse(filterText);
                            Date modelDate = (Date) modelValue;
                            if (operatorKey.equals("operator.dateEquals")) return modelDate.equals(filterDate);
                            if (operatorKey.equals("operator.dateBefore")) return modelDate.before(filterDate);
                            if (operatorKey.equals("operator.dateAfter")) return modelDate.after(filterDate);
                        } catch (ParseException e) {
                            return false;
                        }
                    } break;

                case "operator.isTrue":
                    return modelValue instanceof Boolean && (Boolean)modelValue;
                case "operator.isFalse":
                    return modelValue instanceof Boolean && !(Boolean)modelValue;

                default: return false;
            }
        } catch (NumberFormatException nfe) {
            return false;
        }
        return false;
    }
}