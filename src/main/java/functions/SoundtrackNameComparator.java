package functions;

import classes.HumanBeing;

import java.util.Comparator;

/**
 * @author Dimasavr
 */

public class SoundtrackNameComparator implements Comparator<HumanBeing> {
    /**
     * @param o1 первый объект для сравнения
     * @param o2 второй объект для сравнения
     * @return результат сравнения
     */
    @Override
    public int compare(HumanBeing o1, HumanBeing o2) {
        return o1.getSoundtrackName().length() - o2.getSoundtrackName().length();
    }
}
