package zayne.psic_booking_system.models;

import zayne.psic_booking_system.models.Physician.Treatment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicReference;

/** For simplicity each appointment will be a fixed duration of 2 hrs. */
public class Appointment {

    public Appointment_Type type;
    public Calendar startTime;
    // public Calendar endTime;
    public Patient patient;
    public Physician physician;
    public Room room;
    public Treatment treatment;
    public Appointment_State state;
    public String notes = "";

     * @param visitor
     */
    public Appointment(Calendar start, Physician physician, Visitor visitor) {
        this._id = Calendar.getInstance().getTimeInMillis();
        this.startTime = start;
        this.physician = physician;
        this.patient = null;
        this.notes = "Visitor Name: %s".formatted(visitor.name);

        // TODO: Consultation slots and room availability.
  public String notes = "";

  public Appointment() {}

  /**
   * Book appointments for visitors.
   *
   * @param start
   * @param physician
   * @param visitor
   */
  public Appointment(Calendar start, Physician physician, Visitor visitor) {
    this._id = Calendar.getInstance().getTimeInMillis();
    this.startTime = start;
    this.physician = physician;
    this.patient = null;
    this.notes = "Visitor Name: %s".formatted(visitor.name);

    // TODO: Consultation slots and room availability.
  }

  // public Appointment(Calendar start, Physician physician, Patient patient) {
  //     this._id = Calendar.getInstance().getTimeInMillis();
  //     this.startTime = start;
  //     this.physician = physician;
  //     this.patient = patient;
  //     this.notes = "Visitor Name: %s".formatted(patient.name);
  //
  //     // TODO: Consultation slots and room availability.
  // }

  /**
   * Book appointments for registered patients.
   *
   * @param start
   * @param physician
   * @param patient
   * @param treatment
   */
  public Appointment(Calendar start, Physician physician, Patient patient, Treatment treatment) {
    this._id = Calendar.getInstance().getTimeInMillis();
    this.startTime = start;
    // this.endTime = (Calendar) start.clone();
    // this.endTime.add(Calendar.HOUR_OF_DAY, 2);
    // TODO: Physician available
    this.physician = physician;
    this.patient = patient;
    this.treatment = treatment;
    this.type = Appointment_Type.APPOINTMENT;

    // DONE: Room available
    this.room = Room.getRoom(Room.getAvailableRooms(start, this.treatment).get(0));
  }

  public static ArrayList<Appointment> getAppointments() {
    return appointments;
  }

  public static ArrayList<Appointment> getAppointmentsForPhysician(Physician physician) {
    var result = new ArrayList<Appointment>();
    for (var appointment : appointments) {
      if (appointment.physician._id == physician._id) result.add(appointment);
    }
    return result;
  }

  public static Appointment getAppointment(Calendar calendar, Physician physician) {
    AtomicReference<Appointment> res = new AtomicReference<>();
    res.set(null);
    // AtomicInteger counter = new AtomicInteger();
    getAppointmentsForPhysician(physician)
        .forEach(
            appointment -> {
              if (appointment.startTime == calendar
                  && appointment.state != Appointment_State.CANCELLED) {
                // counter.addAndGet(1);
                // if (counter.get() > 1) {
                //     System.out.println(
                //             "more than 1 appointment for a physician found booked for the same
                // time");
                // }
                res.set(appointment);
              }
            });
    return res.get();
  }

  public Appointment book() {
    // var isVisitor = this.patient == null;
    this.state = Appointment_State.BOOKED;
    appointments.add(this);
    return this;
  }

  public Appointment miss() {
    this.state = Appointment.Appointment_State.MISSED;
    return this;
  }

  public Appointment cancel() {
    this.state = Appointment_State.CANCELLED;
    // TODO: Change room and physician availability after an appointment is canceled.
    return this;
  }

  public Appointment attend() {
    this.state = Appointment_State.ATTENDED;
    return this;
  }

  public String getStat() {
    // TODO: potential display issue
    return "ID:\t\t%s\nPatient:\t%s\nPhysician:\t%s\nTreatment:\t%s\nState:\t%s\nTime:\t\t%s\nRoom:\t\t%s\nNotes:\t%s"
        .formatted(
            this._id,
            this.patient.name,
            this.physician.name,
            this.treatment,
            this.state,
            this.startTime,
            this.room,
            this.notes);
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
