package org.example.classes;

import com.fasterxml.jackson.annotation.*;
import org.example.enums.*;
import org.example.utils.*;

import java.time.*;
import java.time.format.*;
import java.util.*;

public class HumanBeing implements Comparable<HumanBeing>{

    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name; // not null

    private Coordinates coordinates; // not null

    @JsonProperty("creationDate")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate creationDate; // not null

    @JsonProperty("timeOfCreation")
    String timeOfCreation;

    @JsonProperty("realHero")
    private boolean realHero;
    @JsonProperty("hasToothpick")
    private boolean hasToothpick;
    @JsonProperty("impactSpeed")
    private Long impactSpeed; // not null
    @JsonProperty("soundtrackName")
    private String soundtrackName; // not null
    @JsonProperty("weaponType")
    private WeaponType weaponType; // not null
    @JsonProperty("mood")
    private Mood mood; // not null

    private Car car; // not null


    public HumanBeing (){}

    public HumanBeing(boolean b) {
        creationDate = LocalDate.now();
    }

    public HumanBeing(int id, String name, Coordinates coordinates, LocalDate creationDate, boolean realHero, boolean hasToothpick, Long impactSpeed, String soundtrackName, WeaponType weaponType, Mood mood, Car car) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.realHero = realHero;
        this.hasToothpick = hasToothpick;
        this.impactSpeed = impactSpeed;
        this.soundtrackName = soundtrackName;
        this.weaponType = weaponType;
        this.mood = mood;
        this.car = car;
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

    public LocalDate getCreationDate() {
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

    public void setCreationDate(LocalDate creationDate) {
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

    public String getTimeOfCreation() {
        return timeOfCreation;
    }

    public void setTimeOfCreation(String timeOfCreation) {
        this.timeOfCreation = timeOfCreation;
    }

    @Override
    public int compareTo(HumanBeing comp) {
        return (this.mood.getPointOfHappy() - comp.mood.getPointOfHappy()) + (this.weaponType.getDegreeOfCool() - comp.weaponType.getDegreeOfCool());
    }

    @Override
    public String toString() {
        return "ID: " + id + "\nCreationDate: " + creationDate + "\nName: " + name + "\n" + coordinates + "\nCreationDate: " + creationDate + "\nRealHero: " + realHero + "\nHasToothpick: " + hasToothpick + "\nImpactSpeed: " + impactSpeed + "\nSoundtrackName: " + soundtrackName + "\nWeaponType: " + weaponType + "\nMood: " + mood + "\n" + car;
    }
}