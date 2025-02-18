package org.example.utils;

import java.util.Random;

public class IDGen {

    public static int gen(String name){
        String first = String.valueOf(name.hashCode());
        Random rand = new Random();
        String result = "";
        for (int i = 0; i < rand.nextInt(3, 15); i++) {
            result = String.valueOf(first.hashCode());
        }
        int numRes = Integer.parseInt(result);
        return numRes;
    }

}
