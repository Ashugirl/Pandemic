package be.intecbrussel.apps;

import be.intecbrussel.classes.AgeSorter;
import be.intecbrussel.classes.InsuranceSorter;
import be.intecbrussel.classes.Patient;
import be.intecbrussel.classes.TemperatureSorter;
import com.sun.source.tree.Tree;

import java.util.*;

public class PatientApp {
    public static void main(String[] args) {

        List<Patient> patientList = Patient.getAllPatients();

        //Used LinkedHashSet to remove doubles and keep them in order they arrived.
        //BUT not sure how to get it to not remove the FIRST instance of a patient...
        Set<Patient> patientSet = new LinkedHashSet<>(patientList);
        System.out.println("Patients without duplicates in the order they arrived: ");
        System.out.println(patientSet);
        System.out.println("______________________________________________");

        //give priority to patients on basis first of temperature, then of age.
        TreeSet<Patient> patientTreeSet = new TreeSet<>(Comparator.comparing(Patient::getTemperature).thenComparing(Comparator.comparing(Patient::getAge)));
        patientTreeSet.addAll(patientSet);
        TreeSet<Patient> higestTempsAndAges = (TreeSet<Patient>) patientTreeSet.descendingSet();
        System.out.println("Patients sorted by age and then temperature:");
        System.out.println(higestTempsAndAges);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++");

        //create List that uses TemperatureSorter and AgeSorter
        List<Patient> patientList1 = new ArrayList<>();
        patientList1.addAll(patientSet);
        System.out.println("Patients sorted first by temperature, then age, then taking insurance status into account: ");
        patientList1.sort(new TemperatureSorter().thenComparing(new AgeSorter()));
        InsuranceSorter insuranceSorter = new InsuranceSorter(patientList1);

        // insert into Queue and print
        Queue<Patient> patientQueue = new LinkedList<>();
        patientQueue.addAll(patientList1);
        System.out.println(patientQueue);

        //divide patients into category for isolation
        Map<Integer, List<Patient>> patientMap = new HashMap<>();
        int key = 0;
        for (Map.Entry<Integer, List<Patient>> category : patientMap.entrySet()) {
            key = category.getKey();
            for (Patient patient : patientList) {
                if (((patient.getAge() <= 65 && patient.getTemperature() >= 38) || patient.getTemperature() >= 40) && patient.isUnknownVirus()) {
                    key = 1;
                } else if (patient.getTemperature() >= 38 && patient.isUnknownVirus()) {
                    key = 2;
                } else if (patient.getTemperature() < 38 && patient.isUnknownVirus()) {
                    key = 3;
                } else if (patient.getTemperature() >= 38 && !patient.isUnknownVirus()) ;
                {
                    key = 4;
                }
            }
        }
        patientMap.put(key, patientList);
        System.out.println("Category 1: " + patientMap.keySet().contains(1));

        }


    }

