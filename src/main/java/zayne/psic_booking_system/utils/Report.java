package zayne.psic_booking_system.utils;

import zayne.psic_booking_system.models.Appointment;
import zayne.psic_booking_system.models.Patient;
import zayne.psic_booking_system.models.Physician;

import java.util.ArrayList;
import java.util.Calendar;

public class Report {

  public static ArrayList<Appointment> getReportAllAppointments() {
    var time = Calendar.getInstance();
    time.set(2021, Calendar.MAY, 11, 10, 0);
    var app =
        new Appointment(
            time,
            Physician.getPhysicians().get(0),
            Patient.getPatients().get(0),
            Physician.Treatment.MASSAGE);

    app.book();
    for (var item : Appointment.appointments) {
      System.out.println(item);
    }
    return new ArrayList<>();
  }
}
