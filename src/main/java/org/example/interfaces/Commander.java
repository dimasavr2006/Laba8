package org.example.interfaces;

import org.example.classes.HumanBeing;

public interface Commander {
    void execute (String args);
    void execute ();
    String description();
}
