package conference;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Attendee implements Serializable {
    private static final long serialVersionUID = 1L;
    private String attendeeID;
    private String name;
    private String email;
    private Schedule schedule;
    private Set<Session> attendedSessions;  // Track sessions the attendee has attended

    // Constructor
    public Attendee(String name, String email) {
        this.name = name;
        this.email = email;
        this.schedule = new Schedule();
        this.attendedSessions = new HashSet<>();  // Initialize the attended sessions set
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

    // Method to mark an attendee's attendance for a session
    public void markAttendance(Session session) {
        if (!attendedSessions.contains(session)) {
            attendedSessions.add(session);
            session.markAttendance(this);  // Mark attendance in the session as well
        } else {
            System.out.println("Attendee " + name + " has already attended the session: " + session.getSessionName());
        }
    }

    @Override
    public String toString() {
        return "Attendee [ID=" + attendeeID + ", Name=" + name + ", Email=" + email + "]";
    }
}
