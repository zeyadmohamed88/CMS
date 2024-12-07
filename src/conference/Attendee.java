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
    private Session currentSession;  // Store the current session the attendee is attending

    // Static variable to track the current attendee
    private static Attendee currentAttendee;

    // Constructor
    public Attendee(String name, String email) {
        this.name = name;
        this.email = email;
        this.schedule = new Schedule();  // Initialize the schedule
        this.attendedSessions = new HashSet<>();  // Initialize the attended sessions set
        this.currentSession = null;  // Initially, no current session
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

    // Set the current session the attendee is attending
    public void setCurrentSession(Session session) {
        this.currentSession = session;
        System.out.println("Current session set to: " + session.getSessionName());
    }

    // Get the current session the attendee is attending
    public Session getCurrentSession() {
        return currentSession;  // Returns the current session
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
        System.out.println("Feedback submitted by " + name + " for session: " + session.getSessionName());
    }

    // Get all sessions an attendee has attended
    public Set<Session> getAttendedSessions() {
        return attendedSessions;  // Return the set of attended sessions
    }

    // Add an attendee to a session's list
    public void addAttendeeToSession(Session session) {
        session.getAttendees();
        System.out.println(name + " has been added to the session: " + session.getSessionName());
    }

    // Static method to set the current attendee (used for tracking logged-in attendee)
    public static void setCurrentAttendee(Attendee attendee) {
        currentAttendee = attendee;
    }

    // Static method to get the current attendee (used for fetching the active logged-in attendee)
    public static Attendee getCurrentAttendee() {
        return currentAttendee;
    }

    @Override
    public String toString() {
        return "Attendee [ID=" + attendeeID + ", Name=" + name + ", Email=" + email + "]";
    }
}
