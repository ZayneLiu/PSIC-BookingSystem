package zayne.psic_booking_system.models;

import org.junit.Test;

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
    public void getAppointmentTest() {
        var searchResult = Physician.findPhysiciansByName("Fowler");
    }
}
