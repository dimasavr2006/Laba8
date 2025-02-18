package org.example.functions;

import org.example.classes.HumanBeing;

import java.util.Comparator;

public class SoundtrackNameComparator implements Comparator<HumanBeing> {
    @Override
    public int compare(HumanBeing o1, HumanBeing o2) {
        return o1.getSoundtrackName().length() - o2.getSoundtrackName().length();
    }
}
