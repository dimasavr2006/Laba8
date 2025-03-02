package org.example.utils;

public class IDGen {

    static int num = 0;

    public static int gen(String string){
        int first = string.hashCode();
        num += first;
        return num;
    }

}
