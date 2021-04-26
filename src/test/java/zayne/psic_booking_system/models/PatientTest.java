package zayne.psic_booking_system.models;

import org.junit.Test;

import java.util.Calendar;

public class PatientTest {
    public static Patient patient = new Patient("Test", "123456789", "Somewhere");

    @Test
    public void initTest() throws InterruptedException {
        var p1 = new Patient("Test", "123456789", "Somewhere");
        System.out.println(p1);
        Thread.sleep(500);
        var p2 = new Patient("Test2", "123456789", "Somewhere else");
        System.out.println(p2);
    }

    @Test
    public void bookAppointmentTest() {
        var physician = new Physician("TestPhysician");
        var start = Calendar.getInstance();
        start.set(2021, Calendar.MAY, 1, 10, 0);
        patient.bookAppointment(start, physician, Physician.Treatment.MASSAGE);
    }
}
