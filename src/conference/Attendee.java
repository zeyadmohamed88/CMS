package conference;

import java.io.Serializable;

public class Attendee implements Serializable {
    private static final long serialVersionUID = 1L; // Add this for version control
    private String attendeeID;
    private String name;
    private String email;
    private Schedule schedule;

    // Constructor
    public Attendee(String name, String email) {
        this.name = name;
        this.email = email;
        this.schedule = new Schedule();
    }

    // Getters and setters
    public String getAttendeeID() {
        return attendeeID;
    }

    public void setAttendeeID(String attendeeID) {
        this.attendeeID = attendeeID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
    public Schedule getSchedule() {
        return schedule; // Returns the personalized schedule
    }

    @Override
    public String toString() {
        return "Attendee [ID=" + attendeeID + ", Name=" + name + ", Email=" + email + "]";
    }
}
