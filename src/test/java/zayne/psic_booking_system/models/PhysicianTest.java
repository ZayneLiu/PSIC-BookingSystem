package zayne.psic_booking_system.models;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

public class PhysicianTest {

  @Before
  public void clearPhysicians() {
    Physician.getPhysicians().clear();
  }

  @Test
  public void searchByNameTest() {
    Physician.addPhysician(new Physician("Fowler"));

    var searchResult = Physician.getPhysiciansByName("Fowler");

    assertEquals(1, searchResult.size());
  }

  @Test
  public void searchByExpertiseTest() {
    var p = new Physician("test");
    p.expertise.add(Physician.Expertise.PHYSIOTHERAPY);

    Physician.addPhysician(p);

    var searchResult = Physician.getPhysiciansByExpertise(Physician.Expertise.PHYSIOTHERAPY.name());
    for (var item : searchResult) {
      System.out.println(item);
    }
    assertEquals(1, searchResult.size());
  }

  @Test
  public void getAppointmentByPhysician() {
    var physician = new Physician("Test");
    var patient = new Patient("Test-Patient");
    var time = Calendar.getInstance();

    time.set(2021, Calendar.MAY, 12, 10, 0, 0);
    var application = new Appointment(time, physician, patient, Physician.Treatment.MASSAGE).book();

    var actual = physician.getBookedAppointments().size();
    var expected = 1;

    assertEquals(expected, actual);
  }

  @Test
  public void getPhysicians() {
    var p1 = new Physician();
    var p2 = new Physician();

    Physician.addPhysician(p1);
    Physician.addPhysician(p2);
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
  public void testToString() {

    var physician1 = new Physician("Betsy Hopper");
    physician1._id = Long.parseLong("1615503120508");

    var actual = physician1.toString();

    assertNotEquals("", actual);
  }

  @Test
  public void getConsultingSlots() {
    var physician1 = new Physician("Betsy Hopper");
    physician1._id = Long.parseLong("1615503120508");

    physician1.workingDays[0] = Calendar.FRIDAY;
    physician1.workingDays[1] = Calendar.WEDNESDAY;
    physician1.consultHours[0] = Calendar.FRIDAY;
    physician1.consultHours[1] = 14;

    var expected = 16;
    var actual = physician1.getConsultingSlots().size();

    assertEquals(expected, actual);
  }
}
