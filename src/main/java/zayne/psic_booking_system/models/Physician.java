package zayne.psic_booking_system.models;

import zayne.psic_booking_system.utils.Helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Working hours: 10:00 - 17:00 <br>
 * 5 Physicians <br>
 * Each physician only works 2 days/week.
 */
public class Physician extends Person {
  /** In-memory storage of physicians data. */
  private static final ArrayList<Physician> physicians = new ArrayList<>();

  public ArrayList<Expertise> expertise = new ArrayList<>();
  public ArrayList<Treatment> treatments = new ArrayList<>();
  public Integer[] workingDays = new Integer[2];
  /** Consultation hours is 2hrs each week. so only start time will be stored. */
  public Integer[] consultHours = new Integer[2];
  /** This physicians' room */
  public Room room;

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

  public ArrayList<String> getAvailableAppointments() {
    var availableAppointments = new ArrayList<String>();

    var slots = new ArrayList<Calendar>();
    // Get all dates in the month the physician is working.
    // e.g. [Tue, Fri] -> [May-4, May-7, May-11, May-14, May-18, May-21, May-25, May-28]
    var workingDays = this.getWorkingDates();
    workingDays.forEach(
        calendar -> {
          // Get 4 slots on given day.
          // `May-4` -> [May-4-10:00, May-4-12:00, May-4-14:00, May-4-16:00]
          slots.addAll(Helper.getSlotsOnGivenDay(calendar));
        });

    this.treatments.forEach(
        treatment -> {
          slots.forEach(
              slot -> {
                var resAppointment = Appointment.getAppointmentIfExists(slot, this);
                if (resAppointment == null) {
                  // No appointment booked for this physician at this date and slot.
                  // Current slot is available.
                  String[] days = new String[] {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

                  var resMonth = slot.get(Calendar.MONTH) + 1;
                  var resDay = slot.get(Calendar.DAY_OF_MONTH);
                  var resDayOfWeek = days[slot.get(Calendar.DAY_OF_WEEK) - 1];
                  var resHour = slot.get(Calendar.HOUR_OF_DAY);
                  var resPhysicianName = String.join("_", this.name.split(" "));
                  var resRoomName =
                      this.room.roomName.length() == 1
                          ? "Room-" + this.room.roomName
                          : this.room.roomName;
                  var resTreatment = treatment.toString();
                  // Assemble resulting string.
                  // "05-12 Wed 16:00 Some_Name Room-A Message"
                  var res =
                      "%02d-%02d %s %s:00  %s\t%s\t%s"
                          .formatted(
                              resMonth,
                              resDay,
                              resDayOfWeek,
                              resHour,
                              resPhysicianName,
                              resRoomName,
                              resTreatment);

                  availableAppointments.add(res);
                }
              });
        });

    return availableAppointments;
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
          if (physician.treatments.contains(treatment)) res.add(physician);
        });
    return res;
  }

  public ArrayList<Calendar> getWorkingDates() {
    var resDays = new ArrayList<Calendar>();
    var start = Calendar.getInstance();
    start.set(2021, Calendar.MAY, 1);

    do {

      if (Arrays.stream(this.workingDays).anyMatch((day) -> day == start.get(Calendar.DAY_OF_WEEK)))
        resDays.add((Calendar) start.clone());
      start.add(Calendar.DAY_OF_MONTH, 1);

    } while (start.get(Calendar.MONTH) == Calendar.MAY);
    return resDays;
  }

  public ArrayList<Appointment> getBookedAppointments() {
    return Appointment.getBookedAppointmentsForPhysician(this);
  }

  public String getStat() {
    String[] days = new String[] {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

    var resName = this.name;
    var resId = this._id;
    var resTel = this.tel;
    var resWorkingDays = new String[2];
    var resConsultDay = days[this.consultHours[0] - 1];
    var resConsultHour = this.consultHours[1];
    var resExpertise = new AtomicReference<>("");
    var resTreatments = new AtomicReference<>("");

    resWorkingDays[0] = days[this.workingDays[0] - 1];
    resWorkingDays[1] = days[this.workingDays[1] - 1];
    this.expertise.forEach(
        exp -> {
          resExpertise.set(resExpertise.get() + exp.name + " ");
        });
    this.treatments.forEach(
        treat -> {
          resTreatments.set(resTreatments.get() + "\t\t" + treat.name + "\n");
        });

    var res = new StringBuilder();

    res.append("Name:\t%s\n".formatted(resName));
    res.append("ID:\t\t%s\n".formatted(resId));
    res.append("Work:\t%s, %s\n".formatted(resWorkingDays[0], resWorkingDays[1]));
    res.append("Consult:\t%s - %s:00\n".formatted(resConsultDay, resConsultHour));
    res.append("Tel:\t\t%s\n".formatted(resTel));
    res.append("Expertise:\n\t\t%s\n".formatted(resExpertise.get()));
    res.append("Treatments:\n%s".formatted(resTreatments.get()));

    return res.toString();
  }

  public String toString() {
    return this.name;
  }

  // #region enum definitions.
  public enum Expertise {
    PHYSIOTHERAPY("Physiotherapy"),
    REHABILITATION("Rehabilitation"),
    OSTEOPATHY("Osteopathy");

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
    SPINE_JOINTS_MOBILISATION("Spine_Joints_Mobilisation"),
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
