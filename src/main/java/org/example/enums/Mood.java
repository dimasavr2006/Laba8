package org.example.enums;

/**
 * @author Dimasavr
 */

public enum Mood {
    SADNESS(2),
    SORROW(1),
    GLOOM(0),
    CALM(3);

    private final int pointOfHappy;

    Mood(int pointOfHappy) {
        this.pointOfHappy = pointOfHappy;
    }

    /**
     * Возвращает уровень счастья
     * @return уровень счастья
     */
    public int getPointOfHappy() {
        return pointOfHappy;
    }

    /**
     * Возвращает все состояния настроения
     * @return все состояния настроения
     */
    public static String getV(){
        String v = "";
        for (Mood wt : Mood.values()){
            v += wt.toString() + " ";
        }
        return v;
    }
}
