package zayne.psic_booking_system.models;

import zayne.psic_booking_system.models.Physician.Treatment;

import java.util.ArrayList;

public class Visitor extends Person {
  public Visitor(String name) {
    super(name);
  }

  public Appointment bookAppointment(Appointment appointment) {
    appointment.patient = null;
    appointment.notes = "Visitor: %s".formatted(this.name);
    appointment.treatment = Treatment.CONSULTATION;

    return appointment.book();
  }

  public static ArrayList<Appointment> getVisitorAppointments() {
    var res = new ArrayList<Appointment>();
    Appointment.getAppointments()
        .forEach(
            appointment -> {
              if (appointment.treatment == Treatment.CONSULTATION || appointment.patient == null) {
                res.add(appointment);
              }
            });
    return res;
  }
}
