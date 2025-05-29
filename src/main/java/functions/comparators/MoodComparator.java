package functions.comparators;

import classes.HumanBeing;

import java.util.Comparator;

/**
 * @author Dimasavr
 */

public class MoodComparator implements Comparator<HumanBeing> {
    /**
     * Сравнивает 2 объекта по параметру mood
     * @see enums.Mood
     * @param o1 перавый объект для сравнения
     * @param o2 втотой объект для сравнения
     * @return результат сравнения объектов
     */
    @Override
    public int compare(HumanBeing o1, HumanBeing o2) {
        return o1.getMood().getPointOfHappy() - o2.getMood().getPointOfHappy();
    }
}
