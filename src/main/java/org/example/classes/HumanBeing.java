package org.example.classes;

import org.example.enums.*;
import org.example.utils.*;

import java.util.Date;

public class HumanBeing implements Comparable<HumanBeing>{
    private int id;
    private String name; // not null
    private Coordinates coordinates; // not null
    private java.util.Date creationDate; // not null
    private boolean realHero;
    private boolean hasToothpick;
    private Long impactSpeed; // not null
    private String soundtrackName; // not null
    private WeaponType weaponType; // not null
    private Mood mood; // not null
    private Car car; // not null

    public HumanBeing() {
        creationDate = new java.util.Date();
        id = IDGen.gen(String.valueOf(creationDate));
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public boolean isRealHero() {
        return realHero;
    }

    public boolean isHasToothpick() {
        return hasToothpick;
    }

    public Long getImpactSpeed() {
        return impactSpeed;
    }

    public String getSoundtrackName() {
        return soundtrackName;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public Mood getMood() {
        return mood;
    }

    public Car getCar() {
        return car;
    }

    @Override
    public int compareTo(HumanBeing comp) {
        return (this.mood.getPointOfHappy() - comp.mood.getPointOfHappy()) + (this.weaponType.getDegreeOfCool() - comp.weaponType.getDegreeOfCool());
    }
}