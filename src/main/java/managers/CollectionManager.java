package managers;

import classes.HumanBeing;
import enums.Mood;
import exceptions.NullStringException;
import functions.MoodComparator;
import functions.SoundtrackNameComparator;
import utils.JsonParser;
import utils.ScriptFileReader;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

public class CollectionManager{

    /**
     * @author Dimasavr
     */

    LocalDateTime initialazed;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd:MM:yyyy HH:mm:ss");
    String timeOfCreation;
    DBManager db;

    public static ArrayList<HumanBeing> collection;

    public CollectionManager(DBManager db) {
        this.db = db;;
//        initialazed = LocalDateTime.now();
//        timeOfCreation = initialazed.format(formatter);
//        db = new DBManager();
////        collection = new ArrayList<>();
//        collection = db.getCollection();
    }

    public void startCM () {
        initialazed = LocalDateTime.now();
        timeOfCreation = LocalDateTime.now().format(formatter);
        collection = db.getCollection();
    }

    /**
     * Выводит в консоль информацию о коллекции
     */
    public void info(){
        String s = "Тип коллекции: " + collection.getClass() + ", Время создания: " + timeOfCreation + ", Размер коллекции: " + collection.size();
        System.out.println(s);
    }

    /**
     * Добавляет элемент в коллекцию
     * @param hb
     */
    public void add(HumanBeing hb){
        collection.add(hb);
    }

    /**
     * Удаляет элемент коллекции с заданным id
     * @param id
     * @param hb
     */
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

    /**
     * Удаляет элемент коллекции с заданным id
     * @param id
     */
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

    /**
     * Очищает коллекцию полностью
     */
    public void clear(){
        collection.clear();
    }

    /**
     * Сохраняет коллекцию в файл
     * @param way
     */
    public void saveToFile(String way){
        JsonParser.collectionToJson(way, collection);
        System.out.println("Коллекция сохранена в " + way);
    }

    /**
     * Запускает скрипт из файла с заданным именем и путём
     */
    public void executeScriptFromFilename(String filename){
        ScriptFileReader sfl = new ScriptFileReader();
        sfl.readFile(filename);
    }

    /**
     * Выход из программы
     */
    public void exit(){
        System.exit(0);
    }

    /**
     * Удаляет первый элемент коллекции
     */
    public void removeFirst(){
//        collection.removeFirst();
        collection.remove(0);
    }

    /**
     * Добавляет элемент, если он меньше всех остальных в коллекции
     * @param hb
     */
    public void addIfMin(HumanBeing hb){
        if (hb.compareTo(findMin()) < 0){
            add(hb);
        }
    }

    /**
     * Сортирует коллекцию по возрастанию
     */
    public void sort(){
        Collections.sort(collection);
    }

    /**
     * Удаляет элемент с заданным значением поля mood
     * @param mood
     */
    public void removeAnyByMood (Mood mood){
        ArrayList<HumanBeing> toRemove = new ArrayList<>();
        for (HumanBeing hb : collection){
            if (hb.getMood() == mood){
                toRemove.add(hb);
            }
        }
        int index = 0;
        collection.remove(toRemove.get(index));
    }

    /**
     * Выводит элемент с минимальным значением поля soundtrackName
     */
    public void minBySoundtrackName(){
        SoundtrackNameComparator snc = new SoundtrackNameComparator();
        ArrayList<HumanBeing> sortedC = collection;
        Collections.sort(sortedC, snc);
//        System.out.println(sortedC.getFirst());
        System.out.println(sortedC.get(0));
    }

    /**
     * Выводит количество элементов поле mood, которое больше заданного
     * @param mood
     * @return int
     */
    public int countGreaterThanMood(Mood mood){
        MoodComparator mc = new MoodComparator();
        ArrayList<HumanBeing> sortedC = collection;
        Collections.sort(sortedC, mc);
        int count = 0;
        for (HumanBeing hb : sortedC){
            count++;
            if (hb.getMood().getPointOfHappy() > mood.getPointOfHappy()){
                break;
            }
        }
        return collection.size() - count;
    }

    /**
     * Загружает коллекцию из файла, хранящегося в переменной среды
     */
    public void readEnv(){
        try {
            String way = System.getenv("FILE_NAME");
            if (way == null || way.isEmpty()) {
                throw new NullStringException();
            }

            ArrayList<HumanBeing> startCollection = collection;
            ArrayList<HumanBeing> endCollection = JsonParser.jsonToCollection(way);

            startCollection.addAll(endCollection);

            collection = startCollection;

            System.out.println("Коллекция из переменной среды загружена!");

        } catch (NullStringException e) {
            System.out.println("Если вы хотите считать переменную среды, то запустите программу с другими данными");
        }
    }

    /**
     * Загружает коллекцию из файла
     * @param fileName
     */
    public void readJson(String fileName){

        ArrayList<HumanBeing> startCollection = collection;
        ArrayList<HumanBeing> endCollection = JsonParser.jsonToCollection(fileName);

        startCollection.addAll(endCollection);

        collection = startCollection;
    }


    /**
     * Возвращает коллекцию
     * @return collection
     */
    public ArrayList<HumanBeing> getCollection() {
        return collection;
    }

    /**
     * ищет минимальный элемент коллекции
     * @return HumanBeing
     */
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

    /**
     * Возвращает время создания коллекции
     * @return
     */
    public LocalDateTime getInitialazed() {
        return initialazed;
    }

    /**
     * Устанавливает время создания коллекции
     * @param initialazed
     */
    public void setInitialazed(LocalDateTime initialazed) {
        this.initialazed = initialazed;
    }

    /**
     * Возвращает время создания коллекции
     * @return
     */
    public String getTimeOfCreation() {
        return timeOfCreation;
    }

    /**
     * Устанавливает время создания коллекции
     * @param timeOfCreation
     */
    public void setTimeOfCreation(String timeOfCreation) {
        this.timeOfCreation = timeOfCreation;
    }
}
