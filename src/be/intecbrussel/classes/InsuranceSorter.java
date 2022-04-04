package be.intecbrussel.classes;

import java.util.ArrayDeque;

import java.util.Deque;
import java.util.List;

public class InsuranceSorter {
    public InsuranceSorter(List<Patient> patientList1) {
    }
// Couldn't find a way to use the Comparator class and its method for a boolean, so used Deque to sort by insurance status.
    public static void insuredSort(Patient o1, Patient o2){
        Deque<Patient> insuredPatients = new ArrayDeque<>();
        if(o1.isInsured() &!o2.isInsured()){
            insuredPatients.addFirst(o1);
        }else if(o2.isInsured() &!o1.isInsured());{
            insuredPatients.addFirst(o2);
        }

    }
}
