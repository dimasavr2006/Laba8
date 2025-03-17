package org.example.commands;

import org.example.classes.Car;
import org.example.classes.Coordinates;
import org.example.classes.HumanBeing;
import org.example.enums.Mood;
import org.example.enums.WeaponType;
import org.example.exceptions.IncorrectArgsNumber;
import org.example.exceptions.NullStringException;
import org.example.exceptions.WrongAddLineInScriptException;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Dimasavr
 */

public class AddElementCommand extends Command {

    private String desc = "Добавление нового элемента в коллекцию";
    private String name = "add";

    int expected = 0;

    Scanner sc = new Scanner(System.in);

    public AddElementCommand() {
        this.nameOfCommand = name;
        this.description = desc;
    }

    @Override
    public void execute(String args) {
        String[] arguments = args.split(" ");
        if (arguments.length != expected) {
            throw new IncorrectArgsNumber(expected);
        }
    }

    @Override
    public void execute() {
        HumanBeing addable = createNoAdd();

        cm.add(addable);
    }

    @Override
    public void execute(Scanner sc) {
        HumanBeing addable = createNoAddFromFile(sc);
        cm.add(addable);
    }

    /**
     * Создаёт новый элемент без добавления его в коллекцию
     * Используется для работы из консоли
     * @return HumanBeing
     */

    public HumanBeing createNoAdd(){
        boolean b = true;
        HumanBeing addable = new HumanBeing(b);

        System.out.println("Начато добавление нового элемента в коллекцию");
        System.out.println("Небольшая справка: пустая строка приравнивается к значению null :)");
        System.out.println("Введите имя, учтите, что оно не должно быть null");
        while (true){

            try {
                String input = sc.nextLine();
                if (input == null || input.trim().isEmpty()) {
                    throw new NullStringException();
                } else {
                    addable.setName(input);
                    break;
                }
            } catch (NullStringException e) {
                System.out.println(e.getMessage());
                System.out.println("Перевведите значение");
            }
        }

        Coordinates coordinates = new Coordinates(b);
        addable.setCoordinates(coordinates);

        System.out.println("Координаты заданы, идём дальше");
        while (true){
            try {
                System.out.println("Введите реальный ли это герой, false, если нет и true если да");
                String input = sc.nextLine();
                if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false")){
                    addable.setRealHero(Boolean.parseBoolean(input));
                    break;
                }
            } catch (InputMismatchException e){
                System.out.println("Ошибка ввода");
            }
        }
        System.out.println("Про реальность мы узнали, идём дальше");
        while (true){
            try {
                System.out.println("Введите есть ли у этого персонажа зубная боль false, если нет и true если да");
                String input = sc.nextLine();
                if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false")){
                    addable.setHasToothpick(Boolean.parseBoolean(input));
                    break;
                }
            } catch (InputMismatchException e){
                System.out.println("Ошибка ввода зубной боли");
            } catch (NullStringException e){
                System.out.println(e.getMessage());
                System.out.println("Перевведите значение");
            }
        }
        System.out.println("С зубной болью разобрались, идём дальше");
        while (true){
            try {
                System.out.println("Введите скорость удара, пустой ввод не разрешён");
                String input = sc.nextLine();
                Long is = Long.parseLong(input);
                addable.setImpactSpeed(is);
                break;
//                Long is = sc.nextLong();
//                if (is == null){
//                    throw new NullStringException();
//                }
//                addable.setImpactSpeed(is);
//                sc.nextLine();
//                break;
            } catch (InputMismatchException | NumberFormatException e){
                System.out.println("Ошибка ввода значения числа");
            } catch (NullStringException e){
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Со скоростью удара разобрались, идём дальше");
        System.out.println("Введите название саундтрека, пустой ввод не разрешён");
        while (true) {
            try {
                String input = sc.nextLine();
                if (input == null || input.trim().isEmpty()) {
                    throw new NullStringException();
                } else {
                    addable.setSoundtrackName(input);
                    break;
                }
            } catch (NullStringException e) {
                System.out.println(e.getMessage());
                System.out.println("Перевведите значение");
            }
        }
        System.out.println("Название саундтрека ввели, идём дальше");
        System.out.println("Введите типы оружия, вот варианты: " + WeaponType.getV());
        while (sc.hasNext()){
            String input = sc.nextLine();
            try {
                WeaponType wt = WeaponType.valueOf(input.toUpperCase());
                addable.setWeaponType(wt);
                break;
            } catch (IllegalArgumentException e){
                System.out.println("Значение enum введено неверно, попробуй ещё раз");
            }
        }
        System.out.println("С типом оружия закончили, идём дальше");
        System.out.println("Введите варианты настроения, вот варианты: " + Mood.getV());
        while (sc.hasNext()){
            String input = sc.nextLine();
            try {
                Mood m = Mood.valueOf(input.toUpperCase());
                addable.setMood(m);
                break;
            } catch (IllegalArgumentException e){
                System.out.println("Значение enum введено неверно, попробуй ещё раз");
            }
        }
        System.out.println("С настроением закончили, идём дальше");


        Car car = new Car(b);
        addable.setCar(car);

        System.out.println("Задание элемента завершено");

        return addable;
    }

    /**
     * Работает для создания объекта при вводе в строке
     * @param scanner
     * @return
     */
    public HumanBeing createNoAddFromFile(Scanner scanner) {
        boolean b = true;
        HumanBeing addable = new HumanBeing(b);
        try {
            while (true) {

                try {
                    String input = scanner.nextLine();
                    if (input == null || input.trim().isEmpty()) {
//                    throw new NullStringException();
                        throw new WrongAddLineInScriptException();
                    } else {
                        addable.setName(input);
                        break;
                    }
                } catch (NullStringException e) {
                    System.out.println(e.getMessage());
                    System.out.println("Перевведите значение");
                }
            }

            Coordinates coordinates = new Coordinates(b, scanner);
            addable.setCoordinates(coordinates);

//        System.out.println("Координаты заданы, идём дальше");
            while (true) {
                try {
//                System.out.println("Введите реальный ли это герой, false, если нет и true если да");
                    String input = scanner.nextLine();
                    if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false")) {
                        addable.setRealHero(Boolean.parseBoolean(input));
                        break;
                    } else {
                        throw new WrongAddLineInScriptException();
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Ошибка ввода");
                }
            }
//        System.out.println("Про реальность мы узнали, идём дальше");
            while (true) {
                try {
//                System.out.println("Введите есть ли у этого персонажа зубная боль false, если нет и true если да");
                    String input = scanner.nextLine();
                    if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false")) {
                        addable.setHasToothpick(Boolean.parseBoolean(input));
                        break;
                    } else {
                        throw new WrongAddLineInScriptException();
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Ошибка ввода зубной боли");
                } catch (NullStringException e) {
                    System.out.println(e.getMessage());
                    System.out.println("Перевведите значение");
                }
            }
//        System.out.println("С зубной болью разобрались, идём дальше");
            while (true) {
                try {
//                System.out.println("Введите скорость удара, пустой ввод не разрешён");
                    String input = scanner.nextLine();
                    Long is = Long.parseLong(input);
                    addable.setImpactSpeed(is);
                    break;
                } catch (InputMismatchException | NumberFormatException e) {
//                    System.out.println("Ошибка ввода значения числа");
                    throw new WrongAddLineInScriptException();
                } catch (NullStringException e) {
                    System.out.println(e.getMessage());
                    throw new WrongAddLineInScriptException();
                }
            }
//        System.out.println("Со скоростью удара разобрались, идём дальше");
//        System.out.println("Введите название саундтрека, пустой ввод не разрешён");
            while (true) {
                try {
                    String input = scanner.nextLine();
                    if (input == null || input.trim().isEmpty()) {
//                        throw new NullStringException();
                        throw new WrongAddLineInScriptException();
                    } else {
                        addable.setSoundtrackName(input);
                        break;
                    }
                } catch (NullStringException e) {
                    System.out.println(e.getMessage());
                    System.out.println("Перевведите значение");
                }
            }
//        System.out.println("Название саундтрека ввели, идём дальше");
//        System.out.println("Введите типы оружия, вот варианты: " + WeaponType.getV());
            while (sc.hasNext()) {
                String input = scanner.nextLine();
                try {
                    WeaponType wt = WeaponType.valueOf(input.toUpperCase());
                    addable.setWeaponType(wt);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Значение enum введено неверно, попробуй ещё раз");
                    throw new WrongAddLineInScriptException();
                }
            }
//        System.out.println("С типом оружия закончили, идём дальше");
//        System.out.println("Введите варианты настроения, вот варианты: " + Mood.getV());
            while (scanner.hasNext()) {
                String input = scanner.nextLine();
                try {
                    Mood m = Mood.valueOf(input.toUpperCase());
                    addable.setMood(m);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Значение enum введено неверно, попробуй ещё раз");
                    throw new WrongAddLineInScriptException();
                }
            }
//        System.out.println("С настроением закончили, идём дальше");


            Car car = new Car(b, scanner);
            addable.setCar(car);

//        System.out.println("Задание элемента завершено");

            return addable;
        } catch (WrongAddLineInScriptException e) {
            System.out.println(e.getMessage());
            System.out.println("Добавление из файла прервано");
        }
        return addable;
    }

//        System.out.println("Начато добавление нового элемента в коллекцию");
//        System.out.println("Небольшая справка: пустая строка приравнивается к значению null :)");
//        System.out.println("Введите имя, учтите, что оно не должно быть null");


}
