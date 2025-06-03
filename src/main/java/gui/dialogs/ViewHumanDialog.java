package gui.dialogs;

import classes.HumanBeing;
import gui.MainWindow;
import managers.CollectionManager;
import managers.LocalisationManager;
import run.Main;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.util.Locale;

public class ViewHumanDialog extends JDialog {

    // Метки для отображения данных
    private JLabel idValLabel, nameValLabel, coordXValLabel, coordYValLabel, creationDateValLabel,
            realHeroValLabel, hasToothpickValLabel, impactSpeedValLabel, soundtrackNameValLabel,
            weaponTypeValLabel, moodValLabel, carNameValLabel, carCoolValLabel, ownerIdValLabel;

    // Метки для названий полей (будут локализованы)
    private JLabel idLabel, nameLabel, coordXLabelText, coordYLabelText, creationDateLabelText,
            realHeroLabelText, hasToothpickLabelText, impactSpeedLabelText, soundtrackNameLabelText,
            weaponTypeLabelText, moodLabelText, carNameLabelText, carCoolLabelText, ownerIdLabelText;


    private JButton closeButton, editButton, deleteButton;

    private Frame ownerFrame;

    private HumanBeing humanBeingToView;
    private DateFormat dateFormat;

    public ViewHumanDialog(Frame parent, HumanBeing humanBeing) {
        super(parent, LocalisationManager.getString("visualization.objectInfo.title"), false); // Немодальный
        this.ownerFrame = parent; // Сохраняем MainWindow
        this.humanBeingToView = humanBeing;
        // ... (LocalisationManager.addPropertyChangeListener(this); - если нужно обновление открытого диалога)
        initComponents();
        populateFields();
        updateTexts();
        pack();
        setMinimumSize(new Dimension(400, (int)getPreferredSize().getHeight()));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        int gridY = 0;

        // Инициализация всех меток-названий
        idLabel = new JLabel(); coordXLabelText = new JLabel(); nameLabel = new JLabel();
        coordYLabelText = new JLabel(); creationDateLabelText = new JLabel(); realHeroLabelText = new JLabel();
        hasToothpickLabelText = new JLabel(); impactSpeedLabelText = new JLabel(); soundtrackNameLabelText = new JLabel();
        weaponTypeLabelText = new JLabel(); moodLabelText = new JLabel(); carNameLabelText = new JLabel();
        carCoolLabelText = new JLabel(); ownerIdLabelText = new JLabel();

        // Инициализация всех меток-значений
        idValLabel = new JLabel(); nameValLabel = new JLabel(); coordXValLabel = new JLabel();
        coordYValLabel = new JLabel(); creationDateValLabel = new JLabel(); realHeroValLabel = new JLabel();
        hasToothpickValLabel = new JLabel(); impactSpeedValLabel = new JLabel(); soundtrackNameValLabel = new JLabel();
        weaponTypeValLabel = new JLabel(); moodValLabel = new JLabel(); carNameValLabel = new JLabel();
        carCoolValLabel = new JLabel(); ownerIdValLabel = new JLabel();

        // Добавляем пары метка-значение
        addField(idLabel, idValLabel, gridY++, gbc);
        addField(nameLabel, nameValLabel, gridY++, gbc);
        addField(coordXLabelText, coordXValLabel, gridY++, gbc);
        addField(coordYLabelText, coordYValLabel, gridY++, gbc);
        addField(creationDateLabelText, creationDateValLabel, gridY++, gbc);
        addField(realHeroLabelText, realHeroValLabel, gridY++, gbc);
        addField(hasToothpickLabelText, hasToothpickValLabel, gridY++, gbc);
        addField(impactSpeedLabelText, impactSpeedValLabel, gridY++, gbc);
        addField(soundtrackNameLabelText, soundtrackNameValLabel, gridY++, gbc);
        addField(weaponTypeLabelText, weaponTypeValLabel, gridY++, gbc);
        addField(moodLabelText, moodValLabel, gridY++, gbc);
        addField(carNameLabelText, carNameValLabel, gridY++, gbc);
        addField(carCoolLabelText, carCoolValLabel, gridY++, gbc);
        addField(ownerIdLabelText, ownerIdValLabel, gridY++, gbc);


        // Кнопки
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        closeButton = new JButton(); // Текст из LocalisationManager
        editButton = new JButton();
        deleteButton = new JButton();

        closeButton.addActionListener(e -> dispose());
        editButton.addActionListener(e -> onEdit());
        deleteButton.addActionListener(e -> onDelete());
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(closeButton);

        gbc.gridx = 0; gbc.gridy = gridY; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(buttonPanel, gbc);
    }

    private void addField(JLabel labelComponent, JLabel valueComponent, int gridY, GridBagConstraints gbc) {
        gbc.gridx = 0; gbc.gridy = gridY; gbc.weightx = 0.3; gbc.fill = GridBagConstraints.NONE;
        add(labelComponent, gbc);
        gbc.gridx = 1; gbc.gridy = gridY; gbc.weightx = 0.7; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(valueComponent, gbc);
    }


    private void populateFields() {
        if (humanBeingToView == null) return;

        Locale currentLocale = LocalisationManager.getLocale();
        if (currentLocale == null) currentLocale = new Locale("ru", "RU");
        dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, currentLocale);

        idValLabel.setText(String.valueOf(humanBeingToView.getId()));
        nameValLabel.setText(humanBeingToView.getName());
        nameValLabel.setText(humanBeingToView.getName());
        if (humanBeingToView.getCoordinates() != null) {
            coordXValLabel.setText(String.valueOf(humanBeingToView.getCoordinates().getX()));
            coordYValLabel.setText(String.valueOf(humanBeingToView.getCoordinates().getY()));
        } else {
            coordXValLabel.setText("N/A"); coordYValLabel.setText("N/A");
        }
        creationDateValLabel.setText(humanBeingToView.getCreationDate() != null ? dateFormat.format(humanBeingToView.getCreationDate()) : "N/A");

        String yes = LocalisationManager.getString("button.yes"); // Предполагаем, что есть такие ключи
        String no = LocalisationManager.getString("button.no");
        realHeroValLabel.setText(humanBeingToView.isRealHero() ? yes : no);
        hasToothpickValLabel.setText(humanBeingToView.isHasToothpick() ? yes : no);

        impactSpeedValLabel.setText(humanBeingToView.getImpactSpeed() != null ? String.valueOf(humanBeingToView.getImpactSpeed()) : "N/A");
        soundtrackNameValLabel.setText(humanBeingToView.getSoundtrackName());

        weaponTypeValLabel.setText(humanBeingToView.getWeaponType() != null ? LocalisationManager.getString("enum.weaponType." + humanBeingToView.getWeaponType().name().toUpperCase()) : "N/A");
        moodValLabel.setText(humanBeingToView.getMood() != null ? LocalisationManager.getString("enum.mood." + humanBeingToView.getMood().name().toUpperCase()) : "N/A");

        if (humanBeingToView.getCar() != null) {
            carNameValLabel.setText(humanBeingToView.getCar().getName());
            carCoolValLabel.setText(humanBeingToView.getCar().isCool() ? yes : no);
        } else {
            carNameValLabel.setText("N/A"); carCoolValLabel.setText("N/A");
        }
        ownerIdValLabel.setText(String.valueOf(humanBeingToView.getOwnerId()));
    }

    private void updateTexts() {
        setTitle(LocalisationManager.getString("visualization.objectInfo.title")); // Общий заголовок для просмотра

        idLabel.setText(LocalisationManager.getString("visualization.objectInfo.field.id"));
        nameLabel.setText(LocalisationManager.getString("visualization.objectInfo.field.name"));
        coordXLabelText.setText(LocalisationManager.getString("visualization.objectInfo.field.coordinateX"));
        coordYLabelText.setText(LocalisationManager.getString("visualization.objectInfo.field.coordinateY"));
        creationDateLabelText.setText(LocalisationManager.getString("visualization.objectInfo.field.creationDate"));
        realHeroLabelText.setText(LocalisationManager.getString("visualization.objectInfo.field.realHero"));
        hasToothpickLabelText.setText(LocalisationManager.getString("visualization.objectInfo.field.hasToothpick"));
        impactSpeedLabelText.setText(LocalisationManager.getString("visualization.objectInfo.field.impactSpeed"));
        soundtrackNameLabelText.setText(LocalisationManager.getString("visualization.objectInfo.field.soundtrackName"));
        weaponTypeLabelText.setText(LocalisationManager.getString("visualization.objectInfo.field.weaponType"));
        moodLabelText.setText(LocalisationManager.getString("visualization.objectInfo.field.mood"));
        carNameLabelText.setText(LocalisationManager.getString("visualization.objectInfo.field.carName"));
        carCoolLabelText.setText(LocalisationManager.getString("visualization.objectInfo.field.carCool"));
        ownerIdLabelText.setText(LocalisationManager.getString("visualization.objectInfo.field.ownerId"));

        closeButton.setText(LocalisationManager.getString("button.close"));
         if(editButton != null) editButton.setText(LocalisationManager.getString("table.button.edit"));
         if(deleteButton != null) deleteButton.setText(LocalisationManager.getString("table.button.delete"));

        // Если данные уже загружены, обновить их отображение с новой локалью
        populateFields();
    }

    private void onEdit() {
        int currentUserId = Main.db.findUserIDbyUsername(Main.username);
        if (humanBeingToView.getOwnerId() == currentUserId) {
            // this.setVisible(false); // Можно не скрывать, HumanBeingDialog будет модальным поверх него

            HumanBeingDialog editDialog = new HumanBeingDialog(this.ownerFrame, humanBeingToView);
            editDialog.setVisible(true);

            if (editDialog.isSaved()) {
                HumanBeing updatedHuman = editDialog.getHumanBeing();
                boolean success = Main.db.updateID(humanBeingToView.getId(), updatedHuman, Main.username);
                if (success) {
                    if (ownerFrame instanceof MainWindow) {
                        MainWindow mw = (MainWindow) ownerFrame;
                        Main.cm.startCM(false);
                        mw.getHbTableModel().updateData(CollectionManager.collection);
                        mw.updateStatusBarInfo();
                    }
                    JOptionPane.showMessageDialog(ownerFrame, LocalisationManager.getString("dialog.message.objectUpdated"), LocalisationManager.getString("dialog.title.info"), JOptionPane.INFORMATION_MESSAGE);
                    dispose(); // Закрываем ViewHumanDialog, так как редактирование завершено
                } else {
                    JOptionPane.showMessageDialog(ownerFrame, LocalisationManager.getString("command.error.updateFailed"), LocalisationManager.getString("dialog.title.error"), JOptionPane.ERROR_MESSAGE);
                }
            }
            // Если редактирование отменено, ViewHumanDialog остается открытым (если не был скрыт)
        } else {
            JOptionPane.showMessageDialog(this, LocalisationManager.getString("dialog.addEdit.validation.notYourObject"), LocalisationManager.getString("dialog.title.error"), JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onDelete() {
        int currentUserId = Main.db.findUserIDbyUsername(Main.username); // Получаем ID текущего пользователя
        if (humanBeingToView.getOwnerId() == currentUserId) { // Проверяем, принадлежит ли объект текущему пользователю

            // Получаем локализованные тексты для диалога и кнопок
            String dialogTitle = LocalisationManager.getString("command.remove"); // Или "dialog.title.confirmation"
            String dialogMessage = LocalisationManager.getString("dialog.message.confirmDelete");
            String yesButtonText = LocalisationManager.getString("button.yes");
            String noButtonText = LocalisationManager.getString("button.no");

            // Fallback тексты на случай, если ключи не найдены
            if (dialogTitle.startsWith("?") || dialogTitle.startsWith("!")) dialogTitle = "Confirm Delete";
            if (dialogMessage.startsWith("?") || dialogMessage.startsWith("!")) dialogMessage = "Are you sure you want to delete the selected object?";
            if (yesButtonText.startsWith("?") || yesButtonText.startsWith("!")) yesButtonText = "Yes";
            if (noButtonText.startsWith("?") || noButtonText.startsWith("!")) noButtonText = "No";

            Object[] options = {yesButtonText, noButtonText};

            int response = JOptionPane.showOptionDialog(this,
                    dialogMessage,
                    dialogTitle,
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    options,
                    options[0]);

            if (response == 0) {
                boolean success = Main.db.removeByID(Main.username, humanBeingToView.getId());
                if (success) {
                    if (ownerFrame instanceof MainWindow) {
                        MainWindow mw = (MainWindow) ownerFrame;
                        Main.cm.startCM(false);
                        mw.getHbTableModel().updateData(CollectionManager.collection);
                        mw.updateStatusBarInfo();
                        mw.updateVisualization();
                    }
                    JOptionPane.showMessageDialog(ownerFrame, LocalisationManager.getString("dialog.message.objectDeleted"), LocalisationManager.getString("dialog.title.info"), JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, LocalisationManager.getString("command.error.removeFailed"), LocalisationManager.getString("dialog.title.error"), JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, LocalisationManager.getString("dialog.message.notYourObjectToDelete"), LocalisationManager.getString("dialog.title.error"), JOptionPane.ERROR_MESSAGE);
        }
    }


    // Если диалог должен обновляться при смене языка, пока он открыт
    // @Override
    // public void propertyChange(PropertyChangeEvent evt) {
    //     if (LocalisationManager.LOCALE_CHANGED_PROPERTY.equals(evt.getPropertyName())) {
    //         SwingUtilities.invokeLater(this::updateTexts);
    //     }
    // }
    //
    // @Override
    // public void dispose() {
    //     LocalisationManager.removePropertyChangeListener(this);
    //     super.dispose();
    // }
}