package conference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Session implements Serializable {
    private static final long serialVersionUID = 1L;
    private String sessionID;
    private String sessionName;
    private String sessionDate;
    private String time;
    private String room;
    private Speaker speaker;
    private List<Attendee> attendeesList;  // List to track attendees
    private List<Feedback> feedbackList;  // List to store feedbacks for this session

    // Constructor that includes sessionCount for generating session ID
    public Session(String sessionName, String sessionDate, String time, String room, int sessionCount) {
        this.sessionName = sessionName;
        this.sessionDate = sessionDate;
        this.time = time;
        this.room = room;
        this.attendeesList = new ArrayList<>();  // Initialize the attendees list
        this.feedbackList = new ArrayList<>();  // Initialize the feedback list
        this.sessionID = "S" + (sessionCount + 1); // Generate session ID (e.g., S1, S2, ...)
    }

    // Getters and setters
    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getSessionName() {
        return sessionName;
    }

    public String getSessionDate() {
        return sessionDate;
    }

    public String getTime() {
        return time;
    }

    public String getRoom() {
        return room;
    }

    public Speaker getSpeaker() {
        return speaker;
    }

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }

    // Method to mark attendance for an attendee
    public void markAttendance(Attendee attendee) {
        if (!attendeesList.contains(attendee)) {
            attendeesList.add(attendee);
            System.out.println("Attendance marked for: " + attendee.getName());
        } else {
            System.out.println("Attendee " + attendee.getName() + " is already marked as present.");
        }
    }

    // Method to get the list of attendees
    public List<Attendee> getAttendeesList() {
        return attendeesList;
    }

    // Method to add feedback to this session
    public void addFeedback(Feedback feedback) {
        feedbackList.add(feedback);
    }

    // Method to get the list of feedbacks for this session
    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }

    @Override
    public String toString() {
        return "Session [ID=" + sessionID + ", Name=" + sessionName + ", Date=" + sessionDate + ", Time=" + time + ", Room=" + room + ", Speaker=" + (speaker != null ? speaker.getName() : "None") + "]";
    }
}
