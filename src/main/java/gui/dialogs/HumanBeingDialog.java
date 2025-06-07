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

    private JLabel nameLabel;
    private JLabel coordXLabel;
    private JLabel coordYLabel;
    private JLabel realHeroLabel;
    private JLabel hasToothpickLabel;
    private JLabel impactSpeedLabel;
    private JLabel soundtrackNameLabel;
    private JLabel weaponTypeLabel;
    private JLabel moodLabel;
    private JLabel carNameLabel;
    private JLabel carCoolLabel;;

    private JButton saveButton;
    private JButton cancelButton;

    private HumanBeing humanBeing;
    private boolean saved = false;

    private LocalizedEnumComboBoxModel<WeaponType> weaponTypeModel;
    private LocalizedEnumComboBoxModel<Mood> moodModel;

    public HumanBeingDialog(Frame parent, HumanBeing humanBeingToEdit) {
        super(parent, true);

        this.humanBeing = (humanBeingToEdit == null) ? new HumanBeing() : humanBeingToEdit;

        LocalisationManager.addPropertyChangeListener(this);

        initComponents();
        if (humanBeingToEdit != null) {
            fillFields(humanBeingToEdit);
        }
        updateTexts();
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        int gridY = 0;

        nameLabel = new JLabel();
        gbc.gridx = 0; gbc.gridy = gridY; add(nameLabel, gbc);
        nameField = new JTextField(25);
        gbc.gridx = 1; gbc.gridy = gridY; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL; add(nameField, gbc);
        gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE; // Сброс
        gridY++;


        coordXLabel = new JLabel();
        gbc.gridx = 0; gbc.gridy = gridY; add(coordXLabel, gbc);
        coordXField = new JTextField(10);
        gbc.gridx = 1; gbc.gridy = gridY; add(coordXField, gbc);
        gridY++;

        coordYLabel = new JLabel();
        gbc.gridx = 0; gbc.gridy = gridY; add(coordYLabel, gbc);
        coordYField = new JTextField(10);
        gbc.gridx = 1; gbc.gridy = gridY; add(coordYField, gbc);
        gridY++;

        realHeroLabel = new JLabel();
        gbc.gridx = 0; gbc.gridy = gridY; add(realHeroLabel, gbc);
        realHeroCheckBox = new JCheckBox();
        gbc.gridx = 1; gbc.gridy = gridY; add(realHeroCheckBox, gbc);
        gridY++;

        hasToothpickLabel = new JLabel();
        gbc.gridx = 0; gbc.gridy = gridY; add(hasToothpickLabel, gbc);
        hasToothpickCheckBox = new JCheckBox();
        gbc.gridx = 1; gbc.gridy = gridY; add(hasToothpickCheckBox, gbc);
        gridY++;

        impactSpeedLabel = new JLabel();
        gbc.gridx = 0; gbc.gridy = gridY; add(impactSpeedLabel, gbc);
        impactSpeedField = new JTextField(10);
        gbc.gridx = 1; gbc.gridy = gridY; add(impactSpeedField, gbc);
        gridY++;

        soundtrackNameLabel = new JLabel();
        gbc.gridx = 0; gbc.gridy = gridY; add(soundtrackNameLabel, gbc);
        soundtrackNameField = new JTextField(25);
        gbc.gridx = 1; gbc.gridy = gridY; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL; add(soundtrackNameField, gbc);
        gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
        gridY++;

        weaponTypeLabel = new JLabel();
        gbc.gridx = 0; gbc.gridy = gridY; add(weaponTypeLabel, gbc);
        LocalizedEnumComboBoxModel<WeaponType> weaponTypeModel = new LocalizedEnumComboBoxModel<>(WeaponType.values());
        weaponTypeComboBox = new JComboBox<>(weaponTypeModel);
        weaponTypeComboBox.setRenderer(new LocalizedEnumRenderer<>("enum.weaponType."));
        gbc.gridx = 1; gbc.gridy = gridY; gbc.fill = GridBagConstraints.HORIZONTAL; add(weaponTypeComboBox, gbc);
        gbc.fill = GridBagConstraints.NONE;
        gridY++;

        moodLabel = new JLabel();
        gbc.gridx = 0; gbc.gridy = gridY; add(moodLabel, gbc);
        LocalizedEnumComboBoxModel<Mood> moodModel = new LocalizedEnumComboBoxModel<>(Mood.values());
        moodComboBox = new JComboBox<>(moodModel);
        moodComboBox.setRenderer(new LocalizedEnumRenderer<>("enum.mood."));
        gbc.gridx = 1; gbc.gridy = gridY; gbc.fill = GridBagConstraints.HORIZONTAL; add(moodComboBox, gbc);
        gbc.fill = GridBagConstraints.NONE;
        gridY++;

        carNameLabel = new JLabel();
        gbc.gridx = 0; gbc.gridy = gridY; add(carNameLabel, gbc);
        carNameField = new JTextField(25);
        gbc.gridx = 1; gbc.gridy = gridY; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL; add(carNameField, gbc);
        gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
        gridY++;

        carCoolLabel = new JLabel();
        gbc.gridx = 0; gbc.gridy = gridY; add(carCoolLabel, gbc);
        carCoolCheckBox = new JCheckBox();
        gbc.gridx = 1; gbc.gridy = gridY; add(carCoolCheckBox, gbc);
        gridY++;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        saveButton = new JButton();
        cancelButton = new JButton();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0; gbc.gridy = gridY; gbc.gridwidth = 3; gbc.anchor = GridBagConstraints.EAST;
        add(buttonPanel, gbc);

        saveButton.addActionListener(e -> {
            onSave();
        });
        cancelButton.addActionListener(e -> onCancel());
    }

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
        weaponTypeComboBox.setSelectedItem(hb.getWeaponType());
        weaponTypeComboBox.setSelectedItem(hb.getWeaponType());
        moodComboBox.setSelectedItem(hb.getMood());
        if (hb.getCar() != null) {
            carNameField.setText(hb.getCar().getName());
            carCoolCheckBox.setSelected(hb.getCar().isCool());
        }
    }

    private void updateTexts() {
        setTitle( (humanBeing != null && humanBeing.getId() > 0) ?
                LocalisationManager.getString("dialog.addEdit.title.edit") :
                LocalisationManager.getString("dialog.addEdit.title.add") );

        nameLabel.setText(LocalisationManager.getString("dialog.addEdit.label.name"));
        coordXLabel.setText(LocalisationManager.getString("dialog.addEdit.label.coordinateX"));
        coordYLabel.setText(LocalisationManager.getString("dialog.addEdit.label.coordinateY"));
        realHeroLabel.setText(LocalisationManager.getString("dialog.addEdit.label.realHero"));
        hasToothpickLabel.setText(LocalisationManager.getString("dialog.addEdit.label.hasToothpick"));
        impactSpeedLabel.setText(LocalisationManager.getString("dialog.addEdit.label.impactSpeed"));
        soundtrackNameLabel.setText(LocalisationManager.getString("dialog.addEdit.label.soundtrackName"));
        weaponTypeLabel.setText(LocalisationManager.getString("dialog.addEdit.label.weaponType"));
        moodLabel.setText(LocalisationManager.getString("dialog.addEdit.label.mood"));
        carNameLabel.setText(LocalisationManager.getString("dialog.addEdit.label.carName"));
        carCoolLabel.setText(LocalisationManager.getString("dialog.addEdit.label.carCool"));

        saveButton.setText(LocalisationManager.getString("dialog.addEdit.button.save"));
        cancelButton.setText(LocalisationManager.getString("dialog.addEdit.button.cancel"));

        if (weaponTypeComboBox != null) weaponTypeComboBox.repaint();
        if (moodComboBox != null) moodComboBox.repaint();
    }


    private void onSave() {
        if (!validateInput()) {
            return;
        }

        humanBeing.setName(nameField.getText().trim());
        try {
            long x = Long.parseLong(coordXField.getText().trim());
            long y = Long.parseLong(coordYField.getText().trim());
            humanBeing.setCoordinates(new Coordinates(x, y));
        } catch (NumberFormatException e) {

        }

        humanBeing.setRealHero(realHeroCheckBox.isSelected());
        humanBeing.setHasToothpick(hasToothpickCheckBox.isSelected());

        String impactSpeedStr = impactSpeedField.getText().trim();
        humanBeing.setImpactSpeed(impactSpeedStr.isEmpty() ? null : Long.parseLong(impactSpeedStr));

        humanBeing.setSoundtrackName(soundtrackNameField.getText().trim());
        humanBeing.setWeaponType((WeaponType) weaponTypeComboBox.getSelectedItem());
        humanBeing.setMood((Mood) moodComboBox.getSelectedItem());

        humanBeing.setCar(new Car(carNameField.getText().trim(), carCoolCheckBox.isSelected()));

        if (humanBeing.getCreationDate() == null) {
            humanBeing.setCreationDate(new Date());
        }


        saved = true;
        dispose();
    }

    private void onCancel() {
        saved = false;
        dispose();
    }

    private boolean validateInput() {
        if (nameField.getText().trim().isEmpty()) {
            showValidationError("dialog.addEdit.validation.nameEmpty");
            return false;
        }
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
        String impactSpeedStr = impactSpeedField.getText().trim();
        if (!impactSpeedStr.isEmpty()) {
            try {
                Long.parseLong(impactSpeedStr);
            } catch (NumberFormatException e) {
                showValidationError("dialog.addEdit.validation.impactSpeedInvalid");
                return false;
            }
        }
        if (soundtrackNameField.getText().trim().isEmpty()) {
            showValidationError("dialog.addEdit.validation.soundtrackEmpty");
            return false;
        }
        if (carNameField.getText().trim().isEmpty()) {
            showValidationError("dialog.addEdit.validation.carNameEmpty");
            return false;
        }
        if (weaponTypeComboBox.getSelectedItem() == null) {
            showValidationError("dialog.addEdit.validation.weaponTypeRequired"); // Пример ключа
            return false;
        }
        if (moodComboBox.getSelectedItem() == null) {
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