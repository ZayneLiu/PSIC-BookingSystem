package zayne.psic_booking_system.models;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.json.JSONException;

import zayne.psic_booking_system.utils.JSONHelper;

public class Patient extends Person {
    private static ArrayList<Patient> patients;

    public static ArrayList<Patient> findPatient(String name) {
        // DONE: find specific patient by name.
        var result = new ArrayList<Patient>();
        for (Patient patient : patients) {
            if (patient.name.toLowerCase().contains(name.toLowerCase())) {
                result.add(patient);
            }
        }
        return result;
    }

    public static void addPatient(Patient p) {
        patients.add(p);
        // TODO: Write to JSON file after addition;
    }

    public static ArrayList<Patient> GetPhysicians() {
        // DONE: Read data from JSON file
        var result = new ArrayList<Patient>();
        try {
            result = JSONHelper.getPatients();
        } catch (IllegalArgumentException | IllegalAccessException | JSONException | FileNotFoundException
                | NoSuchFieldException | SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    Patient(String name) {
        super(name);
        // TODO Auto-generated constructor stub
    }

}
