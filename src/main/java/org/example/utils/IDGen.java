package org.example.utils;

/**
 * @author Dimasavr
 */

public class IDGen {

    static int num = 0;

    /**
     *
     * @param string
     * @return
     */
    public static int gen(String string){
        num++;
        return num;
    }

}
