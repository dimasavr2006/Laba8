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

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setRealHero(boolean realHero) {
        this.realHero = realHero;
    }

    public void setHasToothpick(boolean hasToothpick) {
        this.hasToothpick = hasToothpick;
    }

    public void setImpactSpeed(Long impactSpeed) {
        this.impactSpeed = impactSpeed;
    }

    public void setSoundtrackName(String soundtrackName) {
        this.soundtrackName = soundtrackName;
    }

    public void setWeaponType(WeaponType weaponType) {
        this.weaponType = weaponType;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public int compareTo(HumanBeing comp) {
        return (this.mood.getPointOfHappy() - comp.mood.getPointOfHappy()) + (this.weaponType.getDegreeOfCool() - comp.weaponType.getDegreeOfCool());
    }

    @Override
    public String toString() {
        return "id: " + id + "\nname: " + name + "\ncoordinates: " + coordinates + "\ncreationDate: " + creationDate;
    }
}