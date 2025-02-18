package org.example.classes;

import org.example.exceptions.*;

public class Car {
    private String name;
    private boolean cool;

    public Car(String name, boolean cool) {
        if (name == null) {
            throw new WrongNameSizeException();
        } else {
            this.name = name;
        }
        this.cool = cool;
    }
}
