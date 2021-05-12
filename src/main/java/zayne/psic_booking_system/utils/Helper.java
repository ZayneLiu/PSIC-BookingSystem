package zayne.psic_booking_system.utils;

import zayne.psic_booking_system.gui.DataController;
import zayne.psic_booking_system.gui.ReportController;
import zayne.psic_booking_system.models.Appointment;
import zayne.psic_booking_system.models.Patient;
import zayne.psic_booking_system.models.Physician;

import java.util.ArrayList;
import java.util.Calendar;

public class Helper {
  /**
   * Get 4 slots on given day. <br>
   * `May-4` -> [May-4-10:00, May-4-12:00, May-4-14:00, May-4-16:00]
   *
   * @param date given day
   * @return all 4 2hr slots on that day.
   */
  public static ArrayList<Calendar> getSlotsOnGivenDay(Calendar date) {
    var slots = new ArrayList<Calendar>();

    var month = date.get(Calendar.MONTH);
    var dayOfMonth = date.get(Calendar.DAY_OF_MONTH);

    var slot1 = Calendar.getInstance();
    slot1.set(2021, month, dayOfMonth, 10, 0, 0);

    var slot2 = Calendar.getInstance();
    slot2.set(2021, month, dayOfMonth, 12, 0, 0);

    var slot3 = Calendar.getInstance();
    slot3.set(2021, month, dayOfMonth, 14, 0, 0);

    var slot4 = Calendar.getInstance();
    slot4.set(2021, month, dayOfMonth, 16, 0, 0);

    slots.add(slot1);
    slots.add(slot2);
    slots.add(slot3);
    slots.add(slot4);

    return slots;
  }

  public static ArrayList<Appointment> availabilityPipeline(Physician physician, Patient patient) {

    var availableAppointments = physician.getAvailableAppointments();
    // Availability Pipeline:::: Patient's side: no parallel appointments are allowed.
    var parallelAppointments = new ArrayList<Appointment>();
    availableAppointments.forEach(
        appointment -> {
          patient
              .getBookedAppointments()
              .forEach(
                  patientAppointment -> {
                    var isSameDate =
                        isSameDateAndTime(appointment.startTime, patientAppointment.startTime);

                    if (isSameDate && !patientAppointment.isCancelled()) {

                      parallelAppointments.add(appointment);
                    }
                  });
        });
    availableAppointments.removeAll(parallelAppointments);

    return availableAppointments;
  }

  public static boolean isSameDateAndTime(Calendar c1, Calendar c2) {
    var isSameMonth = c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH);
    var isSameDay = c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH);
    var isSameTime = c1.get(Calendar.HOUR_OF_DAY) == c2.get(Calendar.HOUR_OF_DAY);
    var isSameMinute = c1.get(Calendar.MINUTE) == c2.get(Calendar.MINUTE);

    return isSameMonth && isSameDay && isSameTime && isSameMinute;
  }

  public static void refreshData(){
    DataController.controller.refreshData();
    ReportController.controller.refreshData();
  }
}
