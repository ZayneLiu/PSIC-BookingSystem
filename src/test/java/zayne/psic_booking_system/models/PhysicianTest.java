package zayne.psic_booking_system.models;

import org.junit.Test;

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
        var searchResult = Physician.getPhysiciansByName("Fowler");
        for (var item : searchResult) {
            System.out.println(item);
        }
        assertEquals(2, searchResult.size());
    }

    @Test
    public void searchByExpertiseTest() {
        var searchResult = Physician.getPhysiciansByExpertise("Physiotherapy");
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

        time.set(2021, Calendar.MAY, 12, 10, 0,0);
        var application =
                new Appointment(time, physician, patient, Physician.Treatment.MASSAGE).book();

        var actual = physician.getBookedAppointments().size();
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
        // DataLoader.load();
        // var date = Calendar.getInstance();
        // date.set(2021, Calendar.MAY, 12, 10, 0);
        // var treatment = Physician.Treatment.GYM_REHABILITATION;
        // var res = Physician.getAvailablePhysicians(date, treatment);

        // assertEquals(2, res.size());
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

  @Test
  public void getConsultingSlots() {
      var physician1 = new Physician("Betsy Hopper");
      physician1._id = Long.parseLong("1615503120508");

      physician1.workingDays[0] = Calendar.FRIDAY;
      physician1.workingDays[1] = Calendar.WEDNESDAY;
      physician1.consultHours[0] = Calendar.FRIDAY;
      physician1.consultHours[1] = 14;

      physician1.getConsultingSlots();
  }
}
