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
    private List<Speaker> listOfSpeakers;


    // Constructor
    public Conference(String conferenceName, String startDate, String endDate) {
        this.conferenceName = conferenceName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.listOfAttendees = new ArrayList<>();
        this.feedbackList = new ArrayList<>();
        this.listOfSpeakers = new ArrayList<>();
        this.listOfSessions = new ArrayList<>();

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
        session.setSpeaker(speaker);
        speaker.addSession(session);
        System.out.println(speaker.getName() + " assigned to session: " + session.getSessionName());
    }


    public void addSpeaker(Speaker speaker) {
        listOfSpeakers.add(speaker);
    }

    // List all speakers and their sessions
    public void listSpeakersAndSessions() {
        for (Speaker speaker : listOfSpeakers) {
            System.out.println(speaker);
            System.out.println("Sessions:");
            for (Session session : speaker.getSessions()) {
                System.out.println(" - " + session.getSessionName() + " on " + session.getSessionDate() + " at " + session.getTime());
            }
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
    public void collectFeedback(String attendeeID, String sessionID, String comment, int rating) {
        // Check if the rating is valid (1-5)
        if (rating < 1 || rating > 5) {
            System.out.println("Invalid rating. Please provide a rating between 1 and 5.");
            return;
        }

        // Create a unique feedback ID
        String feedbackID = "F" + (feedbackList.size() + 1);

        // Create a new feedback object (Now including the comment)
        Feedback feedback = new Feedback(feedbackID, attendeeID, sessionID, comment, rating);

        // Add feedback to the list
        feedbackList.add(feedback);

        System.out.println("Feedback collected: " + feedback);
    }


    // Get all feedbacks for a particular session
    public List<Feedback> getFeedbackForSession(String sessionID) {
        List<Feedback> sessionFeedback = new ArrayList<>();
        for (Feedback feedback : feedbackList) {
            if (feedback.getSessionID().equals(sessionID)) {  // Correctly check sessionID
                sessionFeedback.add(feedback);
            }
        }
        return sessionFeedback;
    }


    public double calculateAverageRatingForSession(String sessionID) {
        List<Feedback> sessionFeedback = getFeedbackForSession(sessionID);
        int totalRating = 0;
        for (Feedback feedback : sessionFeedback) {
            totalRating += feedback.getRating();
        }
        return sessionFeedback.isEmpty() ? 0 : (double) totalRating / sessionFeedback.size();
    }

    // Get all feedbacks for a particular attendee
    public List<Feedback> getFeedbackForAttendee(String attendeeID) {
        List<Feedback> attendeeFeedback = new ArrayList<>();
        for (Feedback feedback : feedbackList) {
            if (feedback.getAttendeeID().equals(attendeeID)) {  // Match feedback with the attendee ID
                attendeeFeedback.add(feedback);
            }
        }
        return attendeeFeedback;
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

    public void markAttendanceForSession(Session session) {
        for (Attendee attendee : listOfAttendees) {
            if (attendee.getSchedule().getSessionsList().contains(session)) {
                attendee.markAttendance(session);
            }
        }
    }

    // Generate certificates for all attendees
    public void generateCertificates() {
        System.out.println("Generating certificates...");

        for (Attendee attendee : listOfAttendees) {
            if (!attendee.getSchedule().getSessionsList().isEmpty()) {  // Check if the attendee has attended any sessions
                Certificate certificate = new Certificate();
                certificate.setCertificateID("C" + (listOfAttendees.indexOf(attendee) + 1));
                certificate.setAttendeeID(attendee.getAttendeeID());
                certificate.setConferenceName(this.conferenceName);
                certificate.setIssueDate(this.endDate); // Using the end date as issue date
                certificate.generateCertificate();  // Generate the certificate
            }
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
