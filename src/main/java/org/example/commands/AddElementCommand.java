package org.example.commands;

import org.example.classes.Car;
import org.example.classes.Coordinates;
import org.example.classes.HumanBeing;
import org.example.enums.Mood;
import org.example.enums.WeaponType;
import org.example.exceptions.IllegalScriptLine;
import org.example.utils.InputFieldBuilder;

import java.util.Scanner;

/**
 * @author Dimasavr
 */

public class AddElementCommand extends Command {

    private String desc = "Добавление нового элемента в коллекцию";
    private String name = "add";

    private Scanner sc;

    int expected = 0;

    public AddElementCommand() {
        super();
        this.nameOfCommand = name;
        this.numberOfArgs = expected;
        this.description = desc;
    }

    @Override
    public void bodyOfCommand(String line) {
        HumanBeing toAdd = createNoAddNew(sc, false);
        cm.add(toAdd);
    }

    //    @Override
//    public void execute(String argument) {
//        String[] arguments = argument.split(" ");
//        if (arguments.length != expected) {
//            throw new IncorrectArgsNumber(expected);
//        }
//    }
//
//    @Override
//    public void execute() {
////        HumanBeing addable = createNoAdd();
//            HumanBeing addable = createNoAddNew(sc, false);
//        cm.add(addable);
//    }
//
//    @Override
//    public void execute(Scanner sc) {
//        HumanBeing addable = createNoAddFromFile(sc);
//        cm.add(addable);
//    }

    /**
     * Создаёт новый элемент без добавления его в коллекцию
     * используется для работы из консоли
     * @return HumanBeing
     */

//    public HumanBeing createNoAdd(){
//        boolean b = true;
//        HumanBeing addable = new HumanBeing(b);
//
//        System.out.println("Начато добавление нового элемента в коллекцию");
//        System.out.println("Небольшая справка: пустая строка приравнивается к значению null :)");
//        System.out.println("Введите имя, учтите, что оно не должно быть null");
//        while (true){
//
//            try {
//                String input = sc.nextLine();
//                if (input == null || input.trim().isEmpty()) {
//                    throw new NullStringException();
//                } else {
//                    addable.setName(input);
//                    break;
//                }
//            } catch (NullStringException e) {
//                System.out.println(e.getMessage());
//                System.out.println("Перевведите значение");
//            }
//        }
//
//        Coordinates coordinates = new Coordinates(b);
//        addable.setCoordinates(coordinates);
//
//        System.out.println("Координаты заданы, идём дальше");
//        while (true){
//            try {
//                System.out.println("Введите реальный ли это герой, false, если нет и true если да");
//                String input = sc.nextLine();
//                if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false")){
//                    addable.setRealHero(Boolean.parseBoolean(input));
//                    break;
//                }
//            } catch (InputMismatchException e){
//                System.out.println("Ошибка ввода");
//            }
//        }
//        System.out.println("Про реальность мы узнали, идём дальше");
//        while (true){
//            try {
//                System.out.println("Введите есть ли у этого персонажа зубная боль false, если нет и true если да");
//                String input = sc.nextLine();
//                if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false")){
//                    addable.setHasToothpick(Boolean.parseBoolean(input));
//                    break;
//                }
//            } catch (InputMismatchException e){
//                System.out.println("Ошибка ввода зубной боли");
//            } catch (NullStringException e){
//                System.out.println(e.getMessage());
//                System.out.println("Перевведите значение");
//            }
//        }
//        System.out.println("С зубной болью разобрались, идём дальше");
//        while (true){
//            try {
//                System.out.println("Введите скорость удара, пустой ввод не разрешён");
//                String input = sc.nextLine();
//                Long is = Long.parseLong(input);
//                addable.setImpactSpeed(is);
//                break;
////                Long is = sc.nextLong();
////                if (is == null){
////                    throw new NullStringException();
////                }
////                addable.setImpactSpeed(is);
////                sc.nextLine();
////                break;
//            } catch (InputMismatchException | NumberFormatException e){
//                System.out.println("Ошибка ввода значения числа");
//            } catch (NullStringException e){
//                System.out.println(e.getMessage());
//            }
//        }
//        System.out.println("Со скоростью удара разобрались, идём дальше");
//        System.out.println("Введите название саундтрека, пустой ввод не разрешён");
//        while (true) {
//            try {
//                String input = sc.nextLine();
//                if (input == null || input.trim().isEmpty()) {
//                    throw new NullStringException();
//                } else {
//                    addable.setSoundtrackName(input);
//                    break;
//                }
//            } catch (NullStringException e) {
//                System.out.println(e.getMessage());
//                System.out.println("Перевведите значение");
//            }
//        }
//        System.out.println("Название саундтрека ввели, идём дальше");
//        System.out.println("Введите типы оружия, вот варианты: " + WeaponType.getV());
//        while (sc.hasNext()){
//            String input = sc.nextLine();
//            try {
//                WeaponType wt = WeaponType.valueOf(input.toUpperCase());
//                addable.setWeaponType(wt);
//                break;
//            } catch (IllegalArgumentException e){
//                System.out.println("Значение enum введено неверно, попробуй ещё раз");
//            }
//        }
//        System.out.println("С типом оружия закончили, идём дальше");
//        System.out.println("Введите варианты настроения, вот варианты: " + Mood.getV());
//        while (sc.hasNext()){
//            String input = sc.nextLine();
//            try {
//                Mood m = Mood.valueOf(input.toUpperCase());
//                addable.setMood(m);
//                break;
//            } catch (IllegalArgumentException e){
//                System.out.println("Значение enum введено неверно, попробуй ещё раз");
//            }
//        }
//        System.out.println("С настроением закончили, идём дальше");
//
//
//        Car car = new Car(b);
//        addable.setCar(car);
//
//        System.out.println("Задание элемента завершено");
//
//        return addable;
//    }

    /**
     * Работает для создания объекта при вводе в строке
     * @param scanner
     * @return
     */
//    public HumanBeing createNoAddFromFile(Scanner scanner) {
//        boolean b = true;
//        HumanBeing addable = new HumanBeing(b);
//        try {
//            while (true) {
//
//                try {
//                    String input = scanner.nextLine();
//                    if (input == null || input.trim().isEmpty()) {
////                    throw new NullStringException();
//                        throw new WrongAddLineInScriptException();
//                    } else {
//                        addable.setName(input);
//                        break;
//                    }
//                } catch (NullStringException e) {
//                    System.out.println(e.getMessage());
//                    System.out.println("Перевведите значение");
//                }
//            }
//
//            Coordinates coordinates = new Coordinates(b, scanner);
//            addable.setCoordinates(coordinates);
//
////        System.out.println("Координаты заданы, идём дальше");
//            while (true) {
//                try {
////                System.out.println("Введите реальный ли это герой, false, если нет и true если да");
//                    String input = scanner.nextLine();
//                    if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false")) {
//                        addable.setRealHero(Boolean.parseBoolean(input));
//                        break;
//                    } else {
//                        throw new WrongAddLineInScriptException();
//                    }
//                } catch (InputMismatchException e) {
//                    System.out.println("Ошибка ввода");
//                }
//            }
////        System.out.println("Про реальность мы узнали, идём дальше");
//            while (true) {
//                try {
////                System.out.println("Введите есть ли у этого персонажа зубная боль false, если нет и true если да");
//                    String input = scanner.nextLine();
//                    if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false")) {
//                        addable.setHasToothpick(Boolean.parseBoolean(input));
//                        break;
//                    } else {
//                        throw new WrongAddLineInScriptException();
//                    }
//                } catch (InputMismatchException e) {
//                    System.out.println("Ошибка ввода зубной боли");
//                } catch (NullStringException e) {
//                    System.out.println(e.getMessage());
//                    System.out.println("Перевведите значение");
//                }
//            }
////        System.out.println("С зубной болью разобрались, идём дальше");
//            while (true) {
//                try {
////                System.out.println("Введите скорость удара, пустой ввод не разрешён");
//                    String input = scanner.nextLine();
//                    Long is = Long.parseLong(input);
//                    addable.setImpactSpeed(is);
//                    break;
//                } catch (InputMismatchException | NumberFormatException e) {
////                    System.out.println("Ошибка ввода значения числа");
//                    throw new WrongAddLineInScriptException();
//                } catch (NullStringException e) {
//                    System.out.println(e.getMessage());
//                    throw new WrongAddLineInScriptException();
//                }
//            }
////        System.out.println("Со скоростью удара разобрались, идём дальше");
////        System.out.println("Введите название саундтрека, пустой ввод не разрешён");
//            while (true) {
//                try {
//                    String input = scanner.nextLine();
//                    if (input == null || input.trim().isEmpty()) {
////                        throw new NullStringException();
//                        throw new WrongAddLineInScriptException();
//                    } else {
//                        addable.setSoundtrackName(input);
//                        break;
//                    }
//                } catch (NullStringException e) {
//                    System.out.println(e.getMessage());
//                    System.out.println("Перевведите значение");
//                }
//            }
////        System.out.println("Название саундтрека ввели, идём дальше");
////        System.out.println("Введите типы оружия, вот варианты: " + WeaponType.getV());
//            while (sc.hasNext()) {
//                String input = scanner.nextLine();
//                try {
//                    WeaponType wt = WeaponType.valueOf(input.toUpperCase());
//                    addable.setWeaponType(wt);
//                    break;
//                } catch (IllegalArgumentException e) {
//                    System.out.println("Значение enum введено неверно, попробуй ещё раз");
//                    throw new WrongAddLineInScriptException();
//                }
//            }
////        System.out.println("С типом оружия закончили, идём дальше");
////        System.out.println("Введите варианты настроения, вот варианты: " + Mood.getV());
//            while (scanner.hasNext()) {
//                String input = scanner.nextLine();
//                try {
//                    Mood m = Mood.valueOf(input.toUpperCase());
//                    addable.setMood(m);
//                    break;
//                } catch (IllegalArgumentException e) {
//                    System.out.println("Значение enum введено неверно, попробуй ещё раз");
//                    throw new WrongAddLineInScriptException();
//                }
//            }
////        System.out.println("С настроением закончили, идём дальше");
//
//
//            Car car = new Car(b, scanner);
//            addable.setCar(car);
//
////        System.out.println("Задание элемента завершено");
//
//            return addable;
//        } catch (WrongAddLineInScriptException e) {
//            System.out.println(e.getMessage());
//            System.out.println("Добавление из файла прервано");
//        }
//        return addable;
//    }

//        System.out.println("Начато добавление нового элемента в коллекцию");
//        System.out.println("Небольшая справка: пустая строка приравнивается к значению null :)");
//        System.out.println("Введите имя, учтите, что оно не должно быть null");


    public HumanBeing createNoAddNew(Scanner scanner,  boolean neededToUseYourScanner) {

        if (neededToUseYourScanner) {
            this.sc = scanner;
        } else {
            this.sc = new Scanner(System.in);
        }

        String name = InputFieldBuilder.build(sc, String.class)
                .start("Начат ввод имени")
                .inputParams("имя не может быть null")
                .againMsg("Перевведите имя")
                .againMsgParams("имя введено неправильно")
                .end("Задание имени завершено")
                .build();

        Long x = InputFieldBuilder.build(sc, Long.class)
                .start("Введите координату x")
                .inputParams("Поле не может быть пустым")
                .againMsg("Перевведите значение")
                .againMsgParams("Пустой ввод не разрешён")
                .end("x задали, идём дальше")
                .build();
        Long y = InputFieldBuilder.build(sc, Long.class)
                .start("Введите координату y")
                .inputParams("Поле не может быть пустым")
                .againMsg("Перевведите значение")
                .againMsgParams("Пустой ввод не разрешён")
                .end("y задали, идём дальше")
                .build();

        Coordinates c = new Coordinates(x, y);

        Boolean rh = InputFieldBuilder.build(sc, Boolean.class)
                .start("Введите является ли персонаж героем")
                .inputParams("true если правда и false если неправда")
                .againMsg("Перевведите значение в соответствии с параметрами")
                .againMsgParams("")
                .end("Узнали, про героизм, идём дальше")
                .build();
        Boolean tp = InputFieldBuilder.build(sc, Boolean.class)
                .start("Введите есть ли у персонажа зубная боль")
                .inputParams("true если правда и false если неправда")
                .againMsg("Перевведите значение в соответствии с параметрами")
                .againMsgParams("")
                .end("Узнали про зубную боль, идём дальше")
                .build();
        Long is = InputFieldBuilder.build(sc, Long.class)
                .start("Введите скорость удара")
                .inputParams("Поле не может быть пустым")
                .againMsg("Перевведите значение")
                .againMsgParams("Пустой ввод не разрешён")
                .end("Скорость удара узнали, идём дальше")
                .build();
        String sName = InputFieldBuilder.build(sc, String.class)
                .start("Введите название саундтрека")
                .inputParams("Значение не может быть null")
                .againMsg("Перевведите значение")
                .againMsgParams("")
                .end("Название саундтрека узнали, идём дальше")
                .build();
        WeaponType wt = InputFieldBuilder.build(sc, WeaponType.class)
                .start("Введите тип оружия")
                .inputParams("Вот список возможных вариантов: " + WeaponType.getV())
                .againMsg("Перевведите значение")
                .againMsgParams("")
                .end("Тип оружия задан")
                .build();
        Mood m = InputFieldBuilder.build(sc, Mood.class)
                .start("Введите настроение")
                .inputParams("Вот список возможных вариантов: " + Mood.getV())
                .againMsg("Перевведите значение")
                .againMsgParams("")
                .end("Настроение задано")
                .build();

        String nameCar = InputFieldBuilder.build(sc, String.class)
                .start("Начат ввод имени машины")
                .inputParams("имя не может быть null")
                .againMsg("Перевведите имя")
                .againMsgParams("имя введено неправильно")
                .end("Задание имени завершено")
                .build();
        Boolean cool = InputFieldBuilder.build(sc, Boolean.class)
                .start("Введите крута ли машина")
                .inputParams("true если правда и false если неправда")
                .againMsg("Перевведите значение в соответствии с параметрами")
                .againMsgParams("")
                .end("Узнали про крутость, идём дальше")
                .build();

        Car car = new Car(nameCar, cool);

        System.out.println("Задание элемента завершено");

        HumanBeing hb = new HumanBeing(name, c, rh, tp, is, sName, wt, m, car);

        return hb;
    }

    public HumanBeing createNoAddNewScanner(Scanner scanner) {

        scanner.nextLine();

        HumanBeing hb;

        try {
            String name = scanner.nextLine().trim();

            Long x = Long.parseLong(scanner.nextLine().trim());
            Long y = Long.parseLong(scanner.nextLine().trim());
            Coordinates c = new Coordinates(x, y);

            Boolean rh = Boolean.parseBoolean(scanner.nextLine().trim());
            Boolean tp = Boolean.parseBoolean(scanner.nextLine().trim());
            Long is = Long.parseLong(scanner.nextLine().trim());
            String sName = scanner.nextLine();
            WeaponType wt = WeaponType.valueOf(scanner.nextLine().trim());
            Mood m = Mood.valueOf(scanner.nextLine().trim());

            String nameCar = scanner.nextLine();
            Boolean cool = Boolean.parseBoolean(scanner.nextLine().trim());
            Car car = new Car(nameCar, cool);

            hb = new HumanBeing(name, c, rh, tp, is, sName, wt, m, car);
            return hb;
        } catch (IllegalArgumentException e) {
            throw new IllegalScriptLine(e.getMessage());
        }
    }

}
