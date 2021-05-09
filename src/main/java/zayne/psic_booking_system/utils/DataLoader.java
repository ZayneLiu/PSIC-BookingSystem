package zayne.psic_booking_system.utils;

import zayne.psic_booking_system.models.Patient;
import zayne.psic_booking_system.models.Physician;
import zayne.psic_booking_system.models.Room;

import java.util.Calendar;

public class DataLoader {
  public static void load() {
    loadRooms();
    loadPhysicians();
    loadPatients();
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

  public static void loadPatients() {
    var patient1 = new Patient("Wyatt");
    patient1._id = Long.parseLong("1616279483532");
    patient1.tel = "+4407994510230";
    patient1.address = "367 Rose Street, Oceola, Palau, 6581";

    var patient2 = new Patient("Mccall");
    patient2._id = Long.parseLong("1618882363126");
    patient2.tel = "+4407805598265";
    patient2.address = "374 Emmons Avenue, Lookingglass, Iowa, 1975";

    var patient3 = new Patient("Moran");
    patient3._id = Long.parseLong("1618992428864");
    patient3.tel = "+4407806536269";
    patient3.address = "151 Raleigh Place, Terlingua, New Jersey, 4082";

    var patient4 = new Patient("Oberlin");
    patient4._id = Long.parseLong("1616926842032");
    patient4.tel = "+4407860563218";
    patient4.address = "158 Boerum Place, Boomer, West Virginia, 4721";

    var patient5 = new Patient("Whitley");
    patient5._id = Long.parseLong("1618858295823");
    patient5.tel = "+4407815492228";
    patient5.address = "406 Mermaid Avenue, Advance, Puerto Rico, 1016";

    var patient6 = new Patient("Walsh");
    patient6._id = Long.parseLong("1618902206548");
    patient6.tel = "+4407926533355";
    patient6.address = "388 Grove Place, Haring, Mississippi, 1561";

    var patient7 = new Patient("Beasley");
    patient7._id = Long.parseLong("1617745403725");
    patient7.tel = "+4407831592222";
    patient7.address = "733 Beekman Place, Edgewater, Hawaii, 5044";

    var patient8 = new Patient("Myers");
    patient8._id = Long.parseLong("1618457252698");
    patient8.tel = "+4407812563365";
    patient8.address = "783 Grand Street, Gila, Louisiana, 9270";

    var patient9 = new Patient("Kirk");
    patient9._id = Long.parseLong("1617438801990");
    patient9.tel = "+4407838591229";
    patient9.address = "241 Bradford Street, Rockhill, Maine, 7175";

    var patient10 = new Patient("Santiago");
    patient10._id = Long.parseLong("1617648945020");
    patient10.tel = "+4407821500399";
    patient10.address = "546 Wolcott Street, Buxton, New York, 5765";

    var patient11 = new Patient("Elvia");
    patient11._id = Long.parseLong("1618230477177");
    patient11.tel = "+4407852438372";
    patient11.address = "243 Last Street, Birmingham, 4545";

    var patient12 = new Patient("Gem");
    patient12._id = Long.parseLong("1618230472217");
    patient12.tel = "+4407852438243";
    patient12.address = "10 College Lane, Herts, 3546";

    var patient13 = new Patient("Shar");
    patient13._id = Long.parseLong("1618212237177");
    patient13.tel = "+4407833128372";
    patient13.address = "876 Last Street, Birmingham, 4545";

    var patient14 = new Patient("Eliza");
    patient14._id = Long.parseLong("1611213477177");
    patient14.tel = "+4407852438372";
    patient14.address = "277 Last Street, Birmingham, 4545";

    var patient15 = new Patient("Cathy");
    patient15._id = Long.parseLong("1611133472227");
    patient15.tel = "+4407852438111";
    patient15.address = "354 Last Street, Birmingham, 4545";

    Patient.getPatients().add(patient1);
    Patient.getPatients().add(patient2);
    Patient.getPatients().add(patient3);
    Patient.getPatients().add(patient4);
    Patient.getPatients().add(patient5);
    Patient.getPatients().add(patient6);
    Patient.getPatients().add(patient7);
    Patient.getPatients().add(patient8);
    Patient.getPatients().add(patient9);
    Patient.getPatients().add(patient10);
    Patient.getPatients().add(patient11);
    Patient.getPatients().add(patient12);
    Patient.getPatients().add(patient13);
    Patient.getPatients().add(patient14);
    Patient.getPatients().add(patient15);
  }
}
