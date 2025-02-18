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
    public int compareTo(HumanBeing o2) {
        boolean hero1 = this.isRealHero();
        boolean hero2 = o2.isRealHero();
        int res = 0;

        if ((hero1 && hero2) || (!hero1 && !hero2)) {
            res = 0;

            switch (neededComp(o2)){
                case 0: res++;
                case 1: res  += 0;
                case -1: res -= 1;
            }

        } else if (hero1 == true && hero2 == false) {
            res = 2;
            switch (neededComp(o2)){
                case 0: res++;
                case 1: res += 0;
                case -1: res -= 1;
            }
        }
        else if (hero1 == false && hero2 == true) {
            res = -2;
            switch (neededComp(o2)){
                case 0: res++;
                case 1: res += 0;
                case -1: res -= 1;
            }
        }
        return res;
    }

    private int neededComp(HumanBeing o2){
        Mood m1 = this.getMood();
        Mood m2 = o2.getMood();
        int returned = 0;
        if (m1.getPointOfHappy() > m2.getPointOfHappy()){
            returned = 1;
        } else if (m1.getPointOfHappy() == m2.getPointOfHappy()){
            returned = 0;
        } else if (m1.getPointOfHappy() < m2.getPointOfHappy()) {
            returned = -1;
        }
        return returned;
    }

}