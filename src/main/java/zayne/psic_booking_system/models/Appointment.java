package zayne.psic_booking_system.models;

import java.util.Date;

import zayne.psic_booking_system.models.Physician.Treatment;

public class Appointment {

    public Appointment_Type type;
    public int weekNumber;
    public Date startTime;
    public Date endTime;
    public Patient patient;
    public Physician physician;
    public Room room;
    public Treatment treatment;

    public Appointment() {
    }

    public Appointment(int week, Date start, Date end, Physician physician, Patient patient, Treatment treatment) {
        this.weekNumber = week;
        this.startTime = start;
        this.endTime = end;
        this.physician = physician;
        this.patient = patient;
        this.treatment = treatment;
    }

    public enum Appointment_Type {
        APPOINTMENT("Appointment"), CONSULTATION("Consultation");

        private final String name;

        Appointment_Type(String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }
    }

}
