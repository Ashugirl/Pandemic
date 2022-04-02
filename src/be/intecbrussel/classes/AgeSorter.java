package be.intecbrussel.classes;

import java.util.Comparator;

public class AgeSorter implements Comparator<Patient> {

    @Override
    public int compare(Patient o1, Patient o2) {
        return o2.getAge() - o1.getAge();
    }
}
