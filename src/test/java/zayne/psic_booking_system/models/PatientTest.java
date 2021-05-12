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
  public void getPatient() {
    Patient.getPatients().add(patient);

    var expected = 1;
    var actual = Patient.getPatients().size();

    assertEquals(expected, actual);
  }
}
