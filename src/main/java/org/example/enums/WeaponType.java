package org.example.enums;

public enum WeaponType {
    HAMMER (2),
    AXE (3),
    PISTOL (4),
    SHOTGUN (5),
    KNIFE (1);

    private final int degreeOfCool;

    WeaponType(int degreeOfCool) {
        this.degreeOfCool = degreeOfCool;
    }

    public int getDegreeOfCool() {
        return degreeOfCool;
    }
}
