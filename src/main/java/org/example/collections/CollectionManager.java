package org.example.collections;

import org.example.classes.*;
import org.example.enums.*;
import org.example.functions.*;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CollectionManager{

    LocalDateTime initialazed;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd:MM:yyyy HH:mm:ss");
    String timeOfCreation;

    public ArrayList<HumanBeing> collection;

    public CollectionManager() {
        initialazed = LocalDateTime.now();
        timeOfCreation = initialazed.format(formatter);

        collection = new ArrayList<>();
    }

    public void info(){
        String s = "Тип коллекции: " + collection.getClass() + ", Время создания: " + timeOfCreation + ", Размер коллекции: " + collection.size();
        System.out.println(s);
    }
    public void add(HumanBeing hb){
        collection.add(hb);
    }
    public void updateID(int id, HumanBeing hb){
        int num = 0;
        for (HumanBeing humanBeing : collection) {
            if (humanBeing.getId() == id) {
                break;
            }
            num++;
        }
        collection.set(num, hb);
    }
    public void removeById(int id){

        int num = 0;
        for (HumanBeing humanBeing : collection) {
            if (humanBeing.getId() == id) {
                break;
            }
            num++;
        }

        collection.remove(num);
    }
    public void clear(){
        collection.clear();
    }
    public void saveToFile(){
        // реализация
    }
    public void executeScriptFromFilename(){
        // реализация
    }
    public void exit(){
        System.exit(0);
    }
    public void removeFirst(){
        collection.removeFirst();
    }
    public void addIfMin(HumanBeing hb){
        if (hb.compareTo(findMin()) < 0){
            add(hb);
        }
    }
    public void sort(){
        Collections.sort(collection);
    }
    public void removeAnyByMood (Mood mood){
        ArrayList<HumanBeing> toRemove = new ArrayList<>();
        for (HumanBeing hb : collection){
            if (hb.getMood() == mood){
                toRemove.add(hb);
            }
        }
        Random random = new Random();
        int index = random.nextInt(toRemove.size());
        collection.remove(toRemove.get(index));
    }
    public void minBySoundtrackName(){
        SoundtrackNameComparator snc = new SoundtrackNameComparator();
        ArrayList<HumanBeing> sortedC = collection;
        Collections.sort(sortedC, snc);
        System.out.println(sortedC.getFirst());
    }
    public int countGreaterThanMood(Mood mood){
        MoodComparator mc = new MoodComparator();
        Collections.sort(collection, mc);
        int count = 0;
        for (HumanBeing hb : collection){
            count++;
            if (hb.getMood().getPointOfHappy() > mood.getPointOfHappy()){
                break;
            }
        }
        return collection.size() - count;
    }


    public HumanBeing findMin(){
        HumanBeing result = null;
        int count = 0;
        for (HumanBeing humanBeing : collection) {
            for (HumanBeing humanBeing2 : collection) {
                if (humanBeing.compareTo(humanBeing2) > 0) {
                    count++;
                }
            }
            if (count == 0){
                result = humanBeing;
                break;
            }
        }
        return result;
    }

    public LocalDateTime getInitialazed() {
        return initialazed;
    }

    public void setInitialazed(LocalDateTime initialazed) {
        this.initialazed = initialazed;
    }

    public String getTimeOfCreation() {
        return timeOfCreation;
    }

    public void setTimeOfCreation(String timeOfCreation) {
        this.timeOfCreation = timeOfCreation;
    }
}
