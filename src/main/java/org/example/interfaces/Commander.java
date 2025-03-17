package org.example.interfaces;

/**
 * @author Dimasavr
 */

public interface Commander {
    /**
     * Базовое выполенение команды с аргументами
     * @param args
     */
    void execute (String args);
    /**
     * Базовое выполнение команды без аргументов
     */
    void execute ();
//    /**
//     * Команды для выполнения из файла со скриптом и/или другими входными данными
//     */
//    void execute (String args, Scanner sc);
//    /**
//     *
//     * Команды без входных данных, но со вводом в консоль
//     */
//    void execute (Scanner sc);
    /**
     * Получение описания команды
     */
    void description();
}
