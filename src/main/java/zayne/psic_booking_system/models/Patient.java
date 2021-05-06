package zayne.psic_booking_system.models;

import zayne.psic_booking_system.models.Physician.Treatment;

import java.util.ArrayList;
import java.util.Calendar;

public class Patient extends Person {
    // Visitor account
    // public static Patient visitorAccount = new Patient("Visitor");
    private static final ArrayList<Patient> patients = new ArrayList<>();

    static {
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

        patients.add(patient1);
        patients.add(patient2);
        patients.add(patient3);
        patients.add(patient4);
        patients.add(patient5);
        patients.add(patient6);
        patients.add(patient7);
        patients.add(patient8);
        patients.add(patient9);
        patients.add(patient10);
        patients.add(patient11);
        patients.add(patient12);
        patients.add(patient13);
        patients.add(patient14);
        patients.add(patient15);
    }

    Patient(String name) {
        super(name);
    }

    public Patient(String name, String tel, String address) {
        super(name);
        this.tel = tel;
        this.address = address;
    }

    public static Patient findPatient(String name) {
        // DONE: find specific patient by name.
        Patient result = null;
        for (Patient patient : patients) {
            if (patient.name.toLowerCase().contains(name.toLowerCase())) result = patient;
        }
        return result;
    }

    /**
     * (Registration) Check for existing patient, if no duplicates then add the patient.
     *
     * @param patient
     * @return whether it is successful.
     */
    public static boolean registerPatient(Patient patient) {
        var ref =
                new Object() {
                    boolean duplicated = false;
                };

        patients.forEach(
                p -> {
                    if (p._id == patient._id) ref.duplicated = true;
                });

        if (ref.duplicated) return false;
        patients.add(patient);
        return true;
    }

    public static ArrayList<Patient> getPatients() {
        return patients;
    }

    public Appointment bookAppointment(Calendar start, Physician physician, Treatment treatment) {
        var appointment = new Appointment(start, physician, this, treatment).book();
        return appointment;
    }

    public void attendAppointment(Appointment appointment) {
        appointment.attend();
    }

    public void cancelAppointment(Appointment appointment) {
        appointment.cancel();
    }

    public void missAppointment(Appointment appointment) {
        appointment.miss();
    }

    public String getStat() {
        var res = "";
        res +=
                "Name:\t%s\nID:\t%s\nTel:\t%s\nAddr:\t%s"
                        .formatted(this.name, this._id, this.tel, this.address);
        return res;
    }
}
