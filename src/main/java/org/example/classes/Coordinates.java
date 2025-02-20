package org.example.classes;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Coordinates {
    private long x;
    private long y;

    Scanner sc = new Scanner(System.in);

    public Coordinates(long x, long y) {
        this.x = x;
        this.y = y;
    }
    public Coordinates() {
        System.out.println("Начинается ввод координат");
        while (true){
            try {
                System.out.println("Введите координату x:");
                Long xxx = sc.nextLong();
                setX(xxx);
                break;
            } catch (InputMismatchException e){
                System.out.println("Поле введено неверно, попробуйте ещё раз");
            } finally {
                sc.nextLine();
            }
        }
        while (true){
            try {
                System.out.println("Введите координату y:");
                Long yyy = sc.nextLong();
                setY(yyy);
                break;
            } catch (InputMismatchException e){
                System.out.println("Поле введено неверно, попробуйте ещё раз");
            } finally {
                sc.nextLine();
            }
        }
    }

    public long getX() {
        return x;
    }

    public void setX(long x) {
        this.x = x;
    }

    public long getY() {
        return y;
    }

    public void setY(long y) {
        this.y = y;
    }
}
