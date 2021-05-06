package zayne.psic_booking_system.models;

import org.junit.Test;
import zayne.psic_booking_system.utils.DataLoader;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

public class PhysicianTest {

    @Test
    public void retrieveTest() {
        var physicians = Physician.getPhysicians();
        // Test the number of physicians retrieved from JSON file.
        for (var item : physicians) {
            System.out.println(item);
        }
        assertEquals(9, physicians.size());
    }

    @Test
    public void searchByNameTest() {
        var searchResult = Physician.findPhysiciansByName("Fowler");
        for (var item : searchResult) {
            System.out.println(item);
        }
        assertEquals(2, searchResult.size());
    }

    @Test
    public void searchByExpertiseTest() {
        var searchResult = Physician.findPhysiciansByExpertise("Physiotherapy");
        for (var item : searchResult) {
            System.out.println(item);
        }
        assertEquals(2, searchResult.size());
    }

    @Test
    public void getAppointmentByPhysician() {
        var physician = new Physician("Test");
        var patient = new Patient("Test-Patient");
        var time = Calendar.getInstance();

        time.set(2021, Calendar.MAY, 12, 10, 0);
        var application =
                new Appointment(time, physician, patient, Physician.Treatment.MASSAGE).book();

        var actual = physician.getAppointments().size();
        var expected = 1;

        assertEquals(expected, actual);
    }

    @Test
    public void getPhysicians() {
        var p1 = new Physician();
        var p2 = new Physician();

        Physician.addPhysicians(p1);
        Physician.addPhysicians(p2);
        var actual = Physician.getPhysicians().size();
        var expected = 2;

        assertEquals(expected, actual);
    }

    @Test
    public void getAvailablePhysicians() {
        DataLoader.load();
        var date = Calendar.getInstance();
        date.set(2021, Calendar.MAY, 12, 10, 0);
        var treatment = Physician.Treatment.GYM_REHABILITATION;
        var res = Physician.getAvailablePhysicians(date, treatment);

        assertEquals(2, res.size());
    }

    @Test
    public void findPhysiciansByName() {}

    @Test
    public void findPhysiciansByExpertise() {}

    @Test
    public void getPhysicianByTreatment() {}

    @Test
    public void getBookedAppointments() {}

    @Test
    public void getStat() {}

    @Test
    public void testToString() {}
}
