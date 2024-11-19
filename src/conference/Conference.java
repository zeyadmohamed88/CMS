package conference;

import java.util.ArrayList;
import java.util.List;

public class Conference {
    private String conferenceName;
    private String startDate;
    private String endDate;
    private List<Session> listOfSessions;
    private List<Attendee> listOfAttendees;
    private List<Feedback> feedbackList;

    // Constructor
    public Conference(String conferenceName, String startDate, String endDate) {
        this.conferenceName = conferenceName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.listOfSessions = new ArrayList<>();
        this.listOfAttendees = new ArrayList<>();
        this.feedbackList = new ArrayList<>();
    }

    // Register an attendee
    public void registerAttendee(Attendee attendee) {
        attendee.setAttendeeID("A" + (listOfAttendees.size() + 1));
        listOfAttendees.add(attendee);
        System.out.println("Attendee " + attendee.getName() + " registered with ID: " + attendee.getAttendeeID());
    }

    // Open a new session
    public void openNewSession(Session session) {
        if (listOfSessions.contains(session)) {
            System.out.println("Session is already added.");
            return;
        }
        session.setSessionID("S" + (listOfSessions.size() + 1));
        listOfSessions.add(session);
        System.out.println("Session " + session.getSessionName() + " added with ID: " + session.getSessionID());
        notifyAllAttendees("New session added: " + session.getSessionName() + " on " + session.getSessionDate() + " at " + session.getTime());
    }

    // Assign a speaker to a session
    public void assignSpeakerToSession(Speaker speaker, Session session) {
        if (listOfSessions.contains(session)) {
            session.setSpeaker(speaker);
            speaker.addSession(session);
            System.out.println("Speaker " + speaker.getName() + " assigned to session: " + session.getSessionName());
        } else {
            System.out.println("Session not found.");
        }
    }

    // Add a session to an attendee's schedule
    public void addSessionToAttendeeSchedule(Attendee attendee, Session session) {
        if (listOfAttendees.contains(attendee) && listOfSessions.contains(session)) {
            attendee.getSchedule().addSession(session);
            System.out.println("Session " + session.getSessionName() + " added to attendee's schedule: " + attendee.getName());
            notifyAttendee(attendee, "Session " + session.getSessionName() + " has been added to your schedule.");
        } else {
            System.out.println("Attendee or session not found.");
        }
    }

    // Remove a session from an attendee's schedule
    public void removeSessionFromAttendeeSchedule(Attendee attendee, Session session) {
        if (listOfAttendees.contains(attendee)) {
            attendee.getSchedule().removeSession(session);
            System.out.println("Session " + session.getSessionName() + " removed from attendee's schedule: " + attendee.getName());
            notifyAttendee(attendee, "Session " + session.getSessionName() + " has been removed from your schedule.");
        } else {
            System.out.println("Attendee not found.");
        }
    }

    // Collect feedback
    public void collectFeedback(String attendeeID, String comment, int rating) {
        Attendee attendee = searchAttendeeByID(attendeeID);
        if (attendee == null) {
            System.out.println("Invalid attendee ID: " + attendeeID);
            return;
        }
        if (rating < 1 || rating > 5) {
            System.out.println("Invalid rating. Please provide a rating between 1 and 5.");
            return;
        }
        String feedbackID = "F" + (feedbackList.size() + 1);
        Feedback feedback = new Feedback(feedbackID, attendeeID, comment, rating);
        feedbackList.add(feedback);
        System.out.println("Feedback collected: " + feedback);
    }

    // Notify an attendee
    private void notifyAttendee(Attendee attendee, String message) {
        System.out.println("Notification for " + attendee.getName() + ": " + message);
    }

    // Notify all attendees
    private void notifyAllAttendees(String message) {
        for (Attendee attendee : listOfAttendees) {
            notifyAttendee(attendee, message);
        }
    }

    // Search for an attendee by ID
    public Attendee searchAttendeeByID(String attendeeID) {
        for (Attendee attendee : listOfAttendees) {
            if (attendee.getAttendeeID().equals(attendeeID)) {
                return attendee;
            }
        }
        System.out.println("Attendee not found with ID: " + attendeeID);
        return null;
    }

    // Search for a session by ID
    public Session searchSessionByID(String sessionID) {
        for (Session session : listOfSessions) {
            if (session.getSessionID().equals(sessionID)) {
                return session;
            }
        }
        System.out.println("Session not found with ID: " + sessionID);
        return null;
    }

    // Getters for feedback and attendees
    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }

    public List<Attendee> getListOfAttendees() {
        return listOfAttendees;
    }
}
