package org.example.classes;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.exceptions.NullStringException;
import org.example.exceptions.WrongAddLineInScriptException;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Dimasavr
 */

public class Car {
    /**
     * Класс Car
     * Хранится в поле car элементов коллекции HumanBeing
     */
    @JsonProperty("name")
    /**
     * Поле имени машины
     * Тип данных String
     */
    private String name;
    @JsonProperty("cool")
    /**
     * Поле крутости машины
     * Тип данных boolean
     */
    private boolean cool;


    public Car(String name, boolean cool) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя машины не может быть null или пустой");
        }
        this.name = name;
        this.cool = cool;
    }

    public Car (boolean b){
//        Scanner sc = Main.sc;
        Scanner sc = new Scanner(System.in);
        while (true){
            try {
                System.out.println("Введите название машины, учтите, что ввод пустой строки не разрешен");
                String sName = sc.nextLine();
                if (sName == null || sName.trim().isEmpty()) {
                    throw new NullStringException();
                }
                this.name = sName;
                break;
            } catch (NullStringException e) {
                System.out.println(e.getMessage());
            }
        }
        while (true){
            try {
                System.out.println("Введите крута ли машина, true если да");
                String input = sc.nextLine();
                if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false")) {
                    this.cool = Boolean.parseBoolean(input);
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Ошибка ввода");
            } catch (NullStringException e) {
                System.out.println(e.getMessage());
                System.out.println("Перевведите значение");
            }
        }
    }

    public Car (boolean b, Scanner sc){
//        Scanner sc = Main.sc;
        while (true){
            try {
                System.out.println("Введите название машины, учтите, что ввод пустой строки не разрешен");
                String sName = sc.nextLine();
                if (sName == null || sName.trim().isEmpty()) {
                    throw new NullStringException();
                }
                this.name = sName;
                break;
            } catch (NullStringException e) {
                System.out.println(e.getMessage());
                throw new WrongAddLineInScriptException();
            }
        }
        while (true){
            try {
                System.out.println("Введите крута ли машина, true если да");
                String input = sc.nextLine();
                if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false")) {
                    this.cool = Boolean.parseBoolean(input);
                    break;
                } else {
                    throw new WrongAddLineInScriptException();
                }
            } catch (InputMismatchException e) {
                System.out.println("Ошибка ввода");
            } catch (NullStringException e) {
                System.out.println(e.getMessage());
                System.out.println("Перевведите значение");
            }
        }
    }

    /**
     * Возвращает имя машины
     * @return Имя
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает крутость машины
     * @return Крутость
     */
    public boolean isCool() {
        return cool;
    }

    public Car (){

    }

    /**
     * Возвращает описание машины
     * @return Строка машины
     */
    @Override
    public String toString() {
        return "Car: " +
                "cool = " + cool +
                ", name = " + name;
    }
}
