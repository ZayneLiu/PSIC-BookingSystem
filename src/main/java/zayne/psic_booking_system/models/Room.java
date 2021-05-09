package zayne.psic_booking_system.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicReference;

public class Room {
  public static final ArrayList<Room> rooms = new ArrayList<>();
  public static HashSet<String> allRooms = new HashSet<>();

  public String roomName;
  public RoomType roomType;

  public Room(String roomName, RoomType roomType) {
    this.roomName = roomName;
    this.roomType = roomType;
  }

  /**
   * Extract room name for room slot string. ("C_10-12" => "C")
   *
   * @param roomSlot (e.g. "C_10-12")
   * @return Room object
   */
  public static Room getRoomFromSlot(String roomSlot) {
    var room = roomSlot.split("_")[0];
    AtomicReference<Room> result = new AtomicReference<>(null);
    rooms.forEach(
        item -> {
          if (item.roomName.equals(room)) result.set(item);
        });
    return result.get();
  }

  public static Room getRoom(String roomName) {
    AtomicReference<Room> res = new AtomicReference<>();
    rooms.forEach(
        room -> {
          if (room.roomName.equals(roomName)) res.set(room);
        });
    return res.get();
  }

  public enum RoomType {
    GENERAL("General"),
    REHABILITATION("Rehabilitation");

    RoomType(String type) {}
  }

  /**
   * Get all available rooms for specific appointment (time slot, treatment type).
   *
   * @param date The date of specific appointment.
   * @param treatment The treatment the specific appointment is for.
   * @return A list of room available for given date and treatment.
   */
  // public static ArrayList<String> getAvailableRooms(Calendar date, Treatment treatment) {
  //   var result = new ArrayList<String>();
  //
  //   // May-12
  //   var targetDate = "%s-%s".formatted("May", date.get(Calendar.DATE));
  //   // 10-12
  //   var targetTime =
  //       "%s-%s".formatted(date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.HOUR_OF_DAY) + 2);
  //   var targetRoomType =
  //       treatment.name().split("_").length == 1 ? RoomType.GENERAL : RoomType.REHABILITATION;
  //
  //   var generalList = new ArrayList<String>() {}; // A,B,C,D
  //   var rehabList = new ArrayList<String>() {}; // Gym,Pool
  //   for (var room : rooms) {
  //     if (room.roomType == RoomType.GENERAL) generalList.add(room.roomName);
  //     else rehabList.add(room.roomName);
  //   }
  //
  //   allSlots.forEach(
  //       (k, v) -> {
  //         var temp = k.split("_");
  //         var tDate = temp[0];
  //         var tRoom = temp[1];
  //         var tTime = temp[2];
  //         if (targetRoomType == RoomType.GENERAL) {
  //           if (tDate.equals(targetDate)
  //               && targetTime.equals(tTime)
  //               && generalList.contains(tRoom)
  //               && v) result.add("%s_%s".formatted(tRoom, tTime));
  //         } else {
  //           if (tDate.equals(targetDate)
  //               && targetTime.equals(tTime)
  //               && rehabList.contains(tRoom)
  //               && v) result.add("%s_%s".formatted(tRoom, tTime));
  //         }
  //       });
  //   // returns "C_10-12", "B_10-12", etc
  //   return result;
  // }

}
