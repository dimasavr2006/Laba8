package gui.dialogs;

import classes.Car;
import classes.Coordinates;
import classes.HumanBeing;
import enums.Mood;
import enums.WeaponType;
import gui.LocalizedEnumComboBoxModel;
import gui.renderers.LocalizedEnumRenderer;
import managers.LocalisationManager;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;

public class HumanBeingDialog extends JDialog implements PropertyChangeListener {

    // Поля для ввода данных
    private JTextField nameField;
    private JTextField coordXField;
    private JTextField coordYField;
    private JCheckBox realHeroCheckBox;
    private JCheckBox hasToothpickCheckBox;
    private JTextField impactSpeedField;
    private JTextField soundtrackNameField;
    private JComboBox<WeaponType> weaponTypeComboBox;
    private JComboBox<Mood> moodComboBox;
    private JTextField carNameField;
    private JCheckBox carCoolCheckBox;

    // Метки для полей
    private JLabel nameLabel, coordXLabel, coordYLabel, realHeroLabel, hasToothpickLabel,
            impactSpeedLabel, soundtrackNameLabel, weaponTypeLabel, moodLabel,
            carNameLabel, carCoolLabel;

    // Кнопки
    private JButton saveButton;
    private JButton cancelButton;

    private HumanBeing humanBeing; // Редактируемый или новый объект
    private boolean saved = false;   // Флаг, успешно ли сохранен диалог

    // Локализованные представления для Enum в JComboBox
    private LocalizedEnumComboBoxModel<WeaponType> weaponTypeModel;
    private LocalizedEnumComboBoxModel<Mood> moodModel;


    /**
     * Конструктор для диалога.
     * @param parent Родительское окно.
     * @param humanBeingToEdit Объект для редактирования (null если создается новый).
     */
    public HumanBeingDialog(Frame parent, HumanBeing humanBeingToEdit) {
        super(parent, true); // Модальный диалог

        this.humanBeing = (humanBeingToEdit == null) ? new HumanBeing() : humanBeingToEdit;
        // Если редактируем, то this.humanBeing будет ссылкой на объект из коллекции.
        // Чтобы избежать изменения оригинала до нажатия "Сохранить", лучше работать с копией.
        // Но для простоты пока оставим так, при сохранении будем создавать новый объект или обновлять поля.
        // Правильнее: this.humanBeing = (humanBeingToEdit == null) ? new HumanBeing() : createEditableCopy(humanBeingToEdit);

        LocalisationManager.addPropertyChangeListener(this);

        initComponents();
        if (humanBeingToEdit != null) {
            fillFields(humanBeingToEdit); // Заполняем поля, если редактируем
        }
        updateTexts(); // Устанавливаем локализованные тексты
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        // Используем GridBagLayout для гибкого расположения
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        int gridY = 0;

        // Name
        nameLabel = new JLabel();
        gbc.gridx = 0; gbc.gridy = gridY; add(nameLabel, gbc);
        nameField = new JTextField(25);
        gbc.gridx = 1; gbc.gridy = gridY; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL; add(nameField, gbc);
        gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE; // Сброс
        gridY++;

        // Coordinate X
        coordXLabel = new JLabel();
        gbc.gridx = 0; gbc.gridy = gridY; add(coordXLabel, gbc);
        coordXField = new JTextField(10);
        gbc.gridx = 1; gbc.gridy = gridY; add(coordXField, gbc);
        gridY++;

        // Coordinate Y
        coordYLabel = new JLabel();
        gbc.gridx = 0; gbc.gridy = gridY; add(coordYLabel, gbc);
        coordYField = new JTextField(10);
        gbc.gridx = 1; gbc.gridy = gridY; add(coordYField, gbc);
        gridY++;

        // Real Hero
        realHeroLabel = new JLabel();
        gbc.gridx = 0; gbc.gridy = gridY; add(realHeroLabel, gbc);
        realHeroCheckBox = new JCheckBox();
        gbc.gridx = 1; gbc.gridy = gridY; add(realHeroCheckBox, gbc);
        gridY++;

        // Has Toothpick
        hasToothpickLabel = new JLabel();
        gbc.gridx = 0; gbc.gridy = gridY; add(hasToothpickLabel, gbc);
        hasToothpickCheckBox = new JCheckBox();
        gbc.gridx = 1; gbc.gridy = gridY; add(hasToothpickCheckBox, gbc);
        gridY++;

        // Impact Speed
        impactSpeedLabel = new JLabel();
        gbc.gridx = 0; gbc.gridy = gridY; add(impactSpeedLabel, gbc);
        impactSpeedField = new JTextField(10);
        gbc.gridx = 1; gbc.gridy = gridY; add(impactSpeedField, gbc);
        gridY++;

        // Soundtrack Name
        soundtrackNameLabel = new JLabel();
        gbc.gridx = 0; gbc.gridy = gridY; add(soundtrackNameLabel, gbc);
        soundtrackNameField = new JTextField(25);
        gbc.gridx = 1; gbc.gridy = gridY; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL; add(soundtrackNameField, gbc);
        gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
        gridY++;

        weaponTypeLabel = new JLabel();
        gbc.gridx = 0; gbc.gridy = gridY; add(weaponTypeLabel, gbc);
        // Модель теперь ComboBoxModel<WeaponType>
        LocalizedEnumComboBoxModel<WeaponType> weaponTypeModel = new LocalizedEnumComboBoxModel<>(WeaponType.values());
        weaponTypeComboBox = new JComboBox<>(weaponTypeModel);
        // Устанавливаем рендерер для отображения локализованных имен
        weaponTypeComboBox.setRenderer(new LocalizedEnumRenderer<>("enum.weaponType."));
        gbc.gridx = 1; gbc.gridy = gridY; gbc.fill = GridBagConstraints.HORIZONTAL; add(weaponTypeComboBox, gbc);
        gbc.fill = GridBagConstraints.NONE;
        gridY++;

        // Mood
        moodLabel = new JLabel();
        gbc.gridx = 0; gbc.gridy = gridY; add(moodLabel, gbc);
        LocalizedEnumComboBoxModel<Mood> moodModel = new LocalizedEnumComboBoxModel<>(Mood.values());
        moodComboBox = new JComboBox<>(moodModel);
        moodComboBox.setRenderer(new LocalizedEnumRenderer<>("enum.mood."));
        gbc.gridx = 1; gbc.gridy = gridY; gbc.fill = GridBagConstraints.HORIZONTAL; add(moodComboBox, gbc);
        gbc.fill = GridBagConstraints.NONE;
        gridY++;

        // Car Name
        carNameLabel = new JLabel();
        gbc.gridx = 0; gbc.gridy = gridY; add(carNameLabel, gbc);
        carNameField = new JTextField(25);
        gbc.gridx = 1; gbc.gridy = gridY; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL; add(carNameField, gbc);
        gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
        gridY++;

        // Car Cool
        carCoolLabel = new JLabel();
        gbc.gridx = 0; gbc.gridy = gridY; add(carCoolLabel, gbc);
        carCoolCheckBox = new JCheckBox();
        gbc.gridx = 1; gbc.gridy = gridY; add(carCoolCheckBox, gbc);
        gridY++;

        // Панель для кнопок
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        saveButton = new JButton();
        cancelButton = new JButton();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0; gbc.gridy = gridY; gbc.gridwidth = 3; gbc.anchor = GridBagConstraints.EAST;
        add(buttonPanel, gbc);

        // Обработчики кнопок
        saveButton.addActionListener(e -> onSave());
        cancelButton.addActionListener(e -> onCancel());
    }

    // Заполнение полей данными из объекта (при редактировании)
    private void fillFields(HumanBeing hb) {
        nameField.setText(hb.getName());
        if (hb.getCoordinates() != null) {
            coordXField.setText(String.valueOf(hb.getCoordinates().getX()));
            coordYField.setText(String.valueOf(hb.getCoordinates().getY()));
        }
        realHeroCheckBox.setSelected(hb.isRealHero());
        hasToothpickCheckBox.setSelected(hb.isHasToothpick());
        if (hb.getImpactSpeed() != null) {
            impactSpeedField.setText(String.valueOf(hb.getImpactSpeed()));
        }
        soundtrackNameField.setText(hb.getSoundtrackName());
        weaponTypeComboBox.setSelectedItem(hb.getWeaponType()); // Модель сама найдет нужный объект
        weaponTypeComboBox.setSelectedItem(hb.getWeaponType()); // Модель сама найдет нужный объект
        moodComboBox.setSelectedItem(hb.getMood());             // Модель сама найдет нужный объект
        if (hb.getCar() != null) {
            carNameField.setText(hb.getCar().getName());
            carCoolCheckBox.setSelected(hb.getCar().isCool());
        }
    }

    // Обновление текстов меток и кнопок при смене локали
    private void updateTexts() {
        setTitle( (humanBeing != null && humanBeing.getId() > 0) ? // Проверка на новый объект (id обычно >0 для существующих)
                LocalisationManager.getString("dialog.addEdit.title.edit") :
                LocalisationManager.getString("dialog.addEdit.title.add") );

        nameLabel.setText(LocalisationManager.getString("dialog.addEdit.label.name"));
        coordXLabel.setText(LocalisationManager.getString("dialog.addEdit.label.coordinateX"));
        coordYLabel.setText(LocalisationManager.getString("dialog.addEdit.label.coordinateY"));
        realHeroLabel.setText(LocalisationManager.getString("dialog.addEdit.label.realHero")); // Ключ для JCheckBox
        hasToothpickLabel.setText(LocalisationManager.getString("dialog.addEdit.label.hasToothpick")); // Ключ для JCheckBox
        impactSpeedLabel.setText(LocalisationManager.getString("dialog.addEdit.label.impactSpeed"));
        soundtrackNameLabel.setText(LocalisationManager.getString("dialog.addEdit.label.soundtrackName"));
        weaponTypeLabel.setText(LocalisationManager.getString("dialog.addEdit.label.weaponType"));
        moodLabel.setText(LocalisationManager.getString("dialog.addEdit.label.mood"));
        carNameLabel.setText(LocalisationManager.getString("dialog.addEdit.label.carName"));
        carCoolLabel.setText(LocalisationManager.getString("dialog.addEdit.label.carCool")); // Ключ для JCheckBox

        saveButton.setText(LocalisationManager.getString("dialog.addEdit.button.save"));
        cancelButton.setText(LocalisationManager.getString("dialog.addEdit.button.cancel"));

        // Обновляем модели JComboBox, чтобы они перерисовали свои элементы с новой локалью
        if (weaponTypeComboBox != null) weaponTypeComboBox.repaint();
        if (moodComboBox != null) moodComboBox.repaint();
    }


    private void onSave() {
        if (!validateInput()) {
            return;
        }

        // Создаем новый объект или обновляем существующий
        // Здесь мы обновляем поля переданного объекта humanBeing.
        // Если это новый объект, он уже был создан как new HumanBeing().
        // Если это редактирование, мы меняем поля копии или оригинала (в зависимости от реализации).
        // Для простоты сейчас будем считать, что мы можем модифицировать humanBeing.

        humanBeing.setName(nameField.getText().trim());
        try {
            long x = Long.parseLong(coordXField.getText().trim());
            long y = Long.parseLong(coordYField.getText().trim());
            humanBeing.setCoordinates(new Coordinates(x, y));
        } catch (NumberFormatException e) { /* Валидация должна была это поймать */ }

        humanBeing.setRealHero(realHeroCheckBox.isSelected());
        humanBeing.setHasToothpick(hasToothpickCheckBox.isSelected());

        String impactSpeedStr = impactSpeedField.getText().trim();
        humanBeing.setImpactSpeed(impactSpeedStr.isEmpty() ? null : Long.parseLong(impactSpeedStr));

        humanBeing.setSoundtrackName(soundtrackNameField.getText().trim());
        humanBeing.setWeaponType((WeaponType) weaponTypeComboBox.getSelectedItem());
        humanBeing.setMood((Mood) moodComboBox.getSelectedItem());

        humanBeing.setCar(new Car(carNameField.getText().trim(), carCoolCheckBox.isSelected()));

        // Если это был новый объект, ему еще нужно присвоить ID (обычно это делает БД)
        // и creationDate (обычно при создании объекта или в БД)
        if (humanBeing.getCreationDate() == null) { // Если новый объект
            humanBeing.setCreationDate(new Date()); // Устанавливаем текущую дату
        }


        saved = true;
        dispose();
    }

    private void onCancel() {
        saved = false;
        dispose();
    }

    private boolean validateInput() {
        // Имя не должно быть пустым
        if (nameField.getText().trim().isEmpty()) {
            showValidationError("dialog.addEdit.validation.nameEmpty");
            return false;
        }
        // Координаты X и Y должны быть числами (long)
        try {
            Long.parseLong(coordXField.getText().trim());
        } catch (NumberFormatException e) {
            showValidationError("dialog.addEdit.validation.coordinateXInvalid");
            return false;
        }
        try {
            Long.parseLong(coordYField.getText().trim());
        } catch (NumberFormatException e) {
            showValidationError("dialog.addEdit.validation.coordinateYInvalid");
            return false;
        }
        // ImpactSpeed: если не пусто, должно быть числом (Long)
        String impactSpeedStr = impactSpeedField.getText().trim();
        if (!impactSpeedStr.isEmpty()) {
            try {
                Long.parseLong(impactSpeedStr);
            } catch (NumberFormatException e) {
                showValidationError("dialog.addEdit.validation.impactSpeedInvalid");
                return false;
            }
        }
        // Название саундтрека не должно быть пустым
        if (soundtrackNameField.getText().trim().isEmpty()) {
            showValidationError("dialog.addEdit.validation.soundtrackEmpty");
            return false;
        }
        // Имя машины не должно быть пустым
        if (carNameField.getText().trim().isEmpty()) {
            showValidationError("dialog.addEdit.validation.carNameEmpty");
            return false;
        }
        // WeaponType и Mood выбираются из ComboBox, обычно не требуют спец. валидации, если только не "null" опция
        if (weaponTypeComboBox.getSelectedItem() == null) {
            // Показать ошибку, если тип оружия обязателен
            showValidationError("dialog.addEdit.validation.weaponTypeRequired"); // Пример ключа
            return false;
        }
        if (moodComboBox.getSelectedItem() == null) {
            // Показать ошибку, если настроение обязательно
            showValidationError("dialog.addEdit.validation.moodRequired"); // Пример ключа
            return false;
        }


        return true;
    }

    private void showValidationError(String messageKey) {
        JOptionPane.showMessageDialog(this,
                LocalisationManager.getString(messageKey),
                LocalisationManager.getString("dialog.title.error"),
                JOptionPane.ERROR_MESSAGE);
    }

    public HumanBeing getHumanBeing() {
        return saved ? humanBeing : null;
    }

    public boolean isSaved() {
        return saved;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (LocalisationManager.LOCALE_CHANGED_PROPERTY.equals(evt.getPropertyName())) {
            SwingUtilities.invokeLater(this::updateTexts);
        }
    }

    @Override
    public void dispose() {
        LocalisationManager.removePropertyChangeListener(this);
        super.dispose();
    }
}