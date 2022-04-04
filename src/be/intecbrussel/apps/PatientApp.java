package be.intecbrussel.apps;

import be.intecbrussel.classes.AgeSorter;
import be.intecbrussel.classes.InsuranceSorter;
import be.intecbrussel.classes.Patient;
import be.intecbrussel.classes.TemperatureSorter;


import java.util.*;

public class PatientApp {
    public static void main(String[] args) {

        List<Patient> patientList = Patient.getAllPatients();

        //Used LinkedHashSet to remove doubles and keep them in order they arrived.
        //BUT not sure if I'm supposed to consider the first three (princeCharming, crazyFrog, and donaldPutin) as having come first as they aren't added to the list immediately...
        Set<Patient> patientSet = new LinkedHashSet<>(patientList);
        System.out.println("Patients without duplicates in the order they arrived: ");
        System.out.println(patientSet);
        System.out.println("______________________________________________");

        //give priority to patients on basis first of temperature, then of age.
        List<Patient> patientsByTempAndAge = new ArrayList<>();
        patientsByTempAndAge.addAll(patientSet);
        patientsByTempAndAge.sort(Comparator.comparing(Patient::getTemperature).thenComparing(Patient::getAge));
        Comparator<Patient> c = Collections.reverseOrder();
        Collections.reverse(patientsByTempAndAge);
        System.out.println("Patients sorted by age and then temperature:");
        System.out.println(patientsByTempAndAge);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++");

        //create List that uses TemperatureSorter and AgeSorter then sorts by insurance status
        List<Patient> patientList1 = new ArrayList<>();
        patientList1.addAll(patientSet);
        System.out.println("Patients sorted first by temperature, then age, then taking insurance status into account: ");
        patientList1.sort(new TemperatureSorter().thenComparing(new AgeSorter()));
        new InsuranceSorter(patientList1) ;



        // insert into Queue and print
        Queue<Patient> patientQueue = new LinkedList<>();
        patientQueue.addAll(patientList1);
        System.out.println(patientQueue);
        System.out.println("-----------------------------------------------------");
        System.out.println();

        //divide patients into category for isolation
        Queue<Patient> category1 = new LinkedList<>();
        Queue<Patient> category2 = new LinkedList<>();
        Queue<Patient> category3 = new LinkedList<>();
        Queue<Patient> category4 = new LinkedList<>();
        for (Patient patient : patientQueue)
            if (((patient.getAge() <= 65 && patient.getTemperature() >= 40) || (patient.getAge() >= 65 && patient.getTemperature() >= 38) && patient.isUnknownVirus())) {
                category1.offer(patient);
            } else if (patient.getTemperature() >= 38 && !patient.isUnknownVirus()) {
                category4.offer(patient);
            } else if (patient.getTemperature() >= 38 && patient.isUnknownVirus()) {
                category2.offer(patient);
            } else if (patient.getTemperature() < 38 && patient.isUnknownVirus()) {
                category3.offer(patient);
            }

        //create Map that uses isolation category as key
        Map<Integer, Queue<Patient>> patientMap = new TreeMap<>();
        patientMap.put(1, category1);
        patientMap.put(2, category2);
        patientMap.put(3, category3);
        patientMap.put(4, category4);
        // prints out patients in each category and takes them out of each queue.
        for (Map.Entry<Integer, Queue<Patient>> category : patientMap.entrySet()) {
            Integer key = category.getKey();
            System.out.println();
            System.out.println("Patients in category: " + category.getKey());
            if (key == 1) {
                while (!category1.isEmpty()) {
                    System.out.println(category1.poll());
                }
            } else if (key == 2) {
                while (!category2.isEmpty()) {
                    System.out.println(category2.poll());
                }
            } else if (key == 3) {
                while (!category3.isEmpty()) {
                    System.out.println(category3.poll());
                }
            } else if (key == 4) {
                while (!category4.isEmpty()) {
                    System.out.println(category4.poll());
                }
                // checks if category queues are really empty
                System.out.println();
                System.out.println("Patients left in the queue from category1: " + category1);
                System.out.println("Category 1 is empty: " + category1.isEmpty());
                System.out.println("Patients left in the queue from category2: " + category2);
                System.out.println("Category 2 is empty: " + category2.isEmpty());
                System.out.println("Patients left in the queue from category3: " + category3);
                System.out.println("Category 3 is empty: " + category3.isEmpty());
                System.out.println("Patients left in the queue from category4: " + category4);
                System.out.println("Category 4 is empty: " + category4.isEmpty());



        }}
    }
}
