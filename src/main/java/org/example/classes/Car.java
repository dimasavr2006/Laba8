package org.example.classes;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.exceptions.*;

import java.util.Scanner;

public class Car {
    @JsonProperty("name")
    private String name;
    @JsonProperty("cool")
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
    public Car (boolean b){
        while (true){
            try {
                System.out.println("Введите название машины, учтите, что ввод пустой строки не разрешен");
                String sName = sc.nextLine();
                if (sName == "" || sName == " " || sName == null){
                    throw new NullStringException();
                }
                this.name = sName;
                break;
            } catch (NullStringException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Car (){}

    @Override
    public String toString() {
        return "Car: " +
                "cool = " + cool +
                ", name = " + name;
    }
}
