package zayne.psic_booking_system.models;

import java.util.ArrayList;

/**
 * Working hours: 10:00 - 17:00 <br>
 * 5 Physicians <br>
 * Each physician only works 2 days/week.
 */
public class Physician extends Person {
    // Working hrs is `10:00 - 18:00`, `Mon - Fri`.
    public static int WORKING_HRS_START = 10;
    public static int WORKING_HRS_END = 18;

    /** In-memory storage of physicians data. */
    private static final ArrayList<Physician> physicians = new ArrayList<>();

    static {
        var physician1 =        new Physician("Betsy Hopper");
        physician1._id =        Long.parseLong("1615503120508");
        physician1.tel =        "+4407866442335";
        physician1.address =    "511 Hawthorne Street, Duryea, New Hampshire, 5065";
        physician1.expertise    .add(Expertise.PHYSIOTHERAPY);
        physician1.treatment    .add(Treatment.MASSAGE);
        physician1.treatment    .add(Treatment.ACUPUNCTURE);

        var physician2 =        new Physician("Santiago Romero");
        physician2._id =        Long.parseLong("1614348032344");
        physician2.tel =        "+4407845585328";
        physician2.address =    "696 Coleman Street, Ahwahnee, Guam, 1622";
        physician2.expertise    .add(Expertise.REHABILITATION);
        physician2.treatment    .add(Treatment.GYM_REHABILITATION);
        physician2.treatment    .add(Treatment.POOL_REHABILITATION);

        var physician3 =        new Physician("Miranda Espinoza");
        physician3._id =        Long.parseLong("1614448459669");
        physician3.tel =        "+4407882552388";
        physician3.address =    "800 Bowne Street, Haring, Hawaii, 523";
        physician3.expertise    .add(Expertise.REHABILITATION);
        physician3.treatment    .add(Treatment.GYM_REHABILITATION);
        physician3.treatment    .add(Treatment.POOL_REHABILITATION);

        var physician4 =        new Physician("Keller Burks");
        physician4._id =        Long.parseLong("1614665107626");
        physician4.tel =        "+4407952458363";
        physician4.address =    "426 Jackson Court, Vandiver, Montana, 2163";
        physician4.expertise    .add(Expertise.REHABILITATION);
        physician4.treatment    .add(Treatment.GYM_REHABILITATION);
        physician4.treatment    .add(Treatment.POOL_REHABILITATION);

        var physician5 =        new Physician("Jewell Burgess");
        physician5._id =        Long.parseLong("1614559905127");
        physician5.tel =        "+4407928560227";
        physician5.address =    "279 Evergreen Avenue, Ventress, Rhode Island, 2918";
        physician5.expertise    .add(Expertise.REHABILITATION);
        physician5.treatment    .add(Treatment.GYM_REHABILITATION);
        physician5.treatment    .add(Treatment.POOL_REHABILITATION);

        physicians.add(physician1);
        physicians.add(physician2);
        physicians.add(physician3);
        physicians.add(physician4);
        physicians.add(physician5);
    }

    public ArrayList<Expertise> expertise = new ArrayList<Expertise>();
    public ArrayList<Treatment> treatment = new ArrayList<Treatment>();

    /** Consultation hours is 2hrs each week. so only start time will be stored. */
    public String ConsultationHours = "Fri-2pm";

    public Physician() {}

    public Physician(String name) {
        super(name);
    }

    /**
     * Get all registered Physicians in PSIC.
     *
     * @return A list of all Physicians.
     */
    public static ArrayList<Physician> getPhysicians() {
        return physicians;
    }

    /**
     * Find Physicians by name.
     *
     * @param name - Name of the physician.
     * @return A list of physicians with the same name provided.
     */
    public static ArrayList<Physician> findPhysiciansByName(String name) {
        var result = new ArrayList<Physician>();
        // Retrieve all physicians with the provided name.
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
    public static ArrayList<Physician> findPhysiciansByExpertise(String expertise) {
        var result = new ArrayList<Physician>();
        var expertiseInNeed = Expertise.valueOf(expertise.toUpperCase());
        // Retrieve all physicians with the provided expertise.
        for (Physician physician : physicians) {
            if (physician.expertise.contains(expertiseInNeed)) {
                result.add(physician);
            }
        }
        return result;
    }

    public ArrayList<Appointment> getBookedAppointments() {
        return Appointment.getAppointmentsForPhysician(this);
    }
    // public static void

    public void CheckIn(Appointment appointment, Patient patient) {
        // Check the appointment with correct physician and patient.
        if (appointment.physician == this && appointment.patient == patient)
            // Set the state of `isAttended` to true.
            appointment.state = Appointment.Appointment_State.ATTENDED;

        // DONE: physician check-in implementation.
    }

    public String toString() {
        StringBuilder result = new StringBuilder("------------\n");
        for (var field : this.getClass().getFields()) {
            try {
                if (field.getName().equals("expertise") || field.getName().equals("treatment")) {
                    StringBuilder listDpkg = new StringBuilder("[ ");
                    for (var item : (ArrayList) field.get(this)) {
                        listDpkg.append("'%s', ".formatted(item));
                    }
                    listDpkg.append(" ]");
                    result.append("%s: %s\n".formatted(field.getName(), listDpkg.toString()));
                } else {

                    result.append("%s: %s\n".formatted(field.getName(), field.get(this)));
                }
                // System.out.println("%s: %s\n".formatted(field.getName(), field.get(this)));
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }

    // #region enum definitions.
    public enum Expertise {
        PHYSIOTHERAPY("Physiotherapy"),
        REHABILITATION("Rehabilitation");

        private final String name;

        Expertise(String expertise) {
            this.name = expertise;
        }

        public String toString() {
            return this.name;
        }
    }

    public enum Treatment {
        MASSAGE("Massage"),
        ACUPUNCTURE("Acupuncture"),
        POOL_REHABILITATION("Pool_Rehabilitation"),
        GYM_REHABILITATION("Gym_Rehabilitation");

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
