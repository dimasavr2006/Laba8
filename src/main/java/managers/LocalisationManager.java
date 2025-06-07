package managers;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class LocalisationManager {
    private static Locale locale;
    private static ResourceBundle rb;
    public static final String DEFAULT_LANGUAGE = "ru";
    public static final String LOCALE_CHANGED_PROPERTY = "changed";

    public static final PropertyChangeSupport pcs = new PropertyChangeSupport(LocalisationManager.class);


    public static final Locale RU_LOCALE = new Locale("ru", "RU");
    public static final Locale NO_LOCALE = new Locale("no", "NO");
    public static final Locale FR_LOCALE = new Locale("fr", "FR");
    public static final Locale EV_SV_LOCALE = new Locale("es", "SV");


    static {
        setLocale(RU_LOCALE);
    }

    public static String getString(String key) {
        if (rb == null) {
            System.err.println("ResourceBundle (rb) не инициализирован. Ключ: " + key);
            return "!" + key + "!";
        }
        try {
            return rb.getString(key);
        } catch (MissingResourceException e) {
            System.err.println("Ключ не найден в ResourceBundle для локали " + locale + ": " + key);
            return "?" + key + "?";
        } catch (Exception e) {
            e.printStackTrace();
            return "!" + key + "!";
        }
    }

    public static void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public static void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }


    public static Locale getLocale() {
        return locale;
    }

    public static void setLocale(Locale newLocale) {
        Locale oldLocale = locale;
        locale = newLocale;
        try {
            rb = ResourceBundle.getBundle("messages", locale, LocalisationManager.class.getClassLoader());
            pcs.firePropertyChange(LOCALE_CHANGED_PROPERTY, oldLocale, locale);
        } catch (Exception e) {
            System.err.println("Ошибка загрузки ResourceBundle для локали " + newLocale + ": " + e.getMessage()); // Добавим логгирование
            if (oldLocale != null && rb == null) {
                locale = oldLocale;
                rb = ResourceBundle.getBundle("messages", locale, LocalisationManager.class.getClassLoader());
                System.err.println("Локализация возвращена на " + locale);
            } else if (rb == null) {
                try {
                    System.err.println("Попытка загрузить RU_LOCALE по умолчанию...");
                    rb = ResourceBundle.getBundle("messages", RU_LOCALE, LocalisationManager.class.getClassLoader());
                    locale = RU_LOCALE;
                    pcs.firePropertyChange(LOCALE_CHANGED_PROPERTY, oldLocale, locale);
                    System.err.println("Установлена стандартная локализация " + locale);
                } catch (Exception ex) {
                    System.err.println("КРИТИЧЕСКАЯ ОШИБКА: Не удалось загрузить ResourceBundle даже для RU_LOCALE: " + ex.getMessage());
                }
            }
        }
    }
}
