package zayne.psic_booking_system.models;

import java.util.Calendar;
import java.util.Date;

public class Person {

    public long _id;
    public String name = "";
    public String address = "";
    public String tel = "";

    public Person(String name) {
        this.name = name;
        _id = System.nanoTime();
    }

    public Person() {}
}
