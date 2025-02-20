package org.example.utils;

import java.util.Random;

public class IDGen {

    public static int gen(String string){
        String first = String.valueOf(string.hashCode());
        Random rand = new Random();
        String result = String.valueOf(first.hashCode());
        String r2 = result;
        for (int i = 0; i < rand.nextInt(3, 15); i++) {
            r2 = String.valueOf(result.hashCode());
            result = r2;
        }
        int numRes = Integer.parseInt(result);
        return numRes;
    }

}
