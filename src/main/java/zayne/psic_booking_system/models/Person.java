package zayne.psic_booking_system.models;

import java.util.Calendar;

public class Person {

    public long _id;
    public String name = "";
    public String address = "";
    public String tel = "";

    public Person(String name) {
        this.name = name;
        // _id = System.nanoTime();
        _id = Calendar.getInstance().getTimeInMillis();
    }

    public Person() {}
}
