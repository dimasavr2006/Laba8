package utils;

import classes.Car;
import classes.Coordinates;
import classes.HumanBeing;
import enums.Mood;
import enums.WeaponType;

import java.util.Scanner;

public class BuildersOfElement {
    public HumanBeing createNoAdd(boolean yourInput, Scanner scanner, String line) {

        if (yourInput) {
                Scanner sc = new Scanner(System.in);
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

        } else {
            DocumentFieldBuilder dfb = new DocumentFieldBuilder();
            HumanBeing hb = dfb.build(scanner, line);
            return hb;
        }
    }
}
