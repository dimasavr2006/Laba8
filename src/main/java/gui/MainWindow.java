package gui;

import enums.Mood;
import enums.WeaponType;
import functions.filter.FilterFieldInfo;
import gui.filter.ColumnRowFilter;
import gui.renderers.CenterRender;
import gui.renderers.DateRenderer;
import gui.renderers.EnumRenderer;
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MainWindow extends JFrame implements PropertyChangeListener {

    private List<FilterFieldInfo> filterFieldsInfoList;

    private static final int DEFAULT_WIDTH = 1920;
    private static final int DEFAULT_HEIGHT = 1080;
    private static final Dimension PREFERRED_WINDOW_SIZE = new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);

    private JMenuBar menuBar;

    private JMenu fileMenu;
    private JMenu languageMenu;

    private JMenuItem exitItem;
    private JMenuItem ruItem;
    private JMenuItem noItem;
    private JMenuItem frItem;
    private JMenuItem esSvItem;

    private JLabel currentUserLabel;

    private JLabel filterByLabel;

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

    private String[] columnFilterDisplayNames;

    private String[] columnFilterKeys;


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

        // 1. Создание меню
        menuBar = new JMenuBar();

        fileMenu = new JMenu(); // Текст установится в updateTexts()
        exitItem = new JMenuItem(); // Текст установится в updateTexts()
        exitItem.addActionListener(e -> confirmAndExit());
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

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
        setJMenuBar(menuBar);

        // 2. Инициализация основных панелей (для таблицы и визуализации)
        tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("")); // Заголовок установится в updateTexts

        visualizationPanel = new JPanel(new BorderLayout());
        visualizationPanel.setBorder(BorderFactory.createTitledBorder("")); // Заголовок установится в updateTexts
        JLabel vizPlaceholder = new JLabel("Место для визуализации (JPanel/Canvas)", SwingConstants.CENTER);
        visualizationPanel.add(vizPlaceholder, BorderLayout.CENTER);

        // 3. Создание и настройка таблицы и фильтров
        // hbTableModel уже должен быть инициализирован в конструкторе MainWindow
        // hbTableModel = new HBTable(); // Если не инициализирован в конструкторе MainWindow

        objectTable = new JTable(hbTableModel);
        objectTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        this.sorter = new TableRowSorter<>(hbTableModel);
        objectTable.setRowSorter(this.sorter);

        // Применение рендереров для ячеек
        CenterRender centerRenderer = new CenterRender();
        objectTable.setDefaultRenderer(Object.class, centerRenderer);
        objectTable.setDefaultRenderer(String.class, centerRenderer);
        objectTable.setDefaultRenderer(Integer.class, centerRenderer);
        objectTable.setDefaultRenderer(Long.class, centerRenderer);
        objectTable.setDefaultRenderer(Boolean.class, centerRenderer); // Будет центрировать стандартное представление true/false
        objectTable.setDefaultRenderer(Date.class, new DateRenderer());
        objectTable.setDefaultRenderer(WeaponType.class, new EnumRenderer("enum.weaponType."));
        objectTable.setDefaultRenderer(Mood.class, new EnumRenderer("enum.mood."));

        // Центрирование заголовков столбцов
        JTableHeader header = objectTable.getTableHeader();
        if (header.getDefaultRenderer() instanceof DefaultTableCellRenderer) {
            ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        } else {
            DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
            headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            TableColumnModel columnModel = objectTable.getColumnModel();
            for (int i = 0; i < columnModel.getColumnCount(); i++) {
                columnModel.getColumn(i).setHeaderRenderer(headerRenderer);
            }
        }

        // Настройка кастомных компараторов для сортировщика
        final int MOOD_COLUMN_INDEX = 10; // Индекс колонки Mood
        final int WEAPON_TYPE_COLUMN_INDEX = 9; // Индекс колонки WeaponType

        if (MOOD_COLUMN_INDEX < this.sorter.getModel().getColumnCount()) {
            this.sorter.setComparator(MOOD_COLUMN_INDEX, new Comparator<Mood>() {
                @Override
                public int compare(Mood m1, Mood m2) {
                    if (m1 == null && m2 == null) return 0;
                    if (m1 == null) return -1;
                    if (m2 == null) return 1;
                    return Integer.compare(m1.getPointOfHappy(), m2.getPointOfHappy());
                }
            });
        }
        if (WEAPON_TYPE_COLUMN_INDEX < this.sorter.getModel().getColumnCount()) {
            this.sorter.setComparator(WEAPON_TYPE_COLUMN_INDEX, new Comparator<WeaponType>() {
                @Override
                public int compare(WeaponType wt1, WeaponType wt2) {
                    if (wt1 == null && wt2 == null) return 0;
                    if (wt1 == null) return -1;
                    if (wt2 == null) return 1;
                    return Integer.compare(wt1.getDegreeOfCool(), wt2.getDegreeOfCool());
                }
            });
        }

        // Настройка ширины колонок
        TableColumnModel columnModel = objectTable.getColumnModel();
        if (columnModel.getColumnCount() > 0) columnModel.getColumn(0).setPreferredWidth(40);  // ID
        if (columnModel.getColumnCount() > 1) columnModel.getColumn(1).setPreferredWidth(150); // Name
        if (columnModel.getColumnCount() > 2) columnModel.getColumn(2).setPreferredWidth(70);  // Coord X
        if (columnModel.getColumnCount() > 3) columnModel.getColumn(3).setPreferredWidth(70);  // Coord Y
        if (columnModel.getColumnCount() > 4) columnModel.getColumn(4).setPreferredWidth(170); // creationDate
        if (columnModel.getColumnCount() > 8) columnModel.getColumn(8).setPreferredWidth(150); // soundtrackName
        if (columnModel.getColumnCount() > 9) columnModel.getColumn(9).setPreferredWidth(100); // weaponType
        if (columnModel.getColumnCount() > 10) columnModel.getColumn(10).setPreferredWidth(100); // mood
        if (columnModel.getColumnCount() > 11) columnModel.getColumn(11).setPreferredWidth(100); // carName
        if (columnModel.getColumnCount() > 13) columnModel.getColumn(13).setPreferredWidth(70); // ownerId


        // --- Динамическое создание UI для фильтров ---
        filterFieldsInfoList = new ArrayList<>();
        String[] columnHeaderKeys = hbTableModel.getColumnHeaderKeys(); // hbTableModel уже создан

        filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5)); // Отступы между элементами
        filterByLabel = new JLabel();

        columnFilterKeys = hbTableModel.getColumnHeaderKeys();
        columnFilterDisplayNames = new String[columnFilterKeys.length];
        for (int i = 0; i < columnFilterKeys.length; i++) {
            columnFilterDisplayNames[i] = LocalisationManager.getString(columnFilterKeys[i]);
        }

        filterColumnComboBox = new JComboBox<>(columnFilterDisplayNames);
        filterValueTextField = new JTextField(20); // Поле для значения фильтра

        filterPanel.add(filterByLabel);
        filterPanel.add(filterColumnComboBox);
        filterPanel.add(new JLabel(":")); // Разделитель
        filterPanel.add(filterValueTextField);

        tablePanel.add(filterPanel, BorderLayout.NORTH);

        DocumentListener combinedFilterListener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { applySingleColumnFilter(); }
            public void removeUpdate(DocumentEvent e) { applySingleColumnFilter(); }
            public void changedUpdate(DocumentEvent e) { applySingleColumnFilter(); }
        };

        filterValueTextField.getDocument().addDocumentListener(combinedFilterListener);
        filterColumnComboBox.addActionListener(e -> applySingleColumnFilter()); // При смене колонки тоже применяем

        // 3. Создание и настройка таблицы
        // hbTableModel уже создан
        objectTable = new JTable(hbTableModel);
        // ... (все настройки таблицы: autoResizeMode, sorter, рендереры, компараторы, ширина колонок - остаются как были) ...
        this.sorter = new TableRowSorter<>(hbTableModel);
        objectTable.setRowSorter(this.sorter);
        // ... (применение рендереров) ...
        // ... (центрирование заголовков) ...
        // ... (настройка компараторов для sorter) ...
        // ... (настройка ширины колонок) ...


        JScrollPane tableScrollPane = new JScrollPane(objectTable);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        // Добавляем панель фильтров (возможно, со скроллом) на tablePanel
        JScrollPane filterScrollPane = new JScrollPane(filterPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        filterScrollPane.setBorder(BorderFactory.createTitledBorder(LocalisationManager.getString("table.filter.label")));
        tablePanel.add(filterScrollPane, BorderLayout.NORTH);


        // 4. Создание разделителя (SplitPane)
        mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tablePanel, visualizationPanel);
        mainSplitPane.setResizeWeight(0.7); // Дадим таблице/фильтрам больше места изначально
        mainSplitPane.setOneTouchExpandable(true);
        mainSplitPane.setContinuousLayout(true);

        // 5. Создание статус-бара
        statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        currentUserLabel = new JLabel(); // Текст будет установлен в updateTexts()
        statusBar.add(currentUserLabel);

        // 6. Компоновка основного окна
        setLayout(new BorderLayout());
        add(mainSplitPane, BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);
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

        if (filterColumnComboBox != null && columnFilterKeys != null) {
            // Сохраняем текущий выбор, чтобы попытаться его восстановить
            int previouslySelectedIndex = filterColumnComboBox.getSelectedIndex();
            DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) filterColumnComboBox.getModel();
            model.removeAllElements();
            columnFilterDisplayNames = new String[columnFilterKeys.length]; // Пересоздаем массив отображаемых имен
            for (int i = 0; i < columnFilterKeys.length; i++) {
                columnFilterDisplayNames[i] = LocalisationManager.getString(columnFilterKeys[i]);
                model.addElement(columnFilterDisplayNames[i]);
            }
            // Восстанавливаем выбор, если возможно
            if (previouslySelectedIndex >= 0 && previouslySelectedIndex < model.getSize()) {
                filterColumnComboBox.setSelectedIndex(previouslySelectedIndex);
            } else if (model.getSize() > 0) {
                filterColumnComboBox.setSelectedIndex(0); // Выбираем первый элемент, если предыдущий выбор невалиден
            }
        }
        // Обновление заголовка панели фильтров (если он есть у JScrollPane)
        if (filterPanel != null && filterPanel.getParent() instanceof JViewport && filterPanel.getParent().getParent() instanceof JScrollPane) {
            JScrollPane filterScrollPane = (JScrollPane) filterPanel.getParent().getParent();
            if (filterScrollPane.getBorder() instanceof TitledBorder) {
                ((TitledBorder) filterScrollPane.getBorder()).setTitle(LocalisationManager.getString("table.filter.label"));
                filterScrollPane.repaint();
            }
        }

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


    private void applyFilter() {
        String text = filterValueTextField.getText();
        if (sorter == null) return; // Проверка, что сортировщик инициализирован

        if (text.trim().isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            try {
                sorter.setRowFilter(new ColumnRowFilter(text, 1, hbTableModel));
            } catch (Exception e) {
                e.printStackTrace();
                sorter.setRowFilter(null); // Сбрасываем фильтр при ошибке
            }
        }
    }

    private void applySingleColumnFilter() {
        if (sorter == null || hbTableModel == null || filterColumnComboBox == null || filterValueTextField == null) {
            return; // Компоненты еще не инициализированы
        }

        String filterText = filterValueTextField.getText();
        int selectedColumnIndex = filterColumnComboBox.getSelectedIndex();

        if (selectedColumnIndex < 0) { // Ничего не выбрано (маловероятно для JComboBox без пустого элемента)
            sorter.setRowFilter(null);
            return;
        }

        // Важно: selectedColumnIndex - это индекс в JComboBox, который соответствует
        // модельному индексу колонки, так как мы заполняли JComboBox по порядку колонок модели.

        if (filterText.trim().isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            try {
                // Используем ColumnRowFilter, передавая ему текст, выбранный индекс колонки и модель
                sorter.setRowFilter(new ColumnRowFilter(filterText, selectedColumnIndex, hbTableModel));
            } catch (Exception e) {
                System.err.println("Ошибка при применении фильтра по колонке: " + e.getMessage());
                e.printStackTrace();
                sorter.setRowFilter(null);
            }
        }
    }


}