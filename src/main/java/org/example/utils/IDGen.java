package org.example.utils;

/**
 * @author Dimasavr
 */

public class IDGen {

    static int num = 0;

    /**
     * Генерируеь айди
     * @return id
     */
    public static int gen(){
        num++;
        return num;
    }

}
