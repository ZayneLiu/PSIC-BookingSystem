package zayne.psic_booking_system.utils;

import zayne.psic_booking_system.models.Physician;
import zayne.psic_booking_system.models.Room;

import java.util.Calendar;

public class DataLoader {
  public static void load() {
    loadRooms();
    loadPhysicians();
  }

  public static void loadPhysicians() {
    var physician1 = new Physician("Betsy Hopper");
    physician1._id = Long.parseLong("1615503120508");
    physician1.tel = "+4407866442335";
    physician1.address = "511 Hawthorne Street, Duryea, New Hampshire, 5065";
    physician1.expertise.add(Physician.Expertise.OSTEOPATHY);
    physician1.treatment.add(Physician.Treatment.SPINE_JOINTS_MOBILISATION);
    physician1.treatment.add(Physician.Treatment.NEURAL_MOBILISATION);
    physician1.room = Room.getRoom("A");
    physician1.workingDays[0] = Calendar.FRIDAY;
    physician1.workingDays[1] = Calendar.WEDNESDAY;
    physician1.consultHours[0] = Calendar.FRIDAY;
    physician1.consultHours[1] = 14;

    var physician2 = new Physician("Santiago Romero");
    physician2._id = Long.parseLong("1614348032344");
    physician2.tel = "+4407845585328";
    physician2.address = "696 Coleman Street, Ahwahnee, Guam, 1622";
    physician2.expertise.add(Physician.Expertise.PHYSIOTHERAPY);
    physician2.treatment.add(Physician.Treatment.ACUPUNCTURE);
    physician2.treatment.add(Physician.Treatment.MASSAGE);
    physician2.room = Room.getRoom("B");
    // physician2.expertise.add(Physician.Expertise.REHABILITATION);
    // physician2.treatment.add(Physician.Treatment.GYM_REHABILITATION);
    // physician2.treatment.add(Physician.Treatment.POOL_REHABILITATION);
    physician2.workingDays[0] = Calendar.TUESDAY;
    physician2.workingDays[1] = Calendar.THURSDAY;
    physician2.consultHours[0] = Calendar.TUESDAY;
    physician2.consultHours[1] = 10;

    var physician3 = new Physician("Miranda Espinoza");
    physician3._id = Long.parseLong("1614448459669");
    physician3.tel = "+4407882552388";
    physician3.address = "800 Bowne Street, Haring, Hawaii, 523";
    physician3.expertise.add(Physician.Expertise.PHYSIOTHERAPY);
    physician3.treatment.add(Physician.Treatment.MASSAGE);
    physician3.treatment.add(Physician.Treatment.ACUPUNCTURE);
    physician3.room = Room.getRoom("C");
    // physician3.expertise.add(Physician.Expertise.REHABILITATION);
    // physician3.treatment.add(Physician.Treatment.GYM_REHABILITATION);
    // physician3.treatment.add(Physician.Treatment.POOL_REHABILITATION);
    physician3.workingDays[0] = Calendar.WEDNESDAY;
    physician3.workingDays[1] = Calendar.MONDAY;
    physician3.consultHours[0] = Calendar.MONDAY;
    physician3.consultHours[1] = 16;

    var physician4 = new Physician("Keller Burks");
    physician4._id = Long.parseLong("1614665107626");
    physician4.tel = "+4407952458363";
    physician4.address = "426 Jackson Court, Vandiver, Montana, 2163";
    physician4.expertise.add(Physician.Expertise.REHABILITATION);
    physician4.treatment.add(Physician.Treatment.POOL_REHABILITATION);
    physician4.room = Room.getRoom("Pool");
    // physician4.treatment.add(Physician.Treatment.GYM_REHABILITATION);
    physician4.workingDays[0] = Calendar.THURSDAY;
    physician4.workingDays[1] = Calendar.MONDAY;
    physician4.consultHours[0] = Calendar.THURSDAY;
    physician4.consultHours[1] = 14;

    var physician5 = new Physician("Jewell Burgess");
    physician5._id = Long.parseLong("1614559905127");
    physician5.tel = "+4407928560227";
    physician5.address = "279 Evergreen Avenue, Ventress, Rhode Island, 2918";
    physician5.expertise.add(Physician.Expertise.REHABILITATION);
    physician5.treatment.add(Physician.Treatment.GYM_REHABILITATION);
    physician5.room = Room.getRoom("Gym");
    // physician5.treatment.add(Physician.Treatment.POOL_REHABILITATION);
    physician5.workingDays[0] = Calendar.WEDNESDAY;
    physician5.workingDays[1] = Calendar.TUESDAY;
    physician5.consultHours[0] = Calendar.WEDNESDAY;
    physician5.consultHours[1] = 12;

    Physician.addPhysicians((physician1));
    Physician.addPhysicians((physician2));
    Physician.addPhysicians((physician3));
    Physician.addPhysicians((physician4));
    Physician.addPhysicians((physician5));
  }

  public static void loadRooms() {
    // Initial Room Data

    Room.rooms.add(new Room("A", Room.RoomType.GENERAL));
    Room.rooms.add(new Room("B", Room.RoomType.GENERAL));
    Room.rooms.add(new Room("C", Room.RoomType.GENERAL));
    Room.rooms.add(new Room("Gym", Room.RoomType.REHABILITATION));
    Room.rooms.add(new Room("Pool", Room.RoomType.REHABILITATION));
    // Room.rooms.add(new Room("D", Room.RoomType.GENERAL));
  }
}
