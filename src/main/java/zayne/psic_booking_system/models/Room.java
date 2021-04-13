package zayne.psic_booking_system.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static zayne.psic_booking_system.models.Physician.WORKING_HRS_START;

public class Room {
    public static final ArrayList<Room> rooms = new ArrayList<>();
    public static HashMap<String, Boolean> allSlots = new HashMap<>();
    public static ArrayList<String> bookedSlots = new ArrayList<>();
    public String roomName = "";
    public RoomType roomType = RoomType.GENERAL;

    static {

        // Room Data
        rooms.add(new Room("A", RoomType.GENERAL));
        rooms.add(new Room("B", RoomType.GENERAL));
        rooms.add(new Room("C", RoomType.GENERAL));
        rooms.add(new Room("D", RoomType.GENERAL));
        rooms.add(new Room("Gym", RoomType.REHABILITATION));
        rooms.add(new Room("Pool", RoomType.REHABILITATION));
    }

    public Room(String roomName, RoomType roomType) {
        this.roomName = roomName;
        this.roomType = roomType;

        var start = Calendar.getInstance();
        start.set(2021, Calendar.MAY, 3);
        var end = Calendar.getInstance();
        end.set(2021, Calendar.MAY, 30);

        var days = end.get(Calendar.DAY_OF_MONTH) - start.get(Calendar.DAY_OF_MONTH) + 1;

        for (int d = 1; d <= days; d++) {
            // Slots init data.
            // key: Room on specific date and time.
            // value: isBooked
            allSlots.put("%s-%d-%s:1".formatted("May", d + 2, roomName), false);
            allSlots.put("%s-%d-%s:2".formatted("May", d + 2, roomName), false);
            allSlots.put("%s-%d-%s:3".formatted("May", d + 2, roomName), false);
            allSlots.put("%s-%d-%s:4".formatted("May", d + 2, roomName), false);
        }
    }

    public static void book(String roomName, int date, int startTime) {
        var slotNo = (startTime - WORKING_HRS_START) / 2 + 1;
        var month = "May";
        // Example key in hashmap `allSlots`
        /* `May-10-A:1` */
        var key = "%s-%d-%s:%d".formatted(month, date, roomName, slotNo);
        var isBooked = allSlots.get(key);
        if (!isBooked) {
            allSlots.put(key, true);
            bookedSlots.add(key);
        }
    }

    public static ArrayList<Room> getAvailableRooms(Appointment appointment) {
        var result = new ArrayList<Room>();

        var roomType =
                appointment.treatment.toString().split("_").length == 1
                        ? RoomType.GENERAL
                        : RoomType.REHABILITATION;
        // TODO: return available rooms (treatment, date, time, not booked)

        /* `May-10-A:1` */
        for (var slot : allSlots.entrySet()) {

            if (slot.getValue() == false) {
                // if ()
            }
        }

        // if () for (var room : rooms) {}

        return result;
    }

    public enum RoomType {
        GENERAL("General"),
        REHABILITATION("Rehabilitation");

        RoomType(String type) {}
    }
}
