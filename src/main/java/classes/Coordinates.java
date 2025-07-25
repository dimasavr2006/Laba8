package classes;

import com.fasterxml.jackson.annotation.JsonProperty;
import run.Main;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Dimasavr
 */

public class Coordinates {

    @JsonProperty("x")
    private long x;
    @JsonProperty("y")
    private long y;

    Scanner sc = Main.sc;

    public Coordinates(long x, long y) {
        this.x = x;
        this.y = y;
    }
    public Coordinates (boolean b){
        while (true) {
            System.out.println("Введите координату x:");
            String xxx = sc.nextLine();
            try {
                this.x = Long.parseLong(xxx.trim());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Поле введено неверно, попробуйте ещё раз");
            }
        }
        while (true){
            System.out.println("Введите координату y:");
            String yyy = sc.nextLine();
            try {
                this.y = Long.parseLong(yyy.trim());
                break;
            } catch (InputMismatchException e){
                System.out.println("Поле введено неверно, попробуйте ещё раз");
            }
        }
        Coordinates c = new Coordinates(x,y);
    }

    public Coordinates (boolean b, Scanner sc) {
        while (true) {
            System.out.println("Введите координату x:");
            String xxx = sc.nextLine();
            try {
                this.x = Long.parseLong(xxx.trim());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Поле введено неверно, попробуйте ещё раз");
            }
        }
        while (true){
            System.out.println("Введите координату y:");
            String yyy = sc.nextLine();
            try {
                this.y = Long.parseLong(yyy.trim());
                break;
            } catch (InputMismatchException e){
                System.out.println("Поле введено неверно, попробуйте ещё раз");
            }
        }
        Coordinates c = new Coordinates(x,y);
    }
    public Coordinates() {
    }

//    public Coordinates createFromConsole() {
//        Scanner sc = new Scanner(System.in);
//        Long x = InputFieldBuilder.build(sc, Long.class)
//                .start("Введите координату x")
//                .inputParams("Поле не может быть пустым")
//                .againMsg("Перевведите значение")
//                .againMsgParams("Пустой ввод не разрешён")
//                .end("x задали, идём дальше")
//                .build();
//        Long y = InputFieldBuilder.build(sc, Long.class)
//                .start("Введите координату y")
//                .inputParams("Поле не может быть пустым")
//                .againMsg("Перевведите значение")
//                .againMsgParams("Пустой ввод не разрешён")
//                .end("y задали, идём дальше")
//                .build();
//        Coordinates c = new Coordinates(x,y);
//        return c;
//    }

    /**
     * Возвращает координату x
     * @return x
     */
    public long getX() {
        return x;
    }

    /**
     * Задаёт координату x
     * @param x
     */
    public void setX(long x) {
        this.x = x;
    }

    /**
     * Возвращает координату y
     * @return y
     */
    public long getY() {
        return y;
    }

    /**
     * Задаёт координату y
     * @param y
     */
    public void setY(long y) {
        this.y = y;
    }

    /**
     * Возвращает описание координат
     * @return Строка координат
     */
    @Override
    public String toString() {
        return "Coordinates: " +
                "y = " + y +
                ", x = " + x;
    }
}
