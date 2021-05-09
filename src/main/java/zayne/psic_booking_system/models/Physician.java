package zayne.psic_booking_system.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Working hours: 10:00 - 17:00 <br>
 * 5 Physicians <br>
 * Each physician only works 2 days/week.
 */
public class Physician extends Person {
  /** In-memory storage of physicians data. */
  private static final ArrayList<Physician> physicians = new ArrayList<>();
  // Working hrs is `10:00 - 18:00`, `Mon - Fri`.
  public static int WORKING_HRS_START = 10;
  public static int WORKING_HRS_END = 18;
  public ArrayList<Expertise> expertise = new ArrayList<>();
  public ArrayList<Treatment> treatment = new ArrayList<>();
  public Integer[] workingDays = new Integer[2];
  /** Consultation hours is 2hrs each week. so only start time will be stored. */
  public Integer[] consultHours = new Integer[2];

  public Physician() {}

  public Physician(String name) {
    super(name);
  }

  public static void addPhysicians(Physician physician) {
    physicians.add(physician);
  }

  /**
   * Get all registered Physicians in PSIC.
   *
   * @return A list of all Physicians.
   */
  public static ArrayList<Physician> getPhysicians() {
    return physicians;
  }

  public static ArrayList<Physician> getAvailablePhysicians(Calendar start, Treatment treatment) {
    // Get appointments booked for the given `date`, `time` and `treatment`.
    var filteredAppointments = new ArrayList<Appointment>();
    Appointment.appointments.forEach(
        appointment -> {
          var startTime = appointment.startTime;
          var date = startTime.get(Calendar.DATE);
          var time = startTime.get(Calendar.HOUR_OF_DAY);
          var physician = appointment.physician;

          if (date == start.get(Calendar.DATE)
              && time == start.get(Calendar.HOUR_OF_DAY)
              && appointment.treatment == treatment) {
            filteredAppointments.add(appointment);
          }
        });

    // Remove booked physician from the list of physicians who provide this treatment.
    var filteredPhysicianByAppointment = getPhysiciansByTreatment(treatment);
    filteredAppointments.forEach(
        appointment -> {
          filteredPhysicianByAppointment.remove(appointment.physician);
        });

    // Each physician only works 2 days per week,
    // only add the physician that are available that day to the result.
    var filteredPhysicianByWorkdays = new ArrayList<Physician>();
    filteredPhysicianByAppointment.forEach(
        physician -> {
          var day = start.get(Calendar.DAY_OF_WEEK);
          if (Arrays.stream(physician.workingDays).anyMatch(d -> d == day))
            filteredPhysicianByWorkdays.add(physician);
        });

    // TODO: filter out consultation hrs
    var res = new ArrayList<Physician>();
    filteredPhysicianByWorkdays.forEach(
        physician -> {
          var day = start.get(Calendar.DAY_OF_WEEK);
          var time = start.get(Calendar.HOUR_OF_DAY);
          // FIXME: potential issue as consultations are 30 mins each.
          if (physician.consultHours[1] != time) res.add(physician);
        });
    return res;
  }

  /**
   * Find Physicians by name.
   *
   * @param name - Name of the physician.
   * @return A list of physicians with the same name provided.
   */
  public static ArrayList<Physician> getPhysiciansByName(String name) {
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
  public static ArrayList<Physician> getPhysiciansByExpertise(String expertise) {
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

  public static ArrayList<Physician> getPhysiciansByTreatment(Treatment treatment) {
    var res = new ArrayList<Physician>();
    physicians.forEach(
        physician -> {
          if (physician.treatment.contains(treatment)) res.add(physician);
        });
    return res;
  }

  public ArrayList<Calendar> getWorkingDates() {
    var resDays = new ArrayList<Calendar>();
    var start = Calendar.getInstance();
    start.set(2021, Calendar.MAY, 1);

    do {
      if (start.get(Calendar.DAY_OF_WEEK) == (this.workingDays[0] + 1)) resDays.add(start);

      start.add(Calendar.DAY_OF_MONTH, 1);
    } while (start.get(Calendar.MONTH) == Calendar.MAY);
    return resDays;
  }

  public ArrayList<Appointment> getAppointments() {
    return Appointment.getAppointmentsForPhysician(this);
  }
  // public static void

  // public void CheckIn(Appointment appointment, Patient patient) {
  //     // Check the appointment with correct physician and patient.
  //     if (appointment.physician == this && appointment.patient == patient)
  //         // Set the state of `isAttended` to true.
  //         appointment.state = Appointment.Appointment_State.ATTENDED;
  //
  //     // DONE: physician check-in implementation.
  // }

  public String getStat() {
    var res = "Name:\t%s\nID:\t\t%s\nTel:\t\t%s\n".formatted(this.name, this._id, this.tel);

    String[] days = new String[] {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

    res += "Work:\t%s, %s\n".formatted(days[this.workingDays[0]], days[this.workingDays[1]]);

    res += "Consult:\t%s - %s:00".formatted(days[this.consultHours[0]], this.consultHours[1]);

    return res;
  }

  public String toString() {
    // StringBuilder result = new StringBuilder("==========================\n");
    // for (var field : this.getClass().getFields()) {
    //     try {
    //         if (field.getName().equals("expertise") || field.getName().equals("treatment")) {
    //             StringBuilder listDpkg = new StringBuilder("[ ");
    //             for (var item : (ArrayList) field.get(this)) {
    //                 listDpkg.append("'%s', ".formatted(item));
    //             }
    //             listDpkg.append(" ]");
    //             result.append("%s: %s\n".formatted(field.getName(), listDpkg.toString()));
    //         } else {
    //
    //             result.append("%s: %s\n".formatted(field.getName(), field.get(this)));
    //         }
    //         // System.out.println("%s: %s\n".formatted(field.getName(), field.get(this)));
    //     } catch (IllegalArgumentException | IllegalAccessException e) {
    //         e.printStackTrace();
    //     }
    // }
    return this.name;
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
    GYM_REHABILITATION("Gym_Rehabilitation"),
    SPINE_AND_JOINTS_MOBILISATION("Spine_And_Joints_Mobilisation"),
    NEURAL_MOBILISATION("Neural_Mobilisation");

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
