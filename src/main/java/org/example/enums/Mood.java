package org.example.enums;

public enum Mood {
    SADNESS(2),
    SORROW(1),
    GLOOM(0),
    CALM(3);

    private final int pointOfHappy;

    Mood(int pointOfHappy) {
        this.pointOfHappy = pointOfHappy;
    }

    public int getPointOfHappy() {
        return pointOfHappy;
    }
}
