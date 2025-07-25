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
import java.util.*;

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
    private JMenuItem addIfMinItem;

    private JLabel currentUserLabel;
    private JLabel filterByLabel;
    private JLabel collectionInfoLabel;

    private JPanel statusBar;
    private JPanel tablePanel;
    private VisualizationPanel visualizationPanel;
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
        initComponents();
        updateTexts();
        setPreferredSize(PREFERRED_WINDOW_SIZE);
        pack();
        setLocationRelativeTo(null);
        SwingUtilities.invokeLater(() -> {
            if (mainSplitPane.getWidth() > 0 && (mainSplitPane.getDividerLocation() < 20 || mainSplitPane.getDividerLocation() > mainSplitPane.getWidth() - 20)) {
                mainSplitPane.setDividerLocation(0.7);
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

        menuBar = new JMenuBar();


        fileMenu = new JMenu();
        exitItem = new JMenuItem();
        exitItem.addActionListener(e -> confirmAndExit());
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        languageMenu = new JMenu();
        ruItem = new JMenuItem();
        ruItem.addActionListener(e -> LocalisationManager.setLocale(LocalisationManager.RU_LOCALE));
        languageMenu.add(ruItem);
        noItem = new JMenuItem();
        noItem.addActionListener(e -> LocalisationManager.setLocale(LocalisationManager.NO_LOCALE));
        languageMenu.add(noItem);
        frItem = new JMenuItem();
        frItem.addActionListener(e -> LocalisationManager.setLocale(LocalisationManager.FR_LOCALE));
        languageMenu.add(frItem);
        esSvItem = new JMenuItem();
        esSvItem.addActionListener(e -> LocalisationManager.setLocale(LocalisationManager.EV_SV_LOCALE));
        languageMenu.add(esSvItem);
        menuBar.add(languageMenu);

        commandsMenu = new JMenu();
        commandsMenu = new JMenu();
        addItem = new JMenuItem();
        addItem.addActionListener(e -> {
            handleAddCommand();
            Main.cm.startCM(false);
            hbTableModel.updateData(CollectionManager.collection);
            updateTexts();
        });
        commandsMenu.add(addItem);
        menuBar.add(commandsMenu);
        clearItem = new JMenuItem();
        clearItem.addActionListener(e -> {
            handleClearCommand();
            Main.cm.startCM(false);
            hbTableModel.updateData(CollectionManager.collection);
            updateTexts();
            updateVisualization();
            updateStatusBarInfo();
        });
        commandsMenu.add(clearItem);
        addIfMinItem = new JMenuItem();
        addIfMinItem.addActionListener(e -> {
            handleAddIfMinCommand();
            Main.cm.startCM(false);
            hbTableModel.updateData(CollectionManager.collection);
            updateTexts();
            updateStatusBarInfo();
        });
        commandsMenu.add(addIfMinItem);

        setJMenuBar(menuBar);


        tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder(""));

        visualizationPanel = new VisualizationPanel();
        visualizationPanel.setBorder(BorderFactory.createTitledBorder(""));

        mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tablePanel, visualizationPanel);

        filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        filterByLabel = new JLabel();

        columnFilterKeys = hbTableModel.getColumnHeaderKeys();
        filterColumnComboBox = new JComboBox<>();

        initializeOperatorMap();
        filterOperatorComboBox = new JComboBox<>();

        filterValueTextField = new JTextField(20);

        filterPanel.add(filterByLabel);
        filterPanel.add(filterColumnComboBox);
        filterPanel.add(new JLabel(": "));
        filterPanel.add(filterOperatorComboBox);
        filterPanel.add(filterValueTextField);

        tablePanel.add(filterPanel, BorderLayout.NORTH);

        objectTable = new JTable(hbTableModel);
        objectTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        this.sorter = new TableRowSorter<>(hbTableModel);
        objectTable.setRowSorter(this.sorter);

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
                if (e.getClickCount() == 2) {
                    int viewRow = objectTable.rowAtPoint(e.getPoint());
                    if (viewRow >= 0) {
                        int modelRow = objectTable.convertRowIndexToModel(viewRow);
                        HumanBeing selectedHuman = hbTableModel.getHumanBeingAt(modelRow);
                        if (selectedHuman != null) {
                            ViewHumanDialog viewDialog = new ViewHumanDialog(MainWindow.this, selectedHuman);
                            viewDialog.setVisible(true);
                            updateVisualization();
                            updateStatusBarInfo();
                        }
                    }
                }
            }
        });

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

        final int MOOD_COLUMN_INDEX = 10;
        final int WEAPON_TYPE_COLUMN_INDEX = 9;
        if (MOOD_COLUMN_INDEX >= 0 && MOOD_COLUMN_INDEX < this.sorter.getModel().getColumnCount()) {
            this.sorter.setComparator(MOOD_COLUMN_INDEX, Comparator.comparingInt(m -> (m == null) ? Integer.MIN_VALUE : ((Mood) m).getPointOfHappy()));
        }
        if (WEAPON_TYPE_COLUMN_INDEX >= 0 && WEAPON_TYPE_COLUMN_INDEX < this.sorter.getModel().getColumnCount()) {
            this.sorter.setComparator(WEAPON_TYPE_COLUMN_INDEX, Comparator.comparingInt(wt -> (wt == null) ? Integer.MIN_VALUE : ((WeaponType) wt).getDegreeOfCool()));
        }

        TableColumnModel columnModel = objectTable.getColumnModel();
        if (columnModel.getColumnCount() > 0) {
            columnModel.getColumn(0).setPreferredWidth(40);
        }
        if (columnModel.getColumnCount() > 13) {
            columnModel.getColumn(13).setPreferredWidth(70);
        }

        JScrollPane tableScrollPane = new JScrollPane(objectTable);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER); // Таблица под панелью фильтра

        mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tablePanel, visualizationPanel);
        mainSplitPane.setResizeWeight(0.7);
        mainSplitPane.setOneTouchExpandable(true);
        mainSplitPane.setContinuousLayout(true);

        statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        currentUserLabel = new JLabel();
        statusBar.add(currentUserLabel);

         collectionInfoLabel = new JLabel();
         statusBar.add(new JSeparator(SwingConstants.VERTICAL));
         statusBar.add(collectionInfoLabel);


        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(mainSplitPane, BorderLayout.CENTER);
        contentPane.add(statusBar, BorderLayout.SOUTH);

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


        updateVisualization();
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
            filterByLabel.setText(LocalisationManager.getString("table.filter.labelBy"));
        }
        if (filterColumnComboBox != null) {
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
        if (filterOperatorComboBox != null) {
            updateOperatorComboBox();
        }

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
        if (addIfMinItem != null) {
            addIfMinItem.setText(LocalisationManager.getString("command.addIfMin"));
        }
        if (collectionInfoLabel != null) {
            String type = "N/A";
            String creationTime = "N/A";
            int size = 0;

            if (CollectionManager.collection != null) {
                type = CollectionManager.collection.getClass().getSimpleName();
                size = CollectionManager.collection.size();
            }
            if (Main.cm != null && Main.cm.getTimeOfCreation() != null) {
                creationTime = Main.cm.getTimeOfCreation();
            }

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

        if (operatorKeys == null) {
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
            Class<?> columnClass = hbTableModel.getColumnClass(selectedColumnIndex);
            String[] currentOperatorKeys = operatorMap.get(columnClass);
            if (currentOperatorKeys == null) currentOperatorKeys = operatorMap.get(String.class);

            if (currentOperatorKeys != null && filterOperatorComboBox.getSelectedIndex() < currentOperatorKeys.length && filterOperatorComboBox.getSelectedIndex() >=0) {
                selectedOperatorKey = currentOperatorKeys[filterOperatorComboBox.getSelectedIndex()];
            }
        }


        if (selectedColumnIndex < 0 || selectedOperatorKey == null) {
            sorter.setRowFilter(null);
            return;
        }

        boolean valueNeeded = true;
        if (hbTableModel.getColumnClass(selectedColumnIndex) == Boolean.class &&
                (selectedOperatorKey.equals("operator.isTrue") || selectedOperatorKey.equals("operator.isFalse"))) {
            valueNeeded = false;
        }


        if (valueNeeded && filterText.trim().isEmpty()) {
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

    public void updateStatusBarInfo() {
        if (currentUserLabel != null) {
            String usernameText = (Main.username != null && !Main.username.isEmpty()) ? Main.username : "N/A";
            currentUserLabel.setText(LocalisationManager.getString("mainWindow.label.currentUser") + " " + usernameText);
        }
        if (hbTableModel != null) {

        }
        String type = "N/A";
        String creationTime = "N/A";
        int size = 0;

        if (CollectionManager.collection != null) {
            type = CollectionManager.collection.getClass().getSimpleName();
            size = CollectionManager.collection.size();
        }
        if (Main.cm != null && Main.cm.getTimeOfCreation() != null) {
            creationTime = Main.cm.getTimeOfCreation();
        }

        String formatKey = "info.collectionDetailsFormat";
        String infoText = String.format(LocalisationManager.getString(formatKey), type, creationTime, size);
        collectionInfoLabel.setText(infoText);
    }

    private void handleAddCommand() {
        HumanBeingDialog dialog = new HumanBeingDialog(this, null);
        dialog.setVisible(true);

        if (dialog.isSaved()) {
            HumanBeing newHuman = dialog.getHumanBeing();
            if (newHuman != null) {
                AddElementCommand.setObjectFromGUI(newHuman);
                try {
                    Command addCmd = Main.inv.commands.get("add");
                    if (addCmd != null) {
                        addCmd.execute("");

                        hbTableModel.updateData(CollectionManager.collection);
                        updateStatusBarInfo();
                        updateVisualization();

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
                } catch (Exception ex) {
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
                Command clearCmd = Main.inv.commands.get("clear");
                if (clearCmd != null) {
                    Command.setUsernameAgain();
                    Boolean success = clearCmd.bodyOfDBCommand(Main.username);

                    if (success != null && success) {
                        hbTableModel.updateData(CollectionManager.collection);
                        updateStatusBarInfo();
                        updateVisualization();

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

    private void handleAddIfMinCommand() {
        HumanBeingDialog dialog = new HumanBeingDialog(this, null);
        dialog.setVisible(true);

        if (dialog.isSaved()) {
            HumanBeing newHuman = dialog.getHumanBeing();
            if (newHuman != null) {
                HumanBeing minElement = Main.cm.findMin();

                boolean canAdd = false;
                if (minElement == null) {
                    canAdd = true;
                } else {
                    if (newHuman.compareTo(minElement) < 0) {
                        canAdd = true;
                    }
                }

                if (canAdd) {
                    boolean dbSuccess = Main.db.add(newHuman, Main.username);
                    if (dbSuccess) {
                        Main.cm.startCM(false);
                        hbTableModel.updateData(CollectionManager.collection);
                        updateStatusBarInfo();
                        updateVisualization();

                        JOptionPane.showMessageDialog(this,
                                LocalisationManager.getString("dialog.message.objectAdded") + " (" + LocalisationManager.getString("text.asMin") + ")",
                                LocalisationManager.getString("command.addIfMin"),
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this,
                                LocalisationManager.getString("command.error.addFailed"),
                                LocalisationManager.getString("dialog.title.error"),
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this,
                            LocalisationManager.getString("message.addIfMin.notAddedNotMin"),
                            LocalisationManager.getString("dialog.title.info"),
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    public void updateVisualization() {
        if (visualizationPanel != null && CollectionManager.collection != null) {
            visualizationPanel.setHumanBeings(new ArrayList<>(CollectionManager.collection));
        }
    }
}