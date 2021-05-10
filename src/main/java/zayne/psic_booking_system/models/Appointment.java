package zayne.psic_booking_system.models;

import zayne.psic_booking_system.models.Physician.Treatment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/** For simplicity each appointment will be a fixed duration of 2 hrs. */
public class Appointment {

  public static ArrayList<Appointment> appointments = new ArrayList<>();
  public long _id;

  public Appointment_Type type;
  public Calendar startTime;
  // public Calendar endTime;
  public Patient patient;
  public Physician physician;
  public Room room;
  public Treatment treatment;
  public Appointment_State state;
  public String notes = "";

  public Appointment() {
    this._id = Calendar.getInstance().getTimeInMillis();
  }

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
    // this.room = Room.getRoom(Room.getAvailableRooms(start, this.treatment).get(0));
    this.room = physician.room;
  }

  public static ArrayList<Appointment> getAppointments() {
    return appointments;
  }

  public static ArrayList<Appointment> getBookedAppointmentsForPhysician(Physician physician) {
    var result = new ArrayList<Appointment>();

    appointments.forEach(
        appointment -> {
          if (appointment.physician._id == physician._id) result.add(appointment);
        });

    return result;
  }

  public static Appointment getAppointmentIfExists(Calendar calendar, Physician physician) {
    AtomicReference<Appointment> res = new AtomicReference<>();
    res.set(null);

    var bookedAppointments = physician.getBookedAppointments();
    bookedAppointments.forEach(
        appointment -> {
          var month = appointment.startTime.get(Calendar.MONTH);
          var date = appointment.startTime.get(Calendar.DAY_OF_MONTH);
          var time = appointment.startTime.get(Calendar.HOUR_OF_DAY);
          if (month == calendar.get(Calendar.MONTH)
              && date == calendar.get(Calendar.DAY_OF_MONTH)
              && time == calendar.get(Calendar.HOUR_OF_DAY)
              && appointment.state != Appointment_State.CANCELLED) {
            res.set(appointment);
          }
        });

    return res.get();
  }

  public Appointment book() {
    this.state = Appointment_State.BOOKED;
    // var isVisitor = this.patient == null;
    var isValid = new AtomicBoolean(true);
    appointments.forEach(
        appointment -> {
          var isCanceled = appointment.state == Appointment_State.CANCELLED;
          if (isCanceled) return;

          if (appointment.equals(this)) isValid.set(false);
        });

    if (isValid.get()) appointments.add(this);
    else return null;

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
    // Done: potential display issue
    return "ID:\t\t\t%s\nPatient:\t\t%s\nPhysician:\t%s\nTreatment:\t%s\nState:\t\t%s\nTime:\t\t%s\nRoom:\t\t%s\nNotes:\t%s"
        .formatted(
            this._id,
            this.patient.name,
            this.physician.name,
            this.treatment,
            this.state,
            "%02d-%02d %s:00"
                .formatted(
                    this.startTime.get(Calendar.MONTH) + 1,
                    this.startTime.get(Calendar.DAY_OF_MONTH),
                    this.startTime.get(Calendar.HOUR_OF_DAY)),
            this.room.roomName,
            this.notes);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Appointment appointment = (Appointment) o;

    var isSameMonth =
        appointment.startTime.get(Calendar.MONTH) == this.startTime.get(Calendar.MONTH);
    var isSameDay =
        appointment.startTime.get(Calendar.DAY_OF_MONTH)
            == this.startTime.get(Calendar.DAY_OF_MONTH);
    var isSameTime =
        appointment.startTime.get(Calendar.HOUR_OF_DAY) == this.startTime.get(Calendar.HOUR_OF_DAY);
    var isSameDate = isSameMonth && isSameDay && isSameTime;
    var isSamePhysician = appointment.physician._id == this.physician._id;

    return isSameDate && isSamePhysician;
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
