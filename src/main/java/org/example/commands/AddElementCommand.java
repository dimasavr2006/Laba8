package org.example.commands;

import org.example.classes.Car;
import org.example.classes.Coordinates;
import org.example.classes.HumanBeing;
import org.example.collections.CollectionManager;
import org.example.enums.Mood;
import org.example.enums.WeaponType;
import org.example.exceptions.IncorrectArgsNumber;
import org.example.exceptions.NullStringException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AddElementCommand extends Command {

    Scanner sc = new Scanner(System.in);

    private String desc = "Добавление нового элемента в коллекцию";
    private String name = "add";

    public AddElementCommand() {
        this.nameOfCommand = name;
        this.description = desc;
    }

    @Override
    public void execute(String args) {
        int expected = 0;
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
    public HumanBeing createNoAdd(){
        boolean b = true;
        HumanBeing addable = new HumanBeing(b);

        System.out.println("Начато добавление нового элемента в колллекцию");
        System.out.println("Небольшая справка: пустая строка приравнивается к значению null :)");
        System.out.println("Введите имя, учтите, что оно не должно быть null");
        while (true){
            boolean status = false;
            try{
                String tried = sc.nextLine();
                if (tried.equals(null)){
                    throw new NullStringException();
                }
                addable.setName(tried);
                break;
            } catch (NullStringException e){
                System.out.println(e.getMessage());
                System.out.println("Перевведите значение:");
            }
        }

        Coordinates coordinates = new Coordinates(b);
        addable.setCoordinates(coordinates);

        System.out.println("Координаты заданы, идём дальше");
        while (true){
            try {
                System.out.println("Введите реальный ли это герой, false, если нет и true если да");
                boolean hero = sc.nextBoolean();
                addable.setRealHero(hero);
                break;
            } catch (InputMismatchException e){
                System.out.println("Ошибка ввода");
            } finally {
                sc.nextLine();
            }
        }
        System.out.println("Про реальность мы узнали, идём дальше");
        while (true){
            try {
                System.out.println("Введите есть ли у этого персонажа зубная боль false, если нет и true если да");
                boolean teeth = sc.nextBoolean();
                addable.setHasToothpick(teeth);
                break;
            } catch (InputMismatchException e){
                System.out.println("Ошибка ввода зубной боли");
            } finally {
                sc.nextLine();
            }
        }
        System.out.println("С зубной болью разобрались, идём дальше");
        while (true){
            try {
                System.out.println("Введите скорость удара, пустой ввод не разрешён");
                Long is = sc.nextLong();
                if (is == null){
                    throw new NullStringException();
                }
                addable.setImpactSpeed(is);
                break;
            } catch (InputMismatchException e){
                System.out.println("Ошибка ввода значения числа");
            } catch (NullStringException e){
                System.out.println(e.getMessage());
            } finally {
                sc.nextLine();
            }
        }
        System.out.println("Со скоростью удара разобрались, идём дальше");
        while (true){
            try {
                System.out.println("Введите название саундтрека, пустой ввод не разрешён");
                String sName = sc.nextLine();
                if (sName == "" || sName == " " || sName == null){
                    throw new NullStringException();
                }
                addable.setName(sName);
                break;
            } catch (NullStringException e){
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Название саундтрека ввели, идём дальше");
        while (true){
            try {
                System.out.println("Введите типы оружия, вот варианты: " + WeaponType.getV());
                WeaponType wt = WeaponType.valueOf(sc.next());
                addable.setWeaponType(wt);
                break;
            } catch (IllegalArgumentException e){
                System.out.println("Значение enum введено неверно, попробуй ещё раз");
            } finally {
                sc.nextLine();
            }
        }
        System.out.println("С типом оружия закончили, идём дальше");
        while (true){
            try {
                System.out.println("Введите варианты настроения, вот варианты: " + Mood.getV());
                Mood m = Mood.valueOf(sc.next());
                addable.setMood(m);
                break;
            } catch (IllegalArgumentException e){
                System.out.println("Значение enum введено неверно, попробуй ещё раз");
            } finally {
                sc.nextLine();
            }
        }
        System.out.println("С настроением закончили, идём дальше");


        Car car = new Car(b);
        addable.setCar(car);

        System.out.println("Задание элемента завершено");

        return addable;
    }
}
