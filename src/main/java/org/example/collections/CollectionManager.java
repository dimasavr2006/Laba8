package org.example.collections;

import org.example.classes.HumanBeing;
import org.example.enums.Mood;
import org.example.functions.MoodComparator;
import org.example.functions.SoundtrackNameComparator;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class CollectionManager{
    ZonedDateTime initialazed;
    public ArrayList<HumanBeing> collection;
    public CollectionManager() {
        initialazed = ZonedDateTime.now();
        collection = new ArrayList<>();
    }

    public String info(){
        return "Тип коллекции: " + collection.getClass() + ", Время создания: " + initialazed + ", Размер коллекции: " + collection.size();
    }
    public void add(HumanBeing hb){
        collection.add(hb);
    }
    public void updateID(int id, HumanBeing hb){
        collection.set(id, hb);
    }
    public void removeById(int id){
        collection.remove(id);
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
        Collections.sort(collection, snc);
        System.out.println(collection.getFirst());
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


}
