package zayne.psic_booking_system.utils;

import java.util.ArrayList;
import java.util.Calendar;

public class Helper {
  public static ArrayList<Calendar> getSlotsOnGivenDay(Calendar date) {
    var slots = new ArrayList<Calendar>();

    var month = date.get(Calendar.MONTH);
    var dayOfMonth = date.get(Calendar.DAY_OF_MONTH);

    var slot1 = Calendar.getInstance();
    slot1.set(2021, month, dayOfMonth, 10, 0, 0);

    var slot2 = Calendar.getInstance();
    slot2.set(2021, month, dayOfMonth, 12, 0, 0);

    var slot3 = Calendar.getInstance();
    slot3.set(2021, month, dayOfMonth, 14, 0, 0);

    var slot4 = Calendar.getInstance();
    slot4.set(2021, month, dayOfMonth, 16, 0, 0);

    slots.add(slot1);
    slots.add(slot2);
    slots.add(slot3);
    slots.add(slot4);

    return slots;
  }
}
