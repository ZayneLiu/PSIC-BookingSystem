package zayne.psic_booking_system.models;

import zayne.psic_booking_system.models.Physician.Treatment;

import java.util.ArrayList;
import java.util.Calendar;

public class Patient extends Person {
  // Visitor account
  // public static Patient visitorAccount = new Patient("Visitor");
  private static final ArrayList<Patient> patients = new ArrayList<>();

  public Patient(String name) {
    super(name);
  }

  public Patient(String name, String tel, String address) {
    super(name);
    this.tel = tel;
    this.address = address;
  }

  public static Patient getPatient(String name) {
    // DONE: find specific patient by name.
    Patient result = null;
    for (Patient patient : patients) {
      if (patient.name.toLowerCase().contains(name.toLowerCase())) result = patient;
    }
    return result;
  }

  /**
   * (Registration) Check for existing patient, if no duplicates then add the patient.
   *
   * @param patient the new patient to be registered.
   * @return whether it is successful.
   */
  public static boolean registerPatient(Patient patient) {
    var ref =
        new Object() {
          boolean duplicated = false;
        };

    patients.forEach(
        p -> {
          if (p._id == patient._id) ref.duplicated = true;
        });

    if (ref.duplicated) return false;
    patients.add(patient);
    return true;
  }

  public static ArrayList<Patient> getPatients() {
    return patients;
  }

  public Appointment bookAppointment(Calendar start, Physician physician, Treatment treatment) {
    var appointment = new Appointment(start, physician, this, treatment).book();
    return appointment;
  }

  public void attendAppointment(Appointment appointment) {
    appointment.attend();
  }

  public void cancelAppointment(Appointment appointment) {
    appointment.cancel();
  }

  public void missAppointment(Appointment appointment) {
    appointment.miss();
  }

  public String getStat() {
    var res = "";
    res +=
        "Name:\t%s\nID:\t\t%s\nTel:\t\t%s\nAddr:\t%s"
            .formatted(this.name, this._id, this.tel, this.address);
    return res;
  }
}
