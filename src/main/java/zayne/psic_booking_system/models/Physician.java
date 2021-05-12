package zayne.psic_booking_system.models;

import zayne.psic_booking_system.utils.Helper;

import java.util.ArrayList;
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

  public ArrayList<Appointment> getAvailableAppointments() {
    // TODO: filter out consultations.
    // var searchResult = new ArrayList<String>();
    var availableAppointments = new ArrayList<Appointment>();

    var slots = this.getWorkingSlots();
    slots.removeAll(this.getConsultingSlots());

    slots.forEach(
        slot -> {
          this.treatments.forEach(
              treatment -> {
                var resAppointment = Appointment.getExistingAppointmentIfExists(slot, this);
                if (resAppointment == null) {
                  // No appointment booked for this physician at this date and slot.
                  // Current slot is available.
                  var appointment = new Appointment(slot, this, null, treatment);

                  availableAppointments.add(appointment);
                }
              });
        });

    return availableAppointments;
  }

  public ArrayList<Appointment> getAvailableConsultations() {
    // TODO: filter out consultations.
    // var searchResult = new ArrayList<String>();
    var availableConsultations = new ArrayList<Appointment>();

    var slots = this.getConsultingSlots();
    // Get all dates in the month the physician is working.
    // e.g. [Tue, Fri] -> [May-4, May-7, May-11, May-14, May-18, May-21, May-25, May-28]

    slots.forEach(
        slot -> {
          var resConsultation = Appointment.getExistingConsultationIfAny(slot, this);
          if (resConsultation == null) {
            // No appointment booked for this physician at this date and slot.
            // Current slot is available.
            var appointment = new Appointment(slot, this, null, Treatment.CONSULTATION);

            availableConsultations.add(appointment);
          }
        });

    return availableConsultations;
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

  // public static ArrayList<Physician> getPhysiciansByTreatment(Treatment treatment) {
  //   var res = new ArrayList<Physician>();
  //   physicians.forEach(
  //       physician -> {
  //         if (physician.treatments.contains(treatment)) res.add(physician);
  //       });
  //   return res;
  // }

  /**
   * // Get all slots on dates in the month the physician is working. <br>
   * e.g. [Tue, Fri] -> [May-4, May-7, May-11, May-14, May-18, May-21, May-25, May-28]
   *
   * @return list of all slots on the physician's working days, including consultations.
   */
  public ArrayList<Calendar> getWorkingSlots() {
    var resSlots = new ArrayList<Calendar>();
    var date = Calendar.getInstance();
    date.set(2021, Calendar.MAY, 1, 0, 0, 0);

    do {
      var dayOfWeek = date.get(Calendar.DAY_OF_WEEK);

      var isWorking = dayOfWeek == workingDays[0] || dayOfWeek == workingDays[1];
      if (isWorking) {
        var slots = Helper.getSlotsOnGivenDay(date);
        resSlots.addAll(slots);
      }
      date.add(Calendar.DAY_OF_MONTH, 1);

    } while (date.get(Calendar.MONTH) == Calendar.MAY);
    return resSlots;
  }

  public ArrayList<Calendar> getConsultingSlots() {
    var resSlots = new ArrayList<Calendar>();
    var slot = Calendar.getInstance();
    slot.set(2021, Calendar.MAY, 1, 0, 0, 0);

    do {
      var isSameDayOfWeek = this.consultHours[0] == slot.get(Calendar.DAY_OF_WEEK);
      if (isSameDayOfWeek) {
        var consultingHrs = consultHours[1];
        // 30min - 1
        slot.set(Calendar.HOUR_OF_DAY, consultingHrs);
        resSlots.add((Calendar) slot.clone());
        // 30min - 2
        slot.add(Calendar.MINUTE, 30);
        resSlots.add((Calendar) slot.clone());
        // 30min - 3
        slot.add(Calendar.MINUTE, 30);
        resSlots.add((Calendar) slot.clone());
        // 30min - 4
        slot.add(Calendar.MINUTE, 30);
        resSlots.add((Calendar) slot.clone());
      }
      slot.add(Calendar.DAY_OF_MONTH, 1);

    } while (slot.get(Calendar.MONTH) == Calendar.MAY);
    return resSlots;
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
    NEURAL_MOBILISATION("Neural_Mobilisation"),
    CONSULTATION("Consultation");

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
