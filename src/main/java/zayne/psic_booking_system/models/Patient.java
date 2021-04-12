package zayne.psic_booking_system.models;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONException;

import zayne.psic_booking_system.models.Physician.Treatment;
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

    public static ArrayList<Patient> getPhysicians() {
        // Read data from JSON file
        try {
            patients = JSONHelper.getPatients();
        } catch (IllegalArgumentException
                | IllegalAccessException
                | JSONException
                | FileNotFoundException
                | NoSuchFieldException
                | SecurityException e) {
            e.printStackTrace();
        }
        return patients;
    }

    public Appointment bookAppointment(
            int weekNumber, Date start, Date end, Physician physician, Treatment treatment) {
        var appointment = new Appointment(weekNumber, start, end, physician, this, treatment);
        // TODO: Add to appointments
        return appointment;
    }

    Patient(String name) {
        super(name);
    }
}
