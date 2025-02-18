package org.example.functions;

import org.example.classes.HumanBeing;

import java.util.Comparator;

public class MoodComparator implements Comparator<HumanBeing> {
    @Override
    public int compare(HumanBeing o1, HumanBeing o2) {
        return o1.getMood().getPointOfHappy() - o2.getMood().getPointOfHappy();
    }
}
