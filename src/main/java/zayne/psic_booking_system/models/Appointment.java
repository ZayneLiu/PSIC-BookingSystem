package zayne.psic_booking_system.models;

import zayne.psic_booking_system.models.Physician.Treatment;

import java.util.ArrayList;
import java.util.Calendar;

/** For simplicity each appointment will be a fixed duration of 2 hrs. */
public class Appointment {

    public static ArrayList<Appointment> appointments = new ArrayList<>();
    public long _id;

    public Appointment_Type type;
    public Calendar startTime;
    public Calendar endTime;
    public Patient patient;
    public Physician physician;
    public Room room;
    public Treatment treatment;
    public Appointment_State state;
    public String notes;

    public Appointment() {}

    /**
     * Constructor for Consultation
     *
     * @param startTime
     * @param physician
     * @param patient
     */
    public Appointment(Calendar startTime, Physician physician, Patient patient) {
        //    TODO:
    }

    public Appointment(Calendar start, Physician physician, Patient patient, Treatment treatment) {
        this.startTime = start;

        this.endTime = (Calendar) start.clone();
        this.endTime.add(Calendar.HOUR_OF_DAY, 2);

        this.physician = physician;
        this.patient = patient;
        this.treatment = treatment;

        this.room = Room.getAvailableRooms(this).get(0);
    }

    public static ArrayList<Appointment> getAppointmentsForPhysician(Physician physician) {
        var result = new ArrayList<Appointment>();
        for (var appointment : appointments) {
            if (appointment.physician._id == physician._id) result.add(appointment);
        }
        return result;
    }

    public static void addAppointment(Appointment appointment) {
        // TODO: Visitor or Patient
        // TODO: Physician available
        // TODO: Room available `time`, ``
        appointments.add(appointment);
    }

    /**
     * Type of appointment. <br>
     * (i.e. Appointment or Consultation)
     */
    public enum Appointment_Type {
        APPOINTMENT("Appointment"),
        CONSULTATION("Consultation");

        private final String name;

        Appointment_Type(String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }
    }

    /** 4 states of Appointment */
    public enum Appointment_State {
        BOOKED("Booked"),
        CANCELLED("Cancelled"),
        ATTENDED("Attended"),
        MISSED("Missed");

        private final String state;

        Appointment_State(String state) {
            this.state = state;
        }

        public String toString() {
            return state;
        }
    }
}
