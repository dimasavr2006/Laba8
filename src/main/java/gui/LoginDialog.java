package gui;

import managers.DBManager;
import managers.LocalisationManager;
import run.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoginDialog extends JDialog implements PropertyChangeListener {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JComboBox<String> languageComboBox;
    private JLabel usernameLabel, passwordLabel, languageLabel;

    private DBManager dbManager;

    private boolean Succeeded = false;


    public LoginDialog(Frame parent, DBManager dbManager) {
        super(parent, true);
        this.dbManager = dbManager;

        LocalisationManager.addPropertyChangeListener(this);

        initComponents();
        updateTexts();
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);

        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;
        cs.insets = new Insets(5, 5, 5, 5); // Отступы

        usernameLabel = new JLabel();
        cs.gridx = 0; cs.gridy = 0; cs.gridwidth = 1;
        add(usernameLabel, cs);

        usernameField = new JTextField(20);
        cs.gridx = 1; cs.gridy = 0; cs.gridwidth = 2;
        add(usernameField, cs);

        passwordLabel = new JLabel();
        cs.gridx = 0; cs.gridy = 1; cs.gridwidth = 1;
        add(passwordLabel, cs);

        passwordField = new JPasswordField(20);
        cs.gridx = 1; cs.gridy = 1; cs.gridwidth = 2;
        add(passwordField, cs);

        languageLabel = new JLabel();
        cs.gridx = 0; cs.gridy = 2; cs.gridwidth = 1;
        add(languageLabel, cs);

        String[] languages = {
                "mainWindow.menu.language.russian",
                "mainWindow.menu.language.norwegian",
                "mainWindow.menu.language.french",
                "mainWindow.menu.language.spanish_sv"
        };
        // Отображаемые значения для JComboBox
        String[] displayLanguages = new String[languages.length];
        for (int i = 0; i < languages.length; i++) {
            displayLanguages[i] = LocalisationManager.getString(languages[i]);
        }

        languageComboBox = new JComboBox<>(displayLanguages);
        languageComboBox.addActionListener(e -> {
            int selectedIndex = languageComboBox.getSelectedIndex();
            switch (selectedIndex) {
                case 0: LocalisationManager.setLocale(LocalisationManager.RU_LOCALE); break;
                case 1: LocalisationManager.setLocale(LocalisationManager.NO_LOCALE); break;
                case 2: LocalisationManager.setLocale(LocalisationManager.FR_LOCALE); break;
                case 3: LocalisationManager.setLocale(LocalisationManager.EV_SV_LOCALE); break;
            }
        });
        cs.gridx = 1; cs.gridy = 2; cs.gridwidth = 2;
        add(languageComboBox, cs);


        loginButton = new JButton();
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });

        registerButton = new JButton();
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                performRegister();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        cs.gridx = 0; cs.gridy = 3; cs.gridwidth = 3; cs.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, cs);
    }

    private void performLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    LocalisationManager.getString("login.message.fieldsCannotBeEmpty"),
                    LocalisationManager.getString("dialog.title.error"),
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = dbManager.login(username, password);

        if (success) {
            Main.username = username;
            Main.login = true;
            Succeeded = true;
            JOptionPane.showMessageDialog(this, LocalisationManager.getString("login.message.success"));
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    LocalisationManager.getString("login.message.failed"),
                    LocalisationManager.getString("dialog.title.error"),
                    JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        }
    }

    private void performRegister() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    LocalisationManager.getString("login.message.fieldsCannotBeEmpty"),
                    LocalisationManager.getString("dialog.title.error"),
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        dbManager.registerUser(username, password);

        if (Main.login) {
            Succeeded = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    LocalisationManager.getString("login.message.registrationFailed") + (Main.username.equals(username) ? "" : " " + LocalisationManager.getString("login.message.userExists")),
                    LocalisationManager.getString("dialog.title.error"),
                    JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        }
    }


    private void updateTexts() {
        setTitle(LocalisationManager.getString("login.title"));
        usernameLabel.setText(LocalisationManager.getString("login.label.username"));
        passwordLabel.setText(LocalisationManager.getString("login.label.password"));
        languageLabel.setText(LocalisationManager.getString("login.label.language"));
        loginButton.setText(LocalisationManager.getString("login.button.login"));
        registerButton.setText(LocalisationManager.getString("login.button.register"));

        if (languageComboBox != null) {
            String[] languages = {
                    "mainWindow.menu.language.russian", "mainWindow.menu.language.norwegian",
                    "mainWindow.menu.language.french", "mainWindow.menu.language.spanish_sv"
            };
            int selectedIndex = languageComboBox.getSelectedIndex();
            DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) languageComboBox.getModel();
            model.removeAllElements();
            for (String langKey : languages) {
                model.addElement(LocalisationManager.getString(langKey));
            }
            if (selectedIndex >=0 && selectedIndex < model.getSize()) {
                languageComboBox.setSelectedIndex(selectedIndex);
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (LocalisationManager.LOCALE_CHANGED_PROPERTY.equals(evt.getPropertyName())) {
            SwingUtilities.invokeLater(this::updateTexts);
        }
    }

    public boolean isSucceeded() {
        return Succeeded;
    }

    @Override
    public void dispose() {
        LocalisationManager.removePropertyChangeListener(this);
        super.dispose();
    }
}