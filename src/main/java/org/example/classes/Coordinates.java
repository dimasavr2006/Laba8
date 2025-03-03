package org.example.classes;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.Main;

import java.util.InputMismatchException;
import java.util.Scanner;

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
        System.out.println("Начинается ввод координат");
        while (true){
            try {
                System.out.println("Введите координату x:");
                Long xxx = sc.nextLong();
                this.x = xxx;
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
                this.y = yyy;
                break;
            } catch (InputMismatchException e){
                System.out.println("Поле введено неверно, попробуйте ещё раз");
            } finally {
                sc.nextLine();
            }
        }
        Coordinates c = new Coordinates(x,y);
    }
    public Coordinates() {}

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

    @Override
    public String toString() {
        return "Coordinates: " +
                "y = " + y +
                ", x = " + x;
    }
}
