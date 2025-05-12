package classes;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import enums.Mood;
import enums.WeaponType;

import java.util.Date;
import java.util.Objects;

public class HumanBeing implements Comparable<HumanBeing>{

    @JsonProperty("id")
    /**
     * ID элемента в коллекции
     * Генерируются автоматически
     */
    private int id;

    /**
     * имя элемента коллекции
     * NotNull
     */
    private String name; // not null

    /**
     * Координаты элемента коллекции
     */
    private Coordinates coordinates; // not null

    @JsonProperty("creationDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    /**
     * Дата создания коллекции
     * @version 0.0001 (prealpha) : на данном этапе просто время запуска кода
     */
    private java.util.Date creationDate; // not null

    @JsonProperty("realHero")
    /**
     * Является ли элемент коллекции настоящим героем
     */
    private boolean realHero;
    @JsonProperty("hasToothpick")
    /**
     * Есть ли у элемента коллекции зубная боль
     */
    private boolean hasToothpick;
    @JsonProperty("impactSpeed")
    /**
     * Скорость удара элемента коллекции
     */
    private Long impactSpeed; // not null
    @JsonProperty("soundtrackName")
    /**
     * Название саундтрека элемента коллекции
     */
    private String soundtrackName; // not null
    @JsonProperty("weaponType")
    /**
     * Тип оружия элемента коллекции
     * Для подробностей enum читайте документауию)))
     * @see WeaponType
     */
    private WeaponType weaponType; // not null
    @JsonProperty("mood")
    /**
     * Настроение элемента коллекции
     * Для подробностей enum читайте документауию)))
     * @see Mood
     */
    private Mood mood; // not null

    private Car car; // not null

    private int ownerId;


    public HumanBeing (){
        this.creationDate = new Date();
        creationDate.setTime(System.currentTimeMillis());
        try {
            if (name == null) {
                throw new IllegalArgumentException("имя может быть null");
            }
            if (coordinates == null) {
                throw new IllegalArgumentException("Координаты не могут быть null");
            }
            if (impactSpeed == null) {
                throw new IllegalArgumentException("Скорость удара не может быть null");
            }
            if (soundtrackName == null) {
                throw new IllegalArgumentException("Название саундтрека не можеть быть null");
            }
            if (weaponType == null) {
                throw new IllegalArgumentException("Тип оружия не может быть null");
            }
            if (mood == null) {
                throw new IllegalArgumentException("Настроение не может быть null");
            }
            if (car == null) {
                throw new IllegalArgumentException("Машина не может быть null");
            }
        } catch (IllegalArgumentException e) {
            e.getMessage();
        }
    }

    public HumanBeing(boolean b) {
        creationDate = new Date();
    }

    public HumanBeing(int id, String name, Coordinates coordinates, Date creationDate, boolean realHero, boolean hasToothpick, Long impactSpeed, String soundtrackName, WeaponType weaponType, Mood mood, Car car) {
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

    public HumanBeing(
            String name,
            Coordinates coordinates,
            boolean realHero,
            boolean hasToothpick,
            Long impactSpeed,
            String soundtrackName,
            WeaponType weaponType,
            Mood mood,
            Car car
    ) {

        this.creationDate = new Date();
        creationDate.setTime(System.currentTimeMillis());
        this.realHero = realHero;
        this.hasToothpick = hasToothpick;

        try {
            if (name == null) {
                throw new IllegalArgumentException("имя может быть null");
            }
            this.name = name;
            if (coordinates == null) {
                throw new IllegalArgumentException("Координаты не могут быть null");
            }
            this.coordinates = coordinates;
            if (impactSpeed == null) {
                throw new IllegalArgumentException("Скорость удара не может быть null");
            }
            this.impactSpeed = impactSpeed;
            if (soundtrackName == null) {
                throw new IllegalArgumentException("Название саундтрека не может быть null");
            }
            this.soundtrackName = soundtrackName;
            if (weaponType == null) {
                throw new IllegalArgumentException("Тип оружия не может быть null");
            }
            this.weaponType = weaponType;
            if (mood == null) {
                throw new IllegalArgumentException("Настроение не может быть null");
            }
            this.mood = mood;
            if (car == null) {
                throw new IllegalArgumentException("Машина не может быть null");
            }
            this.car = car;
        } catch (IllegalArgumentException e) {
            e.getMessage();
        }
    }

    public HumanBeing(int id, String name, Coordinates coordinates, Date creationDate, boolean realHero, boolean hasToothpick, Long impactSpeed, String soundtrackName, WeaponType weaponType, Mood mood, Car car, int ownerId) {
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
        this.ownerId = ownerId;
    }

    /**
     * Возвращает ID элемента
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Возвращает имя элемента
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает координаты элемента
     * @return coordinates
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Возвращает дату создания элемента
     * @return creationDate
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Возвращает является ли элемент героем
     * @return realHero
     */
    public boolean isRealHero() {
        return realHero;
    }

    /**
     * Возвращает есть ли у элемента зубная боль
     * @return hasToothpick
     */
    public boolean isHasToothpick() {
        return hasToothpick;
    }

    /**
     * Возвращает скорость удара элемента
     * @return impactSpeed
     */
    public Long getImpactSpeed() {
        return impactSpeed;
    }

    /**
     * Возвращает название саундтрека элемента
     * @return soundtrackName
     */
    public String getSoundtrackName() {
        return soundtrackName;
    }

    /**
     * Возвращает тип оружия элемента
     * @return weaponType
     */
    public WeaponType getWeaponType() {
        return weaponType;
    }

    /**
     * Возвращает настроение элемента
     * @return mood
     */
    public Mood getMood() {
        return mood;
    }

    /**
     * Возвращает машину элемента
     * @return car
     */
    public Car getCar() {
        return car;
    }

    /**
     * Задаёт ID элемента
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Задаёт имя элемента
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Задаёт координаты элемента
     * @param coordinates
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Задаёт дату создания элемента
     * @param creationDate
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Задаёт является ли элемент героем
     * @param realHero
     */
    public void setRealHero(boolean realHero) {
        this.realHero = realHero;
    }

    /**
     * Задаёт есть ли у элемента зубная боль
     * @param hasToothpick
     */
    public void setHasToothpick(boolean hasToothpick) {
        this.hasToothpick = hasToothpick;
    }

    /**
     * Задаёт скорость удара элемента
     * @param impactSpeed
     */
    public void setImpactSpeed(Long impactSpeed) {
        this.impactSpeed = impactSpeed;
    }

    /**
     * Задаёт название саундтрека элемента
     * @param soundtrackName
     */
    public void setSoundtrackName(String soundtrackName) {
        this.soundtrackName = soundtrackName;
    }

    /**
     * Задаёт тип оружия элемента
     * @param weaponType
     */
    public void setWeaponType(WeaponType weaponType) {
        this.weaponType = weaponType;
    }

    /**
     * Задаёт настроение элемента
     * @param mood
     */
    public void setMood(Mood mood) {
        this.mood = mood;
    }

    /**
     * Задаёт машину элемента
     * @param car
     */
    public void setCar(Car car) {
        this.car = car;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * Сравнвает два объекта
     * @param comp объект с которым сравнивает
     * @return результат сравнения
     */
    @Override
    public int compareTo(HumanBeing comp) {
        return (this.mood.getPointOfHappy() - comp.mood.getPointOfHappy()) + (this.weaponType.getDegreeOfCool() - comp.weaponType.getDegreeOfCool());
    }

    /**
     * Возвращает описание объекта
     * @return
     */
    @Override
    public String toString() {
        return "ID: " + id + "\nCreationDate: " + creationDate + "\nName: " + name + "\n" + coordinates + "\nCreationDate: " + creationDate + "\nRealHero: " + realHero + "\nHasToothpick: " + hasToothpick + "\nImpactSpeed: " + impactSpeed + "\nSoundtrackName: " + soundtrackName + "\nWeaponType: " + weaponType + "\nMood: " + mood + "\n" + car;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        HumanBeing that = (HumanBeing) o;
        return isRealHero() == that.isRealHero() && isHasToothpick() == that.isHasToothpick() && Objects.equals(getName(), that.getName()) && Objects.equals(getCoordinates(), that.getCoordinates()) && Objects.equals(getImpactSpeed(), that.getImpactSpeed()) && Objects.equals(getSoundtrackName(), that.getSoundtrackName()) && getWeaponType() == that.getWeaponType() && getMood() == that.getMood() && Objects.equals(getCar(), that.getCar());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCoordinates(), isRealHero(), isHasToothpick(), getImpactSpeed(), getSoundtrackName(), getWeaponType(), getMood(), getCar());
    }
}