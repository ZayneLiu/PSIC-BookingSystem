package zayne.psic_booking_system.models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AppointmentTest {

  @Before
  public void clearAppointments() {
    Appointment.appointments.clear();
  }

  @Test
  public void getAppointments() {
    var appointment = new Appointment();

    Appointment.appointments.add(appointment);

    var expected = 1;
    var actual = Appointment.getAppointments().size();

    assertEquals(expected, actual);
  }

  @Test
  public void getBookedAppointmentsForPhysician() {
    // Gets all appointments that are booked by patient
    // !!!Appointment state does not matter!!!.
    var physician = new Physician("test");

    var appointment = new Appointment();
    appointment.physician = physician;
    appointment.state = Appointment.Appointment_State.BOOKED;

    var appointment1 = new Appointment();
    appointment1.state = Appointment.Appointment_State.CANCELLED;
    appointment1.physician = physician;

    Appointment.appointments.add(appointment);
    Appointment.appointments.add(appointment1);

    var expected = 2;
    var actual = Appointment.getBookedAppointmentsForPhysician(physician).size();

    assertEquals(expected, actual);
  }

  @Test
  public void getAppointmentById() {
    var appointment = new Appointment();

    Appointment.appointments.add(appointment);

    var id = appointment._id;

    var expected = appointment._id;
    var actual = Appointment.getAppointmentById(String.valueOf(id))._id;

    assertEquals(expected, actual);
  }

  @Test
  public void getConsultations() {
    var appointment = new Appointment();
    appointment.patient = null;

    Appointment.appointments.add(appointment);

    var expected = 1;
    var actual = Appointment.getConsultations().size();

    assertEquals(expected, actual);
  }

  @Test
  public void isCancelled() {
    var appointment = new Appointment();
    appointment.state = Appointment.Appointment_State.CANCELLED;

    var expected = true;
    var actual = appointment.isCancelled();

    assertEquals(expected, actual);
  }
}
