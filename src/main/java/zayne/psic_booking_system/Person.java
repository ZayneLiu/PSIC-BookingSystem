package zayne.psic_booking_system;

import java.util.Date;

public abstract class Person {

    public long _id = new Date().getTime();
    public String name = "";
    public String address = "";
    public String tel = "";

    Person(String name) {
        this.name = name;
    }

    Person() {

    }
}
