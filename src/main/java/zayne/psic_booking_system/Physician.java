package zayne.psic_booking_system;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.json.JSONException;

public class Physician extends Person {
    public Physician() {
        super();
    }

    public Physician(String name) {
        super(name);
    }

    private static ArrayList<Physician> physicians = new ArrayList<>();

    public Expertise[] expertise = new Expertise[] {};

    public Treatment[] treatment = new Treatment[] {};

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
    public static ArrayList<Physician> GetPhysicians() throws IllegalArgumentException, IllegalAccessException,
            JSONException, FileNotFoundException, NoSuchFieldException, SecurityException {
        // DONE: Read data from JSON file
        return JSONHelper.getPhysicians();
    }

    /**
     * Find Physicians by fullname.
     *
     * @param name - Name of the physician.
     * @return A list of physicians with the same name provided.
     */
    public static ArrayList<Physician> FindPhysiciansByName(String name) {
        var result = new ArrayList<Physician>();
        // TODO: Retrieve all phsicians with the provided name.
        return result;
    }

    /**
     * Find physician by their area of expertise.
     *
     * @param expertise - Area of expertise
     * @return A list of physicians with matching area of expertise.
     */
    public static ArrayList<Physician> FindPhysiciansByExpertise(Expertise expertise) {
        var result = new ArrayList<Physician>();
        // TODO: Retrieve all phsicians with the provided expertise.
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
                result.concat("%s: %s\n".formatted(field.getName(), field.get(this).toString()));
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
