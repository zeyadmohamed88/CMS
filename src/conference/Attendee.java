package conference;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Attendee implements Serializable {
    private static final long serialVersionUID = 1L;
    private String attendeeID;
    private String name;
    private String email;
    private Schedule schedule;  // Stores the personalized schedule of the attendee
    private Set<Session> attendedSessions;  // Track sessions the attendee has attended

    // Constructor
    public Attendee(String name, String email) {
        this.name = name;
        this.email = email;
        this.schedule = new Schedule();  // Initialize the schedule
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
            System.out.println("Attendance marked for " + name + " in session: " + session.getSessionName());
        } else {
            System.out.println("Attendee " + name + " has already attended the session: " + session.getSessionName());
        }
    }

    // Add a session to the attendee's schedule
    public void addSessionToSchedule(Session session) {
        schedule.addSession(session);
        System.out.println("Session " + session.getSessionName() + " added to " + name + "'s schedule.");
    }

    // Remove a session from the attendee's schedule
    public void removeSessionFromSchedule(Session session) {
        schedule.removeSession(session);
        System.out.println("Session " + session.getSessionName() + " removed from " + name + "'s schedule.");
    }

    // Display sessions in the attendee's schedule
    public void displaySchedule() {
        System.out.println(name + "'s Schedule: " + schedule);
    }

    // Method to submit feedback for a session
    public void submitFeedback(Session session, String comment, int rating) {
        // Generate feedback ID based on the current feedback list size
        String feedbackID = "F" + (session.getFeedbackList().size() + 1);
        Feedback feedback = new Feedback(feedbackID, this.getAttendeeID(), session.getSessionID(), comment, rating);
        session.addFeedback(feedback);
    }

    @Override
    public String toString() {
        return "Attendee [ID=" + attendeeID + ", Name=" + name + ", Email=" + email + "]";
    }
}
