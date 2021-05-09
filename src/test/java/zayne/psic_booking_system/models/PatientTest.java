package zayne.psic_booking_system.models;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PatientTest {
    public static Patient patient = new Patient("Test", "123456789", "Somewhere");

    @Test
    public void initTest() {
        var p1 = new Patient("Test", "123456789", "Somewhere");
        System.out.println(p1);
        var p2 = new Patient("Test2", "123456789", "Somewhere else");
        System.out.println(p2);
    }

    @Test
    public void bookAppointmentTest() {
        var physician = new Physician("TestPhysician");
        var start = Calendar.getInstance();
        start.set(2021, Calendar.MAY, 5, 10, 0);
        var res = patient.bookAppointment(start, physician, Physician.Treatment.MASSAGE);
        assertEquals("Appointment booking failed", res.physician._id, physician._id);
    }
}
