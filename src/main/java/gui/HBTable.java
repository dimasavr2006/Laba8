package gui;

import classes.HumanBeing;
import enums.Mood;
import enums.WeaponType;
import managers.CollectionManager;
import managers.LocalisationManager;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HBTable extends AbstractTableModel {

    private List<HumanBeing> hbList;
    private final String[] columnKeys = {
            "table.column.id", "table.column.name", "table.column.coordinateX", "table.column.coordinateY",
            "table.column.creationDate", "table.column.realHero", "table.column.hasToothpick",
            "table.column.impactSpeed", "table.column.soundtrackName", "table.column.weaponType",
            "table.column.mood", "table.column.carName", "table.column.carCool", "table.column.ownerId"
    };
    private final Class<?>[] columnClasses = {
            Integer.class,
            String.class,
            Long.class,
            Long.class,
            Date.class,
            Boolean.class,
            Boolean.class,
            Long.class,
            String.class,
            WeaponType.class,
            Mood.class,
            String.class,
            Boolean.class,
            Integer.class
    };


    public HBTable() {
        this.hbList = new ArrayList<>();
        if (CollectionManager.collection != null) {
            this.hbList.addAll(CollectionManager.collection);
        }
    }

    public void updateData(List<HumanBeing> newData) {
        if (newData != null) {
            this.hbList = new ArrayList<>(newData);
        } else {
            this.hbList = new ArrayList<>();
        }
        fireTableDataChanged();
    }

    public HumanBeing getHumanBeingAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < hbList.size()) {
            return hbList.get(rowIndex);
        }
        return null;
    }

    @Override
    public int getRowCount() {
        return hbList.size();
    }

    @Override
    public int getColumnCount() {
        return columnKeys.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        if (columnIndex < 0 || columnIndex >= columnKeys.length) {
            return "";
        }
        return LocalisationManager.getString(columnKeys[columnIndex]);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex < 0 || columnIndex >= columnClasses.length) {
            return Object.class;
        }
        return columnClasses[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex < 0 || rowIndex >= hbList.size()) {
            return null;
        }
        HumanBeing human = hbList.get(rowIndex);
        if (human == null) return null;

        try {
            switch (columnIndex) {
                case 0: return human.getId();
                case 1: return human.getName();
                case 2: return human.getCoordinates() != null ? human.getCoordinates().getX() : null;
                case 3: return human.getCoordinates() != null ? human.getCoordinates().getY() : null;
                case 4: return human.getCreationDate(); // <-- ИЗМЕНЕНО: возвращаем сам объект Date
                case 5: return human.isRealHero();
                case 6: return human.isHasToothpick();
                case 7: return human.getImpactSpeed();
                case 8: return human.getSoundtrackName();
                case 9: return human.getWeaponType();
                case 10: return human.getMood();
                case 11: return human.getCar() != null ? human.getCar().getName() : LocalisationManager.getString("text.noCar");
                case 12: return human.getCar() != null ? human.getCar().isCool() : null;
                case 13: return human.getOwnerId();
                default: return null;
            }
        } catch (Exception e) {
            System.err.println("Ошибка в getValueAt для строки " + rowIndex + ", колонки " + columnIndex + ": " + e.getMessage());
            return "Error";
        }
    }


    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void localeChanged() {
        fireTableStructureChanged();
    }

    public String[] getColumnHeaderKeys() {
        return columnKeys;
    }

}