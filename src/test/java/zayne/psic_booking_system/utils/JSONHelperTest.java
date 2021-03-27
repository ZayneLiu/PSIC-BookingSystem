package zayne.psic_booking_system.utils;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;

import org.json.JSONException;
import org.junit.Test;

public class JSONHelperTest {
    @Test
    public void readJSON() throws IllegalArgumentException, IllegalAccessException, JSONException,
            FileNotFoundException, NoSuchFieldException, SecurityException {
        var physicians = JSONHelper.getPhysicians();

        assertEquals(9, physicians.size());
    }
}
