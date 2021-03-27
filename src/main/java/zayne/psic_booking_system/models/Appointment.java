package zayne.psic_booking_system.models;

import java.util.Date;

import zayne.psic_booking_system.models.Physician.Treatment;

public class Appointment {

    public Appointment_Type type;
    public Date startTime;
    public Date endTime;
    public Patient patient;
    public Physician physician;
    public Room room;
    public Treatment treatment;

    public Appointment() {
    }

    public Appointment(Date startTime, Date endTime, Physician physician, Patient patient, Treatment treatment) {
        this.startTime = startTime;
        this.endTime = endTime;
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
