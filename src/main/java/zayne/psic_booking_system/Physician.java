package zayne.psic_booking_system;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.json.JSONException;

/**
 * Working hours: 10:00 - 17:00
 */
public class Physician extends Person {

    /**
     * In-memory storage of physicians data.
     */
    private static ArrayList<Physician> physicians = GetPhysicians();

    public ArrayList<Expertise> expertise = new ArrayList<Expertise>();

    public ArrayList<Treatment> treatment = new ArrayList<Treatment>();

    /**
     * Consultation hours is 2hrs each week. so only start time will be stored.
     */
    public String ConsultationHours = "Fri-2pm";

    public Physician() {
        super();
    }

    public Physician(String name) {
        super(name);
    }

    /**
     * Get all registered Physicians in PSIC.
     *
     * @return A list of all Physicians.
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws FileNotFoundException
     * @throws JSONException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    public static ArrayList<Physician> GetPhysicians() {
        // DONE: Read data from JSON file
        var result = new ArrayList<Physician>();
        try {
            result = JSONHelper.getPhysicians();
        } catch (IllegalArgumentException | IllegalAccessException | JSONException | FileNotFoundException
                | NoSuchFieldException | SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Find Physicians by fullname.
     *
     * @param name - Name of the physician.
     * @return A list of physicians with the same name provided.
     */
    public static ArrayList<Physician> FindPhysiciansByName(String name) {
        var result = new ArrayList<Physician>();
        // DONE: Retrieve all phsicians with the provided name.
        for (Physician physician : physicians) {
            if (physician.name.toLowerCase().contains(name.toLowerCase())) {
                result.add(physician);
            }
        }

        return result;
    }

    /**
     * Find physician by their area of expertise.
     *
     * @param expertise - Area of expertise
     * @return A list of physicians with matching area of expertise.
     */
    public static ArrayList<Physician> FindPhysiciansByExpertise(String expertise) {
        var result = new ArrayList<Physician>();
        var expertiseInNeed = Expertise.valueOf(expertise.toUpperCase());
        // TODO: Retrieve all phsicians with the provided expertise.
        for (Physician physician : physicians) {
            if (physician.expertise.contains(expertiseInNeed)) {
                result.add(physician);
            }
        }
        return result;
    }

    public ArrayList<Appointment> getBookedAppointments() {
        // TODO: Centralize appointments in `Appointment` and use a getter here?
        return new ArrayList<>();
    }
    // public static void

    public void CheckIn(Appointment appointment, Patient patient) {
        // TODO:
    }

    public String toString() {
        var result = "------------\n";
        for (var field : this.getClass().getFields()) {
            try {
                if (field.getName() == "expertise" || field.getName() == "treatment") {
                    var listDpkg = "[ ";
                    for (var item : (ArrayList) field.get(this)) {
                        listDpkg += "'%s', ".formatted(item);
                    }
                    listDpkg += " ]";
                    result += ("%s: %s\n".formatted(field.getName(), listDpkg));
                } else {

                    result += ("%s: %s\n".formatted(field.getName(), field.get(this)));
                }
                // System.out.println("%s: %s\n".formatted(field.getName(), field.get(this)));
            } catch (IllegalArgumentException | IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return result;
    }

    // #region enum definitions.
    public enum Expertise {

        PHYSIOTHERAPY("Physiotherapy"), REHABILITATION("Rehabilitation");

        private final String name;

        Expertise(String expertise) {
            this.name = expertise;
        }

        public String toString() {
            return this.name;
        }
    }

    public enum Treatment {
        MASSAGE("Massage"), ACUPUNCTURE("Acupuncture"), POOL_REHABILITATION("Pool_Rehabilitation");

        private final String name;

        Treatment(String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }
    }
    // #endregion
}
