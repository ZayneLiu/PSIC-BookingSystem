package zayne.psic_booking_system.models;

import zayne.psic_booking_system.models.Physician.Treatment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicReference;

public class Room {
    public static final ArrayList<Room> rooms = new ArrayList<>();
    public static HashMap<String, Boolean> allSlots;
    public static int totalDays = 0;
    // public static HashMap<String, Boolean> consultationSlots = new HashMap<>();
    public static HashSet<String> allRooms = new HashSet<>();

    public String roomName = "";
    public RoomType roomType = RoomType.GENERAL;

    static {
        allSlots = new HashMap<>();
        var start = Calendar.getInstance();
        start.set(2021, Calendar.MAY, 3);
        var end = Calendar.getInstance();
        end.set(2021, Calendar.MAY, 30);
        totalDays = end.get(Calendar.DAY_OF_MONTH) - start.get(Calendar.DAY_OF_MONTH) + 1;

        // Initial Room Data
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

        for (int d = 1; d <= totalDays; d++) {
            // Slots init data.
            // key: Room on specific date and time.
            // value: isAvailable
            // "May-12_A_10-12" : true,
            // "May-12_A_12-14" : true,
            // "May-12_A_14-16" : false,
            // "May-12_A_16-18" : true,
            if (!allRooms.contains(roomName)) {
                // "May-12_A_16-18" : true,
                var date = "%s-%d".formatted("May", d + 2);

                allSlots.put("%s_%s_10-12".formatted(date, roomName), true);
                allSlots.put("%s_%s_12-14".formatted(date, roomName), true);
                allSlots.put("%s_%s_14-16".formatted(date, roomName), true);
                allSlots.put("%s_%s_16-18".formatted(date, roomName), true);
            }
        }
    }

    /**
     * Extract room name for room slot string. ("C_10-12" => "C")
     *
     * @param roomSlot (e.g. "C_10-12")
     * @return Room object
     */
    public static Room getRoom(String roomSlot) {
        var room = roomSlot.split("_")[0];
        AtomicReference<Room> result = new AtomicReference<>(null);
        rooms.forEach(
                item -> {
                    if (item.roomName.equals(room)) result.set(item);
                });
        return result.get();
    }

    /**
     * Get all available rooms for specific appointment (time slot, treatment type).
     *
     * @param date The date of specific appointment.
     * @param treatment The treatment the specific appointment is for.
     * @return A list of room available for given date and treatment.
     */
    public static ArrayList<String> getAvailableRooms(Calendar date, Treatment treatment) {
        var result = new ArrayList<String>();

        // May-12
        var targetDate = "%s-%s".formatted("May", date.get(Calendar.DATE));
        // 10-12
        var targetTime =
                "%s-%s"
                        .formatted(
                                date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.HOUR_OF_DAY) + 2);
        var targetRoomType =
                treatment.name().split("_").length == 1
                        ? RoomType.GENERAL
                        : RoomType.REHABILITATION;

        var generalList = new ArrayList<String>() {}; // A,B,C,D
        var rehabList = new ArrayList<String>() {}; // Gym,Pool
        for (var room : rooms) {
            if (room.roomType == RoomType.GENERAL) generalList.add(room.roomName);
            else rehabList.add(room.roomName);
        }

        allSlots.forEach(
                (k, v) -> {
                    var temp = k.split("_");
                    var tDate = temp[0];
                    var tRoom = temp[1];
                    var tTime = temp[2];
                    if (targetRoomType == RoomType.GENERAL) {
                        if (tDate.equals(targetDate)
                                && targetTime.equals(tTime)
                                && generalList.contains(tRoom)
                                && v) result.add("%s_%s".formatted(tRoom, tTime));
                    } else {
                        if (tDate.equals(targetDate)
                                && targetTime.equals(tTime)
                                && rehabList.contains(tRoom)
                                && v) result.add("%s_%s".formatted(tRoom, tTime));
                    }
                });
        // returns "C_10-12", "B_10-12", etc
        return result;
    }

    public enum RoomType {
        GENERAL("General"),
        REHABILITATION("Rehabilitation");

        RoomType(String type) {}
    }
}
