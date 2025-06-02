package gui;

import classes.HumanBeing;
import commands.AddElementCommand;
import commands.Command;
import enums.Mood;
import enums.WeaponType;
import functions.filter.ColumnRowFilter;
import gui.dialogs.HumanBeingDialog;
import gui.dialogs.ViewHumanDialog;
import gui.renderers.CenterRender;
import gui.renderers.DateRenderer;
import gui.renderers.EnumRenderer;
import managers.CollectionManager;
import managers.LocalisationManager;
import run.Main;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainWindow extends JFrame implements PropertyChangeListener {

    private static final int DEFAULT_WIDTH = 1920;
    private static final int DEFAULT_HEIGHT = 1080;
    private static final Dimension PREFERRED_WINDOW_SIZE = new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);

    private JMenuBar menuBar;

    private JMenu fileMenu;
    private JMenu languageMenu;
    private JMenu commandsMenu;

    private JMenuItem exitItem;
    private JMenuItem ruItem;
    private JMenuItem noItem;
    private JMenuItem frItem;
    private JMenuItem esSvItem;
    private JMenuItem addItem;
    private JMenuItem clearItem;

    private JLabel currentUserLabel;
    private JLabel filterByLabel;
    private JLabel collectionInfoLabel;

    private JPanel statusBar;
    private JPanel tablePanel;
    private JPanel visualizationPanel;
    private JPanel filterPanel;

    private JSplitPane mainSplitPane;

    private HBTable hbTableModel;

    private JTable objectTable;

    private JTextField filterValueTextField;

    private TableRowSorter<HBTable> sorter;

    private JComboBox<String> filterColumnComboBox;
    private JComboBox<String> filterOperatorComboBox;

    private String[] columnFilterDisplayNames;

    private String[] columnFilterKeys;

    private Map<Class<?>, String[]> operatorMap;


    public MainWindow() {
        LocalisationManager.addPropertyChangeListener(this);
        hbTableModel = new HBTable();
        initComponents(); //initComponents теперь будет использовать hbTableModel для получения имен колонок
        updateTexts();    // Важно вызвать ПОСЛЕ initComponents, чтобы все UI элементы были созданы
        setPreferredSize(PREFERRED_WINDOW_SIZE);
        pack();
        setLocationRelativeTo(null);
        SwingUtilities.invokeLater(() -> { // Установка разделителя
            if (mainSplitPane.getWidth() > 0 && (mainSplitPane.getDividerLocation() < 20 || mainSplitPane.getDividerLocation() > mainSplitPane.getWidth() - 20)) {
                mainSplitPane.setDividerLocation(0.7); // Больше места для таблицы/фильтров
            }
        });
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmAndExit();
            }
        });

        // 1. СОЗДАНИЕ МЕНЮ
        menuBar = new JMenuBar();

        // Меню "Файл"
        fileMenu = new JMenu(); // Текст установится в updateTexts()
        exitItem = new JMenuItem(); // Текст установится в updateTexts()
        exitItem.addActionListener(e -> confirmAndExit());
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        // Меню "Язык"
        languageMenu = new JMenu(); // Текст установится в updateTexts()
        ruItem = new JMenuItem(); // Текст установится в updateTexts()
        ruItem.addActionListener(e -> LocalisationManager.setLocale(LocalisationManager.RU_LOCALE));
        languageMenu.add(ruItem);
        noItem = new JMenuItem(); // Текст установится в updateTexts()
        noItem.addActionListener(e -> LocalisationManager.setLocale(LocalisationManager.NO_LOCALE));
        languageMenu.add(noItem);
        frItem = new JMenuItem(); // Текст установится в updateTexts()
        frItem.addActionListener(e -> LocalisationManager.setLocale(LocalisationManager.FR_LOCALE));
        languageMenu.add(frItem);
        esSvItem = new JMenuItem(); // Текст установится в updateTexts()
        esSvItem.addActionListener(e -> LocalisationManager.setLocale(LocalisationManager.EV_SV_LOCALE));
        languageMenu.add(esSvItem);
        menuBar.add(languageMenu);

        // Меню "Команды"
        commandsMenu = new JMenu(); // Текст установится в updateTexts()
        commandsMenu = new JMenu(); // Текст установится в updateTexts()
        addItem = new JMenuItem(); // Текст установится в updateTexts() (было addItem, переименовал для ясности)
        addItem.addActionListener(e -> {
            handleAddCommand();
            Main.cm.startCM(false);
            hbTableModel.updateData(CollectionManager.collection);
            updateTexts();
        });
        commandsMenu.add(addItem);
        // Сюда будешь добавлять другие пункты меню для команд (clear, add_if_min и т.д.)
        menuBar.add(commandsMenu); // Добавляем commandsMenu в menuBar
        clearItem = new JMenuItem();
        clearItem.addActionListener(e -> {
            handleClearCommand();
            Main.cm.startCM(false);
            hbTableModel.updateData(CollectionManager.collection);
            updateTexts();
        });
        commandsMenu.add(clearItem);

        setJMenuBar(menuBar);


        // 2. ИНИЦИАЛИЗАЦИЯ ОСНОВНЫХ ПАНЕЛЕЙ
        tablePanel = new JPanel(new BorderLayout()); // Главная панель для таблицы и фильтров над ней
        tablePanel.setBorder(BorderFactory.createTitledBorder("")); // Заголовок будет обновлен в updateTexts

        visualizationPanel = new JPanel(new BorderLayout());
        visualizationPanel.setBorder(BorderFactory.createTitledBorder(""));
        JLabel vizPlaceholder = new JLabel("Место для визуализации (JPanel/Canvas)", SwingConstants.CENTER);
        visualizationPanel.add(vizPlaceholder, BorderLayout.CENTER);

        // 3. СОЗДАНИЕ ПАНЕЛИ ФИЛЬТРОВ (ОДИН НАБОР: КОЛОНКА, ОПЕРАТОР, ЗНАЧЕНИЕ)
        filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        filterByLabel = new JLabel(); // Текст "Фильтровать по:" установится в updateTexts

        // hbTableModel уже инициализирован в конструкторе MainWindow
        columnFilterKeys = hbTableModel.getColumnHeaderKeys();
        // columnFilterDisplayNames будет заполняться в updateTexts
        filterColumnComboBox = new JComboBox<>(); // Модель (локализованные имена колонок) заполнится в updateTexts

        initializeOperatorMap(); // Метод для заполнения карты операторов
        filterOperatorComboBox = new JComboBox<>(); // Модель (локализованные операторы) заполнится в updateOperatorComboBox

        filterValueTextField = new JTextField(20); // Поле для ввода значения фильтра

        filterPanel.add(filterByLabel);
        filterPanel.add(filterColumnComboBox);
        filterPanel.add(new JLabel(": ")); // Разделитель для красоты
        filterPanel.add(filterOperatorComboBox);
        filterPanel.add(filterValueTextField);

        // Добавляем панель фильтра в верхнюю часть tablePanel
        tablePanel.add(filterPanel, BorderLayout.NORTH);

        // 4. СОЗДАНИЕ И НАСТРОЙКА ТАБЛИЦЫ
        objectTable = new JTable(hbTableModel); // hbTableModel уже создан
        objectTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        this.sorter = new TableRowSorter<>(hbTableModel);
        objectTable.setRowSorter(this.sorter);

        // Применение рендереров
        CenterRender centerRenderer = new CenterRender();
        objectTable.setDefaultRenderer(Object.class, centerRenderer);
        objectTable.setDefaultRenderer(String.class, centerRenderer);
        objectTable.setDefaultRenderer(Integer.class, centerRenderer);
        objectTable.setDefaultRenderer(Long.class, centerRenderer);
        objectTable.setDefaultRenderer(Boolean.class, centerRenderer);
        objectTable.setDefaultRenderer(Date.class, new DateRenderer());
        objectTable.setDefaultRenderer(WeaponType.class, new EnumRenderer("enum.weaponType."));
        objectTable.setDefaultRenderer(Mood.class, new EnumRenderer("enum.mood."));

        objectTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Двойной клик
                    int viewRow = objectTable.rowAtPoint(e.getPoint());
                    if (viewRow >= 0) {
                        int modelRow = objectTable.convertRowIndexToModel(viewRow);
                        HumanBeing selectedHuman = hbTableModel.getHumanBeingAt(modelRow);
                        if (selectedHuman != null) {
                            ViewHumanDialog viewDialog = new ViewHumanDialog(MainWindow.this, selectedHuman);
                            viewDialog.setVisible(true); // Диалог теперь не строго модальный, но setVisible блокирует до закрытия, если он модальный

//                            if (viewDialog.wasDataChanged()) {
//                                Main.cm.startCM(false); // Обновить коллекцию
//                                hbTableModel.updateData(CollectionManager.collection);
//                                updateStatusBarInfo(); // Обновить статус-бар
//                                // TODO: Обновить визуализацию
//                            }
                        }
                    }
                }
            }
        });

        // Центрирование заголовков
        JTableHeader header = objectTable.getTableHeader();
        if (header.getDefaultRenderer() instanceof DefaultTableCellRenderer) {
            ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        } else {
            DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
            headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            TableColumnModel tempColumnModel = objectTable.getColumnModel();
            for (int i = 0; i < tempColumnModel.getColumnCount(); i++) {
                tempColumnModel.getColumn(i).setHeaderRenderer(headerRenderer);
            }
        }

        // Настройка кастомных компараторов для сортировщика
        final int MOOD_COLUMN_INDEX = 10;
        final int WEAPON_TYPE_COLUMN_INDEX = 9;
        if (MOOD_COLUMN_INDEX >= 0 && MOOD_COLUMN_INDEX < this.sorter.getModel().getColumnCount()) {
            this.sorter.setComparator(MOOD_COLUMN_INDEX, Comparator.comparingInt(m -> (m == null) ? Integer.MIN_VALUE : ((Mood) m).getPointOfHappy()));
        }
        if (WEAPON_TYPE_COLUMN_INDEX >= 0 && WEAPON_TYPE_COLUMN_INDEX < this.sorter.getModel().getColumnCount()) {
            this.sorter.setComparator(WEAPON_TYPE_COLUMN_INDEX, Comparator.comparingInt(wt -> (wt == null) ? Integer.MIN_VALUE : ((WeaponType) wt).getDegreeOfCool()));
        }

        // Настройка ширины колонок
        TableColumnModel columnModel = objectTable.getColumnModel();
        if (columnModel.getColumnCount() > 0) { columnModel.getColumn(0).setPreferredWidth(40);  /* ID */ }
        // ... (остальные настройки ширины) ...
        if (columnModel.getColumnCount() > 13) { columnModel.getColumn(13).setPreferredWidth(70); /* ownerId */ }

        JScrollPane tableScrollPane = new JScrollPane(objectTable);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER); // Таблица под панелью фильтра

        // 5. СОЗДАНИЕ РАЗДЕЛИТЕЛЯ (SPLITPANE)
        mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tablePanel, visualizationPanel);
        mainSplitPane.setResizeWeight(0.7);
        mainSplitPane.setOneTouchExpandable(true);
        mainSplitPane.setContinuousLayout(true);

        // 6. СОЗДАНИЕ СТАТУС-БАРА
        statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        currentUserLabel = new JLabel();
        statusBar.add(currentUserLabel);

         collectionInfoLabel = new JLabel();
         statusBar.add(new JSeparator(SwingConstants.VERTICAL));
         statusBar.add(collectionInfoLabel);


        // 7. КОМПОНОВКА ОСНОВНОГО ОКНА (JFrame)
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(mainSplitPane, BorderLayout.CENTER);
        contentPane.add(statusBar, BorderLayout.SOUTH);

        // 8. ПРИВЯЗКА СЛУШАТЕЛЕЙ К ЭЛЕМЕНТАМ ФИЛЬТРА
        DocumentListener singleFilterListener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { applySingleColumnFilter(); }
            public void removeUpdate(DocumentEvent e) { applySingleColumnFilter(); }
            public void changedUpdate(DocumentEvent e) { applySingleColumnFilter(); }
        };
        filterValueTextField.getDocument().addDocumentListener(singleFilterListener);
        filterOperatorComboBox.addActionListener(e -> applySingleColumnFilter());
        filterColumnComboBox.addActionListener(e -> {
            updateOperatorComboBox();
            applySingleColumnFilter();
        });
    }

    private void confirmAndExit() {
            String dialogTitle = LocalisationManager.getString("dialog.title.confirmation");
            String dialogMessage = LocalisationManager.getString("dialog.confirmExit.message");

            String yesButtonText = LocalisationManager.getString("button.yes");
            String noButtonText = LocalisationManager.getString("button.no");

            if (dialogTitle.startsWith("?") || dialogTitle.startsWith("!")) {
                dialogTitle = "Confirm Exit";
            }
            if (dialogMessage.startsWith("?") || dialogMessage.startsWith("!")) {
                dialogMessage = "Are you sure you want to exit?";
            }
            if (yesButtonText.startsWith("?") || yesButtonText.startsWith("!")) {
                yesButtonText = "Yes";
            }
            if (noButtonText.startsWith("?") || noButtonText.startsWith("!")) {
                noButtonText = "No";
            }

            Object[] options = {yesButtonText, noButtonText};

            int response = JOptionPane.showOptionDialog(this,
                    dialogMessage,
                    dialogTitle,
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);

            if (response == 0) {
                disposeFrame();
            }
    }


    private void updateTexts() {
        setTitle(LocalisationManager.getString("mainWindow.title"));

        if (fileMenu != null) {
            fileMenu.setText(LocalisationManager.getString("mainWindow.menu.file"));
        }
        if (exitItem != null) {
            exitItem.setText(LocalisationManager.getString("mainWindow.menu.file.exit"));
        }
        if (languageMenu != null) {
            languageMenu.setText(LocalisationManager.getString("mainWindow.menu.language"));
        }
        if (ruItem != null) {
            ruItem.setText(LocalisationManager.getString("mainWindow.menu.language.russian"));
        }
        if (noItem != null) {
            noItem.setText(LocalisationManager.getString("mainWindow.menu.language.norwegian"));
        }
        if (frItem != null) {
            frItem.setText(LocalisationManager.getString("mainWindow.menu.language.french"));
        }
        if (esSvItem != null) {
            esSvItem.setText(LocalisationManager.getString("mainWindow.menu.language.spanish_sv"));
        }
        if (currentUserLabel != null) {
            String usernameText = (Main.username != null && !Main.username.isEmpty()) ? Main.username : "N/A";
            currentUserLabel.setText(LocalisationManager.getString("mainWindow.label.currentUser") + " " + usernameText);
        }
        if (tablePanel != null && tablePanel.getBorder() instanceof TitledBorder) {
            ((TitledBorder) tablePanel.getBorder()).setTitle(LocalisationManager.getString("mainWindow.label.table"));
            tablePanel.repaint(); // Важно для обновления заголовка Border
        }
        if (visualizationPanel != null && visualizationPanel.getBorder() instanceof TitledBorder) {
            ((TitledBorder) visualizationPanel.getBorder()).setTitle(LocalisationManager.getString("mainWindow.label.visualization"));
            visualizationPanel.repaint();
        }
        if (hbTableModel != null) {
            hbTableModel.localeChanged();
        }

        if (filterByLabel != null) {
            filterByLabel.setText(LocalisationManager.getString("table.filter.labelBy")); // Новый ключ "Фильтровать по:"
        }
        if (filterColumnComboBox != null) { // Обновляем названия колонок
            int previouslySelectedIndex = filterColumnComboBox.getSelectedIndex();
            DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) filterColumnComboBox.getModel();
            model.removeAllElements();
            columnFilterDisplayNames = new String[columnFilterKeys.length];
            for (int i = 0; i < columnFilterKeys.length; i++) {
                columnFilterDisplayNames[i] = LocalisationManager.getString(columnFilterKeys[i]);
                model.addElement(columnFilterDisplayNames[i]);
            }
            if (previouslySelectedIndex >= 0 && previouslySelectedIndex < model.getSize()) {
                filterColumnComboBox.setSelectedIndex(previouslySelectedIndex);
            } else if (model.getSize() > 0) filterColumnComboBox.setSelectedIndex(0);
        }
        if (filterOperatorComboBox != null) { // Обновляем операторы
            updateOperatorComboBox(); // Этот метод сам обновит тексты операторов
        }

        // Обновление заголовка панели фильтров (если он есть у JScrollPane)
        if (filterPanel != null && filterPanel.getParent() instanceof JViewport && filterPanel.getParent().getParent() instanceof JScrollPane) {
            JScrollPane filterScrollPane = (JScrollPane) filterPanel.getParent().getParent();
            if (filterScrollPane.getBorder() instanceof TitledBorder) {
                ((TitledBorder) filterScrollPane.getBorder()).setTitle(LocalisationManager.getString("table.filter.label"));
                filterScrollPane.repaint();
            }
        }
        if (commandsMenu != null) {
            commandsMenu.setText(LocalisationManager.getString("mainWindow.menu.commands"));
        }
        if (addItem != null) {
            addItem.setText(LocalisationManager.getString("command.add"));
        }
        if (clearItem != null) {
            clearItem.setText(LocalisationManager.getString("command.clear"));
        }
        if (collectionInfoLabel != null) {
            String type = "N/A";
            String creationTime = "N/A";
            int size = 0;

            if (CollectionManager.collection != null) { // Проверяем, что коллекция не null
                type = CollectionManager.collection.getClass().getSimpleName();
                size = CollectionManager.collection.size();
            }
            if (Main.cm != null && Main.cm.getTimeOfCreation() != null) { // Проверяем cm и время создания
                creationTime = Main.cm.getTimeOfCreation();
            }

            // Ключ для формата строки в .properties файлах
            // Например: info.collectionDetailsFormat=Тип: %s, Создана: %s, Элементов: %d
            String formatKey = "info.collectionDetailsFormat";
            String infoText = String.format(LocalisationManager.getString(formatKey), type, creationTime, size);
            collectionInfoLabel.setText(infoText);
        }

        updateStatusBarInfo();

        if (menuBar != null) {
            menuBar.revalidate();
            menuBar.repaint();
        }
        revalidate();
        repaint();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (LocalisationManager.LOCALE_CHANGED_PROPERTY.equals(evt.getPropertyName())) {
            SwingUtilities.invokeLater(this::updateTexts);
        }
    }

    private void disposeFrame() {
        LocalisationManager.removePropertyChangeListener(this);
        super.dispose();
        System.exit(0);
    }

    public HBTable getHbTableModel() {
        return hbTableModel;
    }


    private void initializeOperatorMap() {
        operatorMap = new HashMap<>();
        // Ключи для локализации операторов
        operatorMap.put(String.class, new String[]{"operator.contains", "operator.equals", "operator.notEquals", "operator.startsWith", "operator.endsWith"});
        operatorMap.put(Integer.class, new String[]{"operator.equalsNum", "operator.notEqualsNum", "operator.greaterThan", "operator.lessThan", "operator.greaterOrEquals", "operator.lessOrEquals"});
        operatorMap.put(Long.class, new String[]{"operator.equalsNum", "operator.notEqualsNum", "operator.greaterThan", "operator.lessThan", "operator.greaterOrEquals", "operator.lessOrEquals"});
        operatorMap.put(Date.class, new String[]{"operator.dateEquals", "operator.dateBefore", "operator.dateAfter"}); // Даты сложнее, пока простые
        operatorMap.put(Boolean.class, new String[]{"operator.isTrue", "operator.isFalse"});
        // Для Enum можно "operator.equalsEnum"
        operatorMap.put(Mood.class, new String[]{"operator.equalsEnum"});
        operatorMap.put(WeaponType.class, new String[]{"operator.equalsEnum"});
    }

    private void updateOperatorComboBox() {
        if (filterColumnComboBox == null || hbTableModel == null || operatorMap == null || filterOperatorComboBox == null) return;

        int selectedColumnIndex = filterColumnComboBox.getSelectedIndex();
        if (selectedColumnIndex < 0) return;

        Class<?> columnClass = hbTableModel.getColumnClass(selectedColumnIndex);
        String[] operatorKeys = operatorMap.get(columnClass);

        if (operatorKeys == null) { // Если для типа нет операторов, используем стандартные для String
            operatorKeys = operatorMap.get(String.class);
        }

        DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) filterOperatorComboBox.getModel();
        model.removeAllElements();
        if (operatorKeys != null) {
            for (String key : operatorKeys) {
                model.addElement(LocalisationManager.getString(key));
            }
        }
        if (model.getSize() > 0) {
            filterOperatorComboBox.setSelectedIndex(0);
        }
    }

    private void applySingleColumnFilter() {
        if (sorter == null || hbTableModel == null || filterColumnComboBox == null || filterOperatorComboBox == null || filterValueTextField == null) {
            return;
        }

        String filterText = filterValueTextField.getText();
        int selectedColumnIndex = filterColumnComboBox.getSelectedIndex();
        String selectedOperatorKey = null; // Ключ выбранного оператора

        if (filterOperatorComboBox.getSelectedItem() != null) {
            // Нам нужно найти оригинальный ключ оператора, а не его локализованное представление
            // Для этого при заполнении filterOperatorComboBox нужно хранить и ключи, и отображаемые значения,
            // либо искать ключ по отображаемому значению (что менее надежно из-за возможной не уникальности переводов).

            // Простой вариант: найти ключ по индексу в массиве ключей для данного типа колонки
            Class<?> columnClass = hbTableModel.getColumnClass(selectedColumnIndex);
            String[] currentOperatorKeys = operatorMap.get(columnClass);
            if (currentOperatorKeys == null) currentOperatorKeys = operatorMap.get(String.class); // Fallback

            if (currentOperatorKeys != null && filterOperatorComboBox.getSelectedIndex() < currentOperatorKeys.length && filterOperatorComboBox.getSelectedIndex() >=0) {
                selectedOperatorKey = currentOperatorKeys[filterOperatorComboBox.getSelectedIndex()];
            }
        }


        if (selectedColumnIndex < 0 || selectedOperatorKey == null) {
            sorter.setRowFilter(null);
            return;
        }

        // Для Boolean операторов значение в filterValueTextField может не требоваться
        boolean valueNeeded = true;
        if (hbTableModel.getColumnClass(selectedColumnIndex) == Boolean.class &&
                (selectedOperatorKey.equals("operator.isTrue") || selectedOperatorKey.equals("operator.isFalse"))) {
            valueNeeded = false;
        }


        if (valueNeeded && filterText.trim().isEmpty()) {
            // Если оператор требует значения, а оно пустое, то не фильтруем (или фильтруем как-то по-особому)
            // Для операторов типа "is null", "is not null" это было бы валидно.
            // Пока считаем, что если значение нужно и оно пустое - фильтр не применяем
            sorter.setRowFilter(null);
            return;
        }

        try {
            sorter.setRowFilter(new ColumnRowFilter(filterText, selectedColumnIndex, selectedOperatorKey, hbTableModel));
        } catch (Exception e) {
            System.err.println("Ошибка при применении фильтра: " + e.getMessage());
            e.printStackTrace();
            sorter.setRowFilter(null);
        }
    }

    private void openEditDialog(HumanBeing humanToEdit) {
        // Проверка владения объектом
        // Предполагаем, что Main.db.findUserIDbyUsername(Main.username) возвращает ID текущего пользователя
        // и Main.username уже установлен.
        int currentUserId = Main.db.findUserIDbyUsername(Main.username); // Это может быть затратно, лучше получить ID один раз после логина
        // и хранить в Main.currentUserId или передавать в MainWindow

        if (humanToEdit.getOwnerId() == currentUserId) {
            HumanBeingDialog dialog = new HumanBeingDialog(this, humanToEdit); // 'this' - родительское окно
            dialog.setVisible(true);

            if (dialog.isSaved()) {
                HumanBeing updatedHuman = dialog.getHumanBeing();
                if (updatedHuman != null) {
                    // Логика обновления объекта в БД
                    boolean success = Main.db.updateID(humanToEdit.getId(), updatedHuman, Main.username);
                    if (success) {
                        // Обновляем локальную коллекцию и таблицу
                        Main.cm.startCM(); // Перезагружаем всю коллекцию (или можно реализовать обновление конкретного элемента)
                        hbTableModel.updateData(CollectionManager.collection);
                        // TODO: Обновить визуализацию, если она есть
                        updateStatusBarInfo(); // Обновляем информацию в статус-баре (если есть)

                        JOptionPane.showMessageDialog(this,
                                LocalisationManager.getString("dialog.message.objectUpdated"),
                                LocalisationManager.getString("dialog.title.info"), // Используем общий ключ для информации
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this,
                                LocalisationManager.getString("command.error.updateFailed"),
                                LocalisationManager.getString("dialog.title.error"),
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } else {
            // Пользователь не владеет объектом
            JOptionPane.showMessageDialog(this,
                    LocalisationManager.getString("dialog.addEdit.validation.notYourObject"),
                    LocalisationManager.getString("dialog.title.error"),
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Метод для обновления информации в статус-баре (если вынесено отдельно)
    public void updateStatusBarInfo() {
        if (currentUserLabel != null) {
            String usernameText = (Main.username != null && !Main.username.isEmpty()) ? Main.username : "N/A";
            currentUserLabel.setText(LocalisationManager.getString("mainWindow.label.currentUser") + " " + usernameText);
        }
        // Добавь сюда обновление метки с количеством элементов, если она есть
        // if (collectionInfoLabel != null) { ... }
        if (hbTableModel != null) { // Для обновления количества элементов в статусной строке, если она есть
            // Это вызовет updateTexts, который обновит и collectionInfoLabel
            // Либо можно напрямую обновлять collectionInfoLabel
        }
        // Если у тебя есть отдельный JLabel для информации о коллекции, обнови его здесь
        // Например, если у тебя есть collectionInfoLabel:
        // if (collectionInfoLabel != null && Main.cm != null && CollectionManager.collection != null) {
        //     String type = CollectionManager.collection.getClass().getSimpleName();
        //     String creationTime = Main.cm.getTimeOfCreation() != null ? Main.cm.getTimeOfCreation() : "N/A";
        //     int size = CollectionManager.collection.size();
        //     String formatKey = "statusBar.collectionInfoFormat";
        //     String infoText = String.format(LocalisationManager.getString(formatKey), type, creationTime, size);
        //     collectionInfoLabel.setText(infoText);
        // }
    }

    private void handleAddCommand() {
        HumanBeingDialog dialog = new HumanBeingDialog(this, null); // null для нового объекта
        dialog.setVisible(true);

        if (dialog.isSaved()) {
            HumanBeing newHuman = dialog.getHumanBeing();
            if (newHuman != null) {
                // Используем сеттер в команде
                AddElementCommand.setObjectFromGUI(newHuman);
                try {
                    Command addCmd = Main.inv.commands.get("add");
                    if (addCmd != null) {
                        // Вызываем execute. Command.execute вызовет bodyOfDBCommand.
                        // Если bodyOfDBCommand вернет true, Command.execute обновит CollectionManager.collection
                        addCmd.execute(""); // Аргумент пуст для команды add из GUI

                        // После выполнения команды, CollectionManager.collection должен быть обновлен внутри Command.execute
                        // Нам нужно только обновить модель таблицы и статус-бар
                        hbTableModel.updateData(CollectionManager.collection);
                        updateStatusBarInfo(); // Предполагаем, что этот метод обновляет инфо о коллекции
                        // TODO: Обновить визуализацию, когда она будет

                        JOptionPane.showMessageDialog(this,
                                LocalisationManager.getString("dialog.message.objectAdded"),
                                LocalisationManager.getString("dialog.title.info"),
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        System.err.println("Команда 'add' не найдена в Invoker.");
                        JOptionPane.showMessageDialog(this,
                                "Ошибка: Команда 'add' не найдена.",
                                LocalisationManager.getString("dialog.title.error"),
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) { // Ловим более общие исключения от команды
                    JOptionPane.showMessageDialog(this,
                            LocalisationManager.getString("command.error.addFailed") + "\n" + ex.getMessage(),
                            LocalisationManager.getString("dialog.title.error"),
                            JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        }
    }

    private void handleClearCommand() {
        String dialogTitle = LocalisationManager.getString("command.clear");
        String dialogMessage = LocalisationManager.getString("dialog.message.confirmClear");
        String yesButtonText = LocalisationManager.getString("button.yes");
        String noButtonText = LocalisationManager.getString("button.no");

        // Fallback
        if (dialogTitle.startsWith("?")) dialogTitle = "Clear Collection";
        if (dialogMessage.startsWith("?")) dialogMessage = "Are you sure you want to clear all your items from the collection?";
        if (yesButtonText.startsWith("?")) yesButtonText = "Yes";
        if (noButtonText.startsWith("?")) noButtonText = "No";

        Object[] options = {yesButtonText, noButtonText};
        int response = JOptionPane.showOptionDialog(this,
                dialogMessage,
                dialogTitle,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null, options, options[0]);

        if (response == 0) { // Yes
            try {
                // Вызываем существующую команду ClearCommand через Invoker
                Command clearCmd = Main.inv.commands.get("clear");
                if (clearCmd != null) {
                    Command.setUsernameAgain();
                    Boolean success = clearCmd.bodyOfDBCommand(Main.username);

                    if (success != null && success) {
                        hbTableModel.updateData(CollectionManager.collection);
                        updateStatusBarInfo();
                        // TODO: update visualization

                        JOptionPane.showMessageDialog(this,
                                LocalisationManager.getString("dialog.message.collectionCleared"),
                                dialogTitle,
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this,
                                LocalisationManager.getString("command.error.clearFailed"),
                                LocalisationManager.getString("dialog.title.error"),
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        LocalisationManager.getString("command.error.general") + "\n" + ex.getMessage(),
                        LocalisationManager.getString("dialog.title.error"),
                        JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }


}