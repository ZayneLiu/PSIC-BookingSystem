package zayne.psic_booking_system;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PhysicianTest {

    @Test
    public void retrieve() {
        var physicians = Physician.GetPhysicians();
        // Test the number of physicians retrieved from JSON file.
        for (var item : physicians) {
            System.out.println(item);
        }
        assertEquals(9, physicians.size());

    }

    @Test
    public void searchByName() {
        var searchResult = Physician.FindPhysiciansByName("Fowler");
        for (var item : searchResult) {
            System.out.println(item);
        }
        assertEquals(2, searchResult.size());
    }

    @Test
    public void searchByExpertise() {
        var searchResult = Physician.FindPhysiciansByExpertise("Physiotherapy");
        for (var item : searchResult) {
            System.out.println(item);
        }
        assertEquals(2, searchResult.size());
    }
}
