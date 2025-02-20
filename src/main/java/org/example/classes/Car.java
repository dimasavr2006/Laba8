package org.example.classes;

import org.example.exceptions.*;

import java.util.Scanner;

public class Car {
    private String name;
    private boolean cool;

    Scanner sc = new Scanner(System.in);

    public Car(String name, boolean cool) {
        if (name == null) {
            throw new NullStringException();
        } else {
            this.name = name;
        }
        this.cool = cool;
    }
    public Car (){
        while (true){
            try {
                System.out.println("Введите название машины, учтите, что ввод пустой строки не разрешен");
                String n = sc.nextLine();
                if (n == null){
                    throw new NullStringException();
                }
                this.name = n;
                break;
            } catch (NullStringException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
